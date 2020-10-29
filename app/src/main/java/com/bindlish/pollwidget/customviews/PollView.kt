package com.bindlish.pollwidget.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bindlish.pollwidget.R
import com.bindlish.pollwidget.adapter.OptionListAdapter

class PollView(context : Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    private var isVertical : Boolean = true
    private lateinit var recyclerView : RecyclerView
    private lateinit var quesText : TextView
    private lateinit var voteBtn : Button

    init {
        setUpAttributes(attrs)
        inflateViews()
    }

    private fun setUpAttributes(attrs: AttributeSet) {
        val typedArray = context.theme.obtainStyledAttributes(attrs,
            R.styleable.PollView, 0, 0)
        isVertical = typedArray.getBoolean(R.styleable.PollView_isVertical, true)

        typedArray.recycle()
    }

    private fun inflateViews() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_poll_view, this, true)
        initialiseViews(view)
    }

    private fun initialiseViews(view: View) {
        recyclerView = view.findViewById(R.id.poll_options_list) as RecyclerView
        quesText = view.findViewById(R.id.poll_ques_text) as TextView
        voteBtn = view.findViewById(R.id.vote_button) as Button
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        recyclerView.setHasFixedSize(true)
        val layoutManager : LinearLayoutManager
        if(isVertical){
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        } else {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        recyclerView.layoutManager = layoutManager
        voteBtn.setOnClickListener {
            (recyclerView.adapter as OptionListAdapter).reset()
        }
    }

    fun setAdapter(adapter : OptionListAdapter?) {
        adapter?.apply {
            recyclerView.adapter = this
            if(getPollData().voted) {
                voteBtn.isEnabled = false
                voteBtn.isClickable = false
            } else {
                voteBtn.isEnabled = true
                voteBtn.isClickable = true
            }
        }
    }

    fun setIsVertical(isVertical : Boolean) {
        this.isVertical = isVertical
    }

    fun setQuestionText(text : String) {
        quesText.text = text
    }


}