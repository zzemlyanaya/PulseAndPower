package ru.zzemlyanaya.pulsepower.feature.profile.domain.model

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserEntityProvider @Inject constructor() {
    var userEntity: UserEntity = UserEntity()
}