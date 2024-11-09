package com.challengeonair.challengeonairandroid.view.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.challengeonair.challengeonairandroid.R
import com.challengeonair.challengeonairandroid.databinding.ItemAlarmBinding
import com.challengeonair.challengeonairandroid.model.data.Alarm

class AlarmAdapter(
    private val alarmList: List<Alarm>,
) : RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {

    class AlarmViewHolder(val binding: ItemAlarmBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val binding: ItemAlarmBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_alarm,
            parent,
            false
        )
        return AlarmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val currentAlarm = alarmList[position]
        holder.binding.alarm = currentAlarm
        holder.binding.executePendingBindings()

        holder.binding.btnConfirm.setOnClickListener {
            currentAlarm.isConfirmed = true
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return alarmList.size
    }
}