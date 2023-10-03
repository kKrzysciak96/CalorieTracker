package com.plcoding.tracker_domain.use_case

class TrackerUseCases(
    val calculateMealNutrientsUseCase: CalculateMealNutrientsUseCase,
    val deleteTrackedFoodUseCase: DeleteTrackedFoodUseCase,
    val getFoodsForDateUseCase: GetFoodsForDateUseCase,
    val insertTrackedFoodUseCase: InsertTrackedFoodUseCase,
    val searchFoodsForDateUseCase: SearchFoodUseCase,
)