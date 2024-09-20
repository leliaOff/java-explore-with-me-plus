package ru.practicum.server.hit;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.server.StatServer;
import ru.practicum.server.helpers.DateTimeHelper;
import ru.practicum.server.model.Hit;
import ru.practicum.server.request.RequestHitDto;
import ru.practicum.server.service.HitService;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Transactional
@WebAppConfiguration
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest(classes = StatServer.class)
public class HitServiceTest {
    private final HitService service;
    private final EntityManager em;

    @Test
    void testCreate() {
        RequestHitDto request = new RequestHitDto("test-app", "/app/test", "127.0.0.1", "2024-09-19 10:32:02");
        service.create(request);

        TypedQuery<Hit> query = em.createQuery("Select hit from Hit hit where hit.app = :app", Hit.class);
        Hit hit = query.setParameter("app", request.getApp()).getSingleResult();

        assertThat(hit.getId(), notNullValue());
        assertThat(hit.getUri(), equalTo(request.getUri()));
        assertThat(hit.getIp(), equalTo(request.getIp()));
        assertThat(hit.getTimestamp(), equalTo(DateTimeHelper.toInstant(request.getTimestamp())));
    }
}
