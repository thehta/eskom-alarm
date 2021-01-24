# Mokse
API for loadshedding schedules and loadshedding status.c

## Dependencies
* `Java 11`
* `Maven`
* See [`pom.xml`](./pom.xml)

## Run
```
mvn spring-boot:run
```

## Use
#### `/GetScheduleByCode?code=<suburb_code>`
return schedule for given `suburb_code` ∈ [1, 16]

#### `/GetStatus`
return current loadshedding status

## What's next:
- Map suburb names to loadshedding code
- Endpoint to consume suburb name and return schedule

### Notes:
Scheduling calculations have been adapted from formulas found in the [Eskom loadshedding spreadsheet](https://www.eskom.co.za/Pages/LS_schedules.aspx).