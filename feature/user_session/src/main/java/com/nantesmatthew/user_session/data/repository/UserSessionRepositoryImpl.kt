package com.nantesmatthew.user_session.data.repository

import com.nantesmatthew.core.util.Resource
import com.nantesmatthew.user_session.data.data_source.UserSessionDao
import com.nantesmatthew.user_session.data.entity.UserSessionEntity
import com.nantesmatthew.user_session.data.entity.toDomain
import com.nantesmatthew.user_session.domain.model.UserSession
import com.nantesmatthew.user_session.domain.repository.UserSessionRepository
import javax.inject.Inject

class UserSessionRepositoryImpl @Inject constructor(private val dao: UserSessionDao) : UserSessionRepository {
    override suspend fun save(userSession: UserSession): Resource<Unit> {
        val saveResult = dao.insert(UserSessionEntity(
            lastOpened = userSession.lastOpened.time,
            screenOpened = userSession.screenOpened.name,
            screenInfo = userSession.screenInfo
            ))

        if (saveResult == 0L)
            return Resource.error("User Session not saved")

        return Resource.success(Unit)
    }

    override suspend fun getLastOpened(): Resource<UserSession> {
        val userSessionResult = dao.getLastSession() ?: return Resource.error("No Session Found")

        return Resource.success(userSessionResult.toDomain())
    }
}