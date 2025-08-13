LLD

LLD (Low-Level Design) – Interview Cheatsheet
Quick-reference guide to help during LLD rounds.

✅ Core Concepts
OOP Principles:
* Encapsulation: Hide internal state, expose minimal API
* Abstraction: Focus on what an object does, not how
* Inheritance: Reuse behavior from parent
* Polymorphism: Same interface, different behavior

SOLID Principles:
* S – One class = one reason to change
* O – Open for extension, closed for modification
* L – Subtypes should behave like base types
* I – Prefer small, specific interfaces
* D – Depend on abstractions, not concretions

🔁 Design Patterns (Most Asked)
Pattern	Use Case
Singleton	One instance: Logger, ParkingLot
Factory	Create objects based on input
Builder	Step-by-step object creation (User, Pizza)
Strategy	Runtime behavior swap (Payment, Auth)
Observer	Notify subscribers (Chat, Events)
Decorator	Add features dynamically (Coffee)
📦 System Cheatsheet
🚗 Parking Lot
* Singleton ParkingLot
* Spot, Floor, Vehicle, Ticket
* Spot allocation strategy
🎮 Tic-Tac-Toe
* Board, Cell, Player
* Game class with turns and win check
🎟️ BookMyShow
* Movie, Theatre, Show, Seat
* SeatLocking with timeout
* Booking + Payment flow
📩 Notification System
* Notification type: Email/SMS/Push
* Notifier interface + implementations
* Retry/Logging via Decorator
🚦 Rate Limiter
* Interface: allowRequest(String userId)
* Use Token Bucket / Leaky Bucket
🧾 Splitwise
* User, Expense, Split, Group
* Strategy pattern for split: Equal, Exact, Percent
🛗 Elevator System
* Elevator, Floor, Button, Controller
* Handle multiple elevators
* Direction + Request scheduling logic
🍔 Food Delivery App
* User, Restaurant, Menu, Order, DeliveryPartner
* Search + Cart + Payment + ETA tracking
* Strategy for assigning delivery
🛒 Amazon Cart System
* Product, Cart, Item, Inventory, Checkout
* Add/remove/update items
* Merge carts across devices (Session/User)
📓 Logging Framework
* Logger, Appender, Formatter
* Strategy pattern for log levels (INFO/ERROR/DEBUG)
* Observer pattern to notify appenders
⏰ Notification Scheduler
* Notification, ScheduleJob, Queue, Retry logic
* Cron-based/Delay queue based execution

🛠️ Implementation Tips
* Always start with core entities & relationships
* Use interfaces for extensibility
* Patterns show maturity: identify 1–2 per design
* Avoid over-engineering – keep it simple

🔁 Frequent LLD Scenarios
* Elevator System
* Food Delivery
* Amazon Cart System
* Logging Framework
* Notification Scheduler
* Splitwise
* Rate Limiter
* Parking Lot
* BookMyShow
* Tic-Tac-Toe
* Chat System
* Online Chess
