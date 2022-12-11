package com.sevvalgonul.mobilvize

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Games(val gameList: ArrayList<Game>) : Parcelable
