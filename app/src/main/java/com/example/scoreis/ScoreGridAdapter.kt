package com.example.scoreis

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


class ScoreGridAdapter : ListAdapter<String, ScoreGridAdapter.ScoreGridViewHolder>(DiffHandler()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreGridViewHolder {
        val textView = TextView(parent.context)
        textView.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        return ScoreGridViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ScoreGridViewHolder, position: Int) {
        holder.textView.text = getItem(position)
    }

    inner class ScoreGridViewHolder(val textView: TextView) :
        RecyclerView.ViewHolder(textView)

    private class DiffHandler : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}