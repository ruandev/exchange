## Purpose
The objective of this project is the exchange between currencies. To know the rate to be applied for the exchange, the [Exchange rates API](http://exchangeratesapi.io/) is used

## Features
* Make exchanges
- List all exchanges by a user by ID

## Technologies used in the project
- Kotlin
- Javalin
- Koin
- Exposed
- H2

#### Motivation for these technologies
Although they are new technologies for me, as is the project's tech stack, I decide to have contact with them to learn and get to know them

## Application layers
```
* config: Application and framework configuration files
* database: Classes referring to the database (repositories, tables and immutable classes that represent the database tables).
* services: Classes regarding API business rules
* util: Utility classes
* web
** controllers: Classes and methods to mapping actions of routes
** requests: Classes that represent requests received by the API 
** responses: Classes that represent responses sent by the API
```

## How do I execute the project?

`./gradlew run`

_The server runs on port **7000**_

## How do I use an API?

There is an endpoint to exchange:
`/exchange?currencyFrom=XXX&currencyTo=YYY&amount=0`

- `currencyFrom` is the base currency for the exchange
- `currencyTo` the target currency for the exchange
- `amount` the amount to be exchanged

_The `user_id` must be passed in the header. This is the user's identifier number_

There is an endpoint to list all exchanges for a user
`/exchange/{userId}`

_In case of doubts it is possible to access the endpoints documentation through url `/swagger-ui`_
