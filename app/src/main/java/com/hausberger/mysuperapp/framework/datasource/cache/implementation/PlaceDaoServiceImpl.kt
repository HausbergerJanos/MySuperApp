package com.hausberger.mysuperapp.framework.datasource.cache.implementation

import com.hausberger.mysuperapp.business.domain.model.Place
import com.hausberger.mysuperapp.framework.datasource.cache.abstraction.PlaceDaoService
import com.hausberger.mysuperapp.framework.datasource.cache.database.PlacesDao
import com.hausberger.mysuperapp.framework.datasource.mapper.CacheMapper
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class PlaceDaoServiceImpl
@Inject
constructor(
    private val placesDao: PlacesDao,
    private val cacheMapper: CacheMapper
) : PlaceDaoService {

    override suspend fun insertPlace(place: Place): Long {
        return placesDao.insert(
            place = cacheMapper.mapToEntity(place)
        )
    }

    override suspend fun getPlaces(): Flow<List<Place>> {
        return placesDao.getPlaces().flatMapLatest { placeEntities ->
            flow {
                val places = placeEntities.map { placeEntity ->
                    cacheMapper.mapFromEntity(placeEntity)
                }

                emit(places)
            }
        }
    }

    override suspend fun updatePlace(id: Int, externalId: String, synced: Boolean): Int {
        return placesDao.updatePlace(id, externalId, synced)
    }
}