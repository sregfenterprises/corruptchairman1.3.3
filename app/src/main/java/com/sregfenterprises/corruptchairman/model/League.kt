package com.sregfenterprises.corruptchairman.model

data class League(
    val name: String,
    val clubs: List<Club>,
    val matches: MutableList<Match> = mutableListOf(),
    var currentSeason: String
)
