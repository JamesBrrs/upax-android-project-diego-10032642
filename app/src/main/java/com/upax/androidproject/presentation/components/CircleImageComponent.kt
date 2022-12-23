package com.upax.androidproject.presentation.components

import android.graphics.Color
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.upax.androidproject.R
import com.upax.androidproject.databinding.ImageCircularBinding
import com.upax.androidproject.domain.model.CirlcleImageModel
import com.upax.androidproject.utils.ApplicationApp
import com.upax.androidproject.utils.hide
import com.upax.androidproject.utils.show
import java.util.*

class CircleImageComponent(
    private val view: ImageCircularBinding,
    private val component: CirlcleImageModel
) {

    private val context = ApplicationApp().getContext()

    init {
        setImageFromUrl()
    }

    private fun setImageFromUrl() {
        if (component.url?.isNotEmpty() == true) {
            try {
                Glide.with(context)
                    .load(component.url)
                    .into(view.circleImage)
            } catch (e: Exception) {
                setInitials()
            }
        } else {
            setInitials()
        }
    }

    private fun setInitials() {
        var initials = ""
        if (component.name?.isNotEmpty() == true) {
            val parts = component.name?.split(" ")?.toTypedArray()
            if (parts.isNullOrEmpty().not()) {
                parts!!.map {
                    initials += it[0]
                }
            }
        }
        if (initials.isNotEmpty()) {
            view.initialsText.text = initials.uppercase()
            view.initialsText.show()
            val color = ContextCompat.getColorStateList(context, setRandomColor())
            view.circleImage.setBackgroundColor(setRandomColor())
            view.circleImage.strokeColor = color
            view.initialsText.setTextColor(color)
        } else {
            view.initialsText.hide()
            setPlaceHolder()
        }
    }

    private fun setPlaceHolder() {
        view.initialsText.hide()
        Glide.with(context)
            .load(R.drawable.ic_pokemon_logo)
            .into(view.circleImage)
    }

    private fun setRandomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }
}