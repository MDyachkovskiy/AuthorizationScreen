plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.test.application.authorizationscreen"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.test.application.authorizationscreen"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "BASE_URL", "\"https://easypay.world/api-test/\"")
        buildConfigField("String", "APP_KEY", "\"12345\"")
        buildConfigField("String", "APP_VERSION", "\"1\"")
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
        buildConfig = true
    }
}

dependencies {

    implementation(project(":core"))
    implementation(project(":login_screen"))
    implementation(project(":payments_screen"))
    implementation(project(":remote_data"))
    //Kotlin
    implementation(Kotlin.core)
    //AndroidX
    implementation(AndroidX.appcompat)
    //Design
    implementation(Design.material)
    implementation(Design.constraint_layout)
    //Navigation
    implementation(Navigation.fragment_ktx)
    implementation(Navigation.ui_ktx)
    //Koin
    implementation(Koin.core)
    implementation(Koin.android)
    implementation(Koin.androidx)
    implementation(Koin.navigation)
    //Retrofit
    implementation(Retrofit.main)
    implementation(Retrofit.gson_convertor)
}