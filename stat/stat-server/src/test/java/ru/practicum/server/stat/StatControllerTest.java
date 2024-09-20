package ru.practicum.server.stat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.dto.StatDto;
import ru.practicum.server.controller.StatController;
import ru.practicum.server.service.StatService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class StatControllerTest {
    private final ObjectMapper mapper = new ObjectMapper();
    @Mock
    private StatService service;
    @InjectMocks
    private StatController controller;
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    void testGet() throws Exception {
        Collection<StatDto> result = new ArrayList<>();
        result.add(new StatDto("app", "/", 66L));
        result.add(new StatDto("app", "/price", 11L));
        result.add(new StatDto("app", "/news", 12L));
        when(service.get(any(), any(), any(), any())).thenReturn(result);

        mvc.perform(get("/stats?start=2024-09-01 00:00:00&end=2024-09-06 00:00:00")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
