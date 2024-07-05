# Notification Service

## Clone the repository
```terminal
$ git clone https://github.com/meesho-isha/Notification-Service.git
```

### Prepare your secret

Run the script at the first level:

(You need to assign values to all the variables mentioned in the .env.example file and store those variables in the .env file.)

For example -

```terminal
$ echo "DB_URL=YOUR_DB_URL" >> .env
```

## Endpoints

### 1. Send SMS 
### POST /v1/sms/send

Request Body -
```terminal
{
  "phoneNumber": "+919940630272",
  "message":"Welcome to Meesho!" 
}
```

Success Response - 
```terminal
{
  "data": {
    "requestId": "some_unique_id",
    "comments": "Successfully Sent"
  } 
}
```

Failure Response - 
```terminal
{
  "error": {
    "code": "INVALID_REQUEST",
    "message": "phone_number is mandatory"
  } 
}
```

### 2. Get details of sms from request id
### GET /v1/sms/<request_id>

Success Response -
```terminal
{
  "data": { 
    "id": 
    "phoneNumber": 
    "message": 
     ……. All other details from the table …... 
  } 
}
```

Failure Response -
```terminal
{ 
  "error": { 
    "code": "INVALID_REQUEST", 
    "message": "request_id not found" 
  } 
} 
```

### 3. Add Phone Number to Blacklist
### POST /v1/blacklist

Request Body -
```terminal
{
  "phoneNumbers": [
    "+91904353454534",
    "+91980998787678"
  ]
} 
```

Success Response -
```terminal
{
  "data": "Successfully blacklisted" 
}
```

### 4. Remove Phone Number from Blacklist
### DELETE /v1/blacklist

Request Body -
```terminal
{
  "phoneNumbers": [
    "+91904353454534",
    "+91980998787678"
  ]
}
```

Success Response -
```terminal
{
  "data": "Successfully whitelisted" 
}
```

### 5. Get list of blacklisted numbers
### GET /v1/blacklist

Success Response -
```terminal
{
  "data": { 
    "id": 
    "phoneNumber": 
    "message": 
     ……. All other details from the table …... 
  } 
}
```

Failure Response -
```terminal
{ 
  "error": { 
    "code": "INVALID_REQUEST", 
    "message": "request_id not found" 
  } 
} 
```