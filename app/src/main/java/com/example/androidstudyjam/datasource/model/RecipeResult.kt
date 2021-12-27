package com.example.androidstudyjam.datasource.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "recipe")
data class RecipeResult(
    @SerializedName("id")
    @PrimaryKey val id: Int,
    @SerializedName("content")
    val content: String?,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    var searchQuery: String,
) : Parcelable {

    object ResultDiffUtil : DiffUtil.ItemCallback<RecipeResult>() {

        override fun areItemsTheSame(oldItem: RecipeResult, newItem: RecipeResult): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: RecipeResult, newItem: RecipeResult): Boolean {

            return oldItem.id == newItem.id && oldItem.name == newItem.name && oldItem.content == newItem.content
        }

    }

}

