# Petstore API Automation Tests

## Описание

Этот проект содержит автоматизированные тесты для API [Petstore](https://petstore.swagger.io/), написанные с
использованием **Java, TestNG, RestAssured**.

### Техстек

- Java 17
- Gradle
- Lombok
- RestAssured
- Docker

## Установка

1. Клонируйте репозиторий:
   ```sh
   git clone <репозиторий>
   cd <папка_проекта>
   ```
2. Установите зависимости:
   ```sh
   ./gradlew build
   ```

## Запуск тестов

### Локальный запуск

```sh
./gradlew test
```

### Запуск в Docker

1. Соберите Docker-образ:
   ```sh
   docker build -t petstore-tests .
   ```
2. Запустите тесты в контейнере:
   ```sh
   docker run --rm petstore-tests
   ```

### Создание отчёта Allure

1. После запуска тестов запустите bat-файл:
   ```sh
   ./allureReport.bat
   ```
2. Созданный отчёт сохранился в build/reports/allure-report/allureReport/index.html

3. Для очистки отчёта Allure запустите bat-файл:
   ```sh
   ./allureClean.bat
   ```
## Структура проекта

- `src/test/java/com/petstore/tests/` – тестовые классы
- `build.gradle` – зависимости и конфигурация
- `Dockerfile` – инструкция по созданию Docker-образа
- `allureReport.bat` - создание отчёта Allure
- `allureClean.bat` - очистка отчёта Allure
- `README.md` – данная документация

## Очистка тестовых данных

После выполнения тестов все созданные записи автоматически удаляются.

