package com.example.mvvm_sample.mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mvvm_sample.databinding.FragmentDashboardBinding
import com.example.mvvm_sample.mvvm.viewmodel.FollowViewModel
import kotlinx.coroutines.flow.collect

class FollowFragment : Fragment() {

    private val viewModel: FollowViewModel by lazy {
        ViewModelProvider(this).get(FollowViewModel::class.java)
    }
    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        lifecycleScope.launchWhenStarted {
            viewModel.text.collect{ binding.textDashboard.text = it }
        }
        return binding.root
    }
}