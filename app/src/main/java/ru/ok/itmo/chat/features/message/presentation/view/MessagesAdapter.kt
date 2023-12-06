package ru.ok.itmo.chat.features.message.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.ok.itmo.chat.R
import ru.ok.itmo.chat.data.vo.MessageVO
import ru.ok.itmo.chat.databinding.ItemMessageImageBinding
import ru.ok.itmo.chat.databinding.ItemMessageTextBinding

class MessagesAdapter : ListAdapter<MessageVO, MessagesViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewHolder {
        when (viewType) {
            R.id.view_type_message_text -> {
                val binding = ItemMessageTextBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return MessagesViewHolder(binding)
            }

            R.id.view_type_message_image -> {
                val binding = ItemMessageTextBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return MessagesViewHolder(binding)
            }
        }
        throw IllegalStateException("Unsupported view type")
    }

    override fun onBindViewHolder(holder: MessagesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).image.isNullOrEmpty().not()) {
            R.id.view_type_message_image
        } else {
            R.id.view_type_message_text
        }
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<MessageVO>() {
            override fun areItemsTheSame(oldItem: MessageVO, newItem: MessageVO): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MessageVO, newItem: MessageVO): Boolean {
                return oldItem == newItem
            }

        }
    }
}

class MessagesViewHolder(
    private val binding: ItemMessageTextBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MessageVO?) {
        binding.tvTitle.text = item?.text
    }

}