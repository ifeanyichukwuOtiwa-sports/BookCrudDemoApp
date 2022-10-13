## Problem

Suppose you have two classes that should be registered with Spring context exclusively, e.g. only one of the beans must exist in the context at any time based on some boolean property value. Of course you can do it by adding explicit `if` condition into your `@Configuration` class. But if the classes have no common interface it may be quite cumbersome. As an alternative you can use `@ConditionalOnProperty` annotation on your classes, e.g.:

```java
@Service
@ConditionalOnProperty(name = "use-left-service", havingValue = "true", matchIfMissing = false)
public class LeftService {}
```

and its partner
```java
@Service
@ConditionalOnProperty(name = "use-right-service", havingValue = "false", matchIfMissing = true)
public class RightService {}
```
This code registers `LeftService` bean if `use-left-service` property is `true` and `RightService` if the property is `false`, defaulting to `RightService` in case of the property absence.

The behavior of `@ConditionalOnProperty` is not the most straightforward so you should ensure you use it correctly. For instance, if you forget to add `matchIfMissing` attribute and there would be no explicit property in the environment then you may end up with no bean registered at all. To avoid this, beans registration logic must be covered with unit tests. Spring Boot has a suitable test harness for it, see [47.4 Testing your Auto-configuration](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-test-autoconfig).

## Solution

The idea behind such a test is that you create an `ApplicationContextRunner` instance, fill it with classes involved in bean registration process and then call its `run` method with appropriate assertions (usually expressed with AssertJ).

1. Create `ApplicationContextRunner` instance and fill it with bean candidates.   
   It's better to declare the runner as reusable component of your test class, e.g. as a field:
```java
class Test {
   private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
           .withUserConfiguration(LeftService.class, RightService.class);
}
```

2. Supplement the runner with changing parts of the environment in every unit test and run it:
```java
class Test {
   @Test
   void rightServiceIsAppliedProperly() {
      contextRunner
              .withPropertyValues("use-left-service=false")  // here is the changing part (property value)
              .run(/*will see later*/);
   }
}
```

3. Add assertions on the context and/or beans in `run`'s method lambda:
```java
class Test {
   @Test
   void rightServiceIsAppliedProperly() {
      contextRunner
              .withPropertyValues("use-left-service=false")
              .run(context -> assertAll(
                      () -> assertThat(context).hasSingleBean(RightService.class),
                      () -> assertThat(context).doesNotHaveBean(LeftService.class)));
   }
}
```
**Note** that `assertAll` method is imported from `org.junit.jupiter.api.Assertions` class while `assertThat` is imported from equally named `org.assertj.core.api.Assertions` class. Wrapping into `assertAll` method is used here just to perform all the nested assertions even the first one fails.

With this test you don't have to build up Spring application context with `@SpringBootTest` annotation and `SpringRunner`. The test stays almost trivial but checks for some real infrastructual behavior applying to your beans.

You can also add some extra (mock) dependencies into your test context by means of configuration classes, see the following `SelectServiceBeanTest.java` class.