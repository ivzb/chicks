package com.ivzb.chicks.ui.link

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ivzb.chicks.analytics.AnalyticsActions
import com.ivzb.chicks.analytics.AnalyticsHelper
import com.ivzb.chicks.analytics.AnalyticsScreens
import com.ivzb.chicks.databinding.DialogLinkOptionsBinding
import com.ivzb.chicks.domain.EventObserver
import com.ivzb.chicks.model.Link
import com.ivzb.chicks.util.copy
import com.ivzb.chicks.util.executeAfter
import com.ivzb.chicks.util.share
import com.ivzb.chicks.util.viewModelProvider
import dagger.android.support.DaggerAppCompatDialogFragment
import javax.inject.Inject

/**
 * Dialog that tells the user to sign in to continue the operation.
 */
class LinkOptionsDialogFragment : DaggerAppCompatDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var analyticsHelper: AnalyticsHelper

    private lateinit var linkOptionsViewModel: LinkOptionsViewModel

    private lateinit var binding: DialogLinkOptionsBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // We want to create a dialog, but we also want to use DataBinding for the content view.
        // We can do that by making an empty dialog and adding the content later.
        return MaterialAlertDialogBuilder(requireContext()).create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // In case we are showing as a dialog, use getLayoutInflater() instead.
        binding = DialogLinkOptionsBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        analyticsHelper.sendScreenView(AnalyticsScreens.OPTIONS, requireActivity())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        linkOptionsViewModel = viewModelProvider(viewModelFactory)

        val link = arguments?.getParcelable<Link>(LINK) ?: throw IllegalArgumentException("no link passed")

        linkOptionsViewModel.setLink(link)

        linkOptionsViewModel.performLinkOptionEvent.observe(this, EventObserver { request ->
            when (request) {
                LinkOptionsEvent.Copy -> copy(link)
                LinkOptionsEvent.Share -> share(link)
            }

            dismiss()
        })

        binding.executeAfter {
            viewModel = linkOptionsViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        if (showsDialog) {
            (requireDialog() as AlertDialog).setView(binding.root)
        }
    }

    private fun copy(link: Link) {
        copy(requireActivity(), link.imageUrl)
        analyticsHelper.logUiEvent(AnalyticsActions.LINK_COPY)
    }

    private fun share(link: Link) {
        share(requireActivity(), link.imageUrl)
        analyticsHelper.logUiEvent(AnalyticsActions.LINK_SHARE)
    }

    companion object {

        const val LINK = "LINK"
    }
}
