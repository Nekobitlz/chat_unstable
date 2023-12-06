package ru.ok.itmo.chat.data.repository

import ru.ok.itmo.chat.data.`do`.UserDO

class UserRepository {
    fun getUser(userId: String) = UserDO(userId, userId, "")
}