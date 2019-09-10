package br.com.fiap.hodestito.pokermao.view.model

import com.google.gson.annotations.SerializedName

data class Pokemon(
 @SerializedName("number") val numero: String,
 @SerializedName("name") val nome: String,
 @SerializedName("imageURL") val urlImagem: String
)