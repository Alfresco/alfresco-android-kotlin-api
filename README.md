[<img title="Alfresco" alt='Alfresco' src='docs/logo.svg' align="right" height="32px" />](https://alfresco.com/)

# Alfresco Android Kotlin API

[![GitHub Workflow Status](https://img.shields.io/github/workflow/status/Alfresco/alfresco-android-kotlin-api/Continous%20delivery?logo=github)](https://github.com/Alfresco/alfresco-android-kotlin-api/actions/workflows/cd.yml)
![License](https://img.shields.io/github/license/Alfresco/alfresco-android-kotlin-api)

Support libraries which enable easier development of Android applications that work with Alfresco products.

## Installation

The libraries are available on [Alfresco Nexus](https://artifacts.alfresco.com/nexus/content/groups/public) as independent modules.

To add any of them use:

```groovy
repositories {
    maven { url "https://artifacts.alfresco.com/nexus/content/groups/public-snapshots" }
}

dependencies {
  // Auth support library
  implementation 'com.alfresco.android:auth:latest.integration'

  // Content Services API bindings
  implementation 'com.alfresco.android:content:latest.integration'

  // Content Services API binding extensions
  implementation 'com.alfresco.android:content-ktx:latest.integration'
}
```

The libraries require at minimum Android **API 21**.

## Getting started

Easiest way to get off the ground is to check the included [Sample App](sample).

The sample shows you how to get authentication working with the identity service, and how to integrate that with the API bindings to get your first request going.

The app should just work with your identity service install, but might not match your current configuration so edit [AuthConfig.kt](sample/src/main/java/com/alfresco/sample/AuthConfig.kt) if you run into issues.

## Documentation

* [auth](auth/)
* [content](content/)
* [content-ktx](content-ktx/)

## Development

We'd love to accept your patches and contributions to this project.

## Code reviews

All external submissions require formal review. We use GitHub pull requests for this purpose. Consult [GitHub Help] for more
information on using pull requests.

[GitHub Help]: https://help.github.com/articles/about-pull-requests/

## Building the libs

The libs are built with [gradle](https://gradle.org/)

You may open the project directly in Android Studio and run the Sample App.

Alternatively run `./gradlew assembleDebug` in your terminal.

During development you can use the Sample app as a test bed or publish locally to import the changes into your own application.

To publish locally run`./gradlew publishToMavenLocal` and add `mavenLocal()` first in your app's **repositories** list.

### Before submitting a pull request

We like running a consistent coding style so please run `./gradlew spotlessApply`.

If you're modifying dependencies also run `./gradlew dependencyUpdates -Pstable` to check for newer dependency versions.