package com.example.mvvm_sample.mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm_sample.databinding.FragmentNotificationsBinding
import com.example.mvvm_sample.mvvm.uidata.notification.NotificationViewEvent
import com.example.mvvm_sample.mvvm.uidata.notification.NotificationViewState
import com.example.mvvm_sample.mvvm.viewmodel.NotificationViewModel
import com.example.mvvm_sample.ui.notification.NotificationListAdaptor
import kotlinx.coroutines.flow.collect

class NotificationsFragment : Fragment() {
    private val viewModel: NotificationViewModel by lazy {
        ViewModelProvider(this).get(NotificationViewModel::class.java)
    }
    private lateinit var binding: FragmentNotificationsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)

        binding.notificationListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.notificationListRecyclerView.adapter = NotificationListAdaptor(
            NotificationListAdaptor.Listener(
                clickListener = { viewModel.dispatchViewEvent(NotificationViewEvent.ClickListItem(it)) }
            )
        )

        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect { state ->
                when (state) {
                    is NotificationViewState.Initial -> {

                    }
                    is NotificationViewState.UpdateList -> {
                        (binding.notificationListRecyclerView.adapter as? NotificationListAdaptor)?.apply {
                            data = state.notifications
                        }
                    }
                }
            }
        }
        return binding.root
    }
}