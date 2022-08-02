# Teampl100

### Language and Framework

* kotlin 1.7
* Spring Boot 2.7.1
* Spring Web
* Spring Boot DevTools
* Swagger
* junit5

### swagger

```
http://localhost:8080/api-doc
```

### Junit5

* controller
    * MockMvc 통해 구현
* service
* repository

### 구조

1. user api request -> controller -> service(impl) -> repository(impl) : mock
2. error handling
    1. throw error -> RestExceptionHandler -> response
    2. custom error class 를 통해 에러별 response
3. base entity 상속받아 entity 구현
4. base response 클래스를 만들어 동일한 형태로 응답

### 에러체크

1. 학생 생성시 없는 인자 -> NullArgumentException
2. 학생 생성시 이미 존재하는 id, name -> AlreadyExistsException
3. 업데이트 , 삭제시 없는 id -> NotFoundException
