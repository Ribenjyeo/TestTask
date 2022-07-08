# 1. API серверной части ExamManager

|          Метод          |          Описание          |          Запрос          |
|-------------------------|----------------------------|--------------------------|
|  **POST** */api/register*| Регистрация пользователя и выдача токена  |  username, password  |
|  **GET** */api/user/{username}*| Поверка имени пользователя  |  username  |
|  **POST** */auth/token*| Получение Jwt токена  |  username, password  |
|  **GET** */animal/{id}*| Получить животного по id  |  id  |
|  **POST** */animal/add*| Добавить животного  |  nickname, birthday(YYYY-MM-DD), gender, type |
|  **PUT** */auth/edit/{id}*| Редактировать животного  |  nickname, birthday(YYYY-MM-DD), gender  |
|  **DELETE** */auth/{id}*| Удалить животнотного  |  id  |
