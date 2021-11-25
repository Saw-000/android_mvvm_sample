package com.example.mvvm_sample.mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_sample.ExtendedApplication
import com.example.mvvm_sample.R
import com.example.mvvm_sample.database.datatype.CommentList
import com.example.mvvm_sample.mvvm.model.CommentListModel
import com.example.mvvm_sample.mvvm.uidata.CommentListItemData
import com.example.mvvm_sample.mvvm.uidata.commentlist.CommentListViewEvent
import com.example.mvvm_sample.mvvm.uidata.commentlist.CommentListViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CommentListViewModel(
    val model: CommentListModel = CommentListModel()
) : ViewModel() {

    // ビューの状態
    private val _viewState = MutableStateFlow<CommentListViewState>(CommentListViewState.Initial)
    val viewState: StateFlow<CommentListViewState> = _viewState

    private val currentList = MutableStateFlow<CommentList?>(null)

    init {
        viewModelScope.launch {
            // 監視
            launch {
                model.commentLists.collect {
                    _viewState.value = CommentListViewState.UpdatedCommentLists(
                        it?.map { cmtlist -> cmtlist.title } ?: emptyList()
                    )
                }
            }
            launch {
                currentList.collect {
                    _viewState.value = CommentListViewState.UpdatedCurrentList(it)
                    model.updateCurrentListComments(it?.id)
                }
            }
            launch {
                model.currentListComments.collect {
                    _viewState.value = CommentListViewState.UpdatedCurrentListComments(
                        it?.map { cmt -> CommentListItemData.from(cmt) } ?: emptyList()
                    )
                }
            }

            // 初期処理
            launch {
                model.updateCommentLists()
            }
        }
    }

    fun dispatchViewEvent(event: CommentListViewEvent) {
        when (event) {
            is CommentListViewEvent.ClickItem -> {
                Log.d(com.example.mvvm_sample.mvvm.view.TAG_LOG_SO, "clicked! : ${event.id}")
            }
            is CommentListViewEvent.ReplyItem -> {
                Log.d(com.example.mvvm_sample.mvvm.view.TAG_LOG_SO, "replied! : ${event.id}")
            }
            is CommentListViewEvent.ListItem -> {
                Log.d(com.example.mvvm_sample.mvvm.view.TAG_LOG_SO, "listed! : ${event.id}")
            }
            is CommentListViewEvent.ClickListSpinnerItem -> {
                val index = event.index ?: run {
                    currentList.value = null
                    return@dispatchViewEvent
                }
                val lists = model.commentLists.value ?: run {
                    currentList.value = null
                    return@dispatchViewEvent
                }
                currentList.value = lists.elementAtOrNull(index)
            }
            is CommentListViewEvent.AddCommentListButton -> {

            }
            is CommentListViewEvent.CallbackAddCommentListDialog -> {
                viewModelScope.launch {
                    val data = event.data ?: return@launch
                    val userId = model.getUserId() ?: return@launch

                    // コメントリスト作成
                    val isCreated = model.createCommentList(
                        CommentList(
                            title = data.title,
                            creatorId = userId,
                            commentIds = emptyList()
                        )
                    )

                    // トーストで結果表示
                    val resultToastText = when (isCreated) {
                        true -> ExtendedApplication.getInstance().getString(R.string.comment_list_create_comment_list_succeeded)
                        false -> ExtendedApplication.getInstance().getString(R.string.comment_list_create_comment_list_failed)
                    }.also {
                        // debug: トーストで結果表示
                    }
                }
            }
        }
    }
}