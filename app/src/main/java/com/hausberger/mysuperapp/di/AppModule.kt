package com.hausberger.mysuperapp.di

import com.hausberger.mysuperapp.business.data.cache.abstraction.PlaceCacheDataSource
import com.hausberger.mysuperapp.business.data.network.abstraction.PlaceNetworkDataSource
import com.hausberger.mysuperapp.framework.datasource.cache.database.PlacesDao
import com.hausberger.mysuperapp.business.interactors.CreatePlaceInteractor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    @Singleton
//    @Provides
//    fun provideCreatePlaceInteractor(
//        placeCacheDataSource: PlaceCacheDataSource,
//        placeNetworkDataSource: PlaceNetworkDataSource
//    ) = CreatePlaceInteractor(
//        placeCacheDataSource,
//        placeNetworkDataSource
//    )
}