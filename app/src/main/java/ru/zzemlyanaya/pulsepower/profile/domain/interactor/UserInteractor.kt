package ru.zzemlyanaya.pulsepower.profile.domain.interactor

import ru.zzemlyanaya.pulsepower.profile.data.repository.UserRepository
import ru.zzemlyanaya.pulsepower.profile.domain.model.*
import javax.inject.Inject

class UserInteractor @Inject constructor(
    private val repository: UserRepository,
    private val userProvider: UserEntityProvider,
) {

    suspend fun createUserInfo(user: UserEntity) = repository.createUserInfo(user).also {
        userProvider.userEntity = it
    }

    suspend fun getUserInfo() = repository.getUserInfo().also { userProvider.userEntity = it }


    suspend fun setFavouritePlaces(places: List<String>) = repository.setFavouritePlaces(places)
}