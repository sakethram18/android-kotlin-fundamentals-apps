package com.example.android.trackmysleepquality.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightGridBinding

class SleepTrackerAdapter: ListAdapter<SleepNight, SleepTrackerAdapter.ViewHolder>(
        SleepNightDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val night = getItem(position)
        holder.bind(night)
    }

    class ViewHolder private constructor(private val binding: ListItemSleepNightGridBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(night: SleepNight) {
            // This connects the layout binding with the data
            binding.sleep = night
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = ListItemSleepNightGridBinding.inflate(inflater, parent, false)
                return ViewHolder(view)
            }
        }
    }

}

class SleepNightDiffCallback: DiffUtil.ItemCallback<SleepNight>() {
    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem.nightId == newItem.nightId
    }

    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem == newItem
    }

}