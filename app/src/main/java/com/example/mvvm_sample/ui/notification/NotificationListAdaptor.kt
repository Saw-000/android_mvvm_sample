package com.example.mvvm_sample.ui.notification

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_sample.R
import com.example.mvvm_sample.mvvm.uidata.notification.NotificationListItemData
import java.util.*

class NotificationListAdaptor(private val listener: Listener) : ListAdapter<NotificationListItemData, NotificationListAdaptor.ViewHolder>(
    NotificationListItemDiffCallback()
) {
    // 表示データリソース
    var data = listOf<NotificationListItemData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    // アイテムの数
    override fun getItemCount() : Int {
        return data.size
    }

    // 紐づくviewHolder
    class ViewHolder private constructor(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView){
        // itemView内のviewへの参照
        private val dateTextView = itemView.findViewById<TextView>(R.id.notification_list_item_date_text_view)
        private val bodyTextView = itemView.findViewById<TextView>(R.id.notification_list_item_body_text_view)

        fun bind(item: NotificationListItemData, listener: Listener) {
            dateTextView.text = item.dateStr
            bodyTextView.text = item.text

            itemView.setOnClickListener{ listener.clickListener(item.id) }
        }

        companion object {
            // 紐づくlayoutFileから初期化
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.item_notification_list, parent, false)
                return ViewHolder(view, parent.context)
            }
        }
    }

    // 新しくViewHolderを生成するときに呼ばれるメソッド
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    // ViewHolderにUIにdata[position]の情報を反映させる
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, listener)
    }

    class NotificationListItemDiffCallback : DiffUtil.ItemCallback<NotificationListItemData>() {
        override fun areItemsTheSame(oldItem: NotificationListItemData, newItem: NotificationListItemData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NotificationListItemData, newItem: NotificationListItemData): Boolean {
            return oldItem == newItem
        }
    }

    // Itemのクリックリスナー
    data class Listener(
        val clickListener: (id: UUID) -> Unit
    )
}