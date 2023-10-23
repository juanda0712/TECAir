import android.os.Parcel
import android.os.Parcelable

data class ReservationInfo(
    val flightNumber: Int,
    val date: String,
    val price: String,
    val origin: String,
    val destination: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(flightNumber)
        parcel.writeString(date)
        parcel.writeString(price)
        parcel.writeString(origin)
        parcel.writeString(destination)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ReservationInfo> {
        override fun createFromParcel(parcel: Parcel): ReservationInfo {
            return ReservationInfo(parcel)
        }

        override fun newArray(size: Int): Array<ReservationInfo?> {
            return arrayOfNulls(size)
        }
    }
}
