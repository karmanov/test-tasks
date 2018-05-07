# Cakes application

## Description
Application provides CRUD operation for Cake objects.
Application use polyglot persistence. It use H2(SQL) and MongoDB(NoSQL) databases together.
H2 database contains users information
MongoDB database contains information about cakes

There is 1 users in the system
```
username: admin
password: admin
```

## Build
To build application run  ```mvn clean package -U``` in terminal from the project root directory

## Run
To run the application run ```cakes-application-0.0.1-SNAPSHOT.jar``` in terminal from the project root directory

## Stack
* Java 1.8
* Spring Boot
* Spring Data
* Spring Security
* JWT
* H2
* AngularJS
* MongoDB

## Test
Go to the [http://localhost:5050](http://localhost:5050) <br/>

## CURL commands
### Authentication
``` curl -X POST 'http://localhost:5050/authenticate' -H 'Content-Type: application/x-www-form-urlencoded' -d 'username=admin&password=admin' ```
Success response: 
```
{
    "user": {
        "id": 1,
        "name": "Admin admin",
        "username": "admin",
        "roles": [
            "ADMIN"
        ]
    },
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIkFETUlOIl0sImlhdCI6MTUyNDgxOTcyM30.qcC0S7z0FdC6nS7AhW_YRYq6dqHWJWxJawd77xLb-m0"
}
```

Error response
```
{
    "token": null
}

```
Copy `token` value for the future requests

### Registration
```
curl -X POST \
  http://localhost:6060/register \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -d '{"name":"superuser","username":"superuser","password":"secret"}'
```

Success response
```
{
    "id": 4,
    "name": "superuser",
    "username": "superuser",
    "roles": [
        "ADMIN"
    ]
}

```


### Get all cakes
```
curl -X GET \
  http://localhost:5050/cake-application/cakes \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIkFETUlOIl0sImlhdCI6MTUyNDgzNTM3M30.scBdtNOejGZqPWeJT2rm1zRSaGxtdhqzGMUE-OUQcrY' \
  -H 'Cache-Control: no-cache'
```
Authorization header value should be has format `Bearer` + token value from <b>Authentication</b> step
Response:
 
```
[
    {
        "id": "89431039-246c-4465-84cc-f2af43ecfb82",
        "description": "description",
        "price": 100,
        "quantity": 2,
        "image": "http://namebirthdaycakes.net/img/amazing-red-velvet-cake-for-birthday-wishes-with-name/88.jpg"
    },
    {
        "id": "5ae3139dbf55b765f9caa4c5",
        "description": "aaaaaaaaaaaaaaaaa",
        "price": 100,
        "quantity": 1,
        "image": "asd"
    }
]
```

### Get cake by id
```
curl -X GET \
  http://localhost:5050/cake-application/cakes/89431039-246c-4465-84cc-f2af43ecfb82 \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIkFETUlOIl0sImlhdCI6MTUyNDgzNTM3M30.scBdtNOejGZqPWeJT2rm1zRSaGxtdhqzGMUE-OUQcrY'
```

Success Response:
```
{
    "id": "89431039-246c-4465-84cc-f2af43ecfb82",
    "description": "description",
    "price": 100,
    "quantity": 2,
    "image": "http://namebirthdaycakes.net/img/amazing-red-velvet-cake-for-birthday-wishes-with-name/88.jpg"
}
```

### Create cake
```
curl -X POST \
  http://localhost:5050/cake-application/cakes \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIkFETUlOIl0sImlhdCI6MTUyNDgyMjU2MX0.9wwp61678ckeI0WSfhvyAEYxgVMfn5pQdPkxsoRUiC4' \
  -H 'Content-Type: application/json' \
  -d '{
    "description": "cccccccccccccccccccc",
    "price": 100,
    "quantity": 2,
    "image": "http://namebirthdaycakes.net/img/amazing-red-velvet-cake-for-birthday-wishes-with-name/88.jpg"
}'

```

Success Response
```
{
   "id": "89431039-246c-4465-84cc-f2af43ecfb82",
   "description": "cccccccccccccccccccc",
   "price": 100,
   "quantity": 2,
   "image": "http://namebirthdaycakes.net/img/amazing-red-velvet-cake-for-birthday-wishes-with-name/88.jpg"
}

```

Validation Error
```
{
    "timestamp": "2018-04-27T13:38:27.074+0000",
    "message": "Validation Failed",
    "details": "org.springframework.validation.BeanPropertyBindingResult: 1 errors\nField error in object 'cake' on field 'description': rejected value [dd]; codes [Size.cake.description,Size.description,Size.java.lang.String,Size]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [cake.description,description]; arguments []; default message [description],2147483647,10]; default message [Name should have atleast 2 characters]"
}
```

### Update cake
```
curl -X PUT \
  http://localhost:5050/cake-application/cakes/89431039-246c-4465-84cc-f2af43ecfb82 \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIkFETUlOIl0sImlhdCI6MTUyNDgzNTM3M30.scBdtNOejGZqPWeJT2rm1zRSaGxtdhqzGMUE-OUQcrY' \
  -H 'Content-Type: application/json' \
  -d '{
    "description": "bbbbbbbbbbbbbbbbbbbbbbbb",
    "price": 100,
    "quantity": 2,
    "image": "http://namebirthdaycakes.net/img/amazing-red-velvet-cake-for-birthday-wishes-with-name/88.jpg"
}'
```
Success Response
```
{
    "id": "89431039-246c-4465-84cc-f2af43ecfb82",
    "description": "bbbbbbbbbbbbbbbbbbbbbbbb",
    "price": 100,
    "quantity": 2,
    "image": "http://namebirthdaycakes.net/img/amazing-red-velvet-cake-for-birthday-wishes-with-name/88.jpg"
}

```

Validation Error
```
{
    "timestamp": "2018-04-27T13:38:27.074+0000",
    "message": "Validation Failed",
    "details": "org.springframework.validation.BeanPropertyBindingResult: 1 errors\nField error in object 'cake' on field 'description': rejected value [dd]; codes [Size.cake.description,Size.description,Size.java.lang.String,Size]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [cake.description,description]; arguments []; default message [description],2147483647,10]; default message [Name should have atleast 2 characters]"
}
```

## Delete cake
```
curl -X DELETE \
  http://localhost:5050/cake-application/cakes/89431039-246c-4465-84cc-f2af43ecfb82 \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIkFETUlOIl0sImlhdCI6MTUyNDgzNTM3M30.scBdtNOejGZqPWeJT2rm1zRSaGxtdhqzGMUE-OUQcrY'
``` 


## Advanced features:
* Embedded NoSQL database (MongoDB) used to store cakes
* Form validation implemented on image and quantity fields. Length of the description field
should be at least 10 characters and quantity should be at least 1


 
