import java.util.Properties

val localProperties = Properties().apply {
    val localFile = rootProject.file("apikeys.properties")
    if (localFile.exists()) {
        load(localFile.inputStream())
    }
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.kapt")
    kotlin("plugin.serialization") version "1.9.10"
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.robogyan"
    compileSdk = 35
    android.buildFeatures.buildConfig = true

    defaultConfig {
        applicationId = "com.example.robogyan"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val apiKey = localProperties.getProperty("SUPABASE_ANON_KEY") ?: ""
        val supabaseUrl = localProperties.getProperty("SUPABASE_URL") ?: ""

        buildConfigField(
            type = "String",
            name = "SUPABASE_ANON_KEY",
            value = apiKey
        )
        buildConfigField(
            type = "String",
            name = "SUPABASE_URL",
            value = supabaseUrl
        )
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

    packagingOptions {
        resources.excludes.add("**/dump_syms/linux/dump_syms.bin")
    }
}

dependencies {

    //firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.database)

    //supabase
    implementation(libs.postgrest.kt)
    implementation(libs.storage.kt)
    implementation(libs.auth.kt)
    implementation(libs.gotrue.kt)
    implementation(libs.supabase.kt)
    implementation(libs.kotlinx.serialization.json)

    //ktor
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.core.v322)
    implementation(libs.ktor.utils)
    implementation(libs.ktor.client.core.v235)
    implementation(libs.ktor.client.cio.v235)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.logging.v235)

    // Room Database
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    kapt(libs.androidx.room.compiler.v261)
    implementation(libs.androidx.room.ktx)

    implementation(libs.coil.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx.v261)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.retrofit)
    implementation(libs.accompanist.pager)
    implementation(libs.converter.gson)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.androidx.runtime.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}