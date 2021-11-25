package com.example.mvvm_sample.mvvm.model

import com.example.mvvm_sample.database.datatype.notification.Notification
import com.example.mvvm_sample.database.repository.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow

class NotificationModel(
    private val apiRepo: ApiRepository = ApiRepository()
) {
    // 通知列
    private val _notifications = MutableStateFlow<List<Notification>?>(null)
    val notifications = _notifications

    suspend fun updateNotifications() {
        _notifications.value = apiRepo.getNotifications()
    }
}