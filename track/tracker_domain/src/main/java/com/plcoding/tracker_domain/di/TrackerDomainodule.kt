package com.plcoding.tracker_domain.di

import com.plcoding.core.domain.preferences.Preferences
import com.plcoding.tracker_domain.repository.TrackerRepository
import com.plcoding.tracker_domain.use_case.CalculateMealNutrientsUseCase
import com.plcoding.tracker_domain.use_case.DeleteTrackedFoodUseCase
import com.plcoding.tracker_domain.use_case.GetFoodsForDateUseCase
import com.plcoding.tracker_domain.use_case.InsertTrackedFoodUseCase
import com.plcoding.tracker_domain.use_case.SearchFoodUseCase
import com.plcoding.tracker_domain.use_case.TrackerUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
object TrackerDomainModule {

    @Provides
    @ViewModelScoped
    fun provideTrackerUseCases(
        repository: TrackerRepository,
        preferences: Preferences
    ): TrackerUseCases {
        return TrackerUseCases(
            calculateMealNutrientsUseCase = CalculateMealNutrientsUseCase(preferences),
            deleteTrackedFoodUseCase = DeleteTrackedFoodUseCase(repository),
            getFoodsForDateUseCase = GetFoodsForDateUseCase(repository),
            insertTrackedFoodUseCase = InsertTrackedFoodUseCase(repository),
            searchFoodsForDateUseCase = SearchFoodUseCase(repository),
        )
    }

}