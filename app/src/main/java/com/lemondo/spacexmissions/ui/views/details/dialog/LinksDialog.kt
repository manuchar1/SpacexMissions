package com.lemondo.spacexmissions.ui.views.details.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lemondo.spacexmissions.R
import com.lemondo.spacexmissions.base.goneIf
import com.lemondo.spacexmissions.data.models.Links
import com.lemondo.spacexmissions.databinding.DialogUsefulLinksBinding

/**
 * Created by Manuchar Zakariadze on 3/3/22
 */
class LinksDialog : DialogFragment(), View.OnClickListener {

    private val args: LinksDialogArgs by navArgs()

    private lateinit var binding: DialogUsefulLinksBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogUsefulLinksBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpLinksView(args.usefulLinks)

        binding.tvMissionPatch.setOnClickListener(this)
        binding.btnCloseDialog.setOnClickListener(this)
        binding.tvArticleLink.setOnClickListener(this)
        binding.tvWikipedia.setOnClickListener(this)


    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setUpLinksView(data: Links?) {
        binding.apply {
            tvTittleArticleLink.goneIf(data?.articleLink == null)
            tvArticleLink.text = data?.articleLink as CharSequence?

            tvTitleMissionPatch.goneIf(data?.videoLink == null)
            tvMissionPatch.text = data?.videoLink.toString()


            tvTittleWikipedia.goneIf(data?.wikipedia == null)
            tvWikipedia.text = data?.wikipedia as CharSequence?

        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnCloseDialog -> {
                dialog?.dismiss()
            }
            R.id.tvArticleLink -> {
                val bundle =
                    bundleOf(
                        "articleUrl" to args.usefulLinks?.articleLink)
                findNavController().navigate(R.id.action_linksDialog_to_detailsFragment, bundle)

            }
            R.id.tvMissionPatch -> {
                val bundle =
                    bundleOf(
                        "videoLink" to args.usefulLinks?.videoLink)
                findNavController().navigate(R.id.action_linksDialog_to_detailsFragment, bundle)

            }
            R.id.tvWikipedia -> {
                val bundle = bundleOf(
                    "wikipedia" to args.usefulLinks?.wikipedia)
                findNavController().navigate(R.id.action_linksDialog_to_detailsFragment, bundle)
            }
        }
    }
}