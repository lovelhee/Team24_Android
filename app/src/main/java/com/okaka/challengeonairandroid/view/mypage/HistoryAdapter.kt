package com.okaka.challengeonairandroid.view.mypage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.okaka.challengeonairandroid.R
import com.okaka.challengeonairandroid.model.data.entity.History

class HistoryAdapter(
    private val historyList: List<History>,
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = historyList[position]

        holder.tvDate.text = history.historyDate

        holder.ivChallengeCover.setImageResource(R.drawable.sample_history_cover) // 실제 이미지 로드 필요

        if (history.isSucceeded) {
            holder.tvSuccess.visibility = View.VISIBLE
            holder.tvFail.visibility = View.GONE
        } else {
            holder.tvSuccess.visibility = View.GONE
            holder.tvFail.visibility = View.VISIBLE
        }

        holder.tvMade.visibility = if (history.isHost) View.VISIBLE else View.GONE

        holder.tvTitle.text = history.challengeName

        holder.tvStartTime.text = history.challengeStartTime
        holder.tvEndTime.text = history.challengeEndTime
    }

    override fun getItemCount(): Int = historyList.size

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val ivChallengeCover: ImageView = itemView.findViewById(R.id.ivChallengeCover)
        val tvSuccess: TextView = itemView.findViewById(R.id.tvSuccess)
        val tvFail: TextView = itemView.findViewById(R.id.tvFail)
        val tvMade: TextView = itemView.findViewById(R.id.tvMade)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvStartTime: TextView = itemView.findViewById(R.id.tvStartTime)
        val tvEndTime: TextView = itemView.findViewById(R.id.tvEndTime)
    }
}