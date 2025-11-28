package com.sregfenterprises.corruptchairman.model

import android.util.Log

object LeagueGenerator {

    private const val TAG = "LEAGUE_GENERATOR"

    /**
     * Generate top 18 clubs worldwide and assign league
     */
    fun createTop18WorldLeague(allClubs: List<Club>): League {
        val top18 = allClubs
            .sortedByDescending { it.rankingPoints }
            .take(18)

        Log.d(TAG, "World Super League created with ${top18.size} clubs")
        top18.forEach { Log.d(TAG, "Club: ${it.name}, Points: ${it.rankingPoints}") }

        return createLeague("World Super League", top18)
    }

    /**
     * Generate top 18 clubs for a continent, avoiding duplicates from excludedClubs
     */
    fun createTop18ContinentLeague(
        allClubs: List<Club>,
        continent: String,
        leagueName: String,
        excludedClubs: Set<Club> = emptySet()
    ): League {
        val continentClubs = allClubs
            .filter { it.continent == continent && it !in excludedClubs }
            .sortedByDescending { it.rankingPoints }
            .take(18)

        Log.d(TAG, "$leagueName created with ${continentClubs.size} clubs (Continent: $continent)")
        continentClubs.forEach { Log.d(TAG, "Club: ${it.name}, Points: ${it.rankingPoints}") }

        return createLeague(leagueName, continentClubs)
    }

    /**
     * Internal helper: create matches and assign league to each club
     */
    private fun createLeague(name: String, clubs: List<Club>): League {
        val matches = mutableListOf<Match>()

        // Assign league to each club
        clubs.forEach { it.league = name }

        // Generate all-vs-all matches
        for (i in clubs.indices) {
            for (j in i + 1 until clubs.size) {
                matches.add(Match(clubs[i], clubs[j]))
            }
        }

        Log.d(TAG, "League $name created with ${clubs.size} clubs and ${matches.size} matches")
        return League(name, clubs, matches)
    }
}
