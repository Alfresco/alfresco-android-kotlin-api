# <img title="Alfresco" alt='Alfresco' src='docs/logo.svg' height="32px" /> Android Kotlin API

Support libraries which enable easier development of Android applications that work with Alfresco products.

## Installation

The libraries are available on [Alfresco Nexus](https://artifacts.alfresco.com/nexus/content/groups/public) as independent modules.

To add any of them use:

```groovy
repositories {
    maven { url "https://artifacts.alfresco.com/nexus/content/groups/public" }
}

dependencies {
  // Auth support library
  implementation 'com.alfresco.android:auth:latest.integration'

  // Content Services API bindings
  implementation 'com.alfresco.android:content:latest.integration'
}
```

The libraries require at minimum Android **API 21**.

## Getting started

Easiest way to get off the group is to check the included [Sample App](sample)

The sample shows how to get authentication working with the identity service, and how to integrate that with the API bindings to get a first request going.

The app should just work with your identity service install, but might not match your current configuration so edit [AuthConfig.kt](sample/src/main/java/com/alfresco/sample/AuthConfig.kt) if you run into any issues.

## Authentication

The **auth** module offers several abstractions to work with the Alfresco Identity Service and Basic Authentication.

The easiest way to integrate it is by using the abstract activities we have provided.

Simply extend the classes and implement the abstract methods.

```kotlin

class MyLoginViewModel() : AuthenticationViewModel() {

    override fun onAuthType(authType: AuthType) {
        when (authType) {
            AuthType.PKCE -> { /* SSO login available at URI */ }
            AuthType.BASIC -> { /* Basic login available at URI */ }
            AuthType.UNKNOWN -> { /* No Alfresco instance found */ }
        }
    }
}

class MyLoginActivity() : AuthenticationActivity<MyLoginViewModel>() {
  override val viewModel: MyLoginViewModel by viewModels

  override fun onCredentials(credentials: Credentials) {
    // Called on successful login
  }

  override fun onError(error: String) {
    // Called on login error
  }
}
```

When logging in as a first step you should call `viewModel.checkAuthType(endpoint, authConfig)` to identify if an Alfresco instance is running at an URL and which authentication method it supports. Response will be provided via `viewModel.onAuthType(authType) { }`

### Basic Authentication

For basic authentication build your own UI to collect credentials.

There isn't much needed to provide to the lib in this case but we do recommend you follow this template in your **viewModel**:

```kotlin
class MyLoginViewModel() : AuthenticationViewModel() {
    ...

    fun login(username: String, password: String) {
        val state = AuthInterceptor.basicState(username, password)
        _onCredentials.value = Credentials(username, state, AuthType.BASIC.value)
    }
}
```

You may compute your own state but we offer a helper function `AuthInterceptor.basicState` that works neatly with our `AuthInterceptor`.

Calling `_onCredentials` afterwards triggers `onCredentials` function in your activity so you may resume your normal validation flow.

### SSO Authentication

For SSO your activity will have to present the user a WebView for him to login and then collect the token at the end.

Several steps are required to make it work.

First your viewModel will have to init the **pkceAuth** component with your config, and then call onSsoLogin with the URI of your server.

```kotlin
class MyLoginViewModel() : AuthenticationViewModel() {
    init {
        pkceAuth.initServiceWith(authConfig, null)
    }
    ...
}
```
Next call `pkceLogin` in your activity which will show the user the SSO WebView:
```kotlin
class MyLoginActivity() : AuthenticationActivity<MyLoginViewModel>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButton.setOnClickListener { pkceLogin(server) }
    }
    ...
}
```
Upon successful login the server will callback with your token and to make it work you'll need to declare the callback uri in your **AndroidManifest.xml**:
```
<activity android:name="com.alfresco.auth.pkce.RedirectUriReceiverActivity">
    <intent-filter>
        <action android:name="android.intent.action.VIEW" />

        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />

        <data
            android:host="aims"
            android:path="/auth"
            android:scheme="androidacsapp" />
    </intent-filter>
</activity>
```
This **needs to match** your configuration in the Identity Service and the one provided in your **AuthConfig**

Our library will take care of getting the token and will call you back in via the `onCredentials()` function

### After Authentication

Because authentication is an independent step we **strongly** recommend you do extra validation after receiving **onCredentials** before letting the user into the app.

Things you could check at this time, could be profile information or permissions, which would require calling the Alfresco service and thus verifying your authentication session is working correctly.

### Logging out

While normally you could just destroy the persisted credentials, in case of SSO the session needs to be invalidated or the user will log back in without a credentials prompt.

To do so, just extend `EndSessionActivity` and provide the credentials you were previously given.

```kotlin
class MyLogoutViewModel(context: Context, authType: AuthType?, authState: String, authConfig: AuthConfig)
  : EndSessionViewModel(context, authType, authState, authConfig) {
    ...
}

class MyLogoutActivity() : EndSessionActivity<MyLogoutViewModel>() {
    override val viewModel: MyLogoutViewModel by viewModels
}
```

To trigger the logout just `startActivityForResult`:

```kotlin
fun logout() {
    val intent = Intent(this, LogoutActivity::class.java)
    startActivityForResult(intent, REQUEST_CODE)
}

override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if (requestCode == REQUEST_CODE)
        if (resultCode == Activity.RESULT_OK) {
            // success
        } else {
            // failure
        }
    } else {
        super.onActivityResult(requestCode, resultCode, data)
    }
}
```

### Authentication Interceptor

During the lifetime of a session it may become invalid.

In the case of SSO the refresh token might expire or a remote actor might invalidate the user's session in the Identity Service.

Even in the Basic Authentication scenario this may happen if the password gets changed.

To help with this we provide the `AuthInterceptor` class that works with [OkHttp](https://square.github.io/okhttp/).

Simply create it, attach it to your OkHttp session and register your listener to get notified for changes in the authentication state.

``` kotlin
val authInterceptor = AuthInterceptor(
    context,
    username,
    authType,
    authState,
    authConfig
)

val okHttpClient: OkHttpClient = OkHttpClient()
    .newBuilder()
    .addInterceptor(authInterceptor)
    .build()
```

Creation takes the same parameters we provide via the `onCredentials()` callback.

When a request fails due to authentication problems we callback with `onAuthFailure()`.

In case of SSO this class also takes care of automatic token refresh, and also calls back with `onAuthStateChange()` so you can persist the new session information.

**Note: all** callbacks from the `authInterceptor` are being called on the OkHttp I/O thread. Before proceeding with UI work please transfer your work to the main thread using something like `runOnUiThread()`

### Re-authenticating

As mentioned before a session may become invalid for various reasons.

When getting `onAuthFailure()` we recommend you prompt the user that he'll have to relogin and reuse the same `MyLoginActivity` created above to collect the user's credentials.

While for Basic Authentication it's up to you to figure out the integration for SSO there is a special re-authentication flow.

To trigger it init the `pkceAuth` instance providing the `authState` as a second parameter, notify the viewModel it's running in relogin mode and than trigger `pkceLogin` again

```kotlin
class MyLoginViewModel() : AuthenticationViewModel() {
    init {
        pkceAuth.initServiceWith(authConfig, authState)
    }
    ...
}
class MyLoginActivity() : AuthenticationActivity<MyLoginViewModel>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reLoginButton.setOnClickListener {
            viewModel.isReLogin = true
            pkceLogin(server)
        }
    }
    ...
}
```

On success the new session will be returned again via `onCredentials()` and after updating the stored credentials you can let the user resume their activities.

## Content Services API

The **content** module provides API bindings to be used with [Alfresco Content Services](https://api-explorer.alfresco.com/)

The module includes bindings for both **search** and **core** to make it easier to integrate in most applications.

Our bindings provide an API layer based on [Retrofit](https://square.github.io/retrofit/) and [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)

Since we previously mentioned the `AuthInterceptor` being an **OkHttp** interceptor and **Retrofit** is based on OkHttp, we'll show you how to easily integrate the two to get you started with authenticated requests to Alfresco.

3 steps are required to get Retrofit configured:
* create a AuthInterceptor
* create a OkHttp client and attach the AuthInterceptor to it
* create a Retrofit instance with the OkHttp client

``` kotlin
val authInterceptor = AuthInterceptor(
    context,
    username,
    authType,
    authState,
    authConfig
)

val okHttpClient: OkHttpClient = OkHttpClient()
    .newBuilder()
    .addInterceptor(authInterceptor)
    .build()

val retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .addConverterFactory(GeneratedCodeConverters.converterFactory())
    .baseUrl(baseUrl)
    .build()
```

Now just get a **service** and a **coroutine context** and make your request:

```kotlin
val service = retrofit.create(SearchApi::class.java)
val search = SearchRequest("my query", ...)

lifecycleScope.launch {
    try {
        val results = service.search(search)
    } catch (ex: Exception) {
       // some error
    }
}
```

For more information on the APIs just read our [documentation](https://api-explorer.alfresco.com/)

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