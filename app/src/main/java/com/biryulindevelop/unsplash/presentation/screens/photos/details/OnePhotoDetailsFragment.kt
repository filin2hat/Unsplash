package com.biryulindevelop.unsplash.presentation.screens.photos.details

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class OnePhotoDetailsFragment : Fragment(R.layout.fragment_one_photo_details) {
    //private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    private val binding by viewBinding(FragmentOnePhotoDetailsBinding::bind)
    private val viewModel: OnePhotoDetailsViewModel by viewModels()
    private val navArgs: OnePhotoDetailsFragmentArgs by navArgs()

    private var lattitude: Double? = null
    private var longitude: Double? = null

    private lateinit var receiver: BroadcastReceiver
    private var isReceiverRegistered = false

    private val launcher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { map ->
        if (map.values.isNotEmpty() && map.values.all { it }) {
            Snackbar.make(
                binding.myCoordinatorLayout,
                getString(R.string.ok_to_download),
                Snackbar.LENGTH_SHORT
            ).show()
        } else {
            missingPermissionAlert()
        }
    }

    private fun checkPermission(url: String) {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startDownload(url)
        } else {
            launcher.launch(arrayOf(WRITE_EXTERNAL_STORAGE))
        }
    }

//    private fun checkPermission(url: String) {
//        when {
//            ContextCompat.checkSelfPermission(
//                requireContext(),
//                WRITE_EXTERNAL_STORAGE
//            ) == PackageManager.PERMISSION_GRANTED -> {
//                startDownload(url)
//            }
//
//            shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE) -> {
//                showPermissionRequestDialog("permission_title", "write_permission_request") {
//                    requestPermissionLauncher.launch(WRITE_EXTERNAL_STORAGE)
//                }
//            }
//
//            else -> {
//                requestPermissionLauncher.launch(WRITE_EXTERNAL_STORAGE)
//            }
//        }
//    }
//
//    private fun showPermissionRequestDialog(
//        title: String,
//        body: String,
//        callback: () -> Unit
//    ) {
//        AlertDialog.Builder(requireContext()).also {
//            it.setTitle(title)
//            it.setMessage(body)
//            it.setPositiveButton("Ok") { _, _ ->
//                callback()
//            }
//        }.create().show()
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadPhotoDetails(navArgs.photoId)
        getLoadingState()
        locationClick()
        stateLike()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isReceiverRegistered) {
            requireActivity().unregisterReceiver(receiver)
            isReceiverRegistered = false
        }
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
                            downloadOnClick(state.data.urls.raw)
                        }
                    }
                }
            }
        }
    }

    private fun downloadOnClick(url: String) {
        binding.downloadButton.setOnClickListener {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                startDownload(url)
            } else {
                checkPermission(url)
            }
        }
    }

    private fun startDownload(url: String) {
        startingDownloadMsg()
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "unsl_$timestamp.jpg"

        val request = DownloadManager.Request(Uri.parse(url))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

        val downloadManager =
            requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadId = downloadManager.enqueue(request)

        receiver = object : BroadcastReceiver() {
            @SuppressLint("Range")
            override fun onReceive(context: Context?, intent: Intent?) {
                val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (id == downloadId) {
                    val query = DownloadManager.Query().setFilterById(downloadId)
                    val cursor = downloadManager.query(query)
                    if (cursor.moveToFirst()) {
                        val status =
                            cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                        if (status == DownloadManager.STATUS_SUCCESSFUL) {
                            successfulDownloadMsg()
                        } else {
                            failedDownloadMsg()
                        }
                    }
                    cursor.close()
                }
            }
        }
        val filter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        requireContext().registerReceiver(receiver, filter)
        isReceiverRegistered = true
    }

    private fun failedDownloadMsg() {
        Snackbar.make(
            binding.myCoordinatorLayout,
            getString(R.string.failed_download),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun successfulDownloadMsg() {
        Snackbar.make(
            binding.myCoordinatorLayout,
            getString(R.string.download_finished),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun startingDownloadMsg() {
        Snackbar.make(
            binding.myCoordinatorLayout,
            getString(R.string.download_started),
            Snackbar.LENGTH_SHORT
        ).show()
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
            lattitude = state.data.location.position.latitude
            longitude = state.data.location.position.longitude
        }
    }

    private fun locationClick() {
        binding.locationView.setOnClickListener {
            Log.d(TAG, "lat $lattitude\nlon $longitude ")
            if (lattitude != null && longitude != null) {
                if (lattitude != 0.0 && longitude != 0.0) {
                    Log.d(TAG, "open map")
                    locationOnMap(Uri.parse("geo: $lattitude,$longitude"))
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
}
