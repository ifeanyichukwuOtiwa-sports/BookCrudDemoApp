```java
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportLoggingListener;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class SelectServiceBeanTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withInitializer(new ConditionEvaluationReportLoggingListener())  // to print out conditional config report to log
            .withUserConfiguration(TestConfig.class)                          // satisfy transitive dependencies of beans being tested
            .withUserConfiguration(LeftService.class, RightService.class);

    @Test
    void rightServiceIsAppliedProperly() {
        contextRunner
                .withPropertyValues("use-left-service=false")
                .run(context -> assertAll(
                        () -> assertThat(context).hasSingleBean(RightService.class),
                        () -> assertThat(context).doesNotHaveBean(LeftService.class)));
    }

    @Test
    void leftServiceIsAppliedProperly() {
        contextRunner
                .withPropertyValues("use-left-service=true")
                .run(context -> assertAll(
                        () -> assertThat(context).hasSingleBean(LeftService.class),
                        () -> assertThat(context).doesNotHaveBean(RightService.class)));
    }

    @Test
    void rightServiceIsAppliedByDefault() {
        contextRunner
                .run(context -> assertAll(
                        () -> assertThat(context).hasSingleBean(RightService.class),
                        () -> assertThat(context).doesNotHaveBean(LeftService.class)));
    }

    @Configuration
    // this annotation is not required here as the class is explicitly mentioned in `withUserConfiguration` method
    protected static class TestConfig {
        @Bean
        public SomeThirdDependency someThirdDependency() {
            return Mockito.mock(SomeThirdDependency.class);   // this bean will be automatically autowired into tested beans
        }
    }
}
```