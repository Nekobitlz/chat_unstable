package ru.ok.itmo.chat.data.dto

data class MessageDTO(
    val id: String,
    val from: String,
    val to: ChannelId,
    val data: List<AttachDTO>,
    val time: Long?,
) {
    sealed class AttachDTO {
        class TextDTO(
            val text: String,
        ): AttachDTO()

        class ImageDTO(
            val image: String,
        ): AttachDTO()
    }
}