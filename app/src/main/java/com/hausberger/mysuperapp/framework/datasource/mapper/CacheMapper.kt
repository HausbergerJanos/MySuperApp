package com.hausberger.mysuperapp.framework.datasource.mapper

import com.hausberger.mysuperapp.business.domain.model.Place
import com.hausberger.mysuperapp.business.domain.util.EntityMapper
import com.hausberger.mysuperapp.framework.datasource.cache.model.PlaceEntity
import javax.inject.Inject

class CacheMapper
@Inject
constructor() : EntityMapper<PlaceEntity, Place> {

    override fun mapFromEntity(entity: PlaceEntity): Place {
        return Place(
            id = entity.id,
            town = entity.town,
            country = entity.country,
            synced = entity.synced
        )
    }

    override fun mapToEntity(domainModel: Place): PlaceEntity {
        return PlaceEntity(
            id = domainModel.id,
            town = domainModel.town,
            country = domainModel.country,
            synced = domainModel.synced
        )
    }
}