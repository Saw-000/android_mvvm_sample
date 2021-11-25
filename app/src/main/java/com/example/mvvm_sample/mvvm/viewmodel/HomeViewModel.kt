package com.example.mvvm_sample.mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_sample.ExtendedApplication
import com.example.mvvm_sample.R
import com.example.mvvm_sample.database.datatype.Comment
import com.example.mvvm_sample.mvvm.model.HomeModel
import com.example.mvvm_sample.mvvm.uidata.CommentListItemData
import com.example.mvvm_sample.mvvm.uidata.home.HomeViewEvent
import com.example.mvvm_sample.mvvm.uidata.home.HomeViewState
import com.example.mvvm_sample.mvvm.view.TAG_LOG_SO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

const val TAG_LOG_SO = "debug_so"

class HomeViewModel(
    private val model: HomeModel = HomeModel()
) : ViewModel() {

    // ビューの状態
    private val _viewState = MutableStateFlow<HomeViewState>(HomeViewState.Initial)
    val viewState: StateFlow<HomeViewState> = _viewState

    init {
        viewModelScope.launch {
            launch {
                model.comments.collect {
                    _viewState.value = HomeViewState.CommentsLoaded(
                        it?.map { cmt -> CommentListItemData.from(cmt) } ?: emptyList()
                    )
                }
            }

            model.updateComments()
        }
    }

    fun dispatchViewEvent(event: HomeViewEvent) {
        when (event) {
            is HomeViewEvent.ClickItem -> {
                Log.d(TAG_LOG_SO, "clicked! : ${event.id}")
            }
            is HomeViewEvent.ReplyItem -> {
                Log.d(TAG_LOG_SO, "replied! : ${event.id}")
            }
            is HomeViewEvent.ListItem -> {
                Log.d(TAG_LOG_SO, "listed! : ${event.id}")
            }
            is HomeViewEvent.UpdateDataButton -> {
                viewModelScope.launch { model.updateComments() }
            }
            is HomeViewEvent.NewCommentButton -> {
                _viewState.value = HomeViewState.ShowAddCommentDialog
            }
            is HomeViewEvent.CallbackAddCommentDialog -> {
                viewModelScope.launch {
                    val data = event.data ?: return@launch
                    val user = model.getUser() ?: return@launch

                    // コメント新規作成
                    val newComment = Comment(
                        text = data.text,
                        creatorId = user.id,
                        createdDate = Date().toString()
                    )
                    val isCreated = model.createComment(newComment)

                    // トーストで結果表示
                    when (isCreated) {
                        true -> ExtendedApplication.getInstance().getString(R.string.home_create_comment_succeeded)
                        false -> ExtendedApplication.getInstance().getString(R.string.home_create_comment_failed)
                    }.also {
                        // debug: トースト表示
                    }
                }
            }
        }
    }
}