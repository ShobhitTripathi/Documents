# Merge K Sorted Streams – LLD (Production Ready)

This is a strong Senior SWE / backend system design + machine coding problem.

Typical requirement:

> Design a system that merges K continuously incoming sorted streams into one globally sorted output stream.

Examples:

* merging logs from multiple servers
* Kafka partition ordered consumption
* stock market tick aggregation
* hotel inventory updates from multiple vendors
* distributed event processing

Think of:

```text
K sorted input streams → 1 sorted output stream
```

where each stream is individually sorted by:

* timestamp
* sequence number
* version
* priority

---

# 1. Core Design Idea

Use:

```text
Min Heap (PriorityQueue)
```

Because at any point:

> smallest among K heads is the next globally sorted item

This gives optimal efficiency.

---

# 2. Core Algorithm

For each stream:

* take first element
* push into min heap

Then repeatedly:

* pop smallest element
* send to output
* fetch next element from same stream
* push back into heap

This is classic K-way merge.

Exactly same principle as:

```text
merge k sorted linked lists
```

but production systems add:

* retries
* stream failures
* backpressure
* watermark handling
* deduplication
* ordering guarantees

---

# 3. Class Diagram

```text
+--------------------------+
| StreamEvent              |
+--------------------------+
| streamId                 |
| payload                  |
| timestamp                |
| sequenceNumber           |
+--------------------------+

+--------------------------+
| InputStream              |
+--------------------------+
| streamId                 |
+--------------------------+
| hasNext()                |
| next()                   |
+--------------------------+

+--------------------------+
| MergeKSortedStreams      |
+--------------------------+
| minHeap                  |
| streams                  |
+--------------------------+
| merge()                  |
| initializeHeap()         |
| processNext()            |
+--------------------------+
```

---

# 4. Java Code (Production Style)

```java
import java.util.*;

class StreamEvent {
    String streamId;
    String payload;
    long timestamp;
    long sequenceNumber;

    public StreamEvent(String streamId,
                       String payload,
                       long timestamp,
                       long sequenceNumber) {
        this.streamId = streamId;
        this.payload = payload;
        this.timestamp = timestamp;
        this.sequenceNumber = sequenceNumber;
    }

    @Override
    public String toString() {
        return streamId + " -> " + payload + " @ " + timestamp;
    }
}

interface InputStream {
    boolean hasNext();
    StreamEvent next();
}

class EventWrapper {
    StreamEvent event;
    InputStream sourceStream;

    public EventWrapper(StreamEvent event,
                        InputStream sourceStream) {
        this.event = event;
        this.sourceStream = sourceStream;
    }
}

class MergeKSortedStreams {
    private final PriorityQueue<EventWrapper> minHeap;

    public MergeKSortedStreams() {
        this.minHeap = new PriorityQueue<>(
            (a, b) -> Long.compare(
                a.event.timestamp,
                b.event.timestamp
            )
        );
    }

    public void initialize(List<InputStream> streams) {
        for (InputStream stream : streams) {
            if (stream.hasNext()) {
                minHeap.offer(
                    new EventWrapper(
                        stream.next(),
                        stream
                    )
                );
            }
        }
    }

    public void merge() {
        while (!minHeap.isEmpty()) {
            EventWrapper current = minHeap.poll();

            process(current.event);

            if (current.sourceStream.hasNext()) {
                minHeap.offer(
                    new EventWrapper(
                        current.sourceStream.next(),
                        current.sourceStream
                    )
                );
            }
        }
    }

    private void process(StreamEvent event) {
        // send to Kafka / DB / downstream system
        System.out.println(event);
    }
}
```

---

# 5. Time Complexity

For total N events and K streams:

## Each heap operation

```text
O(log K)
```

## Total

```text
O(N log K)
```

## Space

```text
O(K)
```

This is optimal.

---

# 6. Production-Ready Concerns

This is where senior-level discussion matters.

---

# 7. Handling Late Arriving Events

Problem:

```text
timestamp order may break
```

because distributed systems are messy.

Solution:

Use:

```text
Watermarks
```

like:
Apache Flink

and
Apache Spark

Example:

```text
wait 5 seconds before finalizing order
```

for late arrivals.

Very strong interview point.

---

# 8. Deduplication

Problem:

```text
same event may arrive twice
```

Solution:

Use:

```text
eventId + HashSet
```

or

```text
Redis TTL dedupe cache
```

like:

Redis

Production critical.

---

# 9. Backpressure

Problem:

```text
producer faster than consumer
```

Solution:

* bounded queues
* throttling
* batching
* retry queues

like:

Apache Kafka

systems.

Very important.

---

# 10. Fault Tolerance

Need:

* checkpointing
* replay support
* idempotent writes
* offset commits

Without this:

system is not production ready.

---

# 11. Ordering Guarantee

Need to define:

```text
event time vs processing time
```

This is a very senior discussion.

Most candidates miss this.

---

# 12. Follow-up Questions

Interviewers ask:

---

## What if K = 1 million?

Answer:

Use:

```text
Hierarchical Merge
```

instead of one giant heap

like:

```text
1000 local mergers
→ 100 regional mergers
→ final merger
```

This is excellent senior-level thinking.

---

## What if streams are infinite?

Answer:

Use:

```text
continuous polling + checkpointing
```

not one-time merge.

---

## What if stream crashes?

Answer:

Use:

```text
retry + dead letter queue
```

Very strong answer.

---

# 13. Design Patterns

### Strategy Pattern

for ordering policies

### Factory Pattern

for stream source creation

### Observer Pattern

for downstream subscribers

### Circuit Breaker

for stream failures

### Retry Pattern

for resilience

---

# Final Interview Answer

Say:

> I would use Min Heap–based K-way merge because the smallest among K stream heads is always the next globally sorted event. For production readiness, I would add watermarks for late arrivals, deduplication using Redis, checkpointing for fault tolerance, backpressure handling via bounded queues, and hierarchical merging for very large K systems.

This is the answer expected from strong Senior Engineers.
