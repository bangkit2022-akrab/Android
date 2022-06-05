package com.C22PS320.Akrab.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ModuleQuizResponse(

	@field:SerializedName("data")
	val data: Datamq? = null,

	@field:SerializedName("meta")
	val meta: Metamq? = null
) : Parcelable

@Parcelize
data class Metamq(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("request_id")
	val requestId: String? = null
) : Parcelable

@Parcelize
data class Datamq(

	@field:SerializedName("Quiz")
	val quiz: List<QuizItem?>? = null,

	@field:SerializedName("level")
	val level: String? = null,

	@field:SerializedName("modul")
	val modul: List<ModulItem?>? = null,

	@field:SerializedName("image_level")
	val imageLevel: String? = null,

	@field:SerializedName("tipe")
	val tipe: String? = null
) : Parcelable

@Parcelize
data class QuizItem(

	@field:SerializedName("soal")
	val soal: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("jawaban")
	val jawaban: String? = null
) : Parcelable

@Parcelize
data class ModulItem(

	@field:SerializedName("materi")
	val materi: String? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
) : Parcelable
