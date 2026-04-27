import java.util.*;

class LogEntry {
    String message;
    int timestamp;

    public LogEntry(String message, int timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}

class LoggerRateLimiter {
    private final int limit;
    private final Map<String, Integer> map;
    private final Queue<LogEntry> queue;

    public LoggerRateLimiter(int limit) {
        this.limit = limit;
        this.map = new HashMap<>();
        this.queue = new LinkedList<>();
    }

    public boolean shouldPrint(String message, int timestamp) {
        cleanup(timestamp);

        if (!map.containsKey(message)) {
            map.put(message, timestamp);
            queue.offer(new LogEntry(message, timestamp));
            return true;
        }

        return false;
    }

    private void cleanup(int currentTime) {
        while (!queue.isEmpty()
                && currentTime - queue.peek().timestamp >= limit) {

            LogEntry old = queue.poll();

            if (map.get(old.message) == old.timestamp) {
                map.remove(old.message);
            }
        }
    }
}
