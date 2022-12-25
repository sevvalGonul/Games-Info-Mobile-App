package com.sevvalgonul.mobilvize.util

// A class that is recommended by Google to be used to wrap around network responses
// It's very usefull to differantiate between succesfull and error responses and also help us to handle the loading state
// data will be the body of the response, T will be our network responce which is GamesResponse in our case
// It's a generic, sealed class - sealed class is similar to abstract class but we can define which classes can inherit from this class
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    // classes that only allowed to inherit from Resource:
    class Success<T>(data : T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}