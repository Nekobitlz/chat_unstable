plugins {
    kotlin("kapt")
    kotlin("android")
    alias(libs.plugins.hilt)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.serialization.plugin)
}

android {
    namespace = "ru.ok.itmo.chat"
    compileSdk = libs.versions.compileSdkVersion.get().toInt()

    defaultConfig {
        applicationId = "ru.ok.itmo.chat"
        minSdk = libs.versions.minSdkVersion.get().toInt()
        targetSdk = libs.versions.targetSdkVersion.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    with(libs.androidx) {
        implementation(core)
        implementation(compat)
        implementation(lifecycle.runtime)
        implementation(lifecycle.viewmodel)
        implementation(lifecycle.livedata)
        implementation(navigation.fragment)
        implementation(fragment)
        implementation(constraintlayout)

        implementation(room)
        implementation(room.runtime)
        annotationProcessor(room.compiler)
        kapt(room.compiler)
    }
    implementation(libs.google.material)

    implementation(libs.bundles.squareup)
    implementation(libs.bundles.ktor)

    implementation(libs.google.hilt)
    kapt(libs.google.hilt.compiler)
    implementation(libs.javax.inject)

    implementation(libs.kotlin.serialization)
    implementation(libs.kotlin.serialization.json)
    implementation(libs.kotlin.serialization.converter)
}