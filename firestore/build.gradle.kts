plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdkVersion(28)
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(28)
    }
    lintOptions {
        tasks {
            lint {
                enabled = false
            }
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.3.21")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.2.0")

    implementation("com.google.firebase:firebase-firestore-ktx:18.2.0")

    testImplementation("junit:junit:4.12")
}
