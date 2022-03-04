package com.lemondo.spacexmissions.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.asksira.loopingviewpager.LoopingPagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.lemondo.spacexmissions.R
import com.lemondo.spacexmissions.data.models.ShipsResponse

/**
 * Created by Manuchar Zakariadze on 3/1/22
 */
class ShipsAdapter(
    itemList: ArrayList<ShipsResponse>,
    isInfinite: Boolean,
    private val context: Context
) : LoopingPagerAdapter<ShipsResponse>(itemList, isInfinite) {

    override fun inflateView(viewType: Int, container: ViewGroup, listPosition: Int): View {
        return LayoutInflater.from(container.context).inflate(R.layout.item_ship, container, false)
    }

    override fun bindView(convertView: View, listPosition: Int, viewType: Int) {
        val imgShip = convertView.findViewById<ImageView>(R.id.ivShip)
        val tvShipName = convertView.findViewById<TextView>(R.id.tvShipName)
        val tvShipType = convertView.findViewById<TextView>(R.id.tvShipType)
        val tvHomePort = convertView.findViewById<TextView>(R.id.tvHomePort)
        val model = itemList?.get(listPosition)

        convertView.setOnClickListener {
            onShipClickListener?.let { click ->
                click(model)
            }
        }

        Glide.with(context).load(model?.image)
            .dontAnimate()
            .fitCenter()
            .placeholder(R.mipmap.mask)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imgShip)

        tvShipName.text = model?.shipName
        tvShipType.text = model?.shipType
        tvHomePort.text = model?.homePort


    }

    private var onShipClickListener: ((ShipsResponse?) -> Unit)? = null

    fun setOnShipClickListener(listener: (ShipsResponse?) -> Unit) {
        onShipClickListener = listener
    }

}