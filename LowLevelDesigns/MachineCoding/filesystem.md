# File System – Low Level Design (LLD)

This is a classic Senior Software Engineer / Machine Coding design question.

The expectation is usually:

* Design extensible OOP classes
* Handle directories + files
* Support operations like:

  * create file
  * create directory
  * delete
  * move
  * rename
  * list contents
  * search
  * read/write file
  * size calculation
  * permissions (optional advanced)
  * versioning (optional advanced)

This is essentially designing a mini Unix/Linux-style file system.

---

# 1. Core Design Principle

Use the **Composite Design Pattern**

Because:

* File = leaf node
* Directory = composite node containing files/directories

Both should behave similarly from client perspective.

---

# 2. Class Diagram (Conceptual)

```text
                    +------------------+
                    | FileSystemNode   |   (abstract)
                    +------------------+
                    | name             |
                    | path             |
                    | createdAt        |
                    | modifiedAt       |
                    | parent           |
                    +------------------+
                    | getSize()        |
                    | delete()         |
                    | rename()         |
                    +------------------+
                           / \
                          /   \
                         /     \
                        /       \
         +----------------+   +------------------+
         | File           |   | Directory        |
         +----------------+   +------------------+
         | content        |   | children         |
         | extension      |   | Map<String,node> |
         +----------------+   +------------------+
         | read()         |   | add()            |
         | write()        |   | remove()         |
         +----------------+   | list()           |
                              +------------------+

```

---

# 3. Java Code (Interview Ready)

```java
import java.util.*;

abstract class FileSystemNode {
    protected String name;
    protected Directory parent;
    protected Date createdAt;
    protected Date modifiedAt;

    public FileSystemNode(String name) {
        this.name = name;
        this.createdAt = new Date();
        this.modifiedAt = new Date();
    }

    public String getName() {
        return name;
    }

    public void rename(String newName) {
        this.name = newName;
        this.modifiedAt = new Date();
    }

    public String getPath() {
        if (parent == null) return "/" + name;
        return parent.getPath() + "/" + name;
    }

    public abstract int getSize();
}

class File extends FileSystemNode {
    private String content;

    public File(String name) {
        super(name);
        this.content = "";
    }

    public void write(String content) {
        this.content = content;
        this.modifiedAt = new Date();
    }

    public String read() {
        return content;
    }

    @Override
    public int getSize() {
        return content.length();
    }
}

class Directory extends FileSystemNode {
    private Map<String, FileSystemNode> children;

    public Directory(String name) {
        super(name);
        children = new HashMap<>();
    }

    public void add(FileSystemNode node) {
        children.put(node.getName(), node);
        node.parent = this;
    }

    public void remove(String name) {
        children.remove(name);
    }

    public FileSystemNode get(String name) {
        return children.get(name);
    }

    public List<String> list() {
        return new ArrayList<>(children.keySet());
    }

    @Override
    public int getSize() {
        int size = 0;
        for (FileSystemNode node : children.values()) {
            size += node.getSize();
        }
        return size;
    }
}

class FileSystem {
    private Directory root;

    public FileSystem() {
        root = new Directory("root");
    }

    public Directory getRoot() {
        return root;
    }
}

public class Main {
    public static void main(String[] args) {
        FileSystem fs = new FileSystem();

        Directory root = fs.getRoot();

        Directory docs = new Directory("Documents");
        root.add(docs);

        File file = new File("resume.txt");
        file.write("Senior Software Engineer Resume");

        docs.add(file);

        System.out.println(file.read());
        System.out.println(file.getPath());
        System.out.println(docs.list());
        System.out.println("Directory Size: " + docs.getSize());
    }
}
```

---

# 4. Expected Interview Discussion

Senior-level discussion should include:

---

## Why Composite Pattern?

Because:

* File and Directory treated uniformly
* Common abstraction simplifies APIs
* Extensible for symbolic links, shortcuts, etc.

---

## Why Map instead of List?

```java
Map<String, FileSystemNode>
```

Because:

* O(1) lookup
* faster search
* prevents duplicate names

Very important interview point.

---

## Real-world Optimizations

Production systems add:

### Inode-based storage

Instead of storing full metadata repeatedly.

### Permissions

```text
rwx for owner/group/others
```

### Locks

Concurrent access handling.

### Journaling

Crash recovery.

### Replication

For distributed file systems.

### Snapshot / Versioning

Rollback support.

### Quotas

Storage limits per user.

---

# 5. Follow-up Questions Asked in Interviews

Very common:

### How will you support:

* ls
* cd
* pwd
* find
* mv
* cp
* soft links
* hard links
* permissions
* locking
* distributed FS (HDFS, GFS)

You should be ready.

---

# 6. Senior Engineer Level Improvement

Interviewer gets impressed if you mention:

### Command Pattern

for undo/redo

### Observer Pattern

for file watchers

### Strategy Pattern

for storage policy

### Factory Pattern

for node creation

This pushes answer from Mid-level → Senior-level.

---

# Final Interview Summary

Say:

> I would use Composite Pattern with File and Directory inheriting from a common abstract node. Directory acts as a composite and File as a leaf. Children are maintained using HashMap for O(1) lookup. This design is extensible for permissions, locking, versioning, journaling, and even distributed file system evolution.

This is the ideal senior-level answer.
