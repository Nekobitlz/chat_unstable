package ru.ok.itmo.chat.features.message.domain

import kotlinx.coroutines.flow.map
import ru.ok.itmo.chat.data.`do`.MessageDO
import ru.ok.itmo.chat.data.dto.ChannelId
import ru.ok.itmo.chat.data.dto.MessageDTO
import ru.ok.itmo.chat.data.repository.UserRepository
import ru.ok.itmo.chat.features.message.data.repository.MessagesRepository
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(
    private val messagesRepository: MessagesRepository,
    private val userRepository: UserRepository,
) {

    operator fun invoke(channelId: ChannelId) =
        messagesRepository.getMessages(channelId).map { dtoList ->
            dtoList.map { message ->
                MessageDO(
                    id = message.id,
                    user = userRepository.getUser(message.from),
                    to = message.to,
                    data = message.data.map {
                        when (it) {
                            is MessageDTO.AttachDTO.ImageDTO -> MessageDO.AttachDO.ImageDO(
                                image = it.image
                            )

                            is MessageDTO.AttachDTO.TextDTO -> MessageDO.AttachDO.TextDO(
                                text = it.text,
                            )
                        }
                    },
                    time = message.time,
                )
            }
        }
}