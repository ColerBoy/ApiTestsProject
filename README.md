## Локальный запуск

- Через Maven: `mvn test`
- Через Docker Compose (только тест-раннер): `docker compose --profile tests up --build`

## Allure локально

- Полный сценарий (тесты + история + serve): `./run-allure.ps1`
- Только собрать отчёт без `serve` (удобно для CI): `./run-allure.ps1 -SkipServe -Ci`

## Jenkins в Docker Compose

### 1) Поднять Jenkins

```bash
docker compose up -d --build jenkins
```

Jenkins будет доступен по адресу: `http://localhost:8080`.

### 2) Что настроено автоматически

- Jenkins запускается без setup wizard.
- Через JCasC создаётся job `api-tests-pipeline`.
- В Jenkins установлен PowerShell (`pwsh`), и pipeline **переиспользует ваш `run-allure.ps1`**.
- Job берёт `Jenkinsfile` из текущего репозитория и запускает:
  - `pwsh -File ./run-allure.ps1 -SkipServe -Ci`

### 3) Где смотреть отчёт

После билда в артефактах job:

- `target/allure-results/**` — сырые результаты
- `target/site/allure-maven-plugin/**` — готовый HTML-отчёт
- `allure-history/**` — история трендов для следующего запуска

Откройте `target/site/allure-maven-plugin/index.html` из артефактов.

## Что можно улучшить (next level)

1. Подключить webhook из GitHub/GitLab и автозапуск по push/PR.
2. Сделать nightly-regression (cron в Jenkins).
3. Добавить quality gate: падение пайплайна при росте flaky/failed тестов.
4. Параллелить тесты по тегам Cucumber (`@smoke`, `@regression`) для ускорения CI.
5. Вынести `allure-history` в отдельный volume/object storage для долгой истории трендов.
