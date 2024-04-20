package ru.zzemlyanaya.pulsepower.feature.home.domain.cache

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import ru.zzemlyanaya.pulsepower.feature.home.domain.model.MembershipEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserMembershipCache @Inject constructor() {
    val memberships: ArrayList<MembershipEntity> = arrayListOf()

    fun getAll() = memberships

    fun set(memberships: List<MembershipEntity>) {
        this.memberships.clear()
        this.memberships.addAll(memberships)
    }

    fun add(membership: MembershipEntity) {
        memberships.add(membership)
    }

    fun getActive() = memberships.firstOrNull { it.isActive }
}