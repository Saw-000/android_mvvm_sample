package com.example.mvvm_sample.mvvm.uidata

import android.graphics.Bitmap
import com.example.mvvm_sample.database.datatype.Comment
import java.util.*

data class CommentListItemData (
    val id: UUID,
    val text: String?,
    val images: List<Bitmap>,
    val userImage: Bitmap?,
    val userName: String?
) {
    companion object {
        fun from(comment: Comment): CommentListItemData
            = CommentListItemData(
                id = comment.id,
                text = comment.text,
                images = emptyList(),// debug
                userImage = null,// debug
                userName = comment.creatorId.toString()
            )
    }
}