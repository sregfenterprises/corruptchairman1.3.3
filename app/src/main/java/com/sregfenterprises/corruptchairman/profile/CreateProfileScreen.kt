@file:OptIn(ExperimentalMaterial3Api::class)

package com.sregfenterprises.corruptchairman.profile

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sregfenterprises.corruptchairman.data.ClubRepository

@Composable
fun CreateProfileScreen(
    clubRepository: ClubRepository,
    onProfileCreated: () -> Unit,
    onBack: () -> Unit
) {
    val TAG = "CreateProfileScreen"

    var name by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var selectedBusiness by remember { mutableStateOf("") }

    val businessOptions = listOf(
        "Finance & Investments",
        "Technology",
        "Manufacturing",
        "Fashion & Retail",
        "Healthcare",
        "Food & Beverage",
        "Real Estate",
        "Media & Entertainment",
        "Energy"
    )

    val maxPoints = 15
    var remainingPoints by remember { mutableStateOf(maxPoints) }

    var businessAcumen by remember { mutableStateOf(0) }
    var leadership by remember { mutableStateOf(0) }
    var charisma by remember { mutableStateOf(0) }
    var politicalClout by remember { mutableStateOf(0) }
    var wealth by remember { mutableStateOf(0) }

    fun updateAttribute(oldValue: Int, newValue: Int, setter: (Int) -> Unit) {
        val diff = newValue - oldValue
        if (diff <= remainingPoints) {
            Log.d(TAG, "Updating attribute: old=$oldValue new=$newValue diff=$diff")
            setter(newValue)
            remainingPoints -= diff
            Log.d(TAG, "Remaining points: $remainingPoints")
        } else {
            Log.d(TAG, "Not enough points for update!")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Create Your Chairman", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        // Name input
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                Log.d(TAG, "Name updated: $it")
            },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Country input
        OutlinedTextField(
            value = country,
            onValueChange = {
                country = it
                Log.d(TAG, "Country updated: $it")
            },
            label = { Text("Country of Birth") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Business sector dropdown
        var expanded by remember { mutableStateOf(false) }

        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = selectedBusiness,
                onValueChange = {},
                label = { Text("Business Sector") },
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.clickable { expanded = !expanded }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                businessOptions.forEach { business ->
                    DropdownMenuItem(
                        text = { Text(business) },
                        onClick = {
                            selectedBusiness = business
                            expanded = false
                            Log.d(TAG, "Business selected: $business")
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("Allocate Points to Attributes (Total: $maxPoints)", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        AttributeSlider("Business Acumen", businessAcumen, { newVal -> updateAttribute(businessAcumen, newVal) { businessAcumen = it } }, maxPoints)
        AttributeSlider("Leadership", leadership, { newVal -> updateAttribute(leadership, newVal) { leadership = it } }, maxPoints)
        AttributeSlider("Charisma", charisma, { newVal -> updateAttribute(charisma, newVal) { charisma = it } }, maxPoints)
        AttributeSlider("Political Clout", politicalClout, { newVal -> updateAttribute(politicalClout, newVal) { politicalClout = it } }, maxPoints)
        AttributeSlider("Wealth", wealth, { newVal -> updateAttribute(wealth, newVal) { wealth = it } }, maxPoints)

        Text("Points remaining: $remainingPoints")
        Spacer(modifier = Modifier.height(16.dp))

        // Finish Profile Button
        Button(
            onClick = {
                Log.d(TAG, "Finish Profile clicked")
                UserProfileManager.createProfile(
                    name = name,
                    country = country,
                    business = selectedBusiness,
                    businessAcumen = businessAcumen,
                    leadership = leadership,
                    charisma = charisma,
                    politicalClout = politicalClout,
                    wealth = wealth
                )
                Log.d(TAG, "Profile saved! Redirecting…")
                onProfileCreated()
            },
            enabled = remainingPoints == 0 &&
                    name.isNotBlank() &&
                    country.isNotBlank() &&
                    selectedBusiness.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Finish Profile")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { onBack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}

@Composable
fun AttributeSlider(
    name: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    max: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text("$name: $value")
        Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it.toInt()) },
            valueRange = 0f..max.toFloat(),
            steps = max - 1
        )
    }
}
