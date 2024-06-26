package com.eminokumus.moviesapp.data.repository

enum class Status {
    RUNNING, SUCCESS, FAILED
}

class NetworkState(val status: Status, val msg: String) {
    companion object{
        lateinit var LOADED: NetworkState
        lateinit var LOADING: NetworkState
        lateinit var ERROR: NetworkState
    }
    init {
        LOADED = NetworkState(Status.SUCCESS, "Success")
        LOADING = NetworkState(Status.RUNNING, "Running")
        ERROR = NetworkState(Status.FAILED, "Something went worng")

    }
}