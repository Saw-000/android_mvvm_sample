package com.example.mvvm_sample.mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm_sample.databinding.FragmentHomeBinding
import com.example.mvvm_sample.mvvm.uidata.home.AddCommentDialogCallbackData
import com.example.mvvm_sample.mvvm.uidata.home.HomeViewEvent
import com.example.mvvm_sample.mvvm.uidata.home.HomeViewState
import com.example.mvvm_sample.mvvm.viewmodel.HomeViewModel
import com.example.mvvm_sample.ui.CommentListAdaptor
import com.example.mvvm_sample.ui.home.AddCommentDialog
import kotlinx.coroutines.flow.collect

const val TAG_LOG_SO = "debug_so"
const val DIALOG_ADD_COMMENT = "dialog_add_comment"

class HomeFragment : Fragment(),
        AddCommentDialog.CallbackListener
{
    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.homeListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.homeListRecyclerView.adapter = CommentListAdaptor(
            CommentListAdaptor.Listener(
            clickListener = { viewModel.dispatchViewEvent(HomeViewEvent.ClickItem(it)) },
            replyListener = { viewModel.dispatchViewEvent(HomeViewEvent.ReplyItem(it)) },
            listListener = { viewModel.dispatchViewEvent(HomeViewEvent.ListItem(it)) }
        ))

        binding.updateButton.setOnClickListener {
            viewModel.dispatchViewEvent(HomeViewEvent.UpdateDataButton)
        }
        binding.newCommentButton.setOnClickListener {
            viewModel.dispatchViewEvent(HomeViewEvent.NewCommentButton)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect { state ->
                when (state) {
                    is HomeViewState.Initial -> {

                    }
                    is HomeViewState.CommentsLoaded -> {
                        (binding.homeListRecyclerView.adapter as? CommentListAdaptor)?.apply {
                            data = state.itemDataList
                        }
                    }
                    is HomeViewState.Indicator -> {
                        when (state.needShow) {
                            true -> {}
                            false -> {}
                        }
                    }
                    is HomeViewState.ShowAddCommentDialog -> {// debug_so: ダイアログ表示で落ちる
                        AddCommentDialog.newIntent().apply {
                            show(childFragmentManager, DIALOG_ADD_COMMENT)
                        }
                    }
                }
            }
        }
        return binding.root
    }

    override fun finishAddCommentDialog(data: AddCommentDialogCallbackData?) {
        viewModel.dispatchViewEvent(HomeViewEvent.CallbackAddCommentDialog(data))
    }
}