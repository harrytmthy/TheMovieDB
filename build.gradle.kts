/*
 * Copyright 2022 harrytmthy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.diffplug.spotless.LineEnding.UNIX
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    extra.apply {
        set("versionCode", 1)
        set("versionName", "1.0.0")
        set("minSdkVersion", 22)
        set("compileSdkVersion", 33)
        set("targetSdkVersion", 33)
        set("buildToolsVersion", "33.0.0")
        set("jvmTargetVersion", 1.8)
        set("testInstrumentationRunner", "androidx.test.runner.AndroidJUnitRunner")
        set("consumerProguard", "consumer-rules.pro")
    }
    dependencies {
        classpath(libs.android.gradle)
        classpath(libs.kotlin.gradle)
        classpath(libs.google.hilt.gradle)
        classpath(kotlin(module = "serialization", version = libs.versions.kotlin.get()))
    }
}

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.diffplug.spotless") version libs.versions.spotless
    kotlin("jvm") version libs.versions.kotlin apply false
    kotlin("plugin.serialization") version libs.versions.kotlin
    id("org.jetbrains.kotlinx.kover") version libs.versions.kover
}

allprojects {
    apply(plugin = "com.diffplug.spotless")
    apply(plugin = "kover")
    spotless {
        encoding("UTF-8")
        lineEndings = UNIX

        kotlin {
            target("**/*.kt")
            targetExclude("$buildDir/**/*.kt")
            targetExclude("bin/**/*.kt")
            ktlint(libs.versions.ktlint.get()).userData(
                mapOf(
                    "insert_final_newline" to "false",
                    "max_line_length" to "120"
                )
            )
        }
        kotlinGradle {
            target("*.gradle.kts")
            ktlint(libs.versions.ktlint.get())
        }
    }

    tasks.whenTaskAdded {
        if (name == "preBuild") {
            mustRunAfter("spotlessCheck")
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions.freeCompilerArgs += buildString {
            append("-opt-in=")
            append("kotlinx.coroutines.ExperimentalCoroutinesApi,")
            append("kotlinx.serialization.ExperimentalSerializationApi")
        }
    }

    kover {
        filters {
            classes {
                excludes += listOf("*ViewModel_*", "*\$inlined\$*", "*_Factory")
                includes += listOf("*ViewModel*", "*UseCase*")
            }
        }
    }
}
