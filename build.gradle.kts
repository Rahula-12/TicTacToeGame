// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    extra.apply {
        set("room_version", "2.5.2")
    }
}

plugins {
    id("com.android.application") version "8.1.1" apply false
    id("com.android.library") version "7.4.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id ("com.google.devtools.ksp") version "1.8.0-1.0.8" apply false
    id("org.jetbrains.kotlin.jvm") version "1.8.0" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}