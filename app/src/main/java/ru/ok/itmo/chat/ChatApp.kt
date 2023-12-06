package ru.ok.itmo.chat

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ChatApp : Application() {

    val appContainer = AppContainer()

    override fun onCreate() {
        super.onCreate()
    }
}

class AppContainer {

    // Since you want to expose userRepository out of the container, you need to satisfy
    // its dependencies as you did before
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://example.com")
        .build()
        .create(LoginService::class.java)

    private val remoteDataSource = UserRemoteDataSource(retrofit)
    private val localDataSource = UserLocalDataSource()

    // userRepository is not private; it'll be exposed
    val factory = ViewModel.Factory(localDataSource, remoteDataSource)

    factory.create( )
}

