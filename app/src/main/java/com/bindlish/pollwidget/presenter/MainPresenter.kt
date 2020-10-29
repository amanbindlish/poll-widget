package com.bindlish.pollwidget.presenter

import androidx.fragment.app.FragmentActivity
import com.bindlish.pollwidget.data.Poll
import com.bindlish.pollwidget.data.PollData
import com.bindlish.pollwidget.getPollObjectFromPreference
import com.bindlish.pollwidget.replace
import com.bindlish.pollwidget.saveJsonToPreference
import com.google.gson.Gson

class MainPresenter(private val context: FragmentActivity?) {

    private lateinit var pollData : PollData

    /**
     * Getting poll response from json file locally and then saving/updating
     * from shared preferences (for this assignment)
     */
    fun onCreate() {
        // fetch poll response from json file stored locally
        pollData = getPollObjectFromPreference(context)
    }

    // return poll data list
    fun getPollData() : PollData = pollData

    // return Poll for showing poll with text options only
    fun getTextPoll() : Poll = pollData.polls[0]

    // return Poll for showing poll with image options only
    fun getImagePoll() : Poll = pollData.polls[1]

    /**
     * handling click of poll answering and updating vote count
     * and saving in preferences
     */
    fun onPollAnswered(poll: Poll) {
        var sum = 0
        poll.poll_options.forEach {
            sum += it.option_vote
        }
        poll.poll_options.forEach {
            it.option_percent = ((it.option_vote.toDouble() / sum) * 100).toInt()
        }
        getPollData().polls.replace(newValue = poll) {
            it.poll_id == poll.poll_id
        }
        saveJsonToPreference(Gson().toJson(getPollData()), context)
    }
}