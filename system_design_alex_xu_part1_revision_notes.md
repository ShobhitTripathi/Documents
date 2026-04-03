# CHAPTER 1: SCALE FROM ZERO TO MILLIONS OF USERS

```
Single server setup
Databases - which DB to use
Vertical scaling vs horizontal scaling
Load balancer
Database replication

Cache - Cache Tier
  Considerations for using cache
    Decide when to use cache: when data is read frequently but modified infrequently.
    Expiration policy: Once cached data is expired, it is removed from the cache. Not too long and not too short.
    Consistency: keeping the data store and the cache in sync
    Mitigating failures: multiple cache servers across different data centers, overprovision the required memory by certain percentages
    Eviction Policy: LRU [mostly used], LFU, FIFO

Content delivery network (CDN):
  network of geographically dispersed servers used to deliver static content
  Considerations of using a CDN :
    COST,
    Setting an appropriate cache expiry
    CDN Fallback
    Invalidating files:

Stateless web tier
  A stateful server and stateless server has some key differences. A stateful server remembers
  client data (state) from one request to the next. A stateless server keeps no state information.

Data centers

Message queue
  A message queue is a durable component, stored in memory, that supports asynchronous
  communication. It serves as a buffer and distributes asynchronous requests.

Logging, metrics, automation
  Logging: monitor error logs at per server level or use tools to aggregate them to a centralized service for easy search and viewing.
  Metrics: gain business insights and understand  the health status of the system. Some of the following metrics are useful:
      Host level metrics: CPU, Memory, disk I/O, etc.
      Aggregated level metrics: for example, the performance of the entire database tier, cachetier, etc.
      Key business metrics: daily active users, retention, revenue, etc.
  Automation: Continuous integration, IT, UT, Buil test etc

Database scaling
  Vertical scaling
  Horizontal scaling

Sharding
Sharding separates large databases into smaller, more easily managed parts called shards.
Each shard shares the same schema, though the actual data on each shard is unique to the shard.
It introduces complexities and new challenges to the system:
  Resharding data: Resharding data is needed when
                  1) a single shard could no longer hold more data due to rapid growth.
                  2) Certain shards might experience shard exhaustion faster than others due to uneven data distribution.
  Celebrity problem: Excessive access to a specific shard could cause server overload.
                      To solve this problem, we may need to allocate a shard for each celebrity.
  Join and de-normalization: hard to perform join operations across database shards.
                            A common workaround is to denormalizethe database so that queries can be performed in a single table.

Millions of users and beyond
summary of how we scaleour system to support millions of users:
• Keep web tier stateless
• Build redundancy at every tier
• Cache data as much as you can
• Support multiple data centers
• Host static assets in CDN
• Scale your data tier by sharding
• Split tiers into individual services
• Monitor your system and use automation tools
  
```

# CHAPTER 2: BACK-OF-THE-ENVELOPE ESTIMATION

```

```





