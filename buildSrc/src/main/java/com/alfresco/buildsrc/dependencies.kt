package com.alfresco.buildsrc

@Suppress("unused")
object Libs {
    object AndroidTools {
        const val gradlePlugin = "com.android.tools.build:gradle:4.0.1"
        const val desugar = "com.android.tools:desugar_jdk_libs:1.0.10"
    }

    const val spotless = "com.diffplug.spotless:spotless-plugin-gradle:5.6.1"

    const val gradleVersions = "com.github.ben-manes:gradle-versions-plugin:0.33.0"

    const val swaggerCodegen = "com.agologan:swagger-gradle-codegen:1.4.1-67-gfbda3ab"

    object Kotlin {
        private const val version = "1.4.10"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val serialization = "org.jetbrains.kotlin:kotlin-serialization:$version"
        const val serializationRuntime = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.0-RC2"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.2.0"
        const val browser = "androidx.browser:browser:1.2.0"

        object Lifecycle {
            private const val version = "2.2.0"
            const val viewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        }
    }

    const val okhttp = "com.squareup.okhttp3:okhttp:4.9.0"

    object Retrofit {
        private const val version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val converterMoshi = "com.squareup.retrofit2:converter-moshi:$version"
    }

    object Moshi {
        private const val version = "1.11.0"
        const val kotlin = "com.squareup.moshi:moshi-kotlin:$version"
        const val adapters = "com.squareup.moshi:moshi-adapters:$version"
        const val codegen = "com.squareup.moshi:moshi-kotlin-codegen:$version"
    }

    const val appauth = "com.agologan:appauth-android:0.7.1-16-gd12db86"

    const val jwtdecode = "com.auth0.android:jwtdecode:2.0.0"

    const val junit = "junit:junit:4.13"
}
