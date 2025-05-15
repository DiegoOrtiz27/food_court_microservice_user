package com.foodquart.microserviceuser.domain.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpMessagesTest {
    @Test
    void constructorShouldThrowIllegalStateException() {
        assertThrows(IllegalStateException.class, HttpMessages::new, "Utility class");
    }
}