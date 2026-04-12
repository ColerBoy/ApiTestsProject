import hudson.tools.*
import org.allurereport.jenkins.*

def descriptor = Jenkins.instance.getDescriptor(AllureCommandlineInstallation.class)
def installations = descriptor.getInstallations()

// Если Allure уже настроен, ничего не делаем
if (installations.any { it.name == 'allure' }) {
    println "--- Allure CLI уже настроен, пропускаем ---"
    return
}

println "--- Настройка Allure CLI (путь /opt/allure-2.29.0) ---"
def newInstallation = new AllureCommandlineInstallation(
    "allure",        // Имя, которое мы будем использовать в Jenkinsfile
    "/opt/allure-2.29.0", // Путь к папке (не к бинарнику, а к папке)
    []               // Без авто-установки (так как мы уже поставили вручную)
)

descriptor.setInstallations((installations + newInstallation) as AllureCommandlineInstallation[])
descriptor.save()
println "--- Настройка Allure CLI завершена! ---"
