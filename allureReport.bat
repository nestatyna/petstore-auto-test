@echo off
chcp 65001 > nul

cd /d %~dp0

if not exist "gradlew.bat" (
    echo Ошибка: Файл gradlew.bat не найден! Убедитесь, что вы запустили bat-файл в корне проекта.
    pause
    exit /b 1
)

echo ======================================
echo 📊 Генерация Allure-отчета...
echo ======================================
call gradlew allureReport
if errorlevel 1 (
    echo ❌ Ошибка при генерации отчета! Проверьте логи.
    pause
    exit /b 1
)

echo ======================================
echo 🌍 Открытие Allure-отчета в браузере...
echo ======================================
call gradlew allureServe

echo ✅ Готово! Allure-отчет открыт в браузере.
pause
