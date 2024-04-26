package com.uforpablo.network.model

/**
 * Network representation of a DataCollection with items
 *
 * Each data class represents a single network representation for an item
 */

data class ItemAPI(
    val id: String,
    val attributes: Attributes
)

data class Attributes(
    val name: String,
    val description: String,
    val imageInfo: ImageInfo
)

data class ImageInfo(
    val imageUrl: String
)

data class ItemObject(
    val item: ItemAPI
)

data class DataCollection(
    val dataCollection: List<ItemObject>
)