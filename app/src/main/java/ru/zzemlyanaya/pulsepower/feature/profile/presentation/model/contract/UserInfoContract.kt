package ru.zzemlyanaya.pulsepower.feature.profile.presentation.model.contract

import ru.zzemlyanaya.pulsepower.core.contract.BaseIntent

interface UserInfoContract {

    data class UiState(
        val phone: String = "",
        val name: String = "",
        val surname: String = "",
        val patronymic: String = "",
        val favouritePlaces: String = "",
        val favouritePlacesError: String? = null
    )

    sealed class Intent : BaseIntent {
        data object ChangeFavouritePlaces: Intent()
        data object SaveChanges : Intent()
    }
}