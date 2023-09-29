package com.plcoding.onboarding_domain.use_case

import com.plcoding.core.R
import com.plcoding.core.utils.UiText

class ValidateNutrientsUseCase {

    operator fun invoke(
        carbRatioText: String,
        proteinRatioText: String,
        fatRatioText: String
    ): Result {
        val carbsRatio = carbRatioText.toIntOrNull()
        val proteinRatio = proteinRatioText.toIntOrNull()
        val fatRatio = fatRatioText.toIntOrNull()
        if (carbsRatio == null || proteinRatio == null || fatRatio == null) {
            return Result.Error(UiText.StringResource(R.string.error_invalid_values))
        }
        if (carbsRatio + proteinRatio + fatRatio != 100) {
            return Result.Error(UiText.StringResource(R.string.error_not_100_percent))
        }
        return Result.Success(carbsRatio / 100F, proteinRatio / 100F, fatRatio / 100F)
    }

    sealed class Result {
        data class Success(val carbsRatio: Float, val proteinRatio: Float, val fatRatio: Float) :
            Result()

        data class Error(val message: UiText) : Result()
    }
}