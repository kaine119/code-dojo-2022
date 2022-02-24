package team.stonks.buzoku.helpRequests

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class Parcel(
    val trackingNumber: String,
    var driver: String? = null,
    var address: String? = null,
    var recipientName: String? = null,
    var recipientContact: String? = null,
    var routeID: String? = null,
    var shipper: String? = null,
) {
    fun getInfo(callback: () -> Unit) {
        val db = Firebase.firestore
        val parcelRef = db.collection("parcels").document(trackingNumber)
        val data = parcelRef.get().addOnSuccessListener {
            Log.d("Parcel.getInfo()", "${it["driver.fullName"]}")
            driver = it["driver.fullName"] as? String
            address = it["address"] as? String
            recipientName = it["recipient.fullName"] as? String
            recipientContact = it["recipient.phoneNumber"] as? String
            routeID = it["routeID"] as? String
            shipper = it["shipper"] as? String

            callback()
        }
    }
}

