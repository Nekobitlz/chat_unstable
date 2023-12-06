package ru.ok.itmo.chat.features.message.data.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ru.ok.itmo.chat.data.dto.ChannelId
import ru.ok.itmo.chat.data.dto.MessageDTO

interface MessagesApi {

    @GET("/channel/{channel_id}")
    fun getMessages(@Path("channel_id") channelId: ChannelId): List<MessageDTO>
}