package com.ivzb.chicks.ui.details

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.updatePadding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.ivzb.chicks.R
import com.ivzb.chicks.analytics.AnalyticsActions
import com.ivzb.chicks.analytics.AnalyticsHelper
import com.ivzb.chicks.analytics.AnalyticsScreens
import com.ivzb.chicks.databinding.FragmentDetailsBinding
import com.ivzb.chicks.ui.main.MainNavigationFragment
import com.ivzb.chicks.ui.messages.SnackbarMessageManager
import com.ivzb.chicks.util.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import javax.inject.Inject
import android.content.Intent
import android.graphics.Bitmap
import com.ivzb.chicks.model.Link
import java.text.SimpleDateFormat
import java.util.*

class DetailsFragment : MainNavigationFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var snackbarMessageManager: SnackbarMessageManager

    @Inject
    lateinit var analyticsHelper: AnalyticsHelper

    private lateinit var detailsViewModel: DetailsViewModel

    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailsViewModel = viewModelProvider(viewModelFactory)

        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(R.transition.link_shared_enter)
        // Delay the enter transition until image has loaded.
        postponeEnterTransition(500L)

        binding = FragmentDetailsBinding.inflate(inflater, container, false).apply {
            viewModel = detailsViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        binding.detailsBottomAppBar.run {
            inflateMenu(R.menu.details_menu)

            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_item_copy -> {
                        detailsViewModel.link.value?.apply {
                            copy(requireActivity(), imageUrl)
                            analyticsHelper.logUiEvent(AnalyticsActions.LINK_COPY)
                        }
                    }

                    R.id.menu_item_share -> {
                        detailsViewModel.link.value?.apply {
                            share(requireActivity(), imageUrl)
                            analyticsHelper.logUiEvent(AnalyticsActions.LINK_SHARE)
                        }
                    }
                }

                true
            }
        }

        val detailsAdapter = DetailsAdapter(
            viewLifecycleOwner,
            detailsViewModel
        )

        binding.detailsRecyclerView.run {
            adapter = detailsAdapter
            itemAnimator?.run {
                addDuration = 120L
                moveDuration = 120L
                changeDuration = 120L
                removeDuration = 100L
            }
            doOnApplyWindowInsets { view, insets, state ->
                view.updatePadding(bottom = state.bottom + insets.systemWindowInsetBottom)
                // CollapsingToolbarLayout's defualt scrim visible trigger height is a bit large.
                // Choose something smaller so that the content stays visible longer.
                binding.collapsingToolbar.scrimVisibleHeightTrigger =
                    insets.systemWindowInsetTop * 2
            }
        }

        detailsViewModel.link.observe(this, Observer {
            detailsAdapter.link = listOf(it)
        })


        requireArguments().apply {
            val link = DetailsFragmentArgs.fromBundle(this).link
            detailsViewModel.setLink(link)

            binding.fabDownload.setOnClickListener {
                downloadPhoto(link)
            }
        }

        return binding.root
    }

    private fun downloadPhoto(link: Link) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
            requireActivity().checkSelfPermission(WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(WRITE_EXTERNAL_STORAGE), 21)

        } else {

            Glide.with(requireContext())
                .asBitmap()
                .load(link.imageUrl)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(
                        bitmap: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        saveImage(bitmap)
                    }
                })
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            binding.fabDownload.performClick()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        analyticsHelper.sendScreenView(AnalyticsScreens.DETAILS, requireActivity())
    }

    private fun saveImage(image: Bitmap) {
        val storageFolder =
            File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString())
        val timestamp = SimpleDateFormat("yyyyMMdd_HH_mm_ss", Locale.US).format(Date())
        val imageFile = File(storageFolder, "JPEG_$timestamp.jpg")

        try {
            val output: OutputStream = FileOutputStream(imageFile)
            image.compress(Bitmap.CompressFormat.JPEG, 100, output)
            output.close()
        } catch (e: Exception) {

        }

        // Add the image to the system gallery
        galleryAddPic(imageFile.absolutePath)

        Toast.makeText(requireContext(), "Image saved in Downloads.", Toast.LENGTH_LONG).show()
    }

    private fun galleryAddPic(imagePath: String?) {
        val file = File(imagePath)
        val contentUri: Uri = Uri.fromFile(file)

        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        mediaScanIntent.data = contentUri

        requireContext().sendBroadcast(mediaScanIntent)
    }
}
