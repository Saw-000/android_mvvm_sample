package com.example.mvvm_sample.mvvm.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.mvvm_sample.databinding.FragmentMyPageBinding
import com.example.mvvm_sample.mvvm.viewmodel.MyPageViewModel
import kotlinx.coroutines.flow.collect

class MyPageFragment : Fragment() {

    private val viewModel: MyPageViewModel by lazy {
        ViewModelProvider(this).get(MyPageViewModel::class.java)
    }
    private lateinit var binding: FragmentMyPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyPageBinding.inflate(inflater, container, false)
        lifecycleScope.launchWhenStarted {
            viewModel.text.collect{ binding.myPageTextView.text = it }
        }
        return binding.root
    }
}