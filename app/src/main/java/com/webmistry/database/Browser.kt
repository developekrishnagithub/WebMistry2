package com.webmistry.database

import android.os.Parcel
import android.os.Parcelable

data class Browser(
    val webImage:Int,
    val webName: String?,
    val url: String?
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(webImage)
        parcel.writeString(webName)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Browser> {
        override fun createFromParcel(parcel: Parcel): Browser {
            return Browser(parcel)
        }

        override fun newArray(size: Int): Array<Browser?> {
            return arrayOfNulls(size)
        }
    }

}
