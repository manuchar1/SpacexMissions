package com.lemondo.spacexmissions.ui.views.slideShow

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.lemondo.spacexmissions.R
import com.lemondo.spacexmissions.base.goneIf
import com.lemondo.spacexmissions.base.snackbar
import com.lemondo.spacexmissions.data.models.ShipsResponse
import com.lemondo.spacexmissions.databinding.FragmentShipsBinding
import com.lemondo.spacexmissions.ui.adapters.ShipsAdapter
import com.lemondo.spacexmissions.ui.viewModel.ShipsViewModel
import com.lemondo.spacexmissions.utils.EventObserver


class ShipsFragment : Fragment() {

    private lateinit var binding: FragmentShipsBinding

    private val viewModel by activityViewModels<ShipsViewModel>()

    private var shipsList = arrayListOf<ShipsResponse>()


    private var counter = 1
    private var slideShowSpeed = 6000
    private var position = 1
    private var isPaused = true
    private var count = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            slideShowSpeed = savedInstanceState.getInt("slideShowSpeed")
            position = savedInstanceState.getInt("position")
            isPaused = savedInstanceState.getBoolean("isPaused")
            counter = savedInstanceState.getInt("counter")
            count = savedInstanceState.getInt("count")

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShipsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getShips()

        subscribeToObservers()
        manageSlideShowSpeed()

    }

    override fun onResume() {
        super.onResume()
        if (!isPaused) binding.viewPager.resumeAutoScroll()

    }

    override fun onPause() {
        super.onPause()
        binding.viewPager.pauseAutoScroll()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("slideShowSpeed", slideShowSpeed)
        outState.putInt("position", position)
        outState.putBoolean("isPaused", isPaused)
        outState.putInt("counter", counter)
        outState.putInt("count", count)

    }


    private fun initViewPagerAdapter(data: ArrayList<ShipsResponse>) {

        val adapter = ShipsAdapter(
            data, true, requireContext()
        )

        binding.apply {
            viewPager.adapter = adapter
            pageNumber.goneIf(data.size < 2)
            setCurrentPageIndex(position, data)



            if (data.size >= 2)
                setCurrentPageIndex(position, data)

            viewPager.onIndicatorProgress = { selectingPosition, _ ->

                setCurrentPageIndex(selectingPosition + position, data)
            }

            viewPager.setInterval(slideShowSpeed)

            if (isPaused) {
                viewPager.pauseAutoScroll()
                binding.btnPlay.setImageResource(R.drawable.ic_play)
            } else {
                viewPager.resumeAutoScroll()
                binding.btnPlay.setImageResource(R.drawable.ic_pause)
            }

        }



        binding.btnPlay.setOnClickListener {

            val number = counter++
            when (number % 2) {
                0 -> {
                    binding.btnPlay.setImageResource(R.drawable.ic_play)
                    binding.viewPager.pauseAutoScroll()
                    isPaused = true
                    snackbar("paused")

                }
                1 -> {
                    binding.btnPlay.setImageResource(R.drawable.ic_pause)
                    binding.viewPager.resumeAutoScroll()
                    isPaused = false

                }
            }

        }

        binding.btnUndo.setOnClickListener {
            binding.viewPager.reset()
        }

        adapter.setOnShipClickListener {
            val bundle = Bundle().apply {
                putParcelable("shipsData", it)
            }
            findNavController().navigate(R.id.action_shipsFragment_to_searchFragment, bundle)
        }


    }

    @SuppressLint("SetTextI18n")
    private fun setCurrentPageIndex(index: Int, model: ArrayList<ShipsResponse>) {
        binding.pageNumber.text = "$index/${model.size}"
    }


    @SuppressLint("SetTextI18n")
    private fun manageSlideShowSpeed() {


        binding.apply {
            tvIncrease.text = "+${count}x"
            binding.btnIncreaseSpeed.setOnClickListener {
                if (count == 5) {
                    count = 1
                    tvIncrease.text = "+${count}x"
                    slideShowSpeed = 6000
                    binding.viewPager.setInterval(slideShowSpeed)
                    if (isPaused) viewPager.pauseAutoScroll() else viewPager.resumeAutoScroll()
                } else {
                    slideShowSpeed -= 1350
                    count++
                    binding.viewPager.setInterval(slideShowSpeed)
                    tvIncrease.text = "+${count}x"
                    if (isPaused) viewPager.pauseAutoScroll() else viewPager.resumeAutoScroll()
                }

            }
        }

    }

    private fun subscribeToObservers() {

        viewModel.shipsLiveData.observe(viewLifecycleOwner, EventObserver(
            onError = {
                binding.progressBar.isGone = true
                snackbar(it)
            }

        ) {
            binding.progressBar.isGone = true
            initViewPagerAdapter(it as ArrayList<ShipsResponse>)
            shipsList.addAll(it)
            binding.llPlayerContainer.isGone = false
        })
    }
}