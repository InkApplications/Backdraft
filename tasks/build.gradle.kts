plugins {
    id("com.android.library")
    kotlin("android")
    id("com.github.dcendents.android-maven")
}

android {
    compileSdkVersion(28)
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(28)
    }
    lintOptions {
        tasks.lint {
            enabled = false
        }
    }
}


dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.3.31")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.0-M1")

    api("com.google.firebase:firebase-common:17.1.0")

    testImplementation("junit:junit:4.12")
}
