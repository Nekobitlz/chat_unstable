package ru.ok.itmo.chat.features.message.data.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.ok.itmo.chat.data.dto.ChannelId
import ru.ok.itmo.chat.data.dto.MessageDTO
import ru.ok.itmo.chat.features.message.data.api.MessagesApi
import javax.inject.Inject

class MessagesRemoteDataSource @Inject constructor(
    private val apiService: MessagesApi
) {
    fun getMessages(channelId: ChannelId): Flow<List<MessageDTO>> {
        return flowOf(apiService.getMessages(channelId))
    }
}
