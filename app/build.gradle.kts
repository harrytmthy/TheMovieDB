plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = rootProject.extra["compileSdkVersion"] as Int

    defaultConfig {
        applicationId = "com.timothy.themoviedb"
        minSdk = rootProject.extra["minSdkVersion"] as Int
        targetSdk = rootProject.extra["targetSdkVersion"] as Int
        versionCode = rootProject.extra["versionCode"] as Int
        versionName = rootProject.extra["versionName"] as String
        buildToolsVersion = rootProject.extra["buildToolsVersion"] as String
        testInstrumentationRunner = rootProject.extra["testInstrumentationRunner"] as String

        vectorDrawables.useSupportLibrary = true

        missingDimensionStrategy("environment", "release")
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debug")
        }

        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    lint {
        checkGeneratedSources = true
        checkReleaseBuilds = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    packagingOptions {
        resources.excludes += setOf("META-INF/LICENSE.md", "META-INF/LICENSE-notice.md")
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":core:network"))
    implementation(project(":core:ui"))
    implementation(project(":feature:home:home-api"))
    implementation(project(":feature:home:home-impl"))
    implementation(project(":feature:splash:splash-api"))
    implementation(project(":feature:splash:splash-impl"))

    implementation(libs.android.hilt.work)
    implementation(libs.bundles.android.room)
    kapt(libs.android.room.compiler)
    implementation(libs.android.workRuntime)
    implementation(libs.google.hilt.android)
    kapt(libs.google.hilt.androidCompiler)
}
