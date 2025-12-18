plugins {
    id("com.android.application")
}

android {
    namespace = "com.esi.mahina"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.esi.mahina"
        minSdk = 26
        targetSdk = 34
        versionCode = 3
        versionName = "0.0.3"

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        viewBinding = true
    }

    // Other configurations

    testOptions {
        unitTests {
            isIncludeAndroidResources = true

        }
    }
}

configurations.all {
    resolutionStrategy {
        // Force consistent Kotlin stdlib version to avoid duplicates
        force("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")
        force("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.0")
        force("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.9.0")
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.5.3")
    implementation("androidx.navigation:navigation-ui:2.5.3")
    implementation("androidx.cardview:cardview:1.0.0")

    // Lifecycle - ViewModel and LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime:2.6.2")

    // Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.robolectric:robolectric:4.11.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

}