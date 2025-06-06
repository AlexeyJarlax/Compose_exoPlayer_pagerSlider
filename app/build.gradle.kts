plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.pavlovalexey.startsetupforcomposein2024"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.pavlovalexey.startsetupforcomposein2024"
        resourceConfigurations += setOf("ru", "en")
        minSdk = 29
        targetSdk = 35
        versionCode = 7
        versionName = "0.07"
        testInstrumentationRunner = "com.pavlovalexey.startsetupforcomposein2024.HiltTestRunner"
    }

    buildTypes {
        release {}
        debug {
            isMinifyEnabled = false
        }
    }


    bundle {
        abi {
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
    implementation(libs.androidx.activity)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.foundation.android)
    coreLibraryDesugaring (libs.desugar.jdk.libs)

    // HTTP-клиент
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)

    // mailto: URI
    implementation(libs.email.intent.builder)

    // Dagger Hilt
    implementation(libs.hilt.android)
    kapt(libs.dagger.hilt.compiler)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.hilt.navigation.compose.v100)

    // Jetpack Compose
    implementation(libs.androidx.ui)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.runtime.livedata)
    implementation(libs.android.maps.compose)
    implementation(libs.maps.compose.v272)
    implementation(libs.androidx.foundation)
    implementation(libs.google.accompanist.flowlayout)
    implementation (libs.androidx.ui.tooling.preview)
    debugImplementation (libs.androidx.ui.tooling)

    // Compose навигация
    implementation ("androidx.navigation:navigation-compose:2.8.4")

    // графическая обработка (более современное решение по загрузке пикч вместо Glide или Picasso)
    implementation(libs.coil.compose)
    implementation("com.google.accompanist:accompanist-pager:0.36.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.36.0")

    // визуал material
    implementation (libs.androidx.material3)
    implementation(libs.androidx.material)
    implementation(libs.androidx.material.icons.extended)
    implementation ("androidx.compose.material:material-icons-extended:1.4.3")

    // корутин
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.peko)

    //логи Тимбер
    implementation(libs.timber)

    // Требуемые обфускатором R8:
    implementation(libs.bcprov.jdk15on)
    implementation("org.conscrypt:conscrypt-android:2.5.2")

    // тестирование
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)

    // поиск текущего расположения юзера
    implementation (libs.play.services.location)
    implementation (libs.kotlinx.coroutines.play.services)

    // запрос разрешений
    implementation (libs.accompanist.permissions)

    // Room
    implementation (libs.androidx.room.runtime)
    ksp (libs.androidx.room.compiler)
    implementation (libs.androidx.room.ktx)

    // работа со временем
    implementation (libs.androidx.datastore.preferences)

    // ExoPlayer
    implementation ("com.google.android.exoplayer:exoplayer:2.19.0")
    implementation ("com.google.android.exoplayer:exoplayer-ui:2.19.0")
}