package com.hausberger.mysuperapp.di

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.hausberger.mysuperapp.business.data.network.abstraction.PlaceNetworkDataSource
import com.hausberger.mysuperapp.business.data.network.implementation.PlaceNetworkDataSourceImpl
import com.hausberger.mysuperapp.framework.datasource.network.abstraction.PlaceNetworkService
import com.hausberger.mysuperapp.framework.datasource.network.implementation.PlaceNetworkServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideFirebaseDatabase(): FirebaseDatabase {
        return  Firebase.database("https://mysuperapp-49a9b-default-rtdb.firebaseio.com")
    }

    @Singleton
    @Provides
    fun provideRealtimeDatabaseRef(firebaseDatabase: FirebaseDatabase): DatabaseReference {
        return firebaseDatabase.reference
    }

    @Singleton
    @Provides
    fun provideFireStore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun providesPlaceNetworkService(
        firebaseFirestore: FirebaseFirestore
    ): PlaceNetworkService {
        return PlaceNetworkServiceImpl(firebaseFirestore)
    }

    @Singleton
    @Provides
    fun providePlaceNetworkDataSource(placeNetworkService: PlaceNetworkService): PlaceNetworkDataSource {
        return PlaceNetworkDataSourceImpl(placeNetworkService)
    }
}