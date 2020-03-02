package com.polotechnologies.roadwatch.adapters

import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import com.polotechnologies.roadwatch.dataModels.Report

@BindingAdapter("actedUpon")
fun bind(textView: AppCompatTextView, report: Report?){
    if(report?.acted_upon == false){
        textView.text = "Not Acted Upon"
    }else{
        textView.text = "Yes, Acted upon"
    }
}

@BindingAdapter("actionState")
fun bind(button : MaterialButton, report: Report?){
    button.isEnabled = report?.acted_upon == false
}