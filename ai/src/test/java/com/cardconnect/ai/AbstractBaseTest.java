package com.cardconnect.ai;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest
public abstract class AbstractBaseTest {

    protected static final GenericContainer<?> qdrant = new GenericContainer<>("qdrant/qdrant:latest")
            .withExposedPorts(6334);

    protected static final GenericContainer<?> ollama = new GenericContainer<>("ollama/ollama:latest")
            .withExposedPorts(11434);

    static {
        qdrant.start();
        ollama.start();
        try {
            ollama.execInContainer("ollama", "pull", "nomic-embed-text:latest");
        } catch (Exception e) {
            throw new RuntimeException("Failed to pull Ollama model", e);
        }
    }

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.ai.vectorstore.qdrant.host", qdrant::getHost);
        registry.add("spring.ai.vectorstore.qdrant.port", () -> qdrant.getMappedPort(6334));
        registry.add("spring.ai.ollama.base-url", () ->
                "http://" + ollama.getHost() + ":" + ollama.getMappedPort(11434));
    }
}
