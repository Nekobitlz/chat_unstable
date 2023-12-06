package ru.ok.itmo.chat.features.message.data.datasource

import ru.ok.itmo.chat.data.dto.ChannelId
import ru.ok.itmo.chat.data.dto.MessageDTO
import java.util.UUID
import javax.inject.Inject

class MessagesLocalDataSource @Inject constructor() {

    fun getMessages(channelId: ChannelId): List<MessageDTO>? {
        return (1..10).map {
            MessageDTO(
                id = it.toString(),
                from = UUID.randomUUID().toString(),
                to = channelId,
                data = listOf(
                    MessageDTO.AttachDTO.TextDTO(
                        text = randomMessage()
                    )
                ),
                time = 0L
            )
        }
    }

    fun cacheMessages(remoteMessages: List<MessageDTO>) {

    }

    private fun randomMessage(): String {
        val alphabet: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return List(20) { alphabet.random() }.joinToString("")
    }
}
