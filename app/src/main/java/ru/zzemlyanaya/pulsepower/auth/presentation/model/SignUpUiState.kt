package ru.zzemlyanaya.pulsepower.auth.presentation.model

data class SignUpUiState(
    val name: String = "",
    val nameError: String? = null,
    val surname: String = "",
    val surnameError: String? = null,
    val patronymic: String = "",
    val patronymicError: String? = null,
    val phone: String = "",
    val phoneError: String? = null,
    val favouritePlaces: String = "",
    val favouritePlacesError: String? = null
)