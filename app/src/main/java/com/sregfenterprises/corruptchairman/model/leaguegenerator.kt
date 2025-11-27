package com.sregfenterprises.corruptchairman.model

data class Match(val home: Club, val away: Club, var result: String? = null)

data class League(val name: String, val clubs: List<Club>, val matches: List<Match>)

object LeagueGenerator {

    fun createTop18League(allClubs: List<Club>, leagueName: String): League {
        val topClubs = allClubs
            .sortedByDescending { it.rankingPoints }
            .take(18)

        val matches = mutableListOf<Match>()
        for (i in topClubs.indices) {
            for (j in i + 1 until topClubs.size) {
                matches.add(Match(topClubs[i], topClubs[j]))
            }
        }

        return League(leagueName, topClubs, matches)
    }
}
