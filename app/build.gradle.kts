plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    kotlin("kapt")
}

android {
    namespace = "ru.zzemlyanaya.pulsepower"
    compileSdk = libs.versions.compile.sdk.get().toInt()

    defaultConfig {
        applicationId = "ru.zzemlyanaya.pulsepower"
        minSdk = libs.versions.min.sdk.get().toInt()
        targetSdk = libs.versions.target.sdk.get().toInt()
        versionCode = 3
        versionName = "2.0"
        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["dagger.hilt.disableModulesHaveInstallInCheck"] = "true"
            }
        }
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"

        // Enable Coroutines and Flow APIs
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlinx.coroutines.FlowPreview"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    sourceSets {
        getByName("main").res.srcDir(file("src/main/res"))
        getByName("main").java.srcDir(file("src/main/java"))
    }
}

dependencies {
    ksp(libs.hilt.android.compiler)

    implementation(libs.core.ktx)
    implementation(libs.kotlin.std.lib)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.bundles.ktx)
    implementation(libs.bundles.navigation)
    implementation(libs.bundles.viewmodel)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.gson)
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.rx.java)
    implementation(libs.bundles.paging)

    implementation(libs.app.compat)
    implementation(libs.recycler.view)
    implementation(libs.constraint.layout)
    implementation(libs.swipe.refresh.layout)
    implementation(libs.hilt.android)
    implementation(libs.annotations)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)

    // Testing
    testImplementation(libs.hilt.android.testing)
    testImplementation(libs.bundles.units)
}