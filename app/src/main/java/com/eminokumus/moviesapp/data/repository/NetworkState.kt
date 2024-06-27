package com.eminokumus.moviesapp.data.repository

enum class Status {
    RUNNING, SUCCESS, FAILED
}

class NetworkState(val status: Status, val msg: String) {
    companion object{
        var LOADED: NetworkState = NetworkState(Status.RUNNING,"Success")
        var LOADING: NetworkState = NetworkState(Status.RUNNING, "Running")
        var ERROR: NetworkState = NetworkState(Status.FAILED, "Something went wrong")
    }

}