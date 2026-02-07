#language: ru
Функционал: Регистрация пользователя

  Сценарий: Успешное создание пользователя
    Когда регистрируется новый пользователь
    Тогда пользователь получает код ответа 200
    И в ответе должен быть "id"


  Сценарий: Создание пользователя с существующим email
    Когда пользователь регистрируется с email "vinkotov@example.com"
    Тогда пользователь получает код ответа 400
    И сообщение об ошибке "Users with email 'vinkotov@example.com' already exists"


  Сценарий: Создание пользователя с невалидным email
    Когда пользователь регистрируется с email "vinkotovexample.com"
    Тогда пользователь получает код ответа 400
    И сообщение об ошибке "Invalid email format"


  Сценарий: Создание пользователя без одного из обязательных полей
    Когда регистрируется пользователь без поля "email"
    Тогда пользователь получает код ответа 400
    И сообщение об ошибке "The following required params are missed: email"

    Когда регистрируется пользователь без поля "password"
    Тогда пользователь получает код ответа 400
    И сообщение об ошибке "The following required params are missed: password"

    Когда регистрируется пользователь без поля "username"
    Тогда пользователь получает код ответа 400
    И сообщение об ошибке "The following required params are missed: username"

    Когда регистрируется пользователь без поля "firstName"
    Тогда пользователь получает код ответа 400
    И сообщение об ошибке "The following required params are missed: firstName"

    Когда регистрируется пользователь без поля "lastName"
    Тогда пользователь получает код ответа 400
    И сообщение об ошибке "The following required params are missed: lastName"


  Сценарий: Создание пользователя с невалидной длиной имени
    Когда регистрируется пользователь с именем "v"
    Тогда пользователь получает код ответа 400
    И сообщение об ошибке "The value of 'firstName' field is too short"

    Когда регистрируется пользователь с именем "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc."
    Тогда пользователь получает код ответа 400
    И сообщение об ошибке "The value of 'firstName' field is too long"
