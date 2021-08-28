package com.hausberger.mysuperapp.di

import android.content.Context
import androidx.room.Room
import com.hausberger.mysuperapp.framework.datasource.cache.database.Database
import com.hausberger.mysuperapp.framework.datasource.cache.implementation.PlacesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideAppDb(@ApplicationContext context: Context): Database {
        return Room.databaseBuilder(
            context,
            Database::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providePlacesDao(database: Database): PlacesDao {
        return database.placesDao()
    }
}