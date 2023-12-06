package ru.ok.itmo.chat.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import ru.ok.itmo.chat.features.message.data.api.MessagesApi
import ru.ok.itmo.chat.features.message.data.datasource.MessagesLocalDataSource

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideJson(): Json = Json { ignoreUnknownKeys = true }

    @Provides
    fun provideMessagesApi(): MessagesApi {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl("https://faerytea.name:8008/")
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
            .create(MessagesApi::class.java)
    }

    @Provides
    fun provideMessagesLocalDataSource(): MessagesLocalDataSource {
        return MessagesLocalDataSource()
    }
}

@Module
@InstallIn(FragmentComponent::class)
class FragmentModule {

    @Provides
    fun provideFragmentAnalyics() {

    }
}


class AuthInterceptor : Interceptor, KoinComponent {
    private val modelInstance: TokenModel by inject()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header("X-Auth-Token", modelInstance.token)
            .build()
        return chain.proceed(newRequest)
    }
}