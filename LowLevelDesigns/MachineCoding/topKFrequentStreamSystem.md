# Top K Frequent Stream System – Low Level Design (LLD)

This is a strong Senior SWE / machine-coding question because it tests:

* streaming system thinking
* heap design
* hashmap optimization
* scalability discussion
* real-time updates
* tradeoff analysis

Typical requirement:

> Design a system that receives a continuous stream of events/items and should always be able to return Top K most frequent elements efficiently.

Example:

```text
Stream:
A, B, A, C, A, B, D, B, B

K = 2

Output:
B -> 4
A -> 3
```

---

# 1. Core Design Idea

Use:

```text
HashMap + Min Heap
```

Because:

### HashMap

stores:

```text
item → frequency
```

for O(1) update

### Min Heap of size K

stores only Top K frequent elements

for:

```text
O(log K)
```

efficient maintenance

This is the optimal practical design.

---

# 2. Class Diagram

```text
+----------------------+
| TopKFrequentSystem   |
+----------------------+
| frequencyMap         |
| minHeap              |
| k                    |
+----------------------+
| add(item)            |
| getTopK()            |
| rebalanceHeap()      |
+----------------------+

+----------------------+
| Node                 |
+----------------------+
| item                 |
| frequency            |
+----------------------+
```

---

# 3. Java Code (Interview Ready)

```java
import java.util.*;

class Node {
    String item;
    int frequency;

    public Node(String item, int frequency) {
        this.item = item;
        this.frequency = frequency;
    }
}

class TopKFrequentSystem {
    private final int k;

    // item -> frequency
    private final Map<String, Integer> frequencyMap;

    // min heap based on frequency
    private final PriorityQueue<Node> minHeap;

    public TopKFrequentSystem(int k) {
        this.k = k;
        this.frequencyMap = new HashMap<>();

        this.minHeap = new PriorityQueue<>(
                (a, b) -> a.frequency - b.frequency
        );
    }

    public void add(String item) {
        int updatedFreq = frequencyMap.getOrDefault(item, 0) + 1;
        frequencyMap.put(item, updatedFreq);

        rebalanceHeap(item, updatedFreq);
    }

    private void rebalanceHeap(String item, int freq) {
        Node existing = null;

        // remove old node if exists
        for (Node node : minHeap) {
            if (node.item.equals(item)) {
                existing = node;
                break;
            }
        }

        if (existing != null) {
            minHeap.remove(existing);
        }

        if (minHeap.size() < k) {
            minHeap.offer(new Node(item, freq));
        } else if (freq > minHeap.peek().frequency) {
            minHeap.poll();
            minHeap.offer(new Node(item, freq));
        }
    }

    public List<String> getTopK() {
        List<Node> nodes = new ArrayList<>(minHeap);

        nodes.sort((a, b) -> b.frequency - a.frequency);

        List<String> result = new ArrayList<>();

        for (Node node : nodes) {
            result.add(node.item + " -> " + node.frequency);
        }

        return result;
    }
}

public class Main {
    public static void main(String[] args) {
        TopKFrequentSystem system = new TopKFrequentSystem(2);

        system.add("A");
        system.add("B");
        system.add("A");
        system.add("C");
        system.add("A");
        system.add("B");
        system.add("D");
        system.add("B");
        system.add("B");

        System.out.println(system.getTopK());
    }
}
```

---

# 4. Time Complexity

## add(item)

### HashMap update

```text
O(1)
```

### Heap maintenance

```text
O(log K)
```

### Current implementation issue

Searching existing node in heap:

```text
O(K)
```

So actual:

```text
O(K)
```

This is important to mention.

Senior engineers must identify this.

---

# 5. Senior-Level Optimization

To make truly efficient:

Use:

```text
HashMap<String, Node>
```

extra map for direct node reference

Then:

```text
remove/update = O(log K)
```

instead of O(K)

This is a major interview differentiator.

---

# 6. Follow-up Production Questions

Interviewer may ask:

---

## What if stream is infinite?

Answer:

Use:

```text
sliding window frequency
```

instead of lifetime frequency

Example:

Top K in last 1 hour

not

Top K since system start

---

## What if data is distributed?

Answer:

Use:

```text
Kafka + Partition-wise TopK + Aggregator
```

Each partition computes local TopK

then central aggregator merges them

Like:

Apache Kafka

production-grade systems.

---

## What about exact vs approximate TopK?

Answer:

### Exact

HashMap + Heap

### Approximate

Use:

```text
Count-Min Sketch
```

for massive scale systems

Very strong senior-level answer.

---

## What if frequency decreases?

Use:

```text
Indexed Heap
```

or

```text
TreeMap + HashMap
```

for bidirectional updates.

---

# 7. Design Patterns Mention

Good bonus points:

### Strategy Pattern

for exact vs approximate TopK

### Factory Pattern

for different stream processors

### Observer Pattern

for event listeners

### Singleton

for central metrics collector

---

# Final Interview Answer

Say:

> I would use HashMap for O(1) frequency tracking and a Min Heap of size K for maintaining Top K elements efficiently. For production-grade systems, I would optimize heap updates using direct node references, support sliding windows for infinite streams, and use partition-wise aggregation for distributed systems like Apache Kafka.

That is the senior-level answer interviewers expect.
