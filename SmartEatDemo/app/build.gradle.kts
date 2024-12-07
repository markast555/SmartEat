plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.activities"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.activities"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        // Установка кодировки
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        encoding = "UTF-8"
    }

    packaging {
        resources {
            excludes.add("META-INF/license.txt") // Исключаем дублирующийся файл license.txt
            excludes.add("META-INF/notice.txt") // Исключаем дублирующийся файл notice.txt
            excludes.add("META-INF/LICENSE.md")
            excludes.add("META-INF/LICENSE.txt")
            excludes.add("META-INF/NOTICE.md")
            excludes.add("META-INF/NOTICE.txt")
            pickFirsts.add("META-INF/LICENSE")
            pickFirsts.add("META-INF/NOTICE")
            pickFirsts.add("META-INF/spring.handlers")
            pickFirsts.add("META-INF/spring.tooling")
            pickFirsts.add("META-INF/spring.schemas")
        }
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    //implementation(libs.cronet.embedded)
    testImplementation(libs.junit)
    //testImplementation(libs.junit.jupiter)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    //implementation("com.microsoft.sqlserver:mssql-jdbc:12.2.0.jre17") // для JDK 17./gradlew dependencies

    implementation("com.microsoft.sqlserver:mssql-jdbc:12.8.1.jre11")

    implementation("org.springframework:spring-web:5.3.20")
    implementation("org.springframework:spring-webmvc:5.3.20")

    // Для почты
    implementation("com.sun.mail:android-mail:1.6.7")
    implementation("com.sun.mail:android-activation:1.6.7")

    // Зависимость для MySQL
    //implementation("com.mysql:mysql-connector-j:9.1.0")
    //implementation("com.mysql-connector-java-5.1.49")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")

    implementation("org.mindrot:jbcrypt:0.4")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.0")

    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.0") // Обеспечивает поддержку некоторых типов в jackson (например, LocalDate).
}

tasks.withType<Test> {
    useJUnitPlatform() // Настройка для использования JUnit Platform
}
