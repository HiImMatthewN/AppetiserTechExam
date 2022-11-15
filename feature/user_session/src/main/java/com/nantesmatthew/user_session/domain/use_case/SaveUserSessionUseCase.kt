package com.nantesmatthew.user_session.domain.use_case

import com.nantesmatthew.user_session.domain.model.UserSession
import com.nantesmatthew.user_session.domain.repository.UserSessionRepository
import javax.inject.Inject

class SaveUserSessionUseCase @Inject constructor(
    private val repository: UserSessionRepository) {

    suspend operator fun invoke(userSession: UserSession) = repository.save(userSession)

}