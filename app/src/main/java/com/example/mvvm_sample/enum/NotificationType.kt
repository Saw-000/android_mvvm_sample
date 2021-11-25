package com.example.mvvm_sample.enum

enum class NotificationType(val id: Int) {
    FOLLOWED(0),// フォローされた
    LIST_FOLLOWED(1),// 作成したリストがフォローされた
    REPLIED(2);// リプライされた
}