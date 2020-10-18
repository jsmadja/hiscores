package com.shmup.hiscores;

import org.springframework.beans.factory.annotation.Value;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.servlet.http.Cookie;
import javax.validation.constraints.NotNull;

@Testcontainers
public abstract class ContainerDatabaseTest {

    @Container
    public static final MySQLContainer mysql;

    @Value("${api.shmup-cookie-name}")
    private String shmupCookieName;

    @Value("${api.shmup-cookie-userid}")
    private String shmupUserId;

    @NotNull
    protected Cookie createShmupCookie() {
        return new Cookie(shmupCookieName, shmupUserId);
    }

    static {
        mysql = new MySQLContainer<>("mysql:5.7")
                .withDatabaseName("shmup")
                .withUsername("mysql")
                .withPassword("password");
        mysql.start();
    }
}
