package io.regent.bookcruddemo;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import io.regent.bookcruddemo.repo.resolver.impl.BookRepositoryApiJpaAdapter;
import io.regent.bookcruddemo.repo.resolver.impl.BookRepositoryApiMapImpl;
import io.regent.bookcruddemo.repository.api.BookRepositoryApiJPA;

@SpringBootTest
@ContextConfiguration(classes = BookCrudDemoApplication.class, initializers = IntegrationTestConfig.Initializer.class)
@DirtiesContext
class BookCrudDemoApplicationTests {

    @MockBean
    BookRepositoryApiJPA bookRepository;


    private final ApplicationContextRunner runner =
            new ApplicationContextRunner()
                    .withInitializer(new IntegrationTestConfig.Initializer())
                    .withUserConfiguration(TestConfig.class)
                    .withUserConfiguration(
                            BookRepositoryApiJpaAdapter.class,
                            BookRepositoryApiMapImpl.class
                    );
    ;



    @Test
    void test() {
        runner
                .withPropertyValues(
                "use.repository.stub.enable=true"
                )
                .run(
                        context ->
                                assertAll(
                                        () -> assertThat(context)
                                                .hasBean("bookRepositoryApiMapImpl"),
                                        () -> assertThat(context)
                                                .doesNotHaveBean("bookRepositoryApiJpaAdapter")));
    }


    @Test
    void testContextShouldHaveABeanOFJpaAdapterWhenSetToFalse() {
        runner
                .withPropertyValues(
                        "use.repository.stub.enable=false"
                )
                .run(
                        context ->
                                assertAll(
                                        () -> assertThat(context)
                                                .hasBean("bookRepositoryApiJpaAdapter"),
                                        () -> assertThat(context)
                                                .doesNotHaveBean("bookRepositoryApiMapImpl")));
    }


    protected static class TestConfig {
        @Bean
        public BookRepositoryApiJPA someThirdDependency() {
            return Mockito.mock(BookRepositoryApiJPA.class);   // this bean will be automatically autowired into tested beans
        }
    }

}
