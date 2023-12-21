package org.example.spring.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.spring.listener.event.AccessTypeEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SomeComponent {
    @Value("10")
    private Integer count;

    @Order(50)
    @EventListener(condition = "#p0.type.name == 'READ' || #p0.type.name == 'UPDATE'")
    public void readAccessTypeListener(AccessTypeEvent event) {
        count++;
        log.info("read listener");
    }

    @Order(10)
    @EventListener(condition = "#p0.type.name == 'UPDATE'")
    public void updateAccessTypeListener(AccessTypeEvent event) {
        count++;
        log.info("update listener");
    }
}
