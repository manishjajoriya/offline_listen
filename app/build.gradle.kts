plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
  id("com.google.devtools.ksp")
  id("com.google.dagger.hilt.android")
}

android {
  namespace = "com.manishjajoriya.transferlisten"
  compileSdk = 36

  defaultConfig {
    applicationId = "com.manishjajoriya.transferlisten"
    minSdk = 26
    targetSdk = 36
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
  }
  buildFeatures { compose = true }
}

dependencies {
  val retrofitVersion = "3.0.0"
  val daggerHiltVersion = "2.57.2"
  val coilVersion = "3.3.0"
  val viewmodelVersion = "2.9.4"
  val ketchVersion = "2.0.5"
  val splashScreen = "1.0.1"
  val material3Version = "1.5.0-alpha04"
  val navVersion = "2.9.5"
  val hiltNavigationCompose = "1.3.0"

  // Retrofit
  implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
  implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
  // Dagger Hilt
  implementation("com.google.dagger:hilt-android:$daggerHiltVersion")
  ksp("com.google.dagger:hilt-android-compiler:$daggerHiltVersion")
  // Hilt navigation compose
  implementation("androidx.hilt:hilt-navigation-compose:$hiltNavigationCompose")
  // Coil Image
  implementation("io.coil-kt.coil3:coil-compose:$coilVersion")
  implementation("io.coil-kt.coil3:coil-network-okhttp:$coilVersion")
  // Viewmodel
  implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$viewmodelVersion")
  // Ketch
  implementation("com.github.khushpanchal:Ketch:$ketchVersion")
  // Splash Screen
  implementation("androidx.core:core-splashscreen:$splashScreen")
  // Material 3
  implementation("androidx.compose.material3:material3:$material3Version")
  // Navigation 
  implementation("androidx.navigation:navigation-compose:${navVersion}")

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.ui.graphics)
  implementation(libs.androidx.compose.ui.tooling.preview)
  implementation(libs.androidx.compose.material3)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.compose.ui.test.junit4)
  debugImplementation(libs.androidx.compose.ui.tooling)
  debugImplementation(libs.androidx.compose.ui.test.manifest)
}
