package per.scrumgun.scrum

import android.graphics.Color
import android.view.ViewGroup
import per.scrumgun.core.base.ItemViewHolder
import per.scrumgun.core.base.inflater
import per.scrumgun.scrum.databinding.ViewItemChatBinding
import per.scrumgun.scrum.model.UiChat

class ScrumViewHolder(private val binding: ViewItemChatBinding) :
    ItemViewHolder<UiChat>(binding.root) {
    companion object {
        fun create(
            parent: ViewGroup
        ): ItemViewHolder<UiChat> {
            return ScrumViewHolder(
                ViewItemChatBinding.inflate(
                    parent.inflater(),
                    parent,
                    false
                )
            )
        }
    }

    private var mData: UiChat? = null

    override fun fillData(data: UiChat?, position: Int) {
        mData = data
        binding.nameTextView.text = data?.senderName
        binding.dateTextView.text = data?.sentTime
        binding.workDetailTextView.text = data?.workMessage
        binding.blockDetailTextView.text = data?.blockMessage
        binding.nameTextView.setTextColor(Color.parseColor(data?.textColor))
        binding.dateTextView.setTextColor(Color.parseColor(data?.textColor))
        binding.workDetailTextView.setTextColor(Color.parseColor(data?.textColor))
        binding.blockDetailTextView.setTextColor(Color.parseColor(data?.textColor))
    }
}
