package ru.zzemlyanaya.pulsepower.history.presentation.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.AnnotatedString
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.zzemlyanaya.pulsepower.app.navigation.NavigationRouter
import ru.zzemlyanaya.pulsepower.app.theme.purple_9999ff
import ru.zzemlyanaya.pulsepower.app.theme.white
import ru.zzemlyanaya.pulsepower.core.viewModel.BaseViewModel
import ru.zzemlyanaya.pulsepower.history.presentation.model.HistoryUiState
import ru.zzemlyanaya.pulsepower.history.presentation.model.SubscriptionUiModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val router: NavigationRouter
) : BaseViewModel(router) {

    private val mutableUiState = mutableStateOf(HistoryUiState())
    val historyUiState: State<HistoryUiState> = mutableUiState

    init {
        mutableUiState.value = mutableUiState.value.copy(items = getData())
    }

    companion object {
        fun getData() = listOf(
            SubscriptionUiModel(
                description = AnnotatedString("Индивидуальные утренние занятия"),
                period = AnnotatedString("На 3 месяца до 11.06.2024"),
                pulseColor = purple_9999ff.copy(alpha = 0.6f),
                onRepeat = {},
                isRepeatable = false
            ),
            SubscriptionUiModel(
                description = AnnotatedString("Индивидуальные утренние занятия"),
                period = AnnotatedString("На 3 месяца"),
                pulseColor = white.copy(alpha = 0.3f),
                onRepeat = {},
                isRepeatable = true
            ),
            SubscriptionUiModel(
                description = AnnotatedString("Индивидуальные утренние занятия"),
                period = AnnotatedString("На 3 месяца"),
                pulseColor = white.copy(alpha = 0.3f),
                onRepeat = {},
                isRepeatable = true
            ),SubscriptionUiModel(
                description = AnnotatedString("Индивидуальные утренние занятия"),
                period = AnnotatedString("На 3 месяца"),
                pulseColor = white.copy(alpha = 0.3f),
                onRepeat = {},
                isRepeatable = true
            ),
            SubscriptionUiModel(
                description = AnnotatedString("Индивидуальные утренние занятия"),
                period = AnnotatedString("На 3 месяца"),
                pulseColor = white.copy(alpha = 0.3f),
                onRepeat = {},
                isRepeatable = true
            ),
            SubscriptionUiModel(
                description = AnnotatedString("Индивидуальные утренние занятия"),
                period = AnnotatedString("На 3 месяца"),
                pulseColor = white.copy(alpha = 0.3f),
                onRepeat = {},
                isRepeatable = true
            ),SubscriptionUiModel(
                description = AnnotatedString("Индивидуальные утренние занятия"),
                period = AnnotatedString("На 3 месяца"),
                pulseColor = white.copy(alpha = 0.3f),
                onRepeat = {},
                isRepeatable = true
            ),
            SubscriptionUiModel(
                description = AnnotatedString("Индивидуальные утренние занятия"),
                period = AnnotatedString("На 3 месяца"),
                pulseColor = white.copy(alpha = 0.2f),
                onRepeat = {},
                isRepeatable = true
            )
        )
    }
}