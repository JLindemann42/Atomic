package com.jlindemann.science.activities

import android.content.res.Configuration
import android.graphics.Insets
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.content.ContextCompat
import com.jlindemann.science.R
import com.jlindemann.science.preferences.*
import com.jlindemann.science.utils.Utils
import kotlinx.android.synthetic.main.activity_favorite_settings_page.*

class FavoritePageActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Utils.gestureSetup(window)
        val themePreference = ThemePreference(this)
        val themePrefValue = themePreference.getValue()

        if (themePrefValue == 100) {
            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_NO -> { setTheme(R.style.AppTheme) }
                Configuration.UI_MODE_NIGHT_YES -> { setTheme(R.style.AppThemeDark) }
            }
        }
        if (themePrefValue == 0) { setTheme(R.style.AppTheme) }
        if (themePrefValue == 1) { setTheme(R.style.AppThemeDark) }
        setContentView(R.layout.activity_favorite_settings_page)

        val molarPreference = FavoriteBarPreferences(this)
        var molarPrefValue = molarPreference.getValue()
        if (molarPrefValue == 1) {
            molar_mass_check.isChecked = true
        }
        if (molarPrefValue == 0) {
            molar_mass_check.isChecked = false
        }

        val phasePreferences = FavoritePhase(this)
        var phasePrefValue = phasePreferences.getValue()
        if (phasePrefValue == 1) {
            phase_check.isChecked = true
        }
        if (phasePrefValue == 0) {
            phase_check.isChecked = false
        }

        val electronegativityPreferences = ElectronegativityPreference(this)
        var electronegativityPrefValue = electronegativityPreferences.getValue()
        if (electronegativityPrefValue == 1) {
            electronegativity_check.isChecked = true
        }
        if (electronegativityPrefValue == 0) {
            electronegativity_check.isChecked = false
        }

        //Density
        val densityPreference = DensityPreference(this)
        var densityPrefValue = densityPreference.getValue()
        if (densityPrefValue == 1) {
            density_check.isChecked = true
        }
        if (densityPrefValue == 0) {
            density_check.isChecked = false
        }

        //Degree
        val degreePreference = DegreePreference(this)
        var degreePrefValue = degreePreference.getValue()
        if (degreePrefValue == 0) {
            kelvin_btn.setBackground(ContextCompat.getDrawable(this, R.drawable.toast_outline_high))
            celsius_btn.setBackground(ContextCompat.getDrawable(this, R.drawable.toast_outline))
            fahrenheit_btn.setBackground(ContextCompat.getDrawable(this, R.drawable.toast_outline))
        }
        if (degreePrefValue == 1) {
            kelvin_btn.setBackground(ContextCompat.getDrawable(this, R.drawable.toast_outline))
            celsius_btn.setBackground(ContextCompat.getDrawable(this, R.drawable.toast_outline_high))
            fahrenheit_btn.setBackground(ContextCompat.getDrawable(this, R.drawable.toast_outline))
        }
        if (degreePrefValue == 2) {
            kelvin_btn.setBackground(ContextCompat.getDrawable(this, R.drawable.toast_outline))
            celsius_btn.setBackground(ContextCompat.getDrawable(this, R.drawable.toast_outline))
            fahrenheit_btn.setBackground(ContextCompat.getDrawable(this, R.drawable.toast_outline_high))
        }

        //Boiling Point
        val boilingPreference = BoilingPreference(this)
        var boilingPrefValue = boilingPreference.getValue()
        if (boilingPrefValue == 1) {
            boiling_check.isChecked = true
        }
        if (boilingPrefValue == 0) {
            boiling_check.isChecked = false
        }

        //Melting Point
        val meltingPreference = MeltingPreference(this)
        var meltingPrefValue = meltingPreference.getValue()
        if (meltingPrefValue == 1) {
            melting_check.isChecked = true
        }
        if (meltingPrefValue == 0) {
            melting_check.isChecked = false
        }

        //Specific Heat Capacity
        val specificHeatPreference = SpecificHeatPreference(this)
        var specificHeatValue = specificHeatPreference.getValue()
        if (specificHeatValue == 1) {
            specific_heat_check.isChecked = true
        }
        if (specificHeatValue == 0) {
            specific_heat_check.isChecked = false
        }


        //Fusion Heat
        val fusionheatPreference = FusionHeatPreference(this)
        var fusionHeatValue = fusionheatPreference.getValue()
        if (fusionHeatValue == 1) {
            fusion_heat_check.isChecked = true
        }
        if (fusionHeatValue == 0) {
            fusion_heat_check.isChecked = false
        }

        //Vaporization heat
        val vaporizationHeatPreference = VaporizationHeatPreference(this)
        var vaporizationHeatValue = vaporizationHeatPreference.getValue()
        if (vaporizationHeatValue == 1) {
            vaporization_heat_check.isChecked = true
        }
        if (vaporizationHeatValue == 0) {
            vaporization_heat_check.isChecked = false
        }
        onCheckboxClicked()
        viewf.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

        back_btn_fav.setOnClickListener {
            this.onBackPressed()
        }
    }

    override fun onApplySystemInsets(top: Int, bottom: Int) {
        val params = common_title_back_fav.layoutParams as ViewGroup.LayoutParams
        params.height = top + resources.getDimensionPixelSize(R.dimen.title_bar)
        common_title_back_fav.layoutParams = params

        val params2 = general_header.layoutParams as ViewGroup.MarginLayoutParams
        params2.topMargin = top + resources.getDimensionPixelSize(R.dimen.title_bar)
        general_header.layoutParams = params2
    }

    fun onCheckboxClicked() {
        //Molar Mass
        val molarPreference = FavoriteBarPreferences(this)
        var molarPrefValue = molarPreference.getValue()
        var checkBox:CheckBox = molar_mass_check
        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                molarPreference.setValue(1)
            }
            else {
                molarPreference.setValue(0)
            }
        }

        kelvin_btn.setOnClickListener {
            val degreePreference = DegreePreference(this)
            var degreePrefValue = degreePreference.getValue()

            degreePreference.setValue(0)
            kelvin_btn.setBackground(ContextCompat.getDrawable(this, R.drawable.toast_outline_high))
            celsius_btn.setBackground(ContextCompat.getDrawable(this, R.drawable.toast_outline))
            fahrenheit_btn.setBackground(ContextCompat.getDrawable(this, R.drawable.toast_outline))
        }
        celsius_btn.setOnClickListener {
            val degreePreference = DegreePreference(this)
            var degreePrefValue = degreePreference.getValue()

            degreePreference.setValue(1)
            kelvin_btn.setBackground(ContextCompat.getDrawable(this, R.drawable.toast_outline))
            celsius_btn.setBackground(ContextCompat.getDrawable(this, R.drawable.toast_outline_high))
            fahrenheit_btn.setBackground(ContextCompat.getDrawable(this, R.drawable.toast_outline))
        }
        fahrenheit_btn.setOnClickListener {
            val degreePreference = DegreePreference(this)
            var degreePrefValue = degreePreference.getValue()

            degreePreference.setValue(2)
            kelvin_btn.setBackground(ContextCompat.getDrawable(this, R.drawable.toast_outline))
            celsius_btn.setBackground(ContextCompat.getDrawable(this, R.drawable.toast_outline))
            fahrenheit_btn.setBackground(ContextCompat.getDrawable(this, R.drawable.toast_outline_high))
        }

        //STP Phase
        val phasePreference = FavoritePhase(this)
        var phasePrefValue = phasePreference.getValue()
        var phaseCheckBox:CheckBox = phase_check
        phaseCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                phasePreference.setValue(1)
            }
            else {
                phasePreference.setValue(0)
            }
        }

        //Electronegativity
        val electronegativityPreference = ElectronegativityPreference(this)
        var electronegativityPrefValue = electronegativityPreference.getValue()
        var electronegativityCheckBox:CheckBox = electronegativity_check
        electronegativityCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                electronegativityPreference.setValue(1)
            }
            else {
                electronegativityPreference.setValue(0)
            }
        }

        //Density
        val densityPreference = DensityPreference(this)
        var densityPrefValue = densityPreference.getValue()
        var densityCheckBox:CheckBox = density_check
        densityCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                densityPreference.setValue(1)
            }
            else {
                densityPreference.setValue(0)
            }

        }

        //Boiling Point
        val boilingPreference = BoilingPreference(this)
        var boilingPrefValue = boilingPreference.getValue()
        var boilingCheckBox:CheckBox = boiling_check
        boilingCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                boilingPreference.setValue(1)
            }
            else {
                boilingPreference.setValue(0)
            }

        }

        //Melting Point
        val meltingPreference = MeltingPreference(this)
        var meltingPrefValue = meltingPreference.getValue()
        var meltingCheckBox:CheckBox = melting_check
        meltingCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                meltingPreference.setValue(1)
            }
            else {
                meltingPreference.setValue(0)
            }

        }

        //Specific Heat Point
        val specificHeatPreference = SpecificHeatPreference(this)
        var specificHeatValue = specificHeatPreference.getValue()
        var specificCheckBox:CheckBox = specific_heat_check
        specificCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                specificHeatPreference.setValue(1)
            }
            else {
                specificHeatPreference.setValue(0)
            }

        }

        //Fusion Heat
        val fusionHeatPreference = FusionHeatPreference(this)
        var fusionHeatValue = fusionHeatPreference.getValue()
        var fusionCheckBox:CheckBox = fusion_heat_check
        fusionCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                fusionHeatPreference.setValue(1)
            }
            else {
                fusionHeatPreference.setValue(0)
            }

        }

        //Vapor Heat
        val vaporizationHeatPreference = VaporizationHeatPreference(this)
        var vaporizationHeatValue = vaporizationHeatPreference.getValue()
        var vaporizationCheckBox:CheckBox = vaporization_heat_check
        vaporizationCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                vaporizationHeatPreference.setValue(1)
            }
            else {
                vaporizationHeatPreference.setValue(0)
            }

        }

    }

}




