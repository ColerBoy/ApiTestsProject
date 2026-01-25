#language: ru
Функционал: Проверка User Agent

  Сценарий: Позитивная проверка user agent
    Когда я отправляю user agent "Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30"
    Тогда я должен получить платформу "Mobile", браузер "No" и устройство "Android"

    Когда я отправляю user agent "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0"
    Тогда я должен получить платформу "Web", браузер "Chrome" и устройство "No"

  Сценарий: Негативная проверка user agent
    Когда я отправляю user agent "Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1"
    Тогда я должен получить платформу "Mobile", браузер "Chrome" и устройство "iOS", но поле "browser" должно быть неверным

    Когда я отправляю user agent "Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1"
    Тогда я должен получить платформу "Mobile", браузер "No" и устройство "iPhone", но поле "device" должно быть неверным
