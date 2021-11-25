package com.example.mvvm_sample.mvvm.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm_sample.databinding.FragmentCommentListBinding
import com.example.mvvm_sample.mvvm.uidata.commentlist.CommentListViewEvent
import com.example.mvvm_sample.mvvm.uidata.commentlist.CommentListViewState
import com.example.mvvm_sample.mvvm.viewmodel.CommentListViewModel
import com.example.mvvm_sample.ui.CommentListAdaptor
import kotlinx.coroutines.flow.collect

class CommentListFragment : Fragment() {

    private val viewModel: CommentListViewModel by lazy {
        ViewModelProvider(this).get(CommentListViewModel::class.java)
    }
    private lateinit var binding: FragmentCommentListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommentListBinding.inflate(inflater, container, false)

        binding.commentListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.commentListRecyclerView.adapter = CommentListAdaptor(
            CommentListAdaptor.Listener(
            clickListener = { viewModel.dispatchViewEvent(CommentListViewEvent.ClickItem(it)) },
            replyListener = { viewModel.dispatchViewEvent(CommentListViewEvent.ReplyItem(it))},
            listListener = { viewModel.dispatchViewEvent(CommentListViewEvent.ListItem(it)) }
        ))

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.dispatchViewEvent(CommentListViewEvent.ClickListSpinnerItem(position))
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.dispatchViewEvent(CommentListViewEvent.ClickListSpinnerItem(null))
            }
        }

        binding.addCommentListButton.setOnClickListener {
            viewModel.dispatchViewEvent(CommentListViewEvent.AddCommentListButton)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect { state ->
                when (state) {
                    is CommentListViewState.Initial -> {

                    }
                    is CommentListViewState.ShowAddCommentDialog -> {

                    }
                    is CommentListViewState.UpdatedCommentLists -> {
                        ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            state.lists
                        ).apply {
                            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        }.also {
                            binding.spinner.adapter = it
                        }
                    }
                    is CommentListViewState.UpdatedCurrentList -> {
                        // やることあれば。
                    }
                    is CommentListViewState.UpdatedCurrentListComments -> {
                        (binding.commentListRecyclerView.adapter as? CommentListAdaptor)?.apply {
                            data = state.data
                        }
                    }
                }
            }
        }

        return binding.root
    }
}