package com.publicissapient.anaroc.factory;

import com.publicissapient.anaroc.exception.DebugSessionInvalidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class DebugSessionFactoryTest {

    private DebugSessionFactory sessionFactory;

    @BeforeEach
    void setUp() {
        sessionFactory = new DebugSessionFactory();
    }

    @Test
    void should_be_able_to_add_session() throws Exception {
        boolean result = sessionFactory.add(UUID.randomUUID().toString(), WebDriverFactory.localFireFoxWebDriver());
        assertThat(result).isTrue();
    }

    @Test
    void should_expect_debug_session_invalid_exception_for_invalid_session_id() {
        Assertions.assertThrows(DebugSessionInvalidException.class, ()->sessionFactory.get(UUID.randomUUID().toString()));
    }

    @Test
    void should_be_able_to_get_webdriver_for_valid_session_id() throws Exception {
        String sessionId = UUID.randomUUID().toString();
        sessionFactory.add(sessionId, WebDriverFactory.localFireFoxWebDriver());
        WebDriver webDriver = sessionFactory.get(sessionId);
        assertThat(webDriver).isNotNull();
    }

    @Test
    void should_expect_debug_session_invalid_exception_for_invalid_session_id_in_delete_session() {
        Assertions.assertThrows(DebugSessionInvalidException.class, () ->sessionFactory.delete(UUID.randomUUID().toString()));
    }

    @Test
    void should_be_able_to_delete_session_for_valid_session_id() throws Exception {
        String sessionId = UUID.randomUUID().toString();
        sessionFactory.add(sessionId, WebDriverFactory.localFireFoxWebDriver());
        boolean result = sessionFactory.delete(sessionId);
        assertThat(result).isTrue();
    }
}
