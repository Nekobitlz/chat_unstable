package ru.ok.itmo.chat.features.message.data.datasource

import ru.ok.itmo.chat.data.dto.ChannelId
import ru.ok.itmo.chat.data.dto.MessageDTO
import java.util.UUID
import javax.inject.Inject

class MessagesLocalDataSource @Inject constructor(
    private val messageStorage: MessageStorage
) {

    // todo рассказать про mvvm
    fun getMessages(channelId: ChannelId): List<MessageDTO>? {
        return messageStorage.get()
    }

    fun cacheMessages(remoteMessages: List<MessageDTO>) {
        messageStorage.put()
    }

    private fun randomMessage(): String {
        val alphabet: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return List(20) { alphabet.random() }.joinToString("")
    }
}
