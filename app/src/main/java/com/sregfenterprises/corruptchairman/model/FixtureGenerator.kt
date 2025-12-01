package com.sregfenterprises.corruptchairman.model

import kotlin.random.Random

object FixtureGenerator {

    /**
     * Generate a round-robin league fixture list for a list of clubs.
     * Each club plays every other club once at home and once away.
     */
    fun generateLeagueFixtures(
        clubs: List<Club>,
        startDate: String, // e.g., "2025-12-01"
        matchDays: List<String> // list of dates for each matchday (weekends)
    ): MutableList<Match> {
        val fixtures = mutableListOf<Match>()
        var dayIndex = 0

        for (i in clubs.indices) {
            for (j in clubs.indices) {
                if (i != j) {
                    val matchDate = matchDays.getOrElse(dayIndex) { startDate }
                    fixtures.add(
                        Match(
                            homeClub = clubs[i],
                            awayClub = clubs[j],
                            date = matchDate,
                            competition = CompetitionType.LEAGUE
                        )
                    )
                    dayIndex = (dayIndex + 1) % matchDays.size
                }
            }
        }

        // Shuffle fixtures and return as MutableList
        return fixtures.shuffled(Random(System.currentTimeMillis())).toMutableList()
    }

    /**
     * Simulate match results for a list of matches
     * Returns a MutableList<Match> to match expectations
     */
    fun simulateMatches(fixtures: List<Match>, maxGoals: Int = 5): MutableList<Match> {
        return fixtures.map { match ->
            if (!match.played) {
                match.apply {
                    homeGoals = Random.nextInt(0, maxGoals + 1)
                    awayGoals = Random.nextInt(0, maxGoals + 1)
                    played = true
                }
            } else match
        }.toMutableList()
    }
}
