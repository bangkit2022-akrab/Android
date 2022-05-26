package com.C22PS320.Akrab.network.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("meta")
	val meta: Meta? = null
)

data class User(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("full_name")
	val fullName: String? = null,

	@field:SerializedName("salt")
	val salt: String? = null,

	@field:SerializedName("id_user")
	val idUser: Int? = null,

	@field:SerializedName("email")
	val email: String? = null
)

data class Data(

	@field:SerializedName("server_time")
	val serverTime: String? = null,

	@field:SerializedName("jwt_token")
	val jwtToken: String? = null,

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("token")
	val token: String? = null
)

data class Meta(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("request_id")
	val requestId: String? = null
)
