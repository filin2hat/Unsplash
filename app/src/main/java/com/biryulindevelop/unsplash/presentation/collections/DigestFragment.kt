package com.biryulindevelop.unsplash.presentation.collections

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.biryulindevelop.unsplash.R
import com.biryulindevelop.unsplash.databinding.FragmentDigestBinding
import com.biryulindevelop.unsplash.domain.model.Digest
import com.biryulindevelop.unsplash.presentation.collections.adapter.DigestPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DigestFragment : Fragment(R.layout.fragment_digest) {
    private val binding by viewBinding(FragmentDigestBinding::bind)
    private val viewModel by viewModels<DigestViewModel>()
    private val adapter by lazy { DigestPagingAdapter { item -> onClick(item) } }

    private fun onClick(item: Digest) {
        findNavController().navigate(
            DigestFragmentDirections.actionNavigationDigestToDigestDetailsFragment(item.id)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        settingAdapter()
        initRefresher()
        loadStateItemsObserve()

    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getDigest().collect {
                adapter.submitData(it)
            }
        }
    }

    private fun settingAdapter() {
        binding.digestRecyclerView.adapter = adapter
        binding.digestRecyclerView.itemAnimator?.changeDuration = 0
    }

    private fun initRefresher() {
        binding.swipeRefresh.setOnRefreshListener {
            binding.digestRecyclerView.isVisible = true
            adapter.refresh()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun loadStateItemsObserve() {
        adapter.addLoadStateListener { loadState ->
            binding.errorView.isVisible =
                loadState.refresh is androidx.paging.LoadState.Error
            binding.recyclerProgressBarView.isVisible =
                loadState.refresh is androidx.paging.LoadState.Loading
        }
    }
}






