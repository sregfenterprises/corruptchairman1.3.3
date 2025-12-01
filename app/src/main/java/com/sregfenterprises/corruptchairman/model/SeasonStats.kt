package com.sregfenterprises.corruptchairman.model

data class SeasonStats(
    val seasonName: String,
    val leaguePosition: Int,
    val played: Int,
    val won: Int,
    val drawn: Int,
    val lost: Int,
    val goalsFor: Int,
    val goalsAgainst: Int,
    val points: Int
)
