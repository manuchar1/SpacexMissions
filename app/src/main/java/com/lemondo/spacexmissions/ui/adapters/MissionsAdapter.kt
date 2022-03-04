package com.lemondo.spacexmissions.ui.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lemondo.spacexmissions.R
import com.lemondo.spacexmissions.data.models.Mission
import com.lemondo.spacexmissions.databinding.ItemMissionBinding
import java.util.*


/**
 * Created by Manuchar Zakariadze on 3/2/22
 */
class MissionsAdapter : RecyclerView.Adapter<MissionsAdapter.MissionsViewHolder>() {

    private var missionsList: ArrayList<Mission> = ArrayList()
    var selectedItemPos = -1
    var lastItemSelectedPos = 0

    inner class MissionsViewHolder(private val binding: ItemMissionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var model: Mission



        fun bind() {
            model = missionsList[adapterPosition]
            binding.tvMission.text = model.name

            itemView.setOnClickListener {
                onMissionClickListener?.let { click ->
                    click(model)
                }

                selectedItemPos = adapterPosition
                lastItemSelectedPos = if (lastItemSelectedPos == -1)
                    selectedItemPos
                else {
                    notifyItemChanged(lastItemSelectedPos)
                    selectedItemPos
                }
                notifyItemChanged(selectedItemPos)
            }

            fun defaultBg() {
                binding.cvSpeed.setBackgroundResource(R.drawable.bg_speed_button)
                binding.tvMission.setTextColor(Color.parseColor("#000000"))
            }

            fun selectedBg() {
                binding.cvSpeed.setBackgroundResource(R.drawable.bg_selected_button)
                binding.tvMission.setTextColor(Color.parseColor("#FFFFFF"))
            }

            if (adapterPosition == selectedItemPos)
                selectedBg()
            else
                defaultBg()

        }

    }

    private var onMissionClickListener: ((Mission?) -> Unit)? = null

    fun setOnMissionClickListener(listener: (Mission?) -> Unit) {
        onMissionClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MissionsViewHolder(
        ItemMissionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: MissionsViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = missionsList.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(missions: ArrayList<Mission>) {
        missionsList = missions
        notifyDataSetChanged()
    }


}