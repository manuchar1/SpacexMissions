package com.lemondo.spacexmissions.ui.views.missions

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.lemondo.spacexmissions.R
import com.lemondo.spacexmissions.base.snackbar
import com.lemondo.spacexmissions.data.models.LaunchesResponse
import com.lemondo.spacexmissions.data.models.Mission
import com.lemondo.spacexmissions.data.models.ShipsResponse
import com.lemondo.spacexmissions.databinding.FragmentMissionsBinding
import com.lemondo.spacexmissions.ui.adapters.LaunchesAdapter
import com.lemondo.spacexmissions.ui.adapters.MissionsAdapter
import com.lemondo.spacexmissions.ui.viewModel.ShipsViewModel
import com.lemondo.spacexmissions.utils.EventObserver
import java.util.*
import kotlin.collections.ArrayList


class MissionsFragment : Fragment() {

    private val viewModel by activityViewModels<ShipsViewModel>()


    private lateinit var missionsAdapter: MissionsAdapter

    private lateinit var launchesAdapter: LaunchesAdapter


    private val args: MissionsFragmentArgs by navArgs()
    private lateinit var binding: FragmentMissionsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMissionsBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLaunches()

        subscribeToObservers()


        binding.tvShipName.text = "${args.shipsData.shipName}'s Missions"

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }


    }

    private fun filter(text: String, launchesList: List<LaunchesResponse>) {
        val filteredList: ArrayList<LaunchesResponse> = arrayListOf()

        for (item in launchesList) {
            if (item.missionName?.lowercase(Locale.getDefault())
                    ?.contains(text.lowercase(Locale.getDefault())) == true
            ) {
                filteredList.add(item)
            }

            launchesAdapter.filterList(filteredList)
        }
    }

    private fun setupRecyclerViews(data: ShipsResponse, launchesList: List<LaunchesResponse>) {
        binding.rvMissions.apply {
            missionsAdapter = MissionsAdapter()
            adapter = missionsAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            data.missions?.let { missionsAdapter.submitData(it as java.util.ArrayList<Mission>) }

            missionsAdapter.setOnMissionClickListener {
                viewModel.getLaunchesByMission(it?.flight)
            }


        }

        binding.rvLaunches.apply {
            launchesAdapter = LaunchesAdapter(launchesList as java.util.ArrayList<LaunchesResponse>)
            adapter = launchesAdapter
            layoutManager = LinearLayoutManager(requireContext())

            launchesAdapter.setOnLinksBtnClickListener {
                val bundle = Bundle().apply {
                    putParcelable("usefulLinks", it)
                }
                findNavController().navigate(
                    R.id.action_missionsFragment_to_linksDialog,
                    bundle
                )
            }

        }


    }


    private fun subscribeToObservers() {

        viewModel.launchesLiveData.observe(viewLifecycleOwner, EventObserver(
            onError = {
                binding.progressBar.isGone = true
                snackbar(it)
            }

        ) { launches ->
            setupRecyclerViews(args.shipsData, launches)
            binding.progressBar.isGone = true

            binding.etMissions.doOnTextChanged { text, _, _, _ ->
                filter(text.toString(), launches)
            }
        })

    }

}