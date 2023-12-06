package ru.ok.itmo.chat.features.message.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ok.itmo.chat.data.`do`.MessageDO
import ru.ok.itmo.chat.data.`do`.UserDO
import ru.ok.itmo.chat.data.dto.ChannelId
import ru.ok.itmo.chat.data.dto.MessageDTO
import ru.ok.itmo.chat.data.vo.ErrorVO
import ru.ok.itmo.chat.data.vo.MessageVO
import ru.ok.itmo.chat.features.message.domain.GetMessagesUseCase
import javax.inject.Inject


object Message {
    val messages = listOf<MessageDTO>()
}

@HiltViewModel
class MessagesViewModel @Inject constructor(
    private val getMessagesUseCase: GetMessagesUseCase,
    private val currentUserDO: UserDO,
) : ViewModel() {

    private val _messages: MutableStateFlow<List<MessageDO>> = MutableStateFlow(emptyList())

    private val _state = MutableStateFlow<MessagesState>(MessagesState.Empty)
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<RequestResult>()
    val effect = _effect.asSharedFlow()

    init {
        _messages.onEach { messageDOS ->
            if (messageDOS.isEmpty()) {
                _state.emit(MessagesState.Empty)
                return@onEach
            }
            val messages = messageDOS.map { messageDO ->
                MessageVO.mapFrom(messageDO, currentUserDO)
            }
            _state.emit(MessagesState.Data(messages))
        }
    }

    fun onMessageRequest(channelId: ChannelId) {
        viewModelScope.launch(Dispatchers.IO) {
            getMessagesUseCase(channelId)
                .onStart { _state.emit(MessagesState.Loading) }
                .catch {
                    withContext(Dispatchers.Main) {
                        _state.emit(
                            MessagesState.Error(
                                ErrorVO(
                                    message = it.message ?: "Unsupported error"
                                )
                            )
                        )
                    }
                }
                .collect {
                    _messages.emit(it)
                }
        }
    }
}

sealed class MessagesState {
    object Loading : MessagesState()
    object Empty : MessagesState()
    data class Data(val messages: List<MessageVO>) : MessagesState()
    data class Error(val error: ErrorVO) : MessagesState()
}