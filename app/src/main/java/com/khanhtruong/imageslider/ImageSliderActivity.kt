package com.khanhtruong.imageslider

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.button.MaterialButton
import com.khanhtruong.imageslider.adapter.ImageSliderAdapter
import com.khanhtruong.imageslider.custom.DepthPageTransformer
import com.khanhtruong.imageslider.entity.SliderItem


class ImageSliderActivity : AppCompatActivity() {
    private lateinit var onboardingAdapter: ImageSliderAdapter
    private lateinit var layoutOnboardingIndicator: LinearLayout
    private lateinit var buttonOnboardingAction: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.image_slider_activity)
        layoutOnboardingIndicator = findViewById(R.id.layoutOnboardingIndicators)
        buttonOnboardingAction = findViewById(R.id.buttonOnBoardingAction)
        setOnboardingItem()
        val imageViewPager = findViewById<ViewPager2>(R.id.onboardingViewPager)
        imageViewPager.adapter = onboardingAdapter

        setOnboadingIndicator()
        setCurrentOnboardingIndicators(0)

        imageViewPager.clipToPadding = false
        imageViewPager.clipChildren = false
        imageViewPager.offscreenPageLimit = 3
        imageViewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(DepthPageTransformer())
        imageViewPager.setPageTransformer(compositePageTransformer)

        imageViewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentOnboardingIndicators(position)
            }
        })

        buttonOnboardingAction.setOnClickListener(View.OnClickListener {
            if (imageViewPager.currentItem + 1 < onboardingAdapter.itemCount) {
                imageViewPager.currentItem = imageViewPager.currentItem + 1
            } else {
                imageViewPager.currentItem = 0
            }
        })
    }

    private fun setOnboadingIndicator() {
        val indicators = arrayOfNulls<ImageView>(onboardingAdapter.itemCount)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext, R.drawable.indicator_inactive
                )
            )
            indicators[i]?.layoutParams = layoutParams
            layoutOnboardingIndicator.addView(indicators[i])
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setCurrentOnboardingIndicators(index: Int) {
        val childCount = layoutOnboardingIndicator.childCount
        for (i in 0 until childCount) {
            val imageView = layoutOnboardingIndicator.getChildAt(i) as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active_two
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_two
                    )
                )
            }
        }
        if (index == onboardingAdapter.itemCount - 1) {
            buttonOnboardingAction.text = "Back to start"
        } else {
            buttonOnboardingAction.text = "Next"
        }
    }

    private fun setOnboardingItem() {
        val onBoardingItems: ArrayList<SliderItem> = ArrayList<SliderItem>()

        val itemFastFood = SliderItem(
            R.drawable.choose_your_meal,
            "Choose your meal",
            "You can easily choose your meal and take it!"
        )

        val itemPayOnline = SliderItem(
            R.drawable.choose_your_payment,
            "Choose your payment",
            "You can pay us using any methods, online or offline!"
        )

        val itemEatTogether = SliderItem(
            R.drawable.fast_delivery,
            "Fast delivery",
            "Our delivery partners are too fast, they will not disappoint you!"
        )

        val itemDayAndNight = SliderItem(
            R.drawable.day_and_night,
            "Day and Night",
            "Our service is on day and night!"
        )

        onBoardingItems.add(itemFastFood)
        onBoardingItems.add(itemPayOnline)
        onBoardingItems.add(itemEatTogether)
        onBoardingItems.add(itemDayAndNight)

        onboardingAdapter = ImageSliderAdapter(onBoardingItems.toList())
    }
}