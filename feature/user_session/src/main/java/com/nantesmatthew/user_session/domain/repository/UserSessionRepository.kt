package com.nantesmatthew.user_session.domain.repository

import com.nantesmatthew.core.util.Resource
import com.nantesmatthew.user_session.domain.model.UserSession

interface UserSessionRepository {

    suspend fun save(userSession: UserSession):Resource<Unit>

    suspend fun getLastOpened():Resource<UserSession>
}