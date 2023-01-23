package com.sevvalgonul.mobilvize

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favGameTable")
data class Game(@PrimaryKey(autoGenerate = false) @ColumnInfo(name = "ID") var ID: Int? = null,
                @ColumnInfo(name = "name") var name: String?,
                @ColumnInfo(name = "background_image") var background_Image: String?,
                @ColumnInfo(name = "metacritic") var metacritic: Int?
)



