package ru.zzemlyanaya.pulsepower.core.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.zzemlyanaya.pulsepower.core.contract.BaseIntent
import ru.zzemlyanaya.pulsepower.core.contract.ScreenUiState

@Composable
fun <S: Any> BaseScreen(
    modifier: Modifier,
    uiFlow: StateFlow<ScreenUiState>,
    sendIntent: suspend (BaseIntent) -> Unit,
    errorContent: @Composable () -> Unit = {},
    loadingContent: @Composable (Modifier, S, (BaseIntent) -> Unit) -> Unit = { _, _, _ -> },
    dataContent: @Composable (Modifier, S, (BaseIntent) -> Unit) -> Unit,
){
    val scope = LocalLifecycleOwner.current.lifecycleScope

    fun sendIntentInternal(intent: BaseIntent) {
        scope.launch { sendIntent(intent) }
    }

    val uiState by uiFlow.collectAsState()
    when (uiState) {
        is ScreenUiState.Error -> errorContent()
        is ScreenUiState.Loading<*> -> loadingContent(
            modifier.fillMaxSize(),
            (uiState as ScreenUiState.Loading<*>).state as S,
            ::sendIntentInternal
        )
        is ScreenUiState.Data<*> -> dataContent(
            modifier.fillMaxSize(),
            (uiState as ScreenUiState.Data<*>).state as S,
            ::sendIntentInternal
        )
        else -> {}
    }
}