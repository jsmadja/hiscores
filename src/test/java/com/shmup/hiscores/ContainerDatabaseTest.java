package com.shmup.hiscores;

import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class ContainerDatabaseTest {

    @Container
    public static  MySQLContainer mysql;

    static {
        mysql = new MySQLContainer<>("mysql:5.7")
                .withDatabaseName("shmup")
                .withUsername("mysql")
                .withPassword("password");
        mysql.start();
    }
}
