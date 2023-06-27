package com.biryulindevelop.unsplash.presentation.screens.photos.details

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.RECEIVER_EXPORTED
import androidx.core.content.ContextCompat.registerReceiver
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.biryulindevelop.unsplash.R
import com.biryulindevelop.unsplash.data.state.LoadState
import com.biryulindevelop.unsplash.databinding.FragmentOnePhotoDetailsBinding
import com.biryulindevelop.unsplash.domain.model.PhotoDetails
import com.biryulindevelop.unsplash.presentation.utils.imgLoader
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnePhotoDetailsFragment : Fragment(R.layout.fragment_one_photo_details) {

    private val binding by viewBinding(FragmentOnePhotoDetailsBinding::bind)
    private val viewModel: OnePhotoDetailsViewModel by viewModels()
    private val args by navArgs<OnePhotoDetailsFragmentArgs>()
    private val downloadManager by lazy {
        requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }
    private lateinit var receiver: BroadcastReceiver
    private var lat: Double? = null
    private var lon: Double? = null
    private var enableDownloadFlag = false

    private val launcher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { map ->
        if (map.values.isNotEmpty() && map.values.all { it }) {
            Snackbar.make(
                binding.myCoordinatorLayout,
                getString(R.string.ok_to_download),
                Snackbar.LENGTH_LONG
            ).show()
            enableDownloadFlag = true
        } else {
            missingPermissionAlert()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadPhotoDetails(args.photoId)
        getLoadingState()
        locationClick()
        stateLike()
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                val reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (viewModel.downloadID == reference) {
                    viewModel.getDMStatus(downloadManager)
                    if (viewModel.success) {
                        val uri = downloadManager.getUriForDownloadedFile(viewModel.downloadID)
                        successfulDownloadMsg(uri)
                    } else {
                        failedDownloadMsg()
                    }
                }
            }
        }
        registerReceiver(requireContext(), receiver, filter, RECEIVER_EXPORTED)
    }

    private fun getLoadingState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadState.collect { loadState -> updateUiOnServerResponse(loadState) }
            }
        }
    }

    private fun updateUiOnServerResponse(loadState: LoadState) {
        if (loadState == LoadState.ERROR) {
            with(binding) {
                errorView.isVisible = true
                scrollView.isVisible = false
            }
        }
        if (loadState == LoadState.SUCCESS) updateUi()
    }

    private fun updateUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        OnePhotoDetailsState.NotStarted -> {}
                        is OnePhotoDetailsState.Success -> {
                            uploadedTexts(state)
                            uploadedImages(state)
                            uploadedLocation(state)
                            toolbar(state.data.id)
                            likeClick(state.data)
                            downloadOnClick(state.data.urls.raw, downloadManager)
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("StringFormatMatches")
    private fun uploadedTexts(state: OnePhotoDetailsState.Success) {
        with(binding) {
            authorNameTextView.text = state.data.user.name
            authorAccountTextView.text =
                getString(R.string.author_account, state.data.user.username)
            locationTextView.text = state.data.location.city ?: "N/A"
            currentLikesTextView.text = state.data.likes.toString()
            isLikedButtonView.isSelected = state.data.likedByUser
            tagsTextView.text = state.data.tags.joinToString { tag ->
                "#${tag.title ?: "N/A"}"
            }
            exifTextView.text = exifText(state)
            aboutAuthorTextView.text = getString(
                R.string.about, state.data.user.username, state.data.user.bio ?: "N/A"
            )
            downloadsCountTextView.text =
                getString(R.string.download, state.data.downloads, state.data.downloads)
        }
    }

    private fun uploadedImages(state: OnePhotoDetailsState.Success) {
        with(binding) {
            photoImgView.imgLoader(state.data.urls.regular)
            authorAvatarImgView.imgLoader(state.data.user.profileImage.small)
        }
    }

    private fun uploadedLocation(state: OnePhotoDetailsState.Success) {
        if (state.data.location.position.latitude != null && state.data.location.position.longitude != null) {
            lat = state.data.location.position.latitude
            lon = state.data.location.position.longitude
        }
    }

    private fun locationClick() {
        binding.locationView.setOnClickListener {
            Log.d(TAG, "lat $lat\nlon $lon ")
            if (lat != null && lon != null) {
                if (lat != 0.0 && lon != 0.0) {
                    Log.d(TAG, "open map")
                    locationOnMap(Uri.parse("geo: $lat,$lon"))
                }
            } else {
                Log.d(TAG, "don't open map")
                noLocationMsg()
            }
        }
    }

    private fun stateLike() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loadState.collect { loadStateLike ->
                binding.errorView.isVisible =
                    loadStateLike == LoadState.ERROR
            }
        }
    }

    private fun downloadOnClick(url: String, downloadManager: DownloadManager) {
        binding.downloadButton.setOnClickListener {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                viewModel.startDownLoad(url, downloadManager)
            } else {
                checkPermission()
                if (enableDownloadFlag) {
                    viewModel.startDownLoad(url, downloadManager)
                }
            }
        }
    }

    private fun successfulDownloadMsg(uri: Uri) {
        val mySnackbar = Snackbar.make(
            binding.myCoordinatorLayout,
            getString(R.string.download_finished),
            Snackbar.LENGTH_INDEFINITE
        )
        mySnackbar.setAction(R.string.open) {
            val openIntent = Intent().apply {
                action = Intent.ACTION_VIEW
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                flags = FLAG_GRANT_READ_URI_PERMISSION
            }
            openIntent.setDataAndType(uri, "image/jpg")
            startActivity(openIntent)
        }
        mySnackbar.show()
    }

    private fun failedDownloadMsg() {
        Snackbar.make(
            binding.myCoordinatorLayout,
            getString(R.string.failed_download),
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun likeClick(photo: PhotoDetails) {
        binding.isLikedButtonView.setOnClickListener {
            viewModel.like(photo)
        }
    }

    private fun exifText(state: OnePhotoDetailsState.Success): String {
        return buildString {
            append(getString(R.string.made_with, state.data.exif.make ?: "N/A"))
            append(getString(R.string.model, state.data.exif.model ?: "N/A"))
            append(getString(R.string.exposure, state.data.exif.exposureTime ?: "N/A"))
            append(getString(R.string.aperture, state.data.exif.aperture ?: "N/A"))
            append(getString(R.string.focal_length, state.data.exif.focalLength ?: "N/A"))
            append(getString(R.string.iso, state.data.exif.iso?.toString() ?: "N/A"))
        }
    }

    private fun locationOnMap(locationUri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = locationUri
        }
        startActivity(intent)
    }

    private fun noLocationMsg() {
        Snackbar.make(
            binding.myCoordinatorLayout,
            getString(R.string.no_location),
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun toolbar(id: String) {
        val button = binding.shareBarView.menu.getItem(0)
        button?.setOnMenuItemClickListener {
            sharePhoto(id)
            true
        }
    }

    private fun sharePhoto(id: String) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "https://unsplash.com/photos/$id")
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.share)))
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            enableDownloadFlag = true
        } else {
            launcher.launch(arrayOf(WRITE_EXTERNAL_STORAGE))
        }
    }

    private fun missingPermissionAlert() {
        val alertDialog = AlertDialog.Builder(requireContext()).create()
        alertDialog.setTitle(getString(R.string.alert_title))
        alertDialog.setMessage(getString(R.string.alert_text))
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert)
        alertDialog.setButton(
            DialogInterface.BUTTON_POSITIVE,
            getString(R.string.ok)
        ) { dialog, _ ->
            dialog.cancel()
        }
        alertDialog.show()
    }

    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(receiver)
    }
}
