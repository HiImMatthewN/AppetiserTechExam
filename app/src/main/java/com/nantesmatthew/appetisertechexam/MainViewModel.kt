package com.nantesmatthew.appetisertechexam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nantesmatthew.user_session.domain.model.UserSession
import com.nantesmatthew.user_session.domain.use_case.GetLastUserSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getLastUserSessionUseCase: GetLastUserSessionUseCase):ViewModel() {

    fun getLastUserSession(sessionCallback:((session:UserSession)->Unit)){
        viewModelScope.launch {
            val lastUserSession = getLastUserSessionUseCase().data
            if (lastUserSession != null){
                sessionCallback.invoke(lastUserSession)
            }
        }

    }


}