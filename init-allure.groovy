import hudson.model.*
import hudson.tools.*
import org.allurereport.jenkins.*

// Принудительно задаем инструмент Allure
def descriptor = Jenkins.instance.getDescriptor(AllureCommandlineInstallation.class)

println "=== ГАРАНТИРОВАННАЯ НАСТРОЙКА ALLURE CLI ==="
def allureInstallation = new AllureCommandlineInstallation(
    "allure",
    "/opt/allure-2.29.0", // Путь к КОРНЕВОЙ папке Allure (внутри Dockerfile.jenkins мы ставили его в /opt/allure-2.29.0)
    []
)

// Заменяем существующие или добавляем новую
descriptor.setInstallations([allureInstallation] as AllureCommandlineInstallation[])
descriptor.save()
println "=== ВСЁ ГОТОВО! Инструмент 'allure' теперь доступен в Jenkins на мастере ==="
