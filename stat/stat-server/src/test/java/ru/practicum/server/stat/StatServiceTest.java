package ru.practicum.server.stat;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatDto;
import ru.practicum.server.StatServer;
import ru.practicum.server.service.HitService;
import ru.practicum.server.service.StatService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

@Transactional
@WebAppConfiguration
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest(classes = StatServer.class)
public class StatServiceTest {
    private final StatService statService;
    private final HitService hitService;
    private final EntityManager em;

    @BeforeEach
    void setUp() {
        createHit("alpha", "/", "192.168.1.1", "2024-09-01 10:00:00");
        createHit("alpha", "/", "192.168.1.1", "2024-09-01 11:00:00");
        createHit("alpha", "/", "192.168.1.1", "2024-09-01 12:00:00");
        createHit("alpha", "/news", "192.168.1.1", "2024-09-01 10:10:00");
        createHit("alpha", "/news", "192.168.1.1", "2024-09-01 10:15:00");
        createHit("alpha", "/", "192.168.1.2", "2024-09-01 11:00:00");
        createHit("alpha", "/", "192.168.1.2", "2024-09-01 12:00:00");
        createHit("alpha", "/news", "192.168.1.3", "2024-09-01 10:15:00");
        createHit("alpha", "/", "192.168.1.1", "2024-09-05 12:00:00");
        createHit("alpha", "/news", "192.168.1.1", "2024-09-05 10:10:00");
        createHit("alpha", "/", "192.168.1.3", "2024-09-05 12:00:00");
        createHit("alpha", "/", "192.168.1.3", "2024-09-05 10:10:00");
        createHit("beta", "/", "192.168.1.4", "2024-09-01 10:00:00");
        createHit("beta", "/", "192.168.1.4", "2024-09-01 11:00:00");
        createHit("beta", "/", "192.168.1.1", "2024-09-01 12:00:00");
        createHit("beta", "/news", "192.168.1.1", "2024-09-01 10:10:00");
        createHit("beta", "/news", "192.168.1.1", "2024-09-01 10:15:00");
        createHit("beta", "/", "192.168.1.2", "2024-09-05 11:00:00");
        createHit("beta", "/", "192.168.1.2", "2024-09-05 12:00:00");
        createHit("beta", "/news", "192.168.1.3", "2024-09-05 10:15:00");
        createHit("beta", "/", "192.168.1.1", "2024-09-05 12:00:00");
        createHit("beta", "/news", "192.168.1.1", "2024-09-05 10:10:00");
        createHit("beta", "/", "192.168.1.3", "2024-09-05 12:00:00");
        createHit("beta", "/", "192.168.1.3", "2024-09-05 10:10:00");
        createHit("alpha", "/profile", "192.168.1.1", "2024-09-01 10:00:00");
        createHit("alpha", "/profile", "192.168.1.1", "2024-09-01 11:00:00");
        createHit("alpha", "/profile", "192.168.1.1", "2024-09-01 12:00:00");
        createHit("beta", "/", "192.168.1.4", "2024-09-01 10:00:00");
        createHit("beta", "/profile", "192.168.1.4", "2024-09-01 10:00:00");
        createHit("beta", "/profile", "192.168.1.4", "2024-09-01 11:00:00");
        createHit("beta", "/profile", "192.168.1.1", "2024-09-01 12:00:00");
    }

    @Test
    void getTo02() {
        Collection<StatDto> result = statService.get("2024-09-01 00:00:00", "2024-09-02 00:00:00", null, false);
        assertThat(result, hasSize(6));
        assertThat(result, hasItem(new StatDto("alpha", "/", 5L)));
        assertThat(result, hasItem(new StatDto("alpha", "/news", 3L)));
        assertThat(result, hasItem(new StatDto("alpha", "/profile", 3L)));
        assertThat(result, hasItem(new StatDto("beta", "/", 4L)));
        assertThat(result, hasItem(new StatDto("beta", "/profile", 3L)));
        assertThat(result, hasItem(new StatDto("beta", "/news", 2L)));
    }

