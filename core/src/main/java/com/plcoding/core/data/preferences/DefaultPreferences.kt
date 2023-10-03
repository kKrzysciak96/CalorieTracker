package com.plcoding.core.data.preferences

import android.content.SharedPreferences
import com.plcoding.core.domain.model.ActivityLevel
import com.plcoding.core.domain.model.Gender
import com.plcoding.core.domain.model.GoalType
import com.plcoding.core.domain.model.UserInfo
import com.plcoding.core.domain.preferences.Preferences

class DefaultPreferences(
    private val sharedPreferences: SharedPreferences
) : Preferences {
    override fun saveGender(gender: Gender) {
        sharedPreferences.edit()
            .putString(Preferences.KEY_GENDER, gender.name)
            .apply()
    }

    override fun saveAge(age: Int) {
        sharedPreferences.edit()
            .putInt(Preferences.KEY_AGE, age)
            .apply()
    }

    override fun saveWeight(weight: Float) {
        sharedPreferences.edit()
            .putFloat(Preferences.KEY_WEIGHT, weight)
            .apply()
    }

    override fun saveHeight(height: Int) {
        sharedPreferences.edit()
            .putInt(Preferences.KEY_HEIGHT, height)
            .apply()
    }

    override fun saveGoalType(goalType: GoalType) {
        sharedPreferences.edit()
            .putString(Preferences.KEY_GOAL_TYPE, goalType.name)
            .apply()
    }

    override fun saveActivityLevel(activityLevel: ActivityLevel) {
        sharedPreferences.edit()
            .putString(Preferences.KEY_ACTIVITY_LEVEL, activityLevel.name)
            .apply()
    }

    override fun saveCarbRatio(ratio: Float) {
        sharedPreferences.edit()
            .putFloat(Preferences.KEY_CARB_RATIO, ratio)
            .apply()
    }

    override fun saveProteinRatio(ratio: Float) {
        sharedPreferences.edit()
            .putFloat(Preferences.KEY_PROTEIN_RATIO, ratio)
            .apply()
    }

    override fun saveFatRatio(ratio: Float) {
        sharedPreferences.edit()
            .putFloat(Preferences.KEY_FAT_RATIO, ratio)
            .apply()
    }

    override fun loadUserInfo(): UserInfo {
        val gender = sharedPreferences.getString(Preferences.KEY_GENDER, null)
            .let { Gender.fromString(it ?: "") }
        val age = sharedPreferences.getInt(Preferences.KEY_AGE, -1)
        val weight = sharedPreferences.getFloat(Preferences.KEY_WEIGHT, -1F)
        val height = sharedPreferences.getInt(Preferences.KEY_HEIGHT, -1)
        val goalType = sharedPreferences.getString(Preferences.KEY_GOAL_TYPE, null)
            .let { GoalType.fromString(it ?: "") }
        val activityLevel = sharedPreferences.getString(Preferences.KEY_ACTIVITY_LEVEL, null)
            .let { ActivityLevel.fromString(it ?: "") }
        val carbRatio = sharedPreferences.getFloat(Preferences.KEY_CARB_RATIO, -1F)
        val proteinRatio = sharedPreferences.getFloat(Preferences.KEY_PROTEIN_RATIO, -1F)
        val fatRatio = sharedPreferences.getFloat(Preferences.KEY_FAT_RATIO, -1F)

        return UserInfo(
            gender = gender,
            age = age,
            weight = weight,
            height = height,
            goalType = goalType,
            activityLevel = activityLevel,
            carbRatio = carbRatio,
            proteinRatio = proteinRatio,
            fatRatio = fatRatio
        )
    }

    override fun saveShouldShowOnBoarding(shouldShow: Boolean) {
        sharedPreferences.edit()
            .putBoolean(Preferences.KEY_SHOULD_SHOW_ONBOARDING, shouldShow)
            .apply()
    }

    override fun loadShouldShowOnBoarding(): Boolean {
        return sharedPreferences.getBoolean(Preferences.KEY_SHOULD_SHOW_ONBOARDING, true)
    }
}