package com.curso.unimag.ArenaRanking2;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

@Testcontainers
@SpringBootTest
public abstract class PostgresTestContainerSupport {
    @Container
    @ServiceConnection
    static  final PostgreSQLContainer postgres= new PostgreSQLContainer
            ("postgres:18-alpine");
}
