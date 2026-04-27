# Producer Consumer (Blocking Queue) – LLD (Production Ready)

This is one of the most important Senior SWE machine coding + concurrency design questions.

It tests:

* multithreading fundamentals
* synchronization
* race condition handling
* thread safety
* backpressure
* bounded buffer design
* real production system thinking

Typical requirement:

> Design a Producer-Consumer system using a Blocking Queue where:

* producers add tasks/messages/jobs
* consumers process them
* queue is bounded
* producers wait if full
* consumers wait if empty

Used in:

* task schedulers
* log ingestion systems
* Kafka consumers
* notification systems
* async job processors
* DB write pipelines

---

# 1. Core Design Idea

Use:

```text id="w6s33f"
Bounded Blocking Queue
```

with:

```text id="fbwthm"
wait() + notifyAll()
```

or

```text id="c5g6pz"
Lock + Condition
```

This ensures:

* thread safety
* no busy waiting
* proper synchronization
* backpressure support

This is the real interview expectation.

---

# 2. Class Diagram

```text id="hzbric"
+----------------------+
| Task                 |
+----------------------+
| id                   |
| payload              |
+----------------------+

+----------------------+
| BlockingQueue<T>     |
+----------------------+
| queue                |
| capacity             |
+----------------------+
| put()                |
| take()               |
+----------------------+

+----------------------+
| Producer             |
+----------------------+
| produce()            |
+----------------------+

+----------------------+
| Consumer             |
+----------------------+
| consume()            |
+----------------------+
```

---

# 3. Java Code (Interview Ready)

```java id="g4fd6m"
import java.util.*;

class BlockingQueue<T> {
    private final Queue<T> queue;
    private final int capacity;

    public BlockingQueue(int capacity) {
        this.queue = new LinkedList<>();
        this.capacity = capacity;
    }

    public synchronized void put(T item) throws InterruptedException {
        while (queue.size() == capacity) {
            wait(); // producer waits if full
        }

        queue.offer(item);
        notifyAll(); // wake consumers
    }

    public synchronized T take() throws InterruptedException {
        while (queue.isEmpty()) {
            wait(); // consumer waits if empty
        }

        T item = queue.poll();
        notifyAll(); // wake producers
        return item;
    }

    public synchronized int size() {
        return queue.size();
    }
}

class Producer implements Runnable {
    private final BlockingQueue<Integer> queue;

    public Producer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        int value = 1;

        while (true) {
            try {
                queue.put(value);
                System.out.println("Produced: " + value);
                value++;

                Thread.sleep(500);
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class Consumer implements Runnable {
    private final BlockingQueue<Integer> queue;

    public Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int value = queue.take();
                System.out.println("Consumed: " + value);

                Thread.sleep(1000);
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new BlockingQueue<>(5);

        Thread producer1 = new Thread(new Producer(queue));
        Thread producer2 = new Thread(new Producer(queue));

        Thread consumer1 = new Thread(new Consumer(queue));

        producer1.start();
        producer2.start();
        consumer1.start();
    }
}
```

---

# 4. Time Complexity

## put()

```text id="vs4xbo"
O(1)
```

## take()

```text id="vvpy0h"
O(1)
```

Synchronization overhead exists but logically O(1).

---

# 5. Senior-Level Production Improvement

Above solution works.

But interviewer expects:

> What is wrong with synchronized + wait()?

Answer:

---

# 6. Better Solution

Use:

```text id="lpkndd"
ReentrantLock + Condition
```

instead of:

```text id="e8x52u"
synchronized
```

Because:

* better control
* separate producer/consumer conditions
* fairness support
* interruptible locks
* timeout support
* production-grade behavior

This is a major senior-level differentiator.

---

# 7. Production Version

```java id="tth7i8"
import java.util.*;
import java.util.concurrent.locks.*;

class BetterBlockingQueue<T> {
    private final Queue<T> queue = new LinkedList<>();
    private final int capacity;

    private final ReentrantLock lock = new ReentrantLock();

    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public BetterBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public void put(T item) throws InterruptedException {
        lock.lock();

        try {
            while (queue.size() == capacity) {
                notFull.await();
            }

            queue.offer(item);

            notEmpty.signal();

        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();

        try {
            while (queue.isEmpty()) {
                notEmpty.await();
            }

            T item = queue.poll();

            notFull.signal();

            return item;

        } finally {
            lock.unlock();
        }
    }
}
```

This is much stronger.

---

# 8. Real Production Concerns

---

## What if consumer crashes?

Need:

* retry
* dead letter queue
* visibility timeout

like:
Amazon SQS

---

## What if producers are too fast?

Need:

```text id="h2wwxm"
backpressure
```

* bounded queue
* rate limiting
* throttling

critical in:
Apache Kafka

systems.

---

## What about priority tasks?

Use:

```text id="0zq3tq"
PriorityBlockingQueue
```

---

## Need delayed tasks?

Use:

```text id="pc6zji"
DelayQueue
```

---

## Need distributed queue?

Use:

* Apache Kafka
* RabbitMQ
* Amazon SQS
* Redis

instead of in-memory queue.

---

# 9. Design Patterns

### Producer-Consumer Pattern

obviously

### Strategy Pattern

for processing policies

### Factory Pattern

for worker creation

### Retry Pattern

for failures

### Circuit Breaker

for downstream failures

### Observer Pattern

for event notifications

---

# 10. Final Interview Answer

Say:

> I would design a bounded blocking queue using wait/notify or preferably ReentrantLock + Condition for better concurrency control. Producers block when the queue is full and consumers block when empty, ensuring proper backpressure and thread safety. For production systems, I would add retries, DLQ, visibility timeout, and move to distributed queues like Kafka or SQS when horizontal scaling is needed.

This is the strong senior-level answer interviewers expect.
