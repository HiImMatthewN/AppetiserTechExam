package com.nantesmatthew.core.util.module

import android.content.Context
import com.nantesmatthew.core.util.ConnectivityObserver
import com.nantesmatthew.core.util.NetworkConnectivityObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class ConnectivityModule {

    @Provides
    @ViewModelScoped
    fun providesConnectivityObserver(@ApplicationContext context: Context):ConnectivityObserver
     = NetworkConnectivityObserver(context)

}