package com.plcoding.tracker_presentation.search


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.core.R
import com.plcoding.core.utils.UiEvent
import com.plcoding.core_ui.LocalSpacing
import com.plcoding.tracker_domain.model.MealType
import com.plcoding.tracker_presentation.search.components.SearchTextField
import com.plcoding.tracker_presentation.search.components.TrackableFoodItem
import java.time.LocalDate

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    scaffoldState: ScaffoldState,
    mealName: String,
    dayOfMonth: Int,
    month: Int,
    year: Int,
    onNavigateUp: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = keyboardController, block = {
        viewModel.uiEvent.collect { event ->
            when (event) {
                UiEvent.NavigateUp -> {
                    onNavigateUp()
                }

                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message.asString(context))
                    keyboardController?.hide()
                }

                else -> Unit
            }
        }
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceMedium)
    ) {
        Text(
            text = stringResource(id = R.string.add_meal, mealName),
            style = MaterialTheme.typography.h2
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        SearchTextField(
            text = state.query,
            onValueChange = { viewModel.onEvent(SearchEvent.OnQueryChange(it)) },
            onSearch = {
                keyboardController?.hide()
                viewModel.onEvent(SearchEvent.OnSearch)
            },
            onFocusChanged = { viewModel.onEvent(SearchEvent.OnSearchFocusChange(it.isFocused)) },
            shouldShowHint = state.isHintVisible
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.trackableFoodList) { food ->
                TrackableFoodItem(
                    trackableFoodUiState = food,
                    onClick = {
                        viewModel.onEvent(SearchEvent.OnToggleTrackableFood(food.food))
                    },
                    onAmountChange = {
                        viewModel.onEvent(
                            SearchEvent.OnAmountFoodChange(
                                amount = it,
                                food = food.food
                            )
                        )
                    },
                    onTrack = {
                        keyboardController?.hide()
                        viewModel.onEvent(
                            SearchEvent.OnTrackFoodClick(
                                food = food.food,
                                mealType = MealType.fromString(mealName),
                                date = LocalDate.of(year, month, dayOfMonth)
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.isSearching -> {
                CircularProgressIndicator()
            }

            state.trackableFoodList.isEmpty() -> {
                Text(
                    text = stringResource(id = R.string.no_results),
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}