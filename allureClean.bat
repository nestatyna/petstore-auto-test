@echo off
chcp 65001 > nul

cd /d %~dp0

if not exist "gradlew.bat" (
    echo Ошибка: Файл gradlew.bat не найден! Убедитесь, что вы запустили bat-файл в корне проекта.
    pause
    exit /b 1
)

echo ======================================
echo 🔄 Очистка старых отчетов Allure...
echo ======================================
if exist build\allure-results rmdir /s /q build\allure-results
if exist build\reports\allure-report rmdir /s /q build\reports\allure-report

echo ✅ Готово! Старые отчеты Allure очищены.
pause
