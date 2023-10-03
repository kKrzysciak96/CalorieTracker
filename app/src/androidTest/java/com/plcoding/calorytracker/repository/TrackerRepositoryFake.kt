package com.plcoding.calorytracker.repository


import com.plcoding.tracker_domain.model.TrackableFood
import com.plcoding.tracker_domain.model.TrackedFood
import com.plcoding.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import java.time.LocalDate
import kotlin.random.Random


class TrackerRepositoryFake : TrackerRepository {

    var shouldReturnError = false
    private val trackedFoods = mutableListOf<TrackedFood>()
    var searchResults = listOf<TrackableFood>()

    private val getFoodsForDateFlow = MutableSharedFlow<List<TrackedFood>>(replay = 1)
    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<TrackableFood>> {
        return if (shouldReturnError) {
            Result.failure(Throwable())
        } else {
            Result.success(searchResults)
        }
    }

    override suspend fun insertTrackedFood(trackedFood: TrackedFood) {
        trackedFoods.add(trackedFood.copy(id = Random.nextInt()))
        getFoodsForDateFlow.emit(trackedFoods)
    }

    override suspend fun deleteTrackedFood(trackedFood: TrackedFood) {
        trackedFoods.remove(trackedFood)
        getFoodsForDateFlow.emit(trackedFoods)
    }

    override fun getFoodsForDate(localDate: LocalDate): Flow<List<TrackedFood>> {
        return getFoodsForDateFlow
    }
}