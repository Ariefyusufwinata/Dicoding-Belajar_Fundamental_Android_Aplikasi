package com.rief.model

import com.google.gson.annotations.SerializedName

data class ModelArrUser (
	@SerializedName("items")
	val dataUser : ArrayList<ModelUser>
)