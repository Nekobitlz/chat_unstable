package ru.ok.itmo.chat.data.vo

import ru.ok.itmo.chat.data.`do`.MessageDO
import ru.ok.itmo.chat.data.`do`.UserDO
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class MessageVO(
    val id: String,
    val userName: String,
    val userImage: String,
    val text: String,
    val image: String?,
    val time: String?,
    val isCurrentUser: Boolean,
) {
    companion object Mapper {
        fun mapFrom(messageDO: MessageDO, currentUserDO: UserDO) = MessageVO(
            id = messageDO.id,
            userName = messageDO.user.name,
            userImage = messageDO.user.imageUrl,
            text = messageDO.data.filterIsInstance<MessageDO.AttachDO.TextDO>()
                .first().text,
            image = messageDO.data.filterIsInstance<MessageDO.AttachDO.ImageDO>()
                .firstOrNull()?.image,
            time = messageDO.time?.let {
                SimpleDateFormat(
                    "yyyy.MM.dd HH:mm",
                    Locale.US
                ).format(Date(it))
            },
            isCurrentUser = messageDO.user == currentUserDO
        )
    }
}