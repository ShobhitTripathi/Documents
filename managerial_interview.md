# ENGINEERING MANAGER ROUND – TECHNICAL REVISION SHEET

---

# 1. COMMUNICATION LAYER

## gRPC

**Definition**

* RPC framework over HTTP/2 using Protocol Buffers (binary serialization)

**Core Mechanics**

* HTTP/2 features:

  * Multiplexing (multiple streams over single TCP connection)
  * Header compression (HPACK)
  * Persistent connections
* Serialization:

  * Protobuf → compact, schema-driven, backward-compatible

**Key Features**

* Unary, server streaming, client streaming, bidirectional streaming
* Deadlines (timeouts propagated across services)
* Interceptors (middleware for logging/auth)

**Advantages**

* Lower latency (binary payload + connection reuse)
* Strong contract enforcement (proto schema)
* Efficient for high-throughput internal communication

**Tradeoffs**

* Harder debugging (non-human-readable payload)
* Requires strict schema versioning
* Limited browser support

**Failure Scenarios**

* Schema incompatibility → must follow backward-compatible changes (only add fields)
* Connection issues → need retry with deadlines
* Observability gap → requires structured logging/interceptors

---

## REST (HTTP)

**Definition**

* Stateless request-response communication over HTTP, typically JSON

**Core Mechanics**

* HTTP/1.1 (commonly)
* Each request independent (no persistent multiplexing)
* Text-based payload (JSON)

**Advantages**

* Simplicity and debuggability
* Broad ecosystem support (browser, curl, etc.)
* Flexible schema evolution

**Tradeoffs**

* Higher latency (larger payload + repeated connections)
* Weak contract enforcement
* No native streaming

**Failure Scenarios**

* Versioning issues → breaking API changes
* Payload bloat → performance degradation

---

## gRPC vs REST Decision

**Use gRPC when**

* Internal service-to-service communication
* High throughput / low latency required
* Strong schema enforcement needed

**Use REST when**

* External APIs
* Browser compatibility required
* Flexibility/debugging more important than performance

---

# 2. EVENT-DRIVEN SYSTEMS (KAFKA)

## Core Architecture

* Kafka cluster → multiple brokers
* Topic → logical stream of data
* Partition → ordered, append-only log
* Each partition has:

  * Leader
  * Followers (replication)

---

## Key Concepts

**Producer**

* Writes data to topic
* Can choose partition (via key)

**Consumer**

* Reads data from topic
* Tracks offset

**Consumer Group**

* Parallel processing
* Each partition assigned to one consumer in group

---

## Partitioning

* Key-based partitioning ensures ordering
* Poor key choice → hot partition

---

## Delivery Semantics

* At-most-once → no retries (data loss possible)
* At-least-once → retries (duplicates possible)
* Exactly-once → requires idempotent producers + transactional consumers

---

## Scaling

* Throughput scales with partitions
* Consumers scale via consumer groups

---

## Backpressure Handling

* Monitor consumer lag
* Auto-scale consumers
* Pause/resume consumption

---

## Failure Handling

* Consumer crash → reprocess from last committed offset
* Broker failure → leader election
* Message retention → replay capability

---

## Common Problems

**Duplicate Processing**

* Solution: idempotency (eventId, unique keys)

**Ordering Issues**

* Only guaranteed within partition

**Hot Partitions**

* Solution: better partition key design

---

# 3. LLM / GENAI SYSTEMS

## Architecture (RAG Pattern)

1. Data ingestion
2. Chunking (split documents)
3. Embedding generation
4. Store embeddings in vector DB (Qdrant)
5. Query → embedding
6. Retrieve top-K relevant chunks
7. Construct prompt (context + query)
8. LLM generates response

---

## Key Design Considerations

### Chunking Strategy

* Large chunks → irrelevant context
* Small chunks → loss of semantic meaning

---

### Retrieval Strategy

* Top-K selection
* Hybrid search (semantic + keyword)

---

### Prompt Engineering

* System prompt defines behavior
* Context injection
* Guardrails to reduce hallucination

---

## Hallucination Mitigation

* Retrieval grounding (RAG)
* Confidence scoring
* Deterministic fallback for critical paths

---

## Cost Optimization

* Cache embeddings
* Avoid unnecessary LLM calls
* Batch processing
* Use smaller models where possible

---

## Evaluation

