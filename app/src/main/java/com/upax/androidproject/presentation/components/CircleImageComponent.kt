package com.upax.androidproject.presentation.components

import com.bumptech.glide.Glide
import com.upax.androidproject.databinding.ImageCircularBinding
import com.upax.androidproject.domain.model.CirlcleImageModel
import com.upax.androidproject.utils.ApplicationApp
import com.upax.androidproject.utils.show

class CircleImageComponent(private val view: ImageCircularBinding, private val component: CirlcleImageModel) {

    private val context = ApplicationApp().getContext()

    fun setImageFromUrl(){
        if (component.url?.isNotEmpty() == true){
            Glide.with(context)
                .load(component.url)
                .into(view.circleImage)
        } else {
            setInitials()
        }
    }

    fun setInitials(){
        var initials = ""
        if (component.name?.isNotEmpty() == true){
            val parts = component.name?.split(" ")?.toTypedArray()
            if (parts.isNullOrEmpty().not()){
                parts!!.map {
                    initials += it[0]
                }
            }
        }
        if (initials.isNotEmpty()){
            view.initialsText.text = initials.uppercase()
            view.initialsText.show()
        }
    }
}