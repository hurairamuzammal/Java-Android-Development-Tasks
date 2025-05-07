plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.semester_project"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.semester_project"
        minSdk = 29
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)


    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.camera.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("com.google.firebase:firebase-ml-vision:24.0.3")
    implementation ("com.google.mlkit:text-recognition:16.0.1")
    implementation("androidx.camera:camera-core:1.3.0")

    // CameraX Camera2 implementation
    implementation("androidx.camera:camera-camera2:1.3.0")
    // ML Kit barcode scanner
    implementation ("com.google.mlkit:barcode-scanning:17.2.0")
    // CameraX Lifecycle binding
    implementation("androidx.camera:camera-lifecycle:1.3.0")

    // CameraX View for preview
    implementation("androidx.camera:camera-view:1.3.0")

    // Optional - Video capture support
    implementation("androidx.camera:camera-video:1.3.0")
}