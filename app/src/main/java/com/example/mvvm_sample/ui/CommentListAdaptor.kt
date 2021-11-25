package com.example.mvvm_sample.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_sample.R
import com.example.mvvm_sample.mvvm.uidata.CommentListItemData
import java.util.*

class CommentListAdaptor(private val listener: Listener) : ListAdapter<CommentListItemData, CommentListAdaptor.ViewHolder>(
    CommentListItemDiffCallback()
) {
    // 表示データリソース
    var data = listOf<CommentListItemData>()
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
        private val bodyTextView: TextView = itemView.findViewById(R.id.item_body_text_view)
        private val imageViewContainer: LinearLayoutCompat = itemView.findViewById(R.id.item_image_view_container)
        private val replyButton: ImageButton = itemView.findViewById(R.id.item_reply_button)
        private val listButton: ImageButton = itemView.findViewById(R.id.item_list_button)
        private val additionalMenuButton: ImageButton = itemView.findViewById(R.id.item_additional_menu_button)
        private val userImageView : ImageView = itemView.findViewById(R.id.item_user_image_view)
        private val userNameTextView: TextView = itemView.findViewById(R.id.item_user_name_text_view)

        fun bind(item: CommentListItemData, listener: Listener) {
            bodyTextView.text = item.text
            userNameTextView.text = item.userName

            itemView.setOnClickListener{ listener.clickListener(item.id) }
            replyButton.setOnClickListener { listener.replyListener(item.id) }
            listButton.setOnClickListener { listener.listListener(item.id) }
        }

        companion object {
            // 紐づくlayoutFileから初期化
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.item_comment_list, parent, false)
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

    class CommentListItemDiffCallback : DiffUtil.ItemCallback<CommentListItemData>() {
        override fun areItemsTheSame(oldItem: CommentListItemData, newItem: CommentListItemData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CommentListItemData, newItem: CommentListItemData): Boolean {
            return oldItem == newItem
        }
    }

    // Itemのクリックリスナー
    data class Listener(
        val clickListener: (id: UUID) -> Unit,
        val replyListener: (id: UUID) -> Unit,
        val listListener: (id: UUID) -> Unit
    )
}