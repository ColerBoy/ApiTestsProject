#language: ru
Функционал: Регистрация пользователя

  Сценарий: Успешное создание пользователя
    Когда я создаю нового пользователя с валидными данными
    Тогда я должен получить код ответа 200
    И в ответе должен быть "id"


  Сценарий: Создание пользователя с существующим email
    Когда я создаю нового пользователя с email "vinkotov@example.com"
    Тогда я должен получить код ответа 400
    И сообщение об ошибке должно быть "Users with email 'vinkotov@example.com' already exists"


  Сценарий: Создание пользователя с невалидным email
    Когда я создаю нового пользователя с email "vinkotovexample.com"
    Тогда я должен получить код ответа 400
    И сообщение об ошибке должно быть "Invalid email format"


  Сценарий: Создание пользователя без одного из обязательных полей
    Когда я создаю нового пользователя без поля "email"
    Тогда я должен получить код ответа 400
    И сообщение об ошибке должно быть "The following required params are missed: email"

    Когда я создаю нового пользователя без поля "password"
    Тогда я должен получить код ответа 400
    И сообщение об ошибке должно быть "The following required params are missed: password"

    Когда я создаю нового пользователя без поля "username"
    Тогда я должен получить код ответа 400
    И сообщение об ошибке должно быть "The following required params are missed: username"

    Когда я создаю нового пользователя без поля "firstName"
    Тогда я должен получить код ответа 400
    И сообщение об ошибке должно быть "The following required params are missed: firstName"

    Когда я создаю нового пользователя без поля "lastName"
    Тогда я должен получить код ответа 400
    И сообщение об ошибке должно быть "The following required params are missed: lastName"


  Сценарий: Создание пользователя с невалидной длиной имени
    Когда я создаю нового пользователя с именем "v"
    Тогда я должен получить код ответа 400
    И сообщение об ошибке должно быть "The value of 'firstName' field is too short"

    Когда я создаю нового пользователя с именем "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc."
    Тогда я должен получить код ответа 400
    И сообщение об ошибке должно быть "The value of 'firstName' field is too long"
