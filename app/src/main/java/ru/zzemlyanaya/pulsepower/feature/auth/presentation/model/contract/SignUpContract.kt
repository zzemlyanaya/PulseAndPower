package ru.zzemlyanaya.pulsepower.feature.auth.presentation.model.contract

import ru.zzemlyanaya.pulsepower.core.contract.BaseIntent

sealed interface SignUpContract {

    data class UiState(
        val name: String = "",
        val nameError: String? = null,
        val surname: String = "",
        val surnameError: String? = null,
        val patronymic: String = "",
        val patronymicError: String? = null,
        val favouritePlaces: String = "",
        val favouritePlacesError: String? = null
    )

    sealed interface Intent : BaseIntent {
        data class UpdateName(val name: String) : Intent
        data class UpdateSurname(val surname: String) : Intent
        data class UpdatePatronymic(val patronymic: String) : Intent
        data object SelectPlaces : Intent
        data object SignUp : Intent
    }
}