package com.polotechnologies.roadwatch.adapters

import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.polotechnologies.roadwatch.dataModels.Report

@BindingAdapter("actedUpon")
fun bind(textView: AppCompatTextView, report: Report?){
    if(report?.acted_upon == false){
        textView.text = "Not Acted Upon"
    }else{
        textView.text = "Yes, Acted upon"
    }
}