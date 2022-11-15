package com.nantesmatthew.user_session.domain.use_case

import com.nantesmatthew.user_session.domain.repository.UserSessionRepository
import javax.inject.Inject

class GetLastUserSessionUseCase @Inject constructor(private val repository: UserSessionRepository) {

    suspend operator fun invoke() = repository.getLastOpened()

}