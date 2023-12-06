package ru.ok.itmo.chat.features.message.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.ok.itmo.chat.data.dto.ChannelId
import ru.ok.itmo.chat.databinding.FragmentMessagesBinding
import ru.ok.itmo.chat.features.message.presentation.MessagesState
import ru.ok.itmo.chat.features.message.presentation.MessagesViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MessagesFragment : Fragment() {

    private var _binding: FragmentMessagesBinding? = null
    private val binding get() = _binding!!

    private val messagesAdapter = MessagesAdapter()

    private val viewModel: MessagesViewModel by viewModels()

    @Inject
    lateinit var analytics: Analytics



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        channelId = arguments?.getString(PARAM_CHANNEL_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMessages.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = messagesAdapter
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect {
                    when (it) {
                        is MessagesState.Data -> {
                            binding.progressBar.gone()
                            binding.tvError.gone()
                            binding.rvMessages.visible()
                            messagesAdapter.submitList(it.messages)
                        }

                        MessagesState.Empty -> {
                            binding.progressBar.gone()
                            binding.rvMessages.gone()
                            binding.tvError.visible()
                        }
                        is MessagesState.Error -> {
                            binding.progressBar.gone()
                            binding.rvMessages.gone()
                            binding.tvError.visible()
                            binding.tvError.text = it.error.message
                        }
                        MessagesState.Loading -> {
                            binding.progressBar.visible()
                            binding.rvMessages.gone()
                            binding.tvError.gone()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val PARAM_CHANNEL_ID = "channel_id"

        fun newFragment(channelId: ChannelId) = MessagesFragment().apply {
            arguments = Bundle(1).apply {
                putString(PARAM_CHANNEL_ID, channelId)
            }
        }
    }
}

private fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}
