package com.alfresco.buildsrc

@Suppress("unused")
object Libs {
    object AndroidTools {
        const val gradlePlugin = "com.android.tools.build:gradle:4.0.1"
        const val desugar = "com.android.tools:desugar_jdk_libs:1.0.9"
    }

    const val spotless = "com.diffplug.spotless:spotless-plugin-gradle:5.1.0"

    const val gradleVersions = "com.github.ben-manes:gradle-versions-plugin:0.29.0"

    const val swaggerCodegen = "com.yelp.codegen:plugin:1.5.0"

    object Kotlin {
        private const val version = "1.3.72"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.1.0"
        const val browser = "androidx.browser:browser:1.2.0"

        object Lifecycle {
            private const val version = "2.2.0"
            const val viewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        }
    }

    const val okhttp = "com.squareup.okhttp3:okhttp:4.8.0"

    object Retrofit {
        private const val version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val converterMoshi = "com.squareup.retrofit2:converter-moshi:$version"
    }

    object Moshi {
        private const val version = "1.9.3"
        const val kotlin = "com.squareup.moshi:moshi-kotlin:$version"
        const val adapters = "com.squareup.moshi:moshi-adapters:$version"
        const val codegen = "com.squareup.moshi:moshi-kotlin-codegen:$version"
    }

    const val appauth = "net.openid:appauth:0.7.1-11-g5b56cff"

    const val jwtdecode = "com.auth0.android:jwtdecode:2.0.0"

    const val gson = "com.google.code.gson:gson:2.8.6"

    const val junit = "junit:junit:4.13"
}
