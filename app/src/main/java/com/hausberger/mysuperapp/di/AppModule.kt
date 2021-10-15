package com.hausberger.mysuperapp.di

import com.hausberger.mysuperapp.framework.datasource.cache.implementation.PlacesDao
import com.hausberger.mysuperapp.framework.presentation.contentprovider.CreatePlaceInteractor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCreatePlaceInteractor(placesDao: PlacesDao) =
        CreatePlaceInteractor(placesDao)
}