# time-tracker-api

## About
This is an API for a time tracker application that I am working on. 
This API stores time logs for users! 

`#itsLit`

## Capabilities
- Add time logs containing
    - username
    - activity name
    - date
    - minutes
- Search logs by year given:
    - username
    - year
    - activity name

## API
```
http://localhost:1111/api/v1
    Add Activity:
        /add/log?username=<username>&activity=<activity>&date=<mm/dd/yyyy>&minutes=<min>
    
    Get user's activity for the year:
        /get/year?username=<username>&year=<YEAR>&activity=<activity>
```

# Don't mind me talking to myself for a minute...

## Methods and Things to Implement:
- Password protection (for the top secret secrets)
- JWT
- Remove user
    - remove all activities for the user
- Get month of activities

## Methods Completed:
- Add activity
    - username, activity, date, minutes
    - adds activity to db
- Get year of activities