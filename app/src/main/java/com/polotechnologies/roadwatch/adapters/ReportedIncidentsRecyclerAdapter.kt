package com.polotechnologies.roadwatch.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.polotechnologies.roadwatch.dataModels.Report
import com.polotechnologies.roadwatch.databinding.ItemReportedIncidentBinding
import kotlinx.android.synthetic.main.item_reported_incident.view.*

class ReportedIncidentsRecyclerAdapter(private val onClickListener: OnClickListener) : ListAdapter<Report, ReportedIncidentsRecyclerAdapter.ReportedIncidentsViewHolder>(
    ReportedIncidentDiffCallBack()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportedIncidentsViewHolder {
        return ReportedIncidentsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ReportedIncidentsViewHolder, position: Int) {
        val reportedIncident = getItem(position)
        holder.itemView.button_dispatch.setOnClickListener {
            onClickListener.onClick(reportedIncident)
        }
        holder.bind(reportedIncident)

    }


    class ReportedIncidentsViewHolder private constructor(val binding: ItemReportedIncidentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(reportedIncident: Report) {
            binding.reportedIncident = reportedIncident
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ReportedIncidentsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemReportedIncidentBinding.inflate(layoutInflater, parent, false)
                return ReportedIncidentsViewHolder(binding)
            }
        }

    }

    class OnClickListener(val clickListener : (reportedIncident: Report) -> Unit){
        fun onClick(reportedIncident: Report) = clickListener(reportedIncident)
    }


    class ReportedIncidentDiffCallBack : DiffUtil.ItemCallback<Report>(){
        override fun areItemsTheSame(oldItem: Report, newItem: Report): Boolean {
            return oldItem.report_key == newItem.report_key
        }

        override fun areContentsTheSame(oldItem: Report, newItem: Report): Boolean {
            return oldItem == newItem
        }

    }
}