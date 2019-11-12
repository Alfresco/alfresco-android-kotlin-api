package com.alfresco.core.mapper

/**
 *
 * Created by Bogdan Roatis on 15 September 2019.
 */
interface EntityMapper<DataSourceEntity : Any, Entity : Any> {

    fun mapFromEntity(entity: Entity): DataSourceEntity

    fun mapToEntity(dataSourceEntity: DataSourceEntity): Entity

    fun mapFromEntities(entities: Iterable<Entity>) =
            entities.map { mapFromEntity(it) }

    fun mapToEntities(dataSourceEntities: Iterable<DataSourceEntity>) =
            dataSourceEntities.map { mapToEntity(it) }
}
