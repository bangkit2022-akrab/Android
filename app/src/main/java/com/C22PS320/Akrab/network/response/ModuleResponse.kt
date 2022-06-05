package com.C22PS320.Akrab.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ModuleResponse(

	@field:SerializedName("data")
	val data: List<DataItemModule?>? = null,

	@field:SerializedName("meta")
	val meta: Meta? = null
) : Parcelable

@Parcelize
data class DataItemModule(

	@field:SerializedName("materi")
	val materi: String? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
): Parcelable
