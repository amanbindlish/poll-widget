package com.bindlish.pollwidget.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bindlish.pollwidget.*
import com.bindlish.pollwidget.adapter.OptionListAdapter
import com.bindlish.pollwidget.data.Poll
import com.bindlish.pollwidget.data.PollData
import com.bindlish.pollwidget.presenter.MainPresenter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), OptionListAdapter.OnOptionClickListener {

    companion object {
        const val TAG = "MainFragment"
        fun getInstance() : MainFragment =
            MainFragment()
    }

    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        presenter = MainPresenter(activity)
        presenter.onCreate()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // fetching text poll (hardcoded for this assignment)
        val textPoll = presenter.getTextPoll()
        val textAdapter = OptionListAdapter(this)
        textAdapter.updateOptions(textPoll)

        poll_with_text.setQuestionText(textPoll.poll_question)
        // set adapter with pollView
        poll_with_text.setAdapter(textAdapter)

        // fetching image poll (hardcoded for this assignment)
        val imagePoll = presenter.getImagePoll()
        val imageAdapter = OptionListAdapter(this)
        imageAdapter.updateOptions(imagePoll)

        poll_with_image.setQuestionText(imagePoll.poll_question)
        // set adapter with pollView
        poll_with_image.setAdapter(imageAdapter)
    }

    override fun onOptionClick(poll: Poll) {
        presenter.onPollAnswered(poll)
    }
}