# 🎯 Design Patterns in Java – Cheat Sheet

This guide covers **creational**, **structural**, and **behavioral** patterns with minimal working examples in Java.

---

## 🔨 Creational Patterns

### 1. Singleton

```java
public class Singleton {
    private static Singleton instance;

    private Singleton() {}

    public static synchronized Singleton getInstance() {
        if (instance == null)
            instance = new Singleton();
        return instance;
    }
}

// Double-Checked Locking 
public class Singleton {
    private static volatile Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null)
                    instance = new Singleton();
            }
        }
        return instance;
    }
}
```

---

### 2. Factory Method

```java

public class ShapeFactoryDemo {

    // 1. Product Interface
    interface Shape {
        void draw();
    }

    // 2. Concrete Products
    static class Circle implements Shape {
        @Override
        public void draw() {
            System.out.println("Drawing a Circle");
        }
    }

    static class Square implements Shape {
        @Override
        public void draw() {
            System.out.println("Drawing a Square");
        }
    }

    // 3. The Factory Class
    static class ShapeFactory {
        public Shape getShape(String type) {
            if (type == null) return null;
            if (type.equalsIgnoreCase("CIRCLE")) return new Circle();
            if (type.equalsIgnoreCase("SQUARE")) return new Square();
            return null;
        }
    }

    // 4. Client Code (Main Method)
    public static void main(String[] args) {
        ShapeFactory factory = new ShapeFactory();

        // Get a Circle and call its draw method
        Shape shape1 = factory.getShape("CIRCLE");
        shape1.draw();

        // Get a Square and call its draw method
        Shape shape2 = factory.getShape("SQUARE");
        shape2.draw();
    }
}


```

---

### 3. Builder

```java
class User {
    String name, email;

    static class Builder {
        String name, email;

        Builder name(String name) { this.name = name; return this; }
        Builder email(String email) { this.email = email; return this; }
        User build() {
            User u = new User();
            u.name = name;
            u.email = email;
            return u;
        }
    }
}
```

---

## 🧱 Structural Patterns

### 4. Adapter

```java
interface MediaPlayer {
    void play(String fileType, String fileName);
}

class AudioPlayer implements MediaPlayer {
    public void play(String type, String name) {
        if (type.equals("mp3"))
            System.out.println("Playing mp3: " + name);
        else
            new MediaAdapter(type).play(type, name);
    }
}

class MediaAdapter implements MediaPlayer {
    AdvancedPlayer advancedPlayer;

    MediaAdapter(String type) {
        if (type.equals("vlc")) advancedPlayer = new VlcPlayer();
    }

    public void play(String type, String name) {
        advancedPlayer.playVlc(name);
    }
}

interface AdvancedPlayer { void playVlc(String name); }

class VlcPlayer implements AdvancedPlayer {
    public void playVlc(String name) {
        System.out.println("Playing vlc: " + name);
    }
}
```

---

### 5. Decorator

```java
interface Coffee {
    String getDescription();
}

class BasicCoffee implements Coffee {
    public String getDescription() { return "Basic Coffee"; }
}

class MilkDecorator implements Coffee {
    Coffee coffee;
    MilkDecorator(Coffee coffee) { this.coffee = coffee; }

    public String getDescription() {
        return coffee.getDescription() + ", Milk";
    }
}
```

---

### 6. Facade

```java
class CPU { void start() { System.out.println("CPU started"); } }
class Memory { void load() { System.out.println("Memory loaded"); } }

class Computer {
    CPU cpu = new CPU();
    Memory memory = new Memory();

    void start() {
        cpu.start();
        memory.load();
    }
}
```

---

## 🤝 Behavioral Patterns

### 7. Strategy

```java
interface PaymentStrategy {
    void pay(int amount);
}

class CardPayment implements PaymentStrategy {
    public void pay(int amount) {
        System.out.println("Paid " + amount + " with card");
    }
}

class ShoppingCart {
    PaymentStrategy strategy;

    ShoppingCart(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    void checkout(int amount) {
        strategy.pay(amount);
    }
}
```

---

### 8. Observer

```java
interface Observer { void update(String msg); }

class Subscriber implements Observer {
    String name;
    Subscriber(String name) { this.name = name; }

    public void update(String msg) {
        System.out.println(name + " received: " + msg);
    }
}

class Publisher {
    List<Observer> observers = new ArrayList<>();
    void subscribe(Observer o) { observers.add(o); }
    void notifyAll(String msg) {
        for (Observer o : observers) o.update(msg);
    }
}
```

---

### 9. Command

```java
interface Command { void execute(); }

class Light {
    void on() { System.out.println("Light On"); }
}

class LightOnCommand implements Command {
    Light light;
    LightOnCommand(Light light) { this.light = light; }

    public void execute() {
        light.on();
    }
}

class RemoteControl {
    Command command;
    void setCommand(Command command) { this.command = command; }
    void pressButton() { command.execute(); }
}
```

---

##  Tips for Use

- Use these snippets for **interview revision**, **blog posts**, or **coding challenges**.
- Keep them in folders like `patterns/creational`, `patterns/behavioral`, etc.
- Try enhancing examples with **unit tests** and **real-world use cases** (e.g., order processing, billing, user registration).

---

