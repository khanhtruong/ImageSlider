package com.khanhtruong.imageslider.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.khanhtruong.imageslider.R
import com.khanhtruong.imageslider.entity.SliderItem

class ImageSliderAdapter(onBoardingItems: List<SliderItem>) :
    RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder>() {
    private val onBoardingItems: List<SliderItem>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
        return ImageSliderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.image_slider_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        holder.setOnBoardingData(onBoardingItems[position])
    }

    override fun getItemCount(): Int {
        return onBoardingItems.size
    }

    inner class ImageSliderViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val imageOnboarding: ImageView
        fun setOnBoardingData(onBoardingItem: SliderItem) {
            imageOnboarding.setImageResource(onBoardingItem.image)
        }

        init {
            imageOnboarding = itemView.findViewById(R.id.imageOnboarding)
        }
    }

    init {
        this.onBoardingItems = onBoardingItems
    }
}
