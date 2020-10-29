package com.bindlish.pollwidget.data

data class PollOption(
    val option_id : Int,
    val option_text : String,
    val option_img : String?,
    var option_vote : Int,
    var option_percent : Int
)