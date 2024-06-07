# usejwtexample - приложение демонстрирует применение JWT

## Применение

Посредством *Post* запроса по url *http://localhost:8080/api/v1/public/user* необходимо создать пользователей  с ролями:

 - ROLE_USER,
 - ROLE_MANAGER 
 - ROLE_ADMIN

```
{
    "username":"manager",
    "email":"manager@example.example",
    "password":"12345",
    "roles":["ROLE_MANAGER"]

},
{
    "username":"user",
    "email":"user@example.example",
    "password":"12345",
    "roles":["ROLE_USER"]

},
{
    "username":"admin",
    "email":"admin@example.example",
    "password":"12345",
    "roles":["ROLE_ADMIN"]

}
```
- далее посредством post-запроса по url *http://localhost:8080/api/v1/public/token/password*, можно проверить,
-  что приложение 'выдает' accsses токены для каждого из созданных пользователей, в *body* указываем
-  (отдельно для каждого из ранее созданных пользователей):

  ```
для user
{
    "username":"user",
     "password":"12345"
},
для manager
{
    "username":"manager",
    "password":"12345"
},
для admin
{
    "username":"admin",
   "password":"12345"
}
```
В результате мы получим такое тело ответа(например для роли manager): 
```
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYW5hZ2VyIiwiaWF0IjoxNzE3NzU4ODM3LCJleHAiOjE3MTc3NTg4OTcsInJvbGUiOlsiUk9MRV9NQU5BR0VSIl0sImlkIjoiOGVmOTczNTgtNDliNi00YjZkLTlmNTctMTIwOWI5M2ZhMGMwIn0.jnatF02723n5hd-K2y-yiFmV7SWR2SnA10gHlLuLj9E",
    "refreshToken": "d5b8e93e-88df-4c49-b8c7-816060a34ed2"
}
```
Токены можно провалидировать на ресурсе jwt.io  - сверить данные из БД с резуольтатом 'парсера' токена

Также посредством post-запроса по url *http://localhost:8080/api/v1/public/token/refresh*
с телом запроса:
- *"refreshToken": "d5b8e93e-88df-4c49-b8c7-816060a34ed2"* и проверить его функциональность

## Использовался стек
- *Java 17*
- *Spring Boot 3*
- *Spring security*
- *io.jsonwebtoken*
- *Maven*
