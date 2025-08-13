Flash Cards


HLD Flashcards (10 Core Cards)
🏷️ Concept	💡 Flashcard
1. CAP Theorem	Q: What is the CAP theorem? 
A: You can only choose 2 out of Consistency, Availability, and Partition Tolerance in any distributed system.
2. Load Balancer	Q: Why use a Load Balancer? 
A: Distributes incoming traffic to backend servers to ensure high availability and fault tolerance.
3. CDN	Q: What is a CDN used for? 
A: A Content Delivery Network caches content closer to users to reduce latency and load time.
4. Caching	Q: Where and what to cache in system design? 
A: Cache read-heavy or expensive computations. Use Redis/Memcached at app or DB level.
5. Rate Limiter	Q: How do you design a rate limiter? 
A: Use Token Bucket or Leaky Bucket algorithm, stored in Redis for distributed systems.
6. Data Partitioning	Q: What is horizontal vs vertical partitioning? 
A: Horizontal = by rows (sharding), Vertical = by columns (splitting data by features).
7. Message Queue	Q: Why use MQs like Kafka/RabbitMQ? 
A: For async processing, decoupling services, buffering spikes.
8. Eventual Consistency	Q: Where would you use eventual consistency? 
A: Non-critical updates like feeds, counters, or analytics.
9. Scaling Database	Q: How do you scale a database? 
A: Use sharding, replication, read replicas, and eventual consistency strategies.
10. API Gateway	Q: Role of API Gateway in HLD? 
A: Acts as entry point, handles auth, routing, rate limiting, logging.



LLD Flashcards (10 Key Concepts)
🏷️ Topic	💡 Flashcard
1. SOLID	Q: What are SOLID principles? 
A: Single-responsibility, Open/closed, Liskov, Interface segregation, Dependency inversion.
2. OOP Principles	Q: Name core OOP concepts. 
A: Encapsulation, Inheritance, Polymorphism, Abstraction.
3. Factory Pattern	Q: When to use Factory pattern? 
A: When creating objects without exposing instantiation logic.
4. Strategy Pattern	Q: Strategy pattern use case? 
A: Define a family of algorithms, encapsulate them, and make them interchangeable.
5. Singleton Pattern	Q: Why use Singleton? 
A: To ensure a class has only one instance, e.g., DB connection.
6. Observer Pattern	Q: Use case of Observer? 
A: Notifying multiple subscribers of state changes (pub/sub).
7. Design Parking Lot	Q: What are classes needed for Parking Lot system? 
A: Vehicle, ParkingSpot, ParkingLot, Ticket, Payment.
8. Rate Limiter (LLD)	Q: How to implement a rate limiter? 
A: Use sliding window or token bucket, with a centralized counter.
9. Interface Segregation	Q: What is it? 
A: Clients shouldn’t be forced to depend on interfaces they don’t use.
10. Composition over Inheritance	Q: Why prefer composition? 
A: It's more flexible and reduces tight coupling.


 Behavioral Flashcards (Google Focused)
⭐ Scenario	💡 Flashcard
1. Conflict Resolution	Q: Tell me about a time you disagreed with a coworker. What did you do?
2. Ownership	Q: Tell me about a time you owned a critical project end-to-end.
3. System Failure	Q: Tell me about a system outage you handled.
4. Prioritization	Q: Give an example of handling conflicting priorities.
5. Mentoring	Q: How have you helped a junior engineer grow?
6. Data-Driven Decision	Q: Tell me about a time you used data to influence a decision.
7. Going Above and Beyond	Q: Tell me about a time you did something outside your job description.
8. Working Under Pressure	Q: How did you handle a high-pressure deadline situation?
9. Leadership Without Authority	Q: How did you influence a decision or team as an IC?
10. Feedback Culture	Q: Tell me about a time you received hard feedback. What did you do?

Problem-Solving Patterns (DSA)
🧠 Pattern Name	Description & Common Problems
Sliding Window	For arrays/strings with fixed/moving window sizes (e.g., max sum, substrings)
Two Pointers	Sorted arrays, remove duplicates, reverse string/array, palindrome check
Fast & Slow Pointers	Linked lists (cycle detection, middle, reorder list)
Binary Search	Sorted arrays, search range, rotated arrays, peak element
DFS/BFS	Trees, Graphs, Maze problems, Island counting
Backtracking	Permutations, combinations, Sudoku, N-Queens
Dynamic Programming	Knapsack, LCS, LIS, Matrix path, House Robber
Greedy	Interval scheduling, Huffman coding, Minimum platforms
Union Find (DSU)	Connected components, Kruskal’s MST, accounts merge
Heap / Priority Queue	Top K problems, Median in stream, Dijkstra’s algorithm