* Offline dataset (golden dataset)
* Precision / recall for matching
* Human validation loop

---

## Failure Scenarios

* Hallucinated outputs
* Context overflow (token limits)
* Model drift / inconsistent responses

---

# 4. ARCHITECTURE (HEXAGONAL)

## Definition

* Separates core business logic from external systems using Ports and Adapters

---

## Structure

* Domain Layer → business logic
* Ports → interfaces
* Adapters → implementations (DB, APIs, etc.)

---

## Benefits

* Decoupling from infrastructure
* High testability (mock ports)
* Easy extensibility (add new adapters)

---

## Design Principles

* Dependency inversion (outer layers depend on inner)
* Domain remains pure (no infra logic)

---

## Tradeoffs

* Increased abstraction
* Higher initial complexity

---

## Failure Scenarios

* Poorly defined interfaces → tight coupling
* Over-engineering for small systems

---

# 5. DISTRIBUTED SYSTEMS

## Idempotency

* Same request executed multiple times produces same result
* Required for retries in distributed systems

---

## Retry Strategy

* Exponential backoff
* Jitter to avoid synchronized retries

---

## Consistency Models

* Strong consistency → immediate correctness
* Eventual consistency → delayed propagation

---

## Failure Handling Patterns

**Dead Letter Queue (DLQ)**

* Store failed events for later processing

**Saga Pattern**

* Sequence of local transactions with compensating actions

**Circuit Breaker**

* Stop calling failing service to prevent cascading failures

---

## Common Issues

* Duplicate processing
* Partial failures
* Retry storms
* Network partitions

---

# 6. DATABASES

## SQL (MySQL)

**Strengths**

* ACID compliance
* Strong consistency
* Complex queries (joins)

**Limitations**

* Horizontal scaling difficult
* Write bottlenecks

---

## DynamoDB

**Core Concepts**

* Key-value store
* Partition key determines data distribution

---

## Partitioning

* Data distributed via hash of partition key
* Throughput limited per partition

---

## Design Principle

* Schema designed based on access patterns

---

## Problems

**Hot Partition**

* Uneven traffic distribution
* Solution: composite keys, randomization

---

## Redis

**Use Case**

* In-memory cache

---

## Patterns

* Cache-aside (lazy loading)
* Write-through (less common)

---

## Problems

**Cache Stampede**

* Multiple requests hit DB simultaneously
* Solution: locking, request coalescing

**Stale Data**

* TTL or explicit invalidation

---

## Elasticsearch

**Core Mechanism**

* Inverted index
* Tokenization + analyzers

---

## Scaling

* Shards (horizontal scaling)
* Replicas (fault tolerance)

---

## Tradeoffs

* Faster search → slower indexing
* Eventual consistency

---

# 7. AWS

## Lambda

**Definition**

* Serverless compute service

---

## Internals

* Runs in containers
* Cold start when new container created
* Warm reuse for subsequent requests

---

## Limitations

* Execution timeout
* Cold start latency
* Not suitable for long-running tasks

---

## DynamoDB (AWS Context)

* Managed NoSQL
* Auto scaling
* Eventually consistent reads (default)

---

# 8. OBSERVABILITY

## Three Pillars

**Logs**

* Detailed event records

**Metrics**

* Aggregated system stats (latency, CPU)

**Traces**

* End-to-end request flow

---

## Trace Propagation

* Pass traceId across services
* Enables distributed tracing

---

## Sampling

* Full tracing expensive
* Sample subset of requests

---

## Common Issues

* Missing trace context
* High cardinality metrics (cost explosion)
* Excess logs (noise)

---

# 9. API DESIGN

## Idempotency

* Use idempotency keys for non-idempotent operations (POST)

---

## Versioning

* URI versioning (/v1, /v2)
* Header-based versioning

---

## Backward Compatibility

* Additive changes only
* Avoid breaking existing clients

---

## Failure Scenarios

* Duplicate requests → unintended side effects
* Breaking API changes → system-wide failures

---

# FINAL DECISION FRAMEWORK (USE IN EVERY ANSWER)

When explaining any system:

1. Problem being solved
2. Constraints (scale, latency, cost)
3. Options considered
4. Decision taken
5. Tradeoffs
6. Failure handling strategy

---

If you want, next step should be:

* Converting this into **precise 1–2 minute answer scripts per topic**, or
* Running a **mock EM round where I probe these deeply**
