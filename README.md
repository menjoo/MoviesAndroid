# MovieApp
Demonstrates a small movie listing app built with the MVVM architecture.

## Build status
AppCenter [![Build status](https://build.appcenter.ms/v0.1/apps/c8d15929-0c9a-4fd1-ac19-58f961d1655a/branches/master/badge)](https://appcenter.ms)

## TheMovieDb
This app consumes https://www.themoviedb.org

## Technologies used

### Kotlin
...

### Dagger2
...

### Kotlin Coroutines
Used for asynchronous work and state management without RxJava.

### Android Architecture Components
Uses ViewModel and StateFlow to expose UI state in a reactive way with coroutines. Coroutines are launched within the `viewModelScope` to automatically cancel work when the ViewModel is cleared.

### OkHttp
...

### Retrofit
...

### Picasso
Takes care of loading images from the web and caching them. Alternative to Glide. [This](https://medium.com/@multidots/glide-vs-picasso-930eed42b81d) article compares the two. 

### LeakCanary
Enabled for debug builds. Detects memory leaks during app usage.
