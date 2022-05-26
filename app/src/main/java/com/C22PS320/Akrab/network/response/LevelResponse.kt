package com.C22PS320.Akrab.network.response

import com.google.gson.annotations.SerializedName

data class LevelResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("meta")
	val meta: Meta? = null
)

data class DataItem(

	@field:SerializedName("level")
	val level: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("image_level")
	val imageLevel: String? = null
)
