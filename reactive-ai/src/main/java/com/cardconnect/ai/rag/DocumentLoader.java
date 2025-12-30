package com.cardconnect.ai.rag;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DocumentLoader {

    private final VectorStore vectorStore;

    @Value("classpath:/static/shipment_document.pdf")
    private Resource document;

    public DocumentLoader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    /*@PostConstruct
    public void loadDocuments() {
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(document);
        List<Document> docs = tikaDocumentReader.get();
        TextSplitter textSplitter =
                TokenTextSplitter.builder()
                        .withChunkSize(100)
                        .withMaxNumChunks(400)
                        .build();
        vectorStore.add(textSplitter.split(docs));
    }*/

    @PostConstruct
    public void loadDocuments() {
        PdfDocumentReaderConfig config = PdfDocumentReaderConfig.builder()
                .withPagesPerDocument(1)
                .build();

        List<Document> docs = new PagePdfDocumentReader(document,
                PdfDocumentReaderConfig.builder()
                        .withPagesPerDocument(1)
                        .build()).get();

        vectorStore.add(TokenTextSplitter.builder().build().split(docs));
    }
}
