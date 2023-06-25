package per.scrumgun.scrum.model

import androidx.recyclerview.widget.DiffUtil
import per.scrumgun.core.ChatId

data class UiChat(
    val id: ChatId,
    val workMessage: String,
    val blockMessage: String,
    val senderName: String,
    val sentTime: String,
    val textColor: String,
)

class UiChatDiffCallback : DiffUtil.ItemCallback<UiChat>() {
    override fun areItemsTheSame(oldItem: UiChat, newItem: UiChat): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: UiChat,
        newItem: UiChat,
    ): Boolean {
        return oldItem == newItem && oldItem == newItem
    }
}
