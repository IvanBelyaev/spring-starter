package org.example.spring.listener.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class AccessTypeEvent extends ApplicationEvent {

    @Getter
    private final AccessType type;

    public AccessTypeEvent(Object object, AccessType type) {
        super(object);
        this.type = type;
    }
}
