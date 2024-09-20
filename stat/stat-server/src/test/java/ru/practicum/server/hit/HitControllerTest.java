package ru.practicum.server.hit;

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
import ru.practicum.dto.HitDto;
import ru.practicum.server.controller.HitController;
import ru.practicum.server.service.HitService;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class HitControllerTest {
    private final ObjectMapper mapper = new ObjectMapper();
    @Mock
    private HitService service;
    @InjectMocks
    private HitController controller;
    private MockMvc mvc;

    private HitDto hitDto;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
        hitDto = new HitDto(1L, "test-app", "/app/test", "127.0.0.1", "2024-09-19 10:32:02");
    }

    @Test
    void testCreate() throws Exception {
        when(service.create(any())).thenReturn(hitDto);

        mvc.perform(post("/hit")
                        .content(mapper.writeValueAsString(hitDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
