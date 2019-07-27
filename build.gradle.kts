buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.3.2")
        classpath(kotlin("gradle-plugin", version = "1.3.41"))
        classpath("com.github.dcendents:android-maven-gradle-plugin:2.1")
    }
}

subprojects {
    repositories {
        google()
        jcenter()
    }
}
