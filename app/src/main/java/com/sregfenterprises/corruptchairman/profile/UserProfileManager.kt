package com.sregfenterprises.corruptchairman.profile

import android.content.Context
import androidx.core.content.edit

data class UserProfile(
    val name: String,
    val country: String,
    val business: String,         // NEW FIELD
    val businessAcumen: Int,
    val leadership: Int,
    val charisma: Int,
    val politicalClout: Int,
    val wealth: Int
)

object UserProfileManager {

    private const val PREFS_NAME = "user_profile_prefs"
    private const val KEY_HAS_PROFILE = "has_profile"
    private const val KEY_NAME = "name"
    private const val KEY_COUNTRY = "country"
    private const val KEY_BUSINESS_FIELD = "business_field" // NEW
    private const val KEY_BUSINESS = "business"
    private const val KEY_LEADERSHIP = "leadership"
    private const val KEY_CHARISMA = "charisma"
    private const val KEY_POLITICAL = "political"
    private const val KEY_WEALTH = "wealth"

    private lateinit var prefs: android.content.SharedPreferences
    private var profile: UserProfile? = null

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        if (prefs.getBoolean(KEY_HAS_PROFILE, false)) {
            profile = UserProfile(
                name = prefs.getString(KEY_NAME, "") ?: "",
                country = prefs.getString(KEY_COUNTRY, "") ?: "",
                business = prefs.getString(KEY_BUSINESS_FIELD, "") ?: "", // NEW
                businessAcumen = prefs.getInt(KEY_BUSINESS, 0),
                leadership = prefs.getInt(KEY_LEADERSHIP, 0),
                charisma = prefs.getInt(KEY_CHARISMA, 0),
                politicalClout = prefs.getInt(KEY_POLITICAL, 0),
                wealth = prefs.getInt(KEY_WEALTH, 0)
            )
        }
    }

    fun hasProfile(): Boolean = profile != null

    fun createProfile(
        name: String,
        country: String,
        business: String,          // NEW
        businessAcumen: Int,
        leadership: Int,
        charisma: Int,
        politicalClout: Int,
        wealth: Int
    ) {
        profile = UserProfile(name, country, business, businessAcumen, leadership, charisma, politicalClout, wealth)
        prefs.edit {
            putBoolean(KEY_HAS_PROFILE, true)
            putString(KEY_NAME, name)
            putString(KEY_COUNTRY, country)
            putString(KEY_BUSINESS_FIELD, business) // NEW
            putInt(KEY_BUSINESS, businessAcumen)
            putInt(KEY_LEADERSHIP, leadership)
            putInt(KEY_CHARISMA, charisma)
            putInt(KEY_POLITICAL, politicalClout)
            putInt(KEY_WEALTH, wealth)
        }
    }

    fun getProfile(): UserProfile? = profile
}
