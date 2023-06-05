package com.example.tic_tac_toe_23009273

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceManager

class SettingsActivity : AppCompatActivity() {

    companion object {
        const val GRID_SIZE = "grid_size"
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, MyPreferenceFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class MyPreferenceFragment : PreferenceFragmentCompat() {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            var gridSize = 3

            val switch1 = findPreference<SwitchPreferenceCompat>("grid3")
            val switch2 = findPreference<SwitchPreferenceCompat>("grid4")
            val switch3 = findPreference<SwitchPreferenceCompat>("grid5")

            val humanswitch = findPreference<SwitchPreferenceCompat>("human")
            val aiswitch = findPreference<SwitchPreferenceCompat>("ai")

            val normalswitch = findPreference<SwitchPreferenceCompat>("normal")
            val partyswitch = findPreference<SwitchPreferenceCompat>("party")

            switch1?.isEnabled = true
            //humanswitch?.isEnabled = true
            //normalswitch?.isEnabled = true

            //switch1?.isChecked = true
            //switch2?.isChecked = false
            //switch3?.isChecked = false
            //humanswitch?.isChecked = true
            //aiswitch?.isChecked = false
            //normalswitch?.isChecked = true
            //partyswitch?.isChecked = false

            switch1?.setOnPreferenceChangeListener { preference, newValue ->
                if (newValue as Boolean) {
                    //switch2?.isEnabled = false
                    //switch3?.isEnabled = false
                    gridSize = 3
                    switch2?.isChecked = false
                    switch3?.isChecked = false
                } else {
                    switch2?.isEnabled = true
                    switch3?.isEnabled = true
                }
                // Save the selected grid size to SharedPreferences
                val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context?.applicationContext ?: throw IllegalStateException("Context cannot be null"))
                sharedPreferences.edit().putInt(GRID_SIZE, gridSize).apply()

                true
            }

            switch2?.setOnPreferenceChangeListener { preference, newValue ->
                if (newValue as Boolean) {
                    //switch1?.isEnabled = false
                    //switch3?.isEnabled = false
                    gridSize = 4
                    switch1?.isChecked = false
                    switch3?.isChecked = false
                } else {
                    switch1?.isEnabled = true
                    switch3?.isEnabled = true
                }
                // Save the selected grid size to SharedPreferences
                val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context?.applicationContext ?: throw IllegalStateException("Context cannot be null"))
                sharedPreferences.edit().putInt(GRID_SIZE, gridSize).apply()

                true
            }

            switch3?.setOnPreferenceChangeListener { preference, newValue ->
                if (newValue as Boolean) {
                    //switch1?.isEnabled = false
                    //switch2?.isEnabled = false
                    gridSize = 5
                    switch1?.isChecked = false
                    switch2?.isChecked = false
                } else {
                    switch1?.isEnabled = true
                    switch2?.isEnabled = true
                }
                // Save the selected grid size to SharedPreferences
                val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context?.applicationContext ?: throw IllegalStateException("Context cannot be null"))
                sharedPreferences.edit().putInt(GRID_SIZE, gridSize).apply()

                true
            }

            humanswitch?.setOnPreferenceChangeListener { preference, newValue ->
                if (newValue as Boolean) {
                    //aiswitch?.isEnabled = false
                    aiswitch?.isChecked = false
                } else {
                    aiswitch?.isEnabled = true
                    humanswitch?.isEnabled = true
                }
                true
            }

            aiswitch?.setOnPreferenceChangeListener { preference, newValue ->
                if (newValue as Boolean) {
                    //humanswitch?.isEnabled = false
                    humanswitch?.isChecked = false
                } else {
                    humanswitch?.isEnabled = true
                    aiswitch?.isEnabled = true
                }
                // Save the new value to SharedPreferences
                val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context?.applicationContext ?: throw IllegalStateException("Context cannot be null"))
                sharedPreferences.edit().putBoolean("ai", newValue).apply()

                true
            }

            normalswitch?.setOnPreferenceChangeListener { preference, newValue ->
                if (newValue as Boolean) {
                    //partyswitch?.isEnabled = false
                    partyswitch?.isChecked = false
                } else {
                    partyswitch?.isEnabled = true
                    normalswitch?.isEnabled = true
                }
                true
            }

            partyswitch?.setOnPreferenceChangeListener { preference, newValue ->
                if (newValue as Boolean) {
                    //normalswitch?.isEnabled = false
                    normalswitch?.isChecked = false
                } else {
                    normalswitch?.isEnabled = true
                    partyswitch?.isEnabled = true
                }
                true
            }
        }
    }
}