package com.plcoding.tracker_domain.use_case

import android.util.Log
import com.plcoding.tracker_domain.model.TrackableFood
import com.plcoding.tracker_domain.model.TrackedFood
import com.plcoding.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow

class SearchFoodUseCase(private val repository: TrackerRepository) {

    suspend operator fun invoke(
        query: String,
        page: Int = 1,
        pageSize: Int = 40,
    ): Result<List<TrackableFood>> {
        if (query.isBlank()) {
            return Result.success(emptyList())
        }

        return repository.searchFood(query.trim(), page, pageSize)
    }
}