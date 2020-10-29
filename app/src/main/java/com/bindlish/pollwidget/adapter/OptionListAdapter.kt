package com.bindlish.pollwidget.adapter

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bindlish.pollwidget.R
import com.bindlish.pollwidget.data.Poll
import com.bindlish.pollwidget.data.PollOption
import com.bindlish.pollwidget.hide
import com.bindlish.pollwidget.replace
import com.bindlish.pollwidget.show
import kotlinx.android.synthetic.main.layout_poll_option_item.view.*

class OptionListAdapter(val listener: OnOptionClickListener)
    : RecyclerView.Adapter<OptionListAdapter.OptionViewHolder>() {

    private lateinit var poll : Poll

    fun updateOptions(poll: Poll) {
        this.poll = poll
        notifyDataSetChanged()
    }

    fun getPollData() : Poll = poll

    fun reset() {
        poll.voted = false
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_poll_option_item, parent, false)
        return OptionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return poll.poll_options.size
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        holder.bind(poll.poll_options[position])
    }

    inner class OptionViewHolder(val view : View) : RecyclerView.ViewHolder(view) {

        fun bind(option : PollOption) {
            view.poll_option_text.text = option.option_text
            view.poll_option_percentage.text = option.option_percent.toString() + "%"
            option.option_img?.apply {
                if (this.isNotEmpty()) {
                    view.poll_option_img.show()
                } else {
                    view.poll_option_img.hide()
                }
            }
            if(poll.voted) {
                itemView.isClickable = false
                itemView.isEnabled = false
                view.poll_option_percentage.show()
                view.poll_progress.show()
                val anim = ObjectAnimator.ofInt(view.poll_progress, "progress", 0, option.option_percent)
                anim.duration = 500
                anim.start()
            } else {
                itemView.isClickable = true
                itemView.isEnabled = true
                view.poll_option_percentage.hide()
                view.poll_progress.hide()
            }
            itemView.setOnClickListener {
                option.option_vote = option.option_vote + 1
                poll.voted = true
                poll.poll_options.replace(newValue = option) {
                    it.option_id == option.option_id
                }
                listener.onOptionClick(poll)
                notifyDataSetChanged()
            }
        }
    }

    interface OnOptionClickListener {
        fun onOptionClick(poll : Poll)
    }
}