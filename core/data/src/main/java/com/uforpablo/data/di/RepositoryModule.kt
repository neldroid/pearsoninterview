package com.uforpablo.data.di

import com.uforpablo.data.repository.ItemRepository
import com.uforpablo.data.repository.ItemRepositoryImpl
import com.uforpablo.data.util.ConnectivityManagerNetworkMonitor
import com.uforpablo.data.util.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun providesItemRepository(repository: ItemRepositoryImpl): ItemRepository

    @Binds
    fun bindsNetworkMonitor(networkMonitor: ConnectivityManagerNetworkMonitor): NetworkMonitor
}