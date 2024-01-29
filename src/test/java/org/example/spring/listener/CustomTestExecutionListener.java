package org.example.spring.listener;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class CustomTestExecutionListener implements TestExecutionListener {
    @Override
    public void beforeTestMethod(TestContext testContext) {
        System.out.printf("before method: %s%n", testContext.getTestMethod().getName());
    }
}
