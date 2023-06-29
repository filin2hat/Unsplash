package com.biryulindevelop.unsplash.presentation.screens.collections.digest

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.biryulindevelop.domain.model.Digest
import com.biryulindevelop.unsplash.R
import com.biryulindevelop.unsplash.databinding.FragmentDigestBinding
import com.biryulindevelop.unsplash.presentation.screens.collections.digest.adapter.DigestPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DigestFragment : Fragment(R.layout.fragment_digest) {

    private val binding by viewBinding(FragmentDigestBinding::bind)
    private val viewModel: DigestViewModel by viewModels()
    private val adapter by lazy { DigestPagingAdapter { item -> onClick(item) } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDigest()
        settingAdapter()
        refresher()
        getLoadingState()
    }

    private fun getDigest() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getDigest().collect {
                adapter.submitData(it)
            }
        }
    }

    private fun settingAdapter() {
        with(binding) {
            digestRecyclerView.adapter = adapter
            digestRecyclerView.itemAnimator?.changeDuration = 0
        }
    }

    private fun refresher() {
        with(binding) {
            swipeRefresh.setOnRefreshListener {
                digestRecyclerView.isVisible = true
                adapter.refresh()
                swipeRefresh.isRefreshing = false
            }
        }
    }

    private fun getLoadingState() {
        adapter.addLoadStateListener { loadState ->
            with(binding) {
                errorView.isVisible =
                    loadState.refresh is androidx.paging.LoadState.Error
                recyclerProgressBarView.isVisible =
                    loadState.refresh is androidx.paging.LoadState.Loading
            }
        }
    }

    private fun onClick(item: Digest) {
        findNavController().navigate(
            DigestFragmentDirections.actionNavigationDigestToDigestDetailsFragment(item.id)
        )
    }
}






