package io.regent.bookcruddemo;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 12/10/2022
 */

@TestConfiguration
@Testcontainers
@SuppressWarnings("resource")
public class IntegrationTestConfig {

    private static final Integer MYSQLPORT = 3306;

    @Container
    private static final MySQLContainer<?> MY_SQL_CONTAINER
            = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.30-oracle"))
            .withDatabaseName("book_mysql")
            .withUsername("root")
            .withPassword("123456")
            .withExposedPorts(MYSQLPORT);


    static {
        MY_SQL_CONTAINER.start();
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(@NonNull final ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.datasource.url=" + MY_SQL_CONTAINER.getJdbcUrl(),
                    "spring.datasource.driver-class-name=" + MY_SQL_CONTAINER.getDriverClassName(),
                    "spring.datasource.username=" + MY_SQL_CONTAINER.getUsername(),
                    "spring.datasource.password=" + MY_SQL_CONTAINER.getPassword()
            );
        }
    }
}
