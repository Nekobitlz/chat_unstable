package ru.ok.itmo.chat.features.message.data.repository

import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import ru.ok.itmo.chat.data.dto.ChannelId
import ru.ok.itmo.chat.data.dto.MessageDTO
import ru.ok.itmo.chat.features.message.data.datasource.MessagesLocalDataSource
import ru.ok.itmo.chat.features.message.data.datasource.MessagesRemoteDataSource
import javax.inject.Inject

class MessagesRepository @Inject constructor(
    private val localDataSource: MessagesLocalDataSource,
    private val remoteDataSource: MessagesRemoteDataSource,
) {

    @WorkerThread
    fun getMessages(channelId: ChannelId): Flow<List<MessageDTO>> {

        // bd add
        // remote get
        // get
        val messages = localDataSource.getMessages(channelId) ?: emptyList()
        return remoteDataSource.getMessages(channelId)
            .onStart { emit(messages) }
            .onEach {
                if (it != messages) {
                    localDataSource.cacheMessages(it)
                }
            }
            .flowOn(Dispatchers.IO)
    }
}