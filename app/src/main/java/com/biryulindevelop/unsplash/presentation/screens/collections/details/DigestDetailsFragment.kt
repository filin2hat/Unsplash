package com.biryulindevelop.unsplash.presentation.screens.collections.details

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.biryulindevelop.unsplash.R
import com.biryulindevelop.unsplash.data.state.ClickableView
import com.biryulindevelop.unsplash.data.state.LoadState
import com.biryulindevelop.unsplash.databinding.FragmentDigestDetailsBinding
import com.biryulindevelop.unsplash.domain.model.Photo
import com.biryulindevelop.unsplash.presentation.screens.photos.list.adapter.PhotoPagingAdapter
import com.biryulindevelop.unsplash.presentation.utils.imgLoader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DigestDetailsFragment : Fragment(R.layout.fragment_digest_details) {
    private val binding by viewBinding(FragmentDigestDetailsBinding::bind)
    private val viewModel: DigestDetailsViewModel by viewModels()
    private val args: DigestDetailsFragmentArgs by navArgs()
    private val adapter by lazy {
        PhotoPagingAdapter { buttonState, item ->
            onClickItem(buttonState, item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDigestDetails()
        getLoadingState()
        loadStateItemsObserve()
        loadStateLike()
        settingAdapter()
        refresher()
    }

    private fun getDigestDetails() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.setId(args.id) { adapter.refresh() }
            viewModel.getPhoto().collect { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun getLoadingState() {
        viewModel.getDigestInfo(args.id)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadState.collect { loadState -> updateUi(loadState) }
            }
        }
    }

    private fun updateUi(loadState: LoadState) {
        if (loadState == LoadState.ERROR) {
            binding.errorView.isVisible = true
        }
        if (loadState == LoadState.SUCCESS) {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.state.collect { state -> showDetailInfo(state) }
                }
            }
        }
    }

    private fun showDetailInfo(state: DigestState) {
        when (state) {
            DigestState.NotStarted ->
                binding.toolProgressBar.visibility = View.VISIBLE

            is DigestState.Success -> {
                with(binding) {
                    toolProgressBar.visibility = View.GONE
                    collapsingToolbarLayout.title = state.data.title
                    digestTitleTextView.text = state.data.title
                    descriptionTextView.text = state.data.description
                    tagsTextView.text = state.data.tags.joinToString { tag ->
                        "#${tag.title}"
                    }
                    dateTextView.text =
                        resources.getQuantityString(
                            R.plurals.digest_data,
                            state.data.totalPhotos,
                            state.data.totalPhotos,
                            state.data.userUsername
                        )
                    previewImgView.imgLoader(state.data.previewPhoto)
                }
            }
        }
    }

    private fun onClickItem(buttonState: ClickableView, item: Photo) {
        when (buttonState) {
            ClickableView.PHOTO ->
                findNavController().navigate(
                    DigestDetailsFragmentDirections.actionDigestDetailsFragmentToNavigationPhotoDetails(
                        item.id
                    )
                )

            ClickableView.LIKE -> viewModel.like(item)
        }
    }

    private fun settingAdapter() {
        with(binding) {
            photoRecyclerView.adapter = adapter
            photoRecyclerView.itemAnimator?.changeDuration = 0
        }
    }

    private fun loadStateItemsObserve() {
        adapter.addLoadStateListener { loadState ->
            with(binding) {
                errorView.isVisible =
                    loadState.mediator?.refresh is androidx.paging.LoadState.Error
                recyclerProgressBarView.isVisible =
                    loadState.mediator?.refresh is androidx.paging.LoadState.Loading
            }
        }
    }

    private fun loadStateLike() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loadState.collect { loadStateLike ->
                binding.errorView.isVisible =
                    loadStateLike == LoadState.ERROR
            }
        }
    }

    private fun refresher() {
        with(binding) {
            swipeRefresh.setOnRefreshListener {
                photoRecyclerView.isVisible = true
                adapter.refresh()
                swipeRefresh.isRefreshing = false
            }
        }
    }
}
