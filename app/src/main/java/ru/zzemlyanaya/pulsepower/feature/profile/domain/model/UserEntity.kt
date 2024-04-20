package ru.zzemlyanaya.pulsepower.feature.profile.domain.model

data class UserEntity(
    val id: String = "",
    val name: String = "",
    val surname: String = "",
    val patronymic: String = "",
    val phone: String = "",
    val favouritePlacesIds: List<String> = emptyList(),
    val favouritePlacesText: String = ""
)