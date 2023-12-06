package ru.ok.itmo.chat.data.`do`

import ru.ok.itmo.chat.data.dto.ChannelId

data class MessageDO(
    val id: String,
    val user: UserDO,
    val to: ChannelId,
    val data: List<AttachDO>,
    val time: Long?,
) {
    sealed class AttachDO {
        class TextDO(
            val text: String,
        ): AttachDO()

        class ImageDO(
            val image: String,
        ): AttachDO()
    }
}