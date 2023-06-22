package com.biryulindevelop.unsplash.presentation.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.biryulindevelop.unsplash.R
import com.biryulindevelop.unsplash.data.state.ClickableView
import com.biryulindevelop.unsplash.data.state.LoadState
import com.biryulindevelop.unsplash.databinding.FragmentDigestDetailsBinding
import com.biryulindevelop.unsplash.domain.model.Photo
import com.biryulindevelop.unsplash.presentation.photos.list.adapter.PhotoPagingAdapter
import com.biryulindevelop.unsplash.tools.BaseFragment
import com.biryulindevelop.unsplash.tools.loadImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DigestDetailsFragment : BaseFragment<FragmentDigestDetailsBinding>() {

    private val viewModel by viewModels<DigestDetailsViewModel>()

    private val adapter by lazy {
        PhotoPagingAdapter { buttonState, item ->
            onClick(buttonState, item)
        }
    }

    private val args by navArgs<DigestDetailsFragmentArgs>()

    override fun initBinding(inflater: LayoutInflater) =
        FragmentDigestDetailsBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
        loadStateObserver()
        loadStateItemsObserve()
        loadStateLike()
        settingAdapter()
        initRefresher()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.setId(args.id) { adapter.refresh() }
            viewModel.getPhoto().collect { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun loadStateObserver() {
        viewModel.getDigestInfo(args.id)
        viewLifecycleOwner.lifecycleScope
            .launchWhenStarted {
                viewModel.loadState.collect { loadState -> updateUiOnServerResponse(loadState) }
            }
    }

    private fun updateUiOnServerResponse(loadState: LoadState) {
        if (loadState == LoadState.ERROR) {
            binding.errorView.isVisible = true
        }
        if (loadState == LoadState.SUCCESS) {
            viewLifecycleOwner.lifecycleScope
                .launchWhenStarted {
                    viewModel.state
                        .collect { state -> showInfo(state) }
                }
        }
    }

    private fun showInfo(state: DigestState) {
        when (state) {
            DigestState.NotStartedYet ->
                binding.toolProgressBar.visibility = View.VISIBLE

            is DigestState.Success -> {
                binding.toolProgressBar.visibility = View.GONE
                binding.collapsingToolbarLayout.title = state.data.title
                binding.digestTitleTextView.text = state.data.title
                binding.descriptionTextView.text = state.data.description
                binding.tagsTextView.text = state.data.tags.joinToString { tag ->
                    "#${tag.title}"
                }
                binding.dateTextView.text =
                    resources.getQuantityString(
                        R.plurals.digest_data,
                        state.data.totalPhotos,
                        state.data.totalPhotos,
                        state.data.userUsername
                    )
                binding.previewImgView.loadImage(state.data.previewPhoto)
            }
        }
    }

    private fun onClick(buttonState: ClickableView, item: Photo) {
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
        binding.photoRecyclerView.adapter = adapter
        binding.photoRecyclerView.itemAnimator?.changeDuration = 0
    }

    private fun loadStateItemsObserve() {
        adapter.addLoadStateListener { loadState ->
            binding.errorView.isVisible =
                loadState.mediator?.refresh is androidx.paging.LoadState.Error
            binding.recyclerProgressBarView.isVisible =
                loadState.mediator?.refresh is androidx.paging.LoadState.Loading
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

    private fun initRefresher() {
        binding.swipeRefresh.setOnRefreshListener {
            binding.photoRecyclerView.isVisible = true
            adapter.refresh()
            binding.swipeRefresh.isRefreshing = false
        }
    }
}
