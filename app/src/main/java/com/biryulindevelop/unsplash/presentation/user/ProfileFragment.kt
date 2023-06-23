package com.biryulindevelop.unsplash.presentation.user

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
import com.biryulindevelop.unsplash.R
import com.biryulindevelop.unsplash.application.TOKEN_ENABLED_KEY
import com.biryulindevelop.unsplash.application.TOKEN_KEY
import com.biryulindevelop.unsplash.application.TOKEN_NAME
import com.biryulindevelop.unsplash.data.state.ClickableView
import com.biryulindevelop.unsplash.data.state.LoadState
import com.biryulindevelop.unsplash.databinding.FragmentProfileBinding
import com.biryulindevelop.unsplash.domain.model.Photo
import com.biryulindevelop.unsplash.presentation.photos.list.adapter.PhotoPagingAdapter
import com.biryulindevelop.unsplash.utils.SharedPreferencesUtils
import com.biryulindevelop.unsplash.utils.loadImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel by viewModels<ProfileViewModel>()

    private val adapter by lazy {
        PhotoPagingAdapter { buttonState, item ->
            onClick(buttonState, item)
        }
    }

    private var location: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
        getLoadingState()
        loadStateItemsObserve()
        loadStateLike()
        settingAdapter()
        initRefresher()
        setLocationClick()
        setUpLogoutButton(SharedPreferencesUtils.createSharedPrefs(requireContext(), TOKEN_NAME))
    }

    private fun observe() {
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
                viewModel.loadState.collect { loadState -> updateUiOnServerResponse(loadState) }
            }
        }
    }

    private fun updateUiOnServerResponse(loadState: LoadState) {
        if (loadState == LoadState.ERROR) {
            binding.errorView.isVisible = true
            binding.locationView.isEnabled = false
        }
        if (loadState == LoadState.SUCCESS) {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.state.collect { state -> showInfo(state) }
                }
            }
        }
    }

    private fun showInfo(state: ProfileState) {
        when (state) {
            ProfileState.NotStartedYet -> {}
            is ProfileState.Success -> {
                binding.locationView.isEnabled = true
                viewModel.setUsername(state.data.userName) { adapter.refresh() }
                binding.locationTextView.text = state.data.location
                if (state.data.location == null) binding.locationLayout.visibility = View.GONE
                binding.userNameTextView.text = state.data.userName
                binding.nameTextView.text = state.data.name
                binding.likesTextView.text =
                    getString(R.string.user_total_likes, state.data.totalLikes)
                binding.avatarImgView.loadImage(state.data.avatar)
                location = state.data.location
            }

        }
    }

    private fun onClick(buttonState: ClickableView, item: Photo) {
        when (buttonState) {
            ClickableView.PHOTO ->
                findNavController().navigate(
                    ProfileFragmentDirections
                        .actionNavigationUserToNavigationPhotoDetails(item.id)
                )

            ClickableView.LIKE -> {
                viewModel.like(item)
                getLoadingState()
            }
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

    private fun setUpLogoutButton(preferences: SharedPreferences) {
        val button = binding.logOutBarView.menu.getItem(0)
        button?.setOnMenuItemClickListener {
            setUpAlertDialog(preferences)
            true
        }
    }

    private fun setUpAlertDialog(preferences: SharedPreferences) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle(R.string.logout_title)
            .setMessage(R.string.logout_message)
            .setPositiveButton(R.string.yes) { _, _ ->
                preferences.edit().putString(TOKEN_KEY, "").apply()
                preferences.edit().putBoolean(TOKEN_ENABLED_KEY, false).apply()
                val action = ProfileFragmentDirections.actionNavigationUserToAuthFragment()
                findNavController().navigate(action)
            }
            .setNegativeButton(R.string.no) { _, _ ->
                dialog.create().hide()
            }
        dialog.create().show()
    }
}
