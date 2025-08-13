Kafka Cheat Sheet


Core Concepts
Term	Description
Broker	Kafka server that stores data and serves clients
Producer	Sends messages to Kafka topics
Consumer	Subscribes to and processes messages from topics
Topic	Named stream of data records
Partition	Sub-unit of a topic enabling parallelism
Offset	Unique ID for a message within a partition
Consumer Group	Group of consumers sharing work (each partition only assigned to one consumer)
Replication Factor	Number of copies of each partition
Leader & Follower	One partition leader handles all writes; others replicate (followers)

⚙️ Key Configs
Type	Config	Description
Producer	acks=all	Wait for all ISR to ack before success
Producer	retries, retry.backoff.ms	Retry strategy for failed sends
Consumer	enable.auto.commit	Auto vs manual offset commit
Consumer	max.poll.records	Number of records to return in one poll
Topic	cleanup.policy=compact	Log compaction enabled
Topic	retention.ms / retention.bytes	Controls data retention

🧠 Delivery Semantics
Mode	Description
At-most-once	Risk of message loss (ack before processing)
At-least-once	May result in duplicates (process before commit)
Exactly-once	Requires idempotent producer + transactional consumer (complex, costly)

🧪 Kafka Tools
* Kafka CLI: kafka-topics.sh, kafka-console-consumer.sh, etc.
* Kafka Connect: Source/sink connectors (DB → Kafka, Kafka → Elasticsearch)
* Kafka Streams: Java DSL for stream processing
* ksqlDB: SQL over Kafka Streams
* MirrorMaker: Cross-cluster replication

📊 Monitoring Metrics
Metric	Meaning
consumer_lag	Difference between latest offset and current position
under_replicated_partitions	Partitions with missing replicas
broker count	Health of the Kafka cluster
request rate / latency	Throughput and responsiveness

🧠 Kafka Flashcards
🔹 Basics
Q: What is Kafka used for? A: Distributed event streaming, messaging, log aggregation, and real-time data processing.
Q: Difference between Topic and Partition? A: Topics are logical categories; partitions are sub-units that allow parallelism and scalability.
Q: What is an offset in Kafka? A: A unique, sequential ID of a message within a partition.

🔹 Architecture
Q: How does Kafka achieve fault tolerance? A: Via replication (leader/follower) and in-sync replicas (ISRs).
Q: What is ISR (In-Sync Replica)? A: Replicas that are fully caught up with the leader.
Q: What happens during a partition leader failure? A: A new leader is elected from the ISR list.

🔹 Consumers & Producers
Q: How does a consumer group work? A: Each partition is assigned to only one consumer in the group to balance load.
Q: How can Kafka ensure exactly-once semantics? A: With idempotent producers and transactional APIs.
Q: What is the purpose of acks=all? A: Ensures message durability by waiting for all ISRs to acknowledge a write.

🔹 Advanced
Q: What is log compaction? A: Kafka retains only the latest message per key, useful for changelog-type data.
Q: What is the difference between Kafka Streams and Kafka Connect? A: Streams is for in-app processing, Connect is for integrating with external systems.
Q: What is KRaft mode? A: Kafka’s new mode that eliminates the need for ZooKeeper (uses its own Raft consensus).



