package com.example.mvvm_sample.mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_sample.mvvm.model.NotificationModel
import com.example.mvvm_sample.mvvm.uidata.notification.NotificationListItemData
import com.example.mvvm_sample.mvvm.uidata.notification.NotificationViewEvent
import com.example.mvvm_sample.mvvm.uidata.notification.NotificationViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NotificationViewModel(
    val model: NotificationModel = NotificationModel()
) : ViewModel() {
    // ビューの状態
    private val _viewState = MutableStateFlow<NotificationViewState>(NotificationViewState.Initial)
    val viewState: StateFlow<NotificationViewState> = _viewState

    init {
        viewModelScope.launch {
            launch {
                model.notifications.collect {
                    _viewState.value = NotificationViewState.UpdateList(
                        it?.map { noti -> NotificationListItemData.from(noti) } ?: emptyList()
                    )
                }
            }

            model.updateNotifications()
        }
    }

    fun dispatchViewEvent(event: NotificationViewEvent) {
        when (event) {
            is NotificationViewEvent.ClickListItem -> {
                Log.d("debug_so", "ClickListItem: id = ${event.notificationId}")
            }
        }
    }
}