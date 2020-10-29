package com.bindlish.pollwidget.data

data class Poll(
    val poll_id : Int,
    val poll_question : String,
    val poll_options : List<PollOption>,
    @Transient
    var voted : Boolean
)