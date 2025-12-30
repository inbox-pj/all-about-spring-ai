# Spring AI 

A comprehensive multi-module Spring Boot project demonstrating various **Spring AI** capabilities including chat models, RAG (Retrieval-Augmented Generation), MCP (Model Context Protocol), tool calling, chat memory, and observability.

---

## 📋 Table of Contents

- [Project Overview](#project-overview)
- [Architecture](#architecture)
- [Modules](#modules)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Official Documentation](#official-documentation)

---

## 🎯 Project Overview

This project showcases the integration of Spring AI with:

- **Ollama** - Local LLM inference (llama3.2, gemma3, nomic-embed-text)
- **Qdrant** - Vector database for RAG implementations
- **MCP (Model Context Protocol)** - Tool calling via STDIO and SSE transports
- **Chat Memory** - JDBC-based conversation persistence with H2
- **Observability** - Prometheus, Grafana, Jaeger, and OpenTelemetry integration

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                         Spring AI Project                        │
├──────────────────┬──────────────────┬──────────────────────────-─┤
│   reactive-ai    │   imperative-ai  │  mcp-server-stdio/sse      │
│   (Port 8080)    │   (Port 9080)    │  (Port 8090 for SSE)       │
├──────────────────┴──────────────────┴────────────────────────────┤
│                        Spring AI 1.0.3                           │
├──────────────────────────────────────────────────────────────────┤
│  Ollama (11434)  │  Qdrant (6333/6334)  │  Jaeger (16686/4317)   │
│  Prometheus (9090)  │  Grafana (3000)                            │
└──────────────────────────────────────────────────────────────────┘
```

---

## 📦 Modules

### 1. **reactive-ai** (Main Application)
The primary Spring AI application with comprehensive features.

| Feature | Description |
|---------|-------------|
| **Chat Model** | Ollama integration with llama3.2 |
| **RAG** | PDF document loading and vector search with Qdrant |
| **Chat Memory** | JDBC-based persistence with H2 database |
| **Tool Calling** | Custom tools (getTime, getWeather) + MCP tools |
| **MCP Client** | Connects to MCP servers via STDIO and SSE |
| **Advisors** | TokenUsageAuditAdvisor, MessageChatMemoryAdvisor, RetrievalAugmentationAdvisor |
| **Observability** | Prometheus metrics, OpenTelemetry tracing with Jaeger |

**Key Components:**
- `AIController` - REST API for shipment inquiries
- `AIServiceImpl` - Chat client with memory and RAG
- `DocumentLoader` - PDF document loading into Qdrant
- `AITools` - Custom tools (getTime, getWeather)
- `TokenUsageAuditAdvisor` - Token usage logging

---

### 2. **imperative-ai** (Lightweight Chat Demo)
A simpler module demonstrating basic chat capabilities.

| Feature | Description |
|---------|-------------|
| **Streaming Chat** | Reactive streaming responses |
| **Structured Output** | Entity extraction (ActorFilms) |
| **Simple Setup** | Minimal dependencies for quick demos |

**Endpoints:**
- `GET /api/imperative/chat` - Streaming chat responses
- `GET /api/imperative/getActorFilms` - Structured data extraction

---

### 3. **mcp-server-stdio** (MCP Server - STDIO Transport)
MCP server running as a subprocess with STDIO communication.

| Feature | Description |
|---------|-------------|
| **Transport** | Standard Input/Output (STDIO) |
| **Tools** | `createTicket` - Creates support tickets |
| **Use Case** | Local subprocess tool provider |

---

### 4. **mcp-server-sse** (MCP Server - SSE Transport)
MCP server exposing tools over HTTP with Server-Sent Events.

| Feature | Description |
|---------|-------------|
| **Transport** | HTTP with SSE (Port 8090) |
| **Tools** | `getTicketStatus` - Fetches shipment ticket status |
| **Use Case** | Remote/distributed tool provider |

---

## 🛠️ Tech Stack

| Category | Technology | Version |
|----------|-----------|---------|
| **Framework** | Spring Boot | 3.5.5 |
| **AI Framework** | Spring AI | 1.0.3 |
| **Java** | JDK | 21 |
| **Build** | Maven | - |
| **LLM Runtime** | Ollama | latest |
| **Vector Store** | Qdrant | latest |
| **Database** | H2 (in-memory) | - |
| **Tracing** | Jaeger + OpenTelemetry | latest |
| **Metrics** | Prometheus + Micrometer | latest |
| **Visualization** | Grafana | latest |

---

## ✅ Prerequisites

1. **Java 21** - JDK 21 or later
2. **Maven** - For building the project
3. **Docker** - For running infrastructure services
4. **Ollama** - (Optional) For local model inference

---

## 🚀 Getting Started

### 1. Clone the Repository
```bash
git clone <repository-url>
cd spring-ai
```

### 2. Start Infrastructure Services
```bash
# Start Qdrant, Prometheus, Grafana, and Jaeger
docker compose up -d

# OR use the docker folder for Ollama + Qdrant
cd docker && docker compose up -d
```

### 3. Pull Ollama Models (if running Ollama locally)
```bash
ollama pull llama3.2:latest
ollama pull nomic-embed-text:latest
```

### 4. Build the Project
```bash
mvn clean install
```

### 5. Run the Applications

**Start MCP STDIO Server (build first):**
```bash
# The STDIO server is launched automatically by reactive-ai via mcp-servers.json
```

**Start MCP SSE Server:**
```bash
cd mcp-server-sse
mvn spring-boot:run
# Runs on port 8090
```

**Start Reactive AI Application:**
```bash
cd reactive-ai
mvn spring-boot:run
# Runs on port 8080
```

**Start Imperative AI Application:**
```bash
cd imperative-ai
mvn spring-boot:run
# Runs on port 9080
```

---

## 🔌 API Endpoints

### Reactive AI (Port 8080)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/reactive/shipment/inquiry?shipmentId={id}&message={msg}` | Shipment inquiry with RAG and tools |

**Example:**
```bash
curl "http://localhost:8080/api/reactive/shipment/inquiry?shipmentId=SHIP001&message=What%20is%20the%20status?"
```

### Imperative AI (Port 9080)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/imperative/chat?message={msg}` | Streaming chat response |
| GET | `/api/imperative/getActorFilms?message={msg}` | Extract actor films data |

**Example:**
```bash
curl "http://localhost:9080/api/imperative/chat?message=Hello"
curl "http://localhost:9080/api/imperative/getActorFilms?message=List%20Tom%20Hanks%20films"
```

---

## 📊 Observability URLs

| Service | URL | Description |
|---------|-----|-------------|
| **Prometheus** | http://localhost:9090 | Metrics collection |
| **Grafana** | http://localhost:3000 | Dashboards |
| **Jaeger** | http://localhost:16686 | Distributed tracing |
| **Qdrant** | http://localhost:6333/dashboard | Vector store UI |
| **Actuator** | http://localhost:8080/actuator/prometheus | Spring Boot metrics |

---

## 📘 Official Documentation

- **[Spring AI Official Documentation](https://docs.spring.io/spring-ai/reference/index.html)**  
  The core reference for understanding Spring AI modules, configuration, and supported AI providers.

---

## 🤖 AI Providers & Runtimes

- **[Ollama](https://ollama.com)**  
  Run open-source large language models (LLMs) locally on your machine with simple commands.

---

## 📦 Vector Store & MCP

- **[Qdrant Vector Database](https://qdrant.tech)**  
  An open-source vector store used in Retrieval-Augmented Generation (RAG) demos with Spring AI.

- **[Model Context Protocol (MCP)](https://modelcontextprotocol.io/)**  
  A protocol for connecting AI clients and servers in a decoupled and extensible way.

---

## 📊 Observability & Monitoring Tools

- **[Prometheus](https://prometheus.io/)** - Monitoring and alerting toolkit for collecting Spring Boot and AI app metrics.
- **[Micrometer](https://micrometer.io/)** - Java metrics collection library used with Spring Boot to expose observability data.
- **[OpenTelemetry](https://opentelemetry.io/)** - Industry-standard framework for distributed tracing and telemetry data.
- **[Grafana](https://grafana.com/)** - Visualization tool for creating dashboards from Prometheus and other data sources.
- **[Jaeger Tracing](https://www.jaegertracing.io/)** - Distributed tracing platform used to trace and monitor AI request flows.

---

## 📚 Reference Documentation

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.5/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.5.5/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.5.5/reference/web/servlet.html)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.5.5/reference/using/devtools.html)
* [Ollama Chat Model](https://docs.spring.io/spring-ai/reference/api/chat/ollama-chat.html)
* [Qdrant Vector Store](https://docs.spring.io/spring-ai/reference/api/vectordbs/qdrant.html)
* [Chat Memory Repository](https://docs.spring.io/spring-ai/reference/api/chat-memory.html)
* [Model Context Protocol Server](https://docs.spring.io/spring-ai/reference/api/mcp/mcp-server-boot-starter-docs.html)
* [Model Context Protocol Client](https://docs.spring.io/spring-ai/reference/api/mcp/mcp-client-boot-starter-docs.html)

---

## 📁 Project Structure

```
spring-ai/
├── compose.yml                 # Docker Compose for dev services
├── prometheus-config.yml       # Prometheus configuration
├── pom.xml                     # Parent POM
├── docker/
│   ├── Dockerfile              # Ollama with pre-pulled models
│   └── docker-compose.yml      # Ollama + Qdrant setup
├── reactive-ai/                # Main AI application
│   └── src/main/
│       ├── java/.../
│       │   ├── controller/     # REST controllers
│       │   ├── service/        # Business logic
│       │   ├── config/         # Spring configurations
│       │   ├── advisors/       # Chat advisors
│       │   ├── tools/          # AI tools
│       │   ├── rag/            # Document loaders
│       │   └── model/          # DTOs
│       └── resources/
│           ├── templates/      # Prompt templates
│           ├── static/         # PDF documents
│           ├── mcp/            # MCP server configs
│           └── db/             # DB migrations
├── imperative-ai/              # Simple chat demo
├── mcp-server-stdio/           # MCP server (STDIO)
└── mcp-server-sse/             # MCP server (SSE)
```

---

## 📄 License

This project is for demonstration and learning purposes.

---

