package com.example.mvvm_sample.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mvvm_sample.mvvm.model.MyPageModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MyPageViewModel(
    val model: MyPageModel = MyPageModel()
) : ViewModel() {
    private val _text = MutableStateFlow("This is myPage Fragment")
    val text: StateFlow<String> get() = _text.asStateFlow()
}