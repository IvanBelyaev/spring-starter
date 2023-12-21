package org.example.spring.integration.service;

import lombok.RequiredArgsConstructor;
import org.example.spring.database.pool.ConnectionPool;
import org.example.spring.integration.annotation.IT;
import org.example.spring.service.UserService;
import org.junit.jupiter.api.Test;

@IT
@RequiredArgsConstructor
public class UserServiceIT {

    private final UserService userService;

//    @SpyBean(name = "pool1")
    private final ConnectionPool pool;

    @Test
    void test() {

    }

    @Test
//    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void test2() {

    }
}
