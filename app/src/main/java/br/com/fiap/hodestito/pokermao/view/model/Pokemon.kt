package br.com.fiap.hodestito.pokermao.view.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pokemon(
//    @SerializedName("number") val numero: String,
//    @SerializedName("name") val nome: String,
//    @SerializedName("imageURL") val urlImagem: String
    @SerializedName("number") val number: String,
    @SerializedName("name") val name: String,
    @SerializedName("imageURL") val imageURL: String
): Parcelable