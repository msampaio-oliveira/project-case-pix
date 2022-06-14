package br.com.project.pix.config;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import static org.springframework.test.util.AssertionErrors.assertNotNull;

@Configuration
public class ProjectionConfigTest {

    public ProjectionConfigTest() {
    }

    @Bean
    public ProjectionFactory projectionFactory() {
        return new SpelAwareProxyProjectionFactory();
    }

    @Test
    public void shouldCreteProjectionFactory() {
        assertNotNull("Should not be null!", projectionFactory());
    }

}
