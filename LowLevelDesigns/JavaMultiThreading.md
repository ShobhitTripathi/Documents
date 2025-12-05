Here is the **ultimate Java Multithreading Cheat Sheet** — fast to revise, loaded with interview-ready points, and structured by concepts → APIs → patterns → pitfalls.

---

# 🚀 **JAVA MULTITHREADING CHEAT SHEET (INTERVIEW-READY)**

---

# **1️⃣ Thread Basics**

### **Create Threads**

```java
class MyThread extends Thread {
    public void run() { ... }
}
new MyThread().start();
```

```java
Runnable r = () -> { ... };
new Thread(r).start();
```

### **Thread Lifecycle**

NEW → RUNNABLE → RUNNING → BLOCKED/WAITING/TIMED_WAITING → TERMINATED

---

# **2️⃣ synchronized Keyword**

### **Method-level Lock**

```java
public synchronized void foo() {}
```

### **Block-level Lock**

```java
synchronized(this) { ... }
```

### **Class-level Lock**

```java
synchronized(MyClass.class) { ... }
```

### **Guarantees**

✔ Mutual exclusion
✔ Visibility (happens-before exit → entry)

---

# **3️⃣ volatile Keyword**

* Ensures **visibility** (no caching)
* Prevents **instruction reordering**
* **Does NOT** ensure atomicity
* Good for:

```java
volatile boolean running = true;
```

---

# **4️⃣ Locks API (java.util.concurrent.locks)**

### **ReentrantLock**

```java
Lock lock = new ReentrantLock();
lock.lock();
try { ... } 
finally { lock.unlock(); }
```

✔ tryLock()
✔ interruptible locking
✔ fairness option

### **ReadWriteLock**

* Multiple readers allow concurrency
* Writers get exclusive lock

---

# **5️⃣ Atomic Classes (Lock-free)**

### Using **CAS (Compare-And-Set)** internally

```java
AtomicInteger counter = new AtomicInteger();
counter.incrementAndGet();
```

Types:
AtomicBoolean, AtomicLong, AtomicReference, AtomicIntegerArray, etc.

---

# **6️⃣ Executor Framework (Thread Pools)**

### **Creating Thread Pools**

```java
ExecutorService pool = Executors.newFixedThreadPool(10);
pool.submit(() -> {...});
```

### ThreadPoolExecutor (Full control)

Constructor arguments:

* corePoolSize
* maxPoolSize
* keepAliveTime
* workQueue
* threadFactory
* RejectedExecutionHandler

### Built-in thread pools

* FixedThreadPool → bounded threads
* CachedThreadPool → unbounded, for short tasks
* ScheduledThreadPool → cron-like
* SingleThreadExecutor → sequential execution

---

# **7️⃣ CompletableFuture (Async + Non-blocking)**

### Run async task

```java
CompletableFuture.runAsync(() -> {...});
```

### Return result

```java
CompletableFuture.supplyAsync(() -> 10)
    .thenApply(x -> x * 2)
    .thenAccept(System.out::println);
```

### Parallel API calls

```java
CompletableFuture.allOf(f1, f2, f3)
```

---

# **8️⃣ Concurrent Collections**

### ConcurrentHashMap

✔ Lock striping
✔ CAS
✔ Tree bins (JDK 8+)

```java
map.putIfAbsent(key, value);
map.compute(...);
```

### Blocking Queues (producer-consumer)

* ArrayBlockingQueue
* LinkedBlockingQueue
* PriorityBlockingQueue
* DelayQueue

---

# **9️⃣ ThreadLocal**

### Each thread gets its own copy

```java
ThreadLocal<SimpleDateFormat> df = ThreadLocal.withInitial(
    () -> new SimpleDateFormat("yyyy-MM-dd")
);
```

Used for:

* Request context
* User session
* Formatter caching

---

# 🔟 wait(), notify(), notifyAll()

### Used for low-level coordination (avoid if possible)

```java
synchronized(obj) {
    obj.wait();
}

synchronized(obj) {
    obj.notify();
}
```

Rules:

* Must be inside **synchronized**
* Releases the monitor when wait() is called
* notify() wakes **one** waiting thread
* notifyAll() wakes **all**

---

# 1️⃣1️⃣ Thread Safety Strategies

### ✔ Avoid shared state

### ✔ Use immutable objects

### ✔ Use thread pools

### ✔ Prefer concurrent collections

### ✔ Use atomic & lock-free algorithms where possible

### ✔ Minimize locking granularity

---

# 1️⃣2️⃣ Common Concurrency Problems

### **Race Condition**

Multiple threads modify shared state unsafely.

### **Deadlock**

Thread A waits on B, B waits on A.

### **Livelock**

Threads keep changing state but make no progress.

### **Starvation**

Thread never gets CPU or resource.

---

# 1️⃣3️⃣ Memory Model (JMM) — MUST KNOW

### **Happens-Before Rules**

* synchronized unlock → synchronized lock
* volatile write → volatile read
* Thread start() → new thread run()
* Thread completion → join()

---

# 1️⃣4️⃣ Performance Concepts

### **False Sharing**

Two threads modify variables on the same cache line → cache invalidation storms.

### **Lock Contention**

Too many threads try to acquire same lock → throughput drops.

### **Lock Coarsening / Lock Elimination**

Done by JVM JIT for optimization.

---

# 1️⃣5️⃣ Useful Patterns

### **Producer–Consumer (BlockingQueue)**

### **Future + Callable**

### **Read-Write Lock Pattern**

### **Fork-Join (Work Stealing)**

### **Double-Checked Locking + volatile**

---

# ⚡ Literally 30-second Recap

* synchronized = mutual exclusion + visibility
* volatile = visibility + ordering
* atomic classes = lock-free CAS
* ExecutorService = scalable thread management
* CompletableFuture = async non-blocking workflows
* ThreadLocal = per-thread memory
* ConcurrentHashMap = high-performance map
* BlockingQueue = producer-consumer
* Deadlock/starvation/livelock — know definitions

---

If you want, I can also give you a **PDF version of this cheat sheet**, or a **100-interview-question set on multithreading**.
