import java.util.Properties
import java.io.FileInputStream

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(FileInputStream(localPropertiesFile))
}

// ссылки в localProperties на ключи для авторизации
//val apiKey: String = localProperties.getProperty("API_KEY") ?: "" // ключ приложения для файербейс
//val apiMapKey: String = localProperties.getProperty("API_MAP_KEY") ?: "" // это для гугл карт
//val yandexClientId: String = localProperties.getProperty("YANDEX_CLIENT_ID") ?: "" // авторизация по яндексу
//val vkClientId: String = localProperties.getProperty("VK_CLIENT_ID") ?: "" // авторизация по вк

plugins {
    id("com.android.application")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
//    id("com.autonomousapps.dependency-analysis")  //выявляет лишние библиотеки, анализ - ввести в терминале: ./gradlew buildHealth
//    id("com.google.gms.google-services") // рекомендуют размещать в конце списка
}

android {
    namespace = "com.pavlovalexey.startsetupforcomposein2024"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.pavlovalexey.startsetupforcomposein2024"
        resourceConfigurations += setOf("ru", "en")
        minSdk = 29
        targetSdk = 35
        versionCode = 2
        versionName = "0.2"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

//        resValue("integer", "com_vk_sdk_AppId", vkClientId) // авторизация
//        buildConfigField("String", "API_KEY", "\"$apiKey\"") // авторизация

//        addManifestPlaceholders(
//            mapOf( // авторизация
//                "appAuthRedirectScheme" to "yx$yandexClientId",
//                "yandexRedirectHost" to "oauth2redirect",
//                "YANDEX_CLIENT_ID" to yandexClientId,
//                "API_MAP_KEY" to apiMapKey,
//                "VK_CLIENT_ID" to vkClientId
//            )
//        )
    }

    buildTypes {
        release { // до релиза не нужно
//            isMinifyEnabled = true
//            isShrinkResources = true
//            proguardFiles( // обфускатор
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//            isDebuggable = false
        }
        debug {
            isMinifyEnabled = false
        }
    }


    bundle {
        abi {// Оптимизация для разных ABI (процессорных архитектур)
            enableSplit = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
        encoding = "UTF-8"
    }

    kotlinOptions {
        jvmTarget = "17"
        languageVersion = "1.9"
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
        dataBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }

    packaging {
        resources {
            excludes += "META-INF/DEPENDENCIES"
        }
    }

    hilt {
        enableAggregatingTask = true
    }

    kapt {
        correctErrorTypes = true
        includeCompileClasspath = false
    }
}

composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
}

dependencies {
    // для работы с Activity.
    implementation(libs.androidx.activity)

    // для работы с API Android.
    implementation(libs.androidx.core.ktx)

    // для обеспечения совместимости с новыми возможностями платформы Android на более старых устройствах.
    implementation(libs.androidx.appcompat)

    // HTTP-клиент для обмена данными с удаленными серверами.
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)

    // для создания функций отправки в Intents с помощью mailto: URI
    implementation(libs.email.intent.builder)

    // Фреймворк для написания и запуска тестов в Java.
    testImplementation(libs.junit)

    // Библиотека для написания и запуска тестов на Android.
    androidTestImplementation(libs.androidx.junit)

    // Библиотека для управления зависимостями koin и Dagger Hilt
    //  implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03") больше не поддерживается! не добавлять в активные
    implementation(libs.hilt.android)
    kapt(libs.dagger.hilt.compiler)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.hilt.navigation.compose.v100)

    // Jetpack Compose
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.navigation.compose)
    implementation(libs.runtime.livedata)
    implementation(libs.android.maps.compose)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.maps.compose.v272)
    implementation(libs.androidx.foundation)
    implementation(libs.google.accompanist.flowlayout)
    implementation (libs.androidx.ui.tooling.preview)
    debugImplementation (libs.androidx.ui.tooling)

    // графическая обработка (более современное решение по загрузке пикч вместо Glide или Picasso)
    implementation(libs.coil.compose)

    // коллекции визуала
    implementation (libs.androidx.material3)
    implementation (libs.androidx.material3.v120alpha04)
    implementation (libs.androidx.material.v153)

    // корутин
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.peko)

    // google.play.services