    @Test
    void getTo06() {
        Collection<StatDto> result = statService.get("2024-09-01 00:00:00", "2024-09-06 00:00:00", null, false);
        assertThat(result, hasSize(6));
        assertThat(result, hasItem(new StatDto("alpha", "/", 8L)));
        assertThat(result, hasItem(new StatDto("alpha", "/news", 4L)));
        assertThat(result, hasItem(new StatDto("alpha", "/profile", 3L)));
        assertThat(result, hasItem(new StatDto("beta", "/", 9L)));
        assertThat(result, hasItem(new StatDto("beta", "/profile", 3L)));
        assertThat(result, hasItem(new StatDto("beta", "/news", 4L)));
    }

    @Test
    void getUniqueTo02() {
        Collection<StatDto> result = statService.get("2024-09-01 00:00:00", "2024-09-02 00:00:00", null, true);
        assertThat(result, hasSize(6));
        assertThat(result, hasItem(new StatDto("alpha", "/", 2L)));
        assertThat(result, hasItem(new StatDto("alpha", "/news", 2L)));
        assertThat(result, hasItem(new StatDto("alpha", "/profile", 1L)));
        assertThat(result, hasItem(new StatDto("beta", "/", 2L)));
        assertThat(result, hasItem(new StatDto("beta", "/profile", 2L)));
        assertThat(result, hasItem(new StatDto("beta", "/news", 1L)));
    }

    @Test
    void getUniqueTo06() {
        Collection<StatDto> result = statService.get("2024-09-01 00:00:00", "2024-09-06 00:00:00", null, true);
        assertThat(result, hasSize(6));
        assertThat(result, hasItem(new StatDto("alpha", "/", 3L)));
        assertThat(result, hasItem(new StatDto("beta", "/", 4L)));
        assertThat(result, hasItem(new StatDto("alpha", "/news", 2L)));
        assertThat(result, hasItem(new StatDto("beta", "/news", 2L)));
        assertThat(result, hasItem(new StatDto("alpha", "/profile", 1L)));
        assertThat(result, hasItem(new StatDto("beta", "/profile", 2L)));
    }



    @Test
    void getByUrisTo02() {
        Collection<StatDto> result = statService.get("2024-09-01 00:00:00", "2024-09-02 00:00:00", getUris(), false);
        assertThat(result, hasSize(4));
        assertThat(result, hasItem(new StatDto("alpha", "/news", 3L)));
        assertThat(result, hasItem(new StatDto("alpha", "/profile", 3L)));
        assertThat(result, hasItem(new StatDto("beta", "/profile", 3L)));
        assertThat(result, hasItem(new StatDto("beta", "/news", 2L)));
    }

    @Test
    void getByUrisTo06() {
        Collection<StatDto> result = statService.get("2024-09-01 00:00:00", "2024-09-06 00:00:00", getUris(), false);
        assertThat(result, hasSize(4));
        assertThat(result, hasItem(new StatDto("alpha", "/news", 4L)));
        assertThat(result, hasItem(new StatDto("beta", "/news", 4L)));
        assertThat(result, hasItem(new StatDto("alpha", "/profile", 3L)));
        assertThat(result, hasItem(new StatDto("beta", "/profile", 3L)));
    }

    @Test
    void getUniqueByUrisTo02() {
        Collection<StatDto> result = statService.get("2024-09-01 00:00:00", "2024-09-02 00:00:00", getUris(), true);
        assertThat(result, hasSize(4));
        assertThat(result, hasItem(new StatDto("alpha", "/news", 2L)));
        assertThat(result, hasItem(new StatDto("alpha", "/profile", 1L)));
        assertThat(result, hasItem(new StatDto("beta", "/profile", 2L)));
        assertThat(result, hasItem(new StatDto("beta", "/news", 1L)));
    }

    @Test
    void getUniqueByUrisTo06() {
        Collection<StatDto> result = statService.get("2024-09-01 00:00:00", "2024-09-06 00:00:00", getUris(), true);
        assertThat(result, hasSize(4));
        assertThat(result, hasItem(new StatDto("alpha", "/news", 2L)));
        assertThat(result, hasItem(new StatDto("beta", "/news", 2L)));
        assertThat(result, hasItem(new StatDto("alpha", "/profile", 1L)));
        assertThat(result, hasItem(new StatDto("beta", "/profile", 2L)));
    }

    private void createHit(String app, String uri, String ip, String timestamp) {
        hitService.create(new HitDto(null, app, uri, ip, timestamp));
    }

    private ArrayList<String> getUris() {
        return new ArrayList<>(Arrays.asList("/news", "/profile", "/price"));
    }
}
