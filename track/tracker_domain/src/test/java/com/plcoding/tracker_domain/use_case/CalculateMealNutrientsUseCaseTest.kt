package com.plcoding.tracker_domain.use_case


import com.google.common.truth.Truth.assertThat
import com.plcoding.core.domain.model.ActivityLevel
import com.plcoding.core.domain.model.Gender
import com.plcoding.core.domain.model.GoalType
import com.plcoding.core.domain.model.UserInfo
import com.plcoding.core.domain.preferences.Preferences
import com.plcoding.tracker_domain.model.MealType
import com.plcoding.tracker_domain.model.TrackedFood
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import kotlin.random.Random

class CalculateMealNutrientsUseCaseTest {

    private lateinit var calculateNutrients: CalculateMealNutrientsUseCase

    @Before
    fun setUp() {
        val preferences = mockk<Preferences>(relaxed = true)
        every { preferences.loadUserInfo() } returns UserInfo(
            gender = Gender.Male,
            age = 20,
            weight = 60f,
            height = 170,
            goalType = GoalType.GainWeight,
            carbRatio = 0.3f,
            proteinRatio = 0.3f,
            fatRatio = 0.4f,
            activityLevel = ActivityLevel.High

        )
        calculateNutrients = CalculateMealNutrientsUseCase(preferences)
    }

    @Test
    fun `Calories for breakfast properly calculated`() {
        val trackedFoods = (1..30).map {
            TrackedFood(
                name = "name",
                carbs = Random.nextInt(100),
                proteins = Random.nextInt(100),
                fat = Random.nextInt(100),
                mealType = (1..4).shuffled().first().let {
                    when (it) {
                        1 -> MealType.Breakfast
                        2 -> MealType.Lunch
                        3 -> MealType.Dinner
                        else -> MealType.Snack
                    }
                },
                photo = null,
                amount = Random.nextInt(100),
                date = LocalDate.now(),
                calories = Random.nextInt(1000)
            )
        }
        val result = calculateNutrients(trackedFoods)
        val breakfastCalories =
            result.mealNutrients.values.filter { it.mealType is MealType.Breakfast }
                .sumOf { it.caloriesAmount }
        val expectedCalories =
            trackedFoods.filter { it.mealType is MealType.Breakfast }.sumOf { it.calories }

        assertThat(breakfastCalories).isEqualTo(expectedCalories)
    }

}