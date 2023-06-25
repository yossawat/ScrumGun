package per.scrumgun.scrum

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import per.scrumgun.core.base.ItemViewHolder
import per.scrumgun.scrum.model.UiChat
import per.scrumgun.scrum.model.UiChatDiffCallback

class ScrumAdapter : ListAdapter<UiChat, ItemViewHolder<UiChat>>(UiChatDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ItemViewHolder<UiChat> {
        return ScrumViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemViewHolder<UiChat>, position: Int) {
        holder.fillData(getItem(position), position)
    }
}
