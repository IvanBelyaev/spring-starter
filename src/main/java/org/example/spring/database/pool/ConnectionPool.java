package org.example.spring.database.pool;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component("pool1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ConnectionPool {

    @Value("${db.name}")
    private final String name;
    @Value("${pool.size}")
    private final Integer size;

    @PostConstruct
    private void init() {
        log.info("Create connection");
    }

    @PreDestroy
    private void destroy() {
        log.info("All connection closed");
    }
}
