package com.lemondo.spacexmissions.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lemondo.spacexmissions.data.models.LaunchesResponse
import com.lemondo.spacexmissions.data.models.Links
import com.lemondo.spacexmissions.databinding.ItemLaunchesBinding
import java.util.ArrayList

/**
 * Created by Manuchar Zakariadze on 3/2/22
 */
class LaunchesAdapter(private var launchesList: ArrayList<LaunchesResponse>) :
    RecyclerView.Adapter<LaunchesAdapter.LaunchesViewHolder>() {

    inner class LaunchesViewHolder(private val binding: ItemLaunchesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var model: LaunchesResponse

        fun bind() {
            model = launchesList[adapterPosition]
            binding.apply {
                tvMissionName.text = model.missionName
                tvLaunchYear.text = model.launchYear
                tvDetails.text = model.details
            }

            binding.tvLinks.setOnClickListener {
                onLinksBtnClickListener?.let { click ->
                    click(model.links)
                }
            }

        }

    }

    private var onLinksBtnClickListener: ((Links?) -> Unit)? = null

    fun setOnLinksBtnClickListener(listener: (Links?) -> Unit) {
        onLinksBtnClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LaunchesViewHolder(
        ItemLaunchesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: LaunchesViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = launchesList.size

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filteredList: ArrayList<LaunchesResponse>) {
        launchesList = filteredList
        notifyDataSetChanged()
    }
}