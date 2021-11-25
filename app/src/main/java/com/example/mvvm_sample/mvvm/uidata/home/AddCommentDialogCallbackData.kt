package com.example.mvvm_sample.mvvm.uidata.home

import android.graphics.Bitmap

data class AddCommentDialogCallbackData(
    val text: String?,
    val images: List<Bitmap>
)