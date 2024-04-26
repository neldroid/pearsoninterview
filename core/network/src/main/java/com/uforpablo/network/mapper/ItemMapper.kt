package com.uforpablo.network.mapper

import com.uforpablo.model.Item
import com.uforpablo.network.model.ItemObject

object ItemMapper {

    fun List<ItemObject>.toDomain(): List<Item> =
        this.map { itemObject ->
            val itemAPI = itemObject.item
            Item(
                id = itemAPI.id,
                name = itemAPI.attributes.name,
                description = itemAPI.attributes.description,
                imageUrl = itemAPI.attributes.imageInfo.imageUrl
            )
        }

}