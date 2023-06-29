package com.biryulindevelop.unsplash.presentation.screens.user

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.biryulindevelop.domain.model.Photo
import com.biryulindevelop.domain.state.ClickableView
import com.biryulindevelop.domain.state.LoadState
import com.biryulindevelop.domain.state.ProfileState
import com.biryulindevelop.unsplash.R
import com.biryulindevelop.unsplash.databinding.FragmentProfileBinding
import com.biryulindevelop.unsplash.presentation.screens.photos.list.adapter.PhotoPagingAdapter
import com.biryulindevelop.unsplash.presentation.utils.SharedPreferencesUtils
import com.biryulindevelop.unsplash.presentation.utils.imgLoader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel: ProfileViewModel by viewModels()
    private val adapter by lazy {
        PhotoPagingAdapter { buttonState, item ->
            onClickItem(buttonState, item)
        }
    }
    private var location: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLikedPhotos()
        getLoadingState()
        loadStateItems()
        loadStateLike()
        settingAdapter()
        refresher()
        setLocationClick()
        setupLogoutButton(
            SharedPreferencesUtils.createSharedPrefs(
                requireContext(),
                com.biryulindevelop.common.TOKEN_NAME
            )
        )
    }

    private fun getLikedPhotos() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getPhoto().collect { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun getLoadingState() {
        viewModel.getProfile()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadState.collect { loadState -> updateUi(loadState) }
            }
        }
    }

    private fun updateUi(loadState: LoadState) {
        if (loadState == LoadState.ERROR) {
            with(binding) {
                errorView.isVisible = true
                locationView.isEnabled = false
            }

        }
        if (loadState == LoadState.SUCCESS) {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.state.collect { state -> showProfileInfo(state) }
                }
            }
        }
    }

    private fun showProfileInfo(state: ProfileState) {
        when (state) {
            ProfileState.NotStarted -> {}
            is ProfileState.Success -> {
                with(binding) {
                    locationView.isEnabled = true
                    viewModel.setUsername(state.data.userName) { adapter.refresh() }
                    locationTextView.text = state.data.location ?: getString(R.string.not_specified)
                    userEmailTextView.text = getString(R.string.not_specified)
                    userNameTextView.text = state.data.userName
                    nameTextView.text = state.data.name
                    likesTextView.text =
                        getString(R.string.user_total_likes, state.data.totalLikes)
                    avatarImgView.imgLoader(state.data.avatar)
                    location = state.data.location
                }
            }
        }
    }

    private fun onClickItem(buttonState: ClickableView, item: Photo) {
        when (buttonState) {
            ClickableView.PHOTO ->
                findNavController().navigate(
                    ProfileFragmentDirections.actionNavigationUserToNavigationPhotoDetails(item.id)
                )

            ClickableView.LIKE -> {
                viewModel.like(item)
                getLoadingState()
            }
        }
    }

    private fun settingAdapter() {
        with(binding) {
            photoRecyclerView.adapter = adapter
            photoRecyclerView.itemAnimator?.changeDuration = 0
        }
    }

    private fun loadStateItems() {
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
        binding.swipeRefresh.setOnRefreshListener {
            with(binding) {
                photoRecyclerView.isVisible = true
                adapter.refresh()
                swipeRefresh.isRefreshing = false
            }
        }
    }

    private fun showLocationOnMap(locationUri: Uri) {
        val mapIntent = Intent(Intent.ACTION_VIEW).apply { data = locationUri }
        startActivity(mapIntent)
    }

    private fun setLocationClick() {
        binding.locationView.setOnClickListener {
            if (location != null) {
                showLocationOnMap(Uri.parse("geo:0,0?q=$location"))
            }
        }
    }

    private fun setupLogoutButton(preferences: SharedPreferences) {
        val button = binding.logOutBarView.menu.getItem(0)
        button?.setOnMenuItemClickListener {
            alertDialog(preferences)
            true
        }
    }

    private fun alertDialog(preferences: SharedPreferences) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle(R.string.logout_title)
            .setMessage(R.string.logout_message)
            .setPositiveButton(R.string.yes) { _, _ ->
                preferences.edit().putString(com.biryulindevelop.common.TOKEN_KEY, "").apply()
                preferences.edit().putBoolean(com.biryulindevelop.common.TOKEN_ENABLED, false)
                    .apply()
                val action = ProfileFragmentDirections.actionNavigationUserToAuthFragment()
                findNavController().navigate(action)
            }
            .setNegativeButton(R.string.no) { _, _ ->
                dialog.create().hide()
            }
        dialog.create().show()
    }
}
