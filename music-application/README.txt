# Music application

## Description
Application provides CRUD operation for Songs objects.
Application use polyglot persistence. It use H2(SQL) and MongoDB(NoSQL) databases together.
H2 database contains users information
MongoDB database contains information about songs

There are 2 users in the system
1. admin/admin
2. user/user  - state when application start, password will be changed every 30 seconds

## Build
To build application run  ```mvn clean package -U``` in terminal from the project root directory

## Run
To run the application run ```java -jar target/music-application-0.0.1-SNAPSHOT.jar``` in terminal from the project root directory

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
Go to the [http://localhost:6060](http://localhost:6060) <br/>

## CURL commands
### Authentication
``` curl -X POST 'http://localhost:6060/authenticate' -H 'Content-Type: application/x-www-form-urlencoded' -d 'username=admin&password=admin' ```
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

### Get all songs
```curl -X GET http://localhost:6060/song-application/songs -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIkFETUlOIl0sImlhdCI6MTUyNDgyMDExM30.h4quU1YLxZl-ly_pKGoRSUA1pDv5H_Il0nBQzYN_8WY'``` <br/>
Authorization header value should be has format `Bearer` + token value from <b>Authentication</b> step
Response: 
```
[
    {
        "id": "4f18defb-76b9-4c12-98ff-86584e444201",
        "image": "https://www.nasa.gov/sites/default/files/styles/full_width_feature/public/thumbnails/image/dbpbyitxcaa5ttg.jpg_orig.jpg",
        "artist": "artist1",
        "genre": "genre1"
    },
    {
        "id": "082fe0c4-42c7-4763-91e5-bb26e280054f",
        "image": "https://www.nasa.gov/sites/default/files/styles/full_width_feature/public/thumbnails/image/dbpbyitxcaa5ttg.jpg_orig.jpg",
        "artist": "artist2",
        "genre": "genre2"
    }
]

```

### Get song by id
```
curl -X GET http://localhost:6060/song-application/songs/4f18defb-76b9-4c12-98ff-86584e444201 -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIkFETUlOIl0sImlhdCI6MTUyNDgyMjU2MX0.9wwp61678ckeI0WSfhvyAEYxgVMfn5pQdPkxsoRUiC4'
```

Response:
```
{
    "id": 4f18defb-76b9-4c12-98ff-86584e444201,
    "image": "https://www.nasa.gov/sites/default/files/styles/full_width_feature/public/thumbnails/image/dbpbyitxcaa5ttg.jpg_orig.jpg",
    "artist": "artist1",
    "genre": "genre1"
}

```

### Create song
```
curl -X POST \
  http://localhost:6060/song-application/songs \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIkFETUlOIl0sImlhdCI6MTUyNDgyMjU2MX0.9wwp61678ckeI0WSfhvyAEYxgVMfn5pQdPkxsoRUiC4' \
  -H 'Content-Type: application/json' \
  -d '{
    "image": "https://www.nasa.gov/sites/default/files/styles/full_width_feature/public/thumbnails/image/dbpbyitxcaa5ttg.jpg_orig.jpg",
    "artist": "artist3",
    "genre": "genre3"
}'

```

Success Response
```
{
    "id": "90d89855-926f-40df-bbc8-c7229b20df11",
    "image": "https://www.nasa.gov/sites/default/files/styles/full_width_feature/public/thumbnails/image/dbpbyitxcaa5ttg.jpg_orig.jpg",
    "artist": "artist3",
    "genre": "genre3"
}

```

Validation error
```
{
    "timestamp": "2018-04-27T10:10:02.520+0000",
    "message": "Validation Failed",
    "details": "org.springframework.validation.BeanPropertyBindingResult: 1 errors\nField error in object 'song' on field 'image': rejected value [a]; codes [Size.song.image,Size.image,Size.java.lang.String,Size]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [song.image,image]; arguments []; default message [image],2147483647,2]; default message [Name should have atleast 2 characters]"
}
```

### Update song
```
curl -X PUT \
  http://localhost:6060/song-application/songs/082fe0c4-42c7-4763-91e5-bb26e280054f \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIkFETUlOIl0sImlhdCI6MTUyNDgyMjU2MX0.9wwp61678ckeI0WSfhvyAEYxgVMfn5pQdPkxsoRUiC4' \
  -H 'Content-Type: application/json' \
  -d '{
    "image": "aaaaa",
    "artist": "artist22222",
    "genre": "genre3"
}'

```

## Delete song
```
curl -X DELETE \
  http://localhost:6060/song-application/songs/4f18defb-76b9-4c12-98ff-86584e444201 \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIkFETUlOIl0sImlhdCI6MTUyNDgyMjU2MX0.9wwp61678ckeI0WSfhvyAEYxgVMfn5pQdPkxsoRUiC4'
``` 


## Advanced features:
* Embedded NoSQL database (MongoDB) used to store songs
* Form validation implemented on image fields. Length of the image field
should be at least 2 characters
* Background task: Every 30 seconds job find user with username admin, 
change it's password to random string and post password to the application logs

 
