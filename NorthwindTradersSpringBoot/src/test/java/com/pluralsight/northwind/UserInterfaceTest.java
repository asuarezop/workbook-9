package com.pluralsight.northwind;

import com.pluralsight.northwind.service.UserInterface;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserInterfaceTest {

    @Autowired
    UserInterface ui;

    @Test
    void contextLoads() {
        {
            // Verify that the application context is loaded and the UserInterface bean is injected
            assertNotNull(ui, "UserInterface bean should be created and not null");
        }
    }
}
