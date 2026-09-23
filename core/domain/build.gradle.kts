plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.domain"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(project(":core:data-api"))
    implementation(project(":core:model"))
    implementation(project(":core:utils"))

    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    implementation(libs.androidx.work.runtime.ktx)

    implementation(libs.timber)
}
