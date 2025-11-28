package com.sregfenterprises.corruptchairman.ui.takeover

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sregfenterprises.corruptchairman.model.Club
import com.sregfenterprises.corruptchairman.viewmodel.TakeoverViewModel
import kotlin.math.ceil

@Composable
fun TakeoverTeamScreen(
    viewModel: TakeoverViewModel,
    onBack: () -> Unit,
    onClubSelected: (Club) -> Unit
) {
    val continents by viewModel.continents.collectAsState()
    val countries by viewModel.countries.collectAsState()
    val clubs by viewModel.clubs.collectAsState()

    var screenStep by remember { mutableStateOf(1) } // 1=continent, 2=country, 3=club
    var selectedContinent by remember { mutableStateOf<String?>(null) }
    var selectedCountry by remember { mutableStateOf<String?>(null) }

    // For country pagination
    var countryPage by remember { mutableStateOf(0) }
    val countriesPerPage = 10
    val totalCountryPages = ceil(countries.size.toDouble() / countriesPerPage).toInt()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        when (screenStep) {

            // ───────────────────────────────
            // 1️⃣ CONTINENT SELECTION
            // ───────────────────────────────
            1 -> {
                Text(
                    text = "Select a Continent",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn {
                    items(continents) { continent ->
                        Button(
                            onClick = {
                                selectedContinent = continent
                                viewModel.loadCountries(continent)
                                countryPage = 0
                                screenStep = 2
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Text(continent)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Back") }
            }

            // ───────────────────────────────
            // 2️⃣ COUNTRY SELECTION
            // ───────────────────────────────
            2 -> {
                Text(
                    text = "Select a Country (${selectedContinent})",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))

                val start = countryPage * countriesPerPage
                val end = ((countryPage + 1) * countriesPerPage).coerceAtMost(countries.size)
                val visibleCountries = countries.subList(start, end)

                LazyColumn {
                    items(visibleCountries) { countryItem ->
                        Button(
                            onClick = {
                                selectedCountry = countryItem
                                viewModel.loadClubs(countryItem)
                                screenStep = 3
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Text(text = countryItem)
                        }
                    }
                }

                // Pagination buttons
                if (totalCountryPages > 1) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { if (countryPage > 0) countryPage-- },
                            enabled = countryPage > 0
                        ) { Text("Prev") }

                        Text("Page ${countryPage + 1} / $totalCountryPages")

                        Button(
                            onClick = { if (countryPage < totalCountryPages - 1) countryPage++ },
                            enabled = countryPage < totalCountryPages - 1
                        ) { Text("Next") }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { screenStep = 1 }, modifier = Modifier.fillMaxWidth()) { Text("Back") }
            }

            // ───────────────────────────────
            // 3️⃣ CLUB SELECTION
            // ───────────────────────────────
            3 -> {
                Text(
                    "Select a Club (${selectedCountry})",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(8.dp))

                LazyColumn {
                    items(clubs) { club ->
                        Button(
                            onClick = { onClubSelected(club) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Text(club.name)
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = { screenStep = 2 },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Back") }
            }
        }
    }
}









