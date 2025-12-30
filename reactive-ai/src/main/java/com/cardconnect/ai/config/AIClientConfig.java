package com.cardconnect.ai.config;

import com.cardconnect.ai.advisors.TokenUsageAuditAdvisor;
import com.cardconnect.ai.tools.AITools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class AIClientConfig {

    @Value("classpath:/templates/systemPromptTemplate.st")
    private Resource systemPromptTemplate;

    @Value("classpath:/templates/userPromptTemplate.st")
    private Resource userPromptTemplate;

    @Bean
    public ToolCallbackProvider toolCallbackProvider(ToolCallbackProvider mcpToolProvider, AITools aiTools) {
        List<ToolCallback> allToolCallbacks = new ArrayList<>();
        allToolCallbacks.addAll(Arrays.asList(mcpToolProvider.getToolCallbacks()));
     allToolCallbacks.addAll(List.of(ToolCallbacks.from(aiTools)));
        return ToolCallbackProvider.from(allToolCallbacks);
    }

    @Bean
    ChatMemory chatMemory(JdbcChatMemoryRepository jdbcChatMemoryRepository) {
        return MessageWindowChatMemory.builder().maxMessages(10)
                .chatMemoryRepository(jdbcChatMemoryRepository)
                .build();
    }

    @Bean
    public ChatClient.Builder chatClientBuilder(OllamaChatModel chatModel) {
        return ChatClient.builder(chatModel);
    }

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, ChatMemory chatMemory, RetrievalAugmentationAdvisor retrievalAugmentationAdvisor, ToolCallbackProvider toolCallbackProvider) {

        return builder
                .defaultAdvisors(
                        SimpleLoggerAdvisor.builder().build(),
                        TokenUsageAuditAdvisor.builder().build(),
                        //MessageChatMemoryAdvisor.builder(MessageWindowChatMemory.builder().build())   // In-Memory Chat Memory
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),    // JDBC Chat Memory
                        retrievalAugmentationAdvisor
                )
                .defaultSystem(systemPromptTemplate)
                .defaultUser(userPromptTemplate)
                .defaultToolCallbacks(toolCallbackProvider)
                //.defaultTools(new AITools())
                .build();
    }

    @Bean
    public RetrievalAugmentationAdvisor retrievalAugmentationAdvisor(VectorStore vectorStore) {
        return RetrievalAugmentationAdvisor.builder()
                /*.queryTransformers(TranslationQueryTransformer.builder()
                        .chatClientBuilder(chatClientBuilder.clone())
                        .targetLanguage("english").build())*/
                .documentRetriever(VectorStoreDocumentRetriever.builder().vectorStore(vectorStore)
                        .topK(4).similarityThreshold(0.5).build())
               /* .queryAugmenter(ContextualQueryAugmenter.builder()
                        .allowEmptyContext(true)
                        .build())*/
                /*.documentPostProcessors(DocumentPostProcessor.builder())*/
                .build();
    }

/*

    @Bean
    public VectorStore vectorStore(JdbcTemplate jdbcTemplate, EmbeddingModel embeddingModel) {
        return PgVectorStore.builder(jdbcTemplate, embeddingModel)
                .dimensions(1536)                    // Optional: defaults to model dimensions or 1536
                .distanceType(COSINE_DISTANCE)       // Optional: defaults to COSINE_DISTANCE
                .indexType(HNSW)                     // Optional: defaults to HNSW
                .initializeSchema(true)              // Optional: defaults to false
                .schemaName("public")                // Optional: defaults to "public"
                .vectorTableName("vector_store")     // Optional: defaults to "vector_store"
                .maxDocumentBatchSize(10000)         // Optional: defaults to 10000
                .build();
    }

    @Bean
    ToolExecutionExceptionProcessor toolExecutionExceptionProcessor() {
        return new DefaultToolExecutionExceptionProcessor(true);
    }*/
}