//    implementation(libs.play.services.auth)
//    implementation(libs.play.services.location)
//    implementation(libs.play.services.maps)

    // Firebase
//    implementation(platform(libs.firebase.bom))
//    implementation(libs.firebase.auth)
//    implementation(libs.firebase.storage.ktx)
//    implementation(libs.firebase.firestore)
//    implementation(libs.com.google.firebase.firebase.analytics2)
//    implementation(libs.firebase.storage)
//    implementation(libs.firebase.database)
//    implementation(libs.firebase.core)
//    implementation (libs.firebase.functions.ktx)
//    implementation (libs.google.firebase.auth.ktx)
//    implementation (libs.firebase.firestore.ktx)

    // Для Yandex
//    implementation(libs.authsdk)

    //ВКонтакте
//    implementation(libs.android.sdk.core)
//    coreLibraryDesugaring(libs.desugar.jdk.libs)
//    implementation ("com.vk:android-sdk-core:4.1.0") // 4.1.0 рабочая, не обновляю
//    implementation ("com.vk:android-sdk-api:4.1.0") // 4.1.0 рабочая, не обновляю

    coreLibraryDesugaring ("com.android.tools:desugar_jdk_libs:2.0.3")

    //логи Тимбер
    implementation(libs.timber)

    // Требуемые обфускатором R8:
    implementation(libs.bcprov.jdk15on)
    implementation("org.conscrypt:conscrypt-android:2.5.2")

    // переходные(транзитивные) зависимости - зависимости, которые подтягиваются другими библиотеками тут вызываю явно
    androidTestImplementation("androidx.test:monitor:1.7.1")
    androidTestImplementation(libs.junit)
    implementation("androidx.annotation:annotation:1.8.1")
    implementation("androidx.compose.animation:animation-core:1.7.2")
    implementation("androidx.compose.animation:animation:1.7.2")
    implementation(libs.androidx.foundation.layout)
    implementation(libs.androidx.material.icons.core)
    implementation(libs.androidx.runtime.saveable)
    implementation(libs.androidx.runtime)
    implementation("androidx.compose.ui:ui-geometry:1.7.2")
    implementation("androidx.compose.ui:ui-graphics:1.7.2")
    implementation("androidx.compose.ui:ui-text:1.7.2")
    implementation("androidx.compose.ui:ui-unit:1.7.2")
    implementation("androidx.core:core:1.13.1")
    implementation("androidx.fragment:fragment:1.8.2")
    implementation("androidx.lifecycle:lifecycle-common:2.8.4")
    implementation("androidx.lifecycle:lifecycle-livedata-core:2.8.4")
    implementation("androidx.lifecycle:lifecycle-livedata:2.8.4")
    implementation("androidx.lifecycle:lifecycle-process:2.8.4")
    implementation("androidx.lifecycle:lifecycle-runtime:2.8.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.8.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.8.4")
    implementation("androidx.navigation:navigation-common:2.8.0")
    implementation("androidx.navigation:navigation-runtime:2.8.0")
    implementation("com.google.dagger:dagger:2.52")
    implementation("com.google.dagger:hilt-core:2.52")
    implementation("com.google.guava:guava:32.1.3-android")
    implementation("io.coil-kt:coil-base:2.3.0")
    implementation("javax.inject:javax.inject:1")
    implementation(libs.kotlinx.coroutines.core.v173)
    implementation(libs.kotlinx.coroutines.play.services)
    kapt(libs.dagger.compiler)
    runtimeOnly(libs.kotlinx.coroutines.android.v180)
    runtimeOnly(libs.peko)
}