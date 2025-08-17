# System Design - Design Google Calendar

## Functional Requirements:
```
Book an event: In a calendar, we are able to add an event with other people. It can be a single user or multiple users.
Check availability of users: While we are scheduling an event before that if we can figure out in that particular time the person is available or not.
Invite users to the meeting/event: The user should be able to invite other users to the meeting or event. The number of users invited for a scheduled meeting can be one or more than one.
Request RSVP: Before posting an invite to the person we want to know if that particular user is already blocked or if he is available. So these types of responses can be handled by requesting RSVP.
Meeting Reminders: Getting a notification before the time for a particular meeting with a particular person about the agenda and all those details would help prepare for the meeting beforehand.
Modify the meeting /event: This includes making changes to an existing meeting or event that you have created or have been invited to.
Cancel a meeting/event: This includes deleting or removing a meeting or event that you have created or have been invited to.
Lookup for all meetings in the calendar: This includes viewing a list of all events that have been designated as meetings or events.
View Calendar: This allows the user to see their scheduled events and appointments in a visual format which can be a monthly, weekly, or daily view, and it includes details such as the date, time, location, and description of each event.
```

## Non-Functional Requirements:
```
High availability: This means that users should be able to use Google Calendar whenever they need it, without experiencing any significant interruptions or downtime.
Eventual Consistency: This means that sometimes changes made to an event might not show up for everyone right away, but eventually the changes will be applied and everyone's calendar will show the same updated event.
Low/minimum latency: This means that the application should respond quickly to your requests and updates, so you don't have to wait for a long time to see your changes.
Durable storage: The system should be durable enough to not lose any kind of user and meeting data.
```

## Memory and Estimations of the System

```
Let's assume the system is designed for 100M users.
So, the number of concurrent users = 100M
And out of all these users, 1/10 of the people are actually scheduling the event.

So, 1/10 post invites = 10M
And assuming a particular person creates 5 events in a day.
So, 5 events/ user=50M invites per day

So, storage for 1 invite={ Meeting content +userId, invite list+date } = 1 KB

Therefore, the Total storage=1KB*50M = 50,000,000KB = 50GB/day
Storage for 1 year = 50*365 = 20TB/year
```

## Data Flow Diagram

<img width="229" height="931" alt="image" src="https://github.com/user-attachments/assets/f9de8baa-8a1a-4418-8038-a76772a74e3b" />


<img width="586" height="656" alt="image" src="https://github.com/user-attachments/assets/a7fe41ee-3066-43a0-8b11-40184081568c" />

## Database Schema

<img width="820" height="926" alt="image" src="https://github.com/user-attachments/assets/9d6069a0-568f-4de3-88ba-930f2f9ef57e" />


## High-Level Design of the System

<img width="1001" height="501" alt="image" src="https://github.com/user-attachments/assets/fd241694-9d59-46e5-8b3b-57552cd81f56" />

<img width="1001" height="501" alt="image" src="https://github.com/user-attachments/assets/6cb986c3-623d-4239-9562-4750dc3a2de3" />

## API Design

```
Let us do a basic API design for our services:

1. Display info for each user.
@GET /api/users/{userId}

For Scheduled Meetings or Events
2. Get meetings for the user for the current week
@GET /api/users/{userId}/meetings
{
     date: currentDate,
     days: 7
}

3. Creating the meeting Event
@POST /api/users/{userId}/meetings
{
     startTime: anyStartTime,
     endTime: anyEndTime,
     "users": ["abc@gmail.com","xyz@gmail.com"],
     "subject": "Important Meeting",
     "meetingBody": "Let's have a stand-up"
}


4. Updating a meeting Event
PUT /api/users/{userId}/meetings/{meetingId}
{
   startTime: anyStartTime,
   endTime: anyEndTime,,
   "users": ["abc@gmail.com", "xyz@gmail.com"],
   "subject" : "Meeting Cancelled",
   "meetingBody": "Let's have a stand-up"
}



For Reminders
A reminder consists of:

When to show the reminder, expressed minutes before the event starts time.
The delivery method to use.
Reminders can be specified for whole calendars and for individual events. Users can set default reminders for each of their calendars; these defaults apply to all events within that calendar. However, users can also override these defaults for individual events, replacing them with a different set of reminders.

5. Setting up the default meeting reminder
"reminders": {
  "useDefault": false,
  # Overrides can be set if and only if useDefault is false.
  "overrides": [
      {
        "method": "reminderMethod",
        "minutes": "reminderMinutes"
      },
      # ...
  ]
}


The delivery methods offered by the system are:

Pop-up: These are supported on mobile platforms and on web clients.
Email: Sent by the server.

```


