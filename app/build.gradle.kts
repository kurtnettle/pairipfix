plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "io.github.ahmedmani.pairipfix"
    compileSdk = 37

    defaultConfig {
        applicationId = "io.github.ahmedmani.pairipfix"
        minSdk = 26
        targetSdk = 34
        versionCode = 3
        versionName = "2.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    platform(libs.yukihook.bom).apply {
        implementation(this)
        ksp(this)
    }

    ksp(libs.yukihook.compiler)
    implementation(libs.kavaref.extension)
    implementation(libs.yukihook.core)
    implementation(libs.yukihook.extension.kavaref)

    compileOnly(libs.libxposed.api)
    implementation(libs.yukihook.runtime.libxposed)
}