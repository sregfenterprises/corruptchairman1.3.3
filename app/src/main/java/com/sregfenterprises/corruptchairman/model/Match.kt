package com.sregfenterprises.corruptchairman.model

data class Match(
    val homeClub: Club,
    val awayClub: Club,
    var homeGoals: Int = 0,
    var awayGoals: Int = 0,
    var played: Boolean = false,
    val date: String, // to schedule weekends/midweek
    val competition: CompetitionType
)

enum class CompetitionType {
    LEAGUE,
    CUP,
    PRESEASON,
    POSTSEASON
}
