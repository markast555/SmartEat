plugins {
    alias(libs.plugins.android.application)
    //kotlin("android")
    //kotlin("kapt") version "1.9.24"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packaging {
        resources {
            excludes.add("META-INF/NOTICE.md") // Исключение дубликата NOTICE.md, если он не важен
            excludes.add("META-INF/LICENSE.md") // Исключение дубликата LICENSE.md, если он не важен
            pickFirsts.add("META-INF/LICENSE")
            pickFirsts.add("META-INF/LICENSE.txt")
            pickFirsts.add("META-INF/NOTICE")
            pickFirsts.add("META-INF/NOTICE.txt")
        }
    }

}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    //testImplementation(libs.junit.jupiter)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //Для почты
    implementation ("com.sun.mail:android-mail:1.6.7")
    implementation ("com.sun.mail:android-activation:1.6.7")



    //implementation("org.postgresql:postgresql:42.5.0") // Зависимость для PostgreSQL
    implementation("mysql:mysql-connector-java:8.0.33") // Зависимость для MySQL

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")

    implementation("org.mindrot:jbcrypt:0.4")
}

tasks.withType<Test> {
    useJUnitPlatform() // Настройка для использования JUnit Platform
}

