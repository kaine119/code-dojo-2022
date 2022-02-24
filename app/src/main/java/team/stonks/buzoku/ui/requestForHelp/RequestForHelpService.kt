package team.stonks.buzoku.ui.requestForHelp

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import team.stonks.buzoku.helpRequests.Parcel
import java.time.Instant

/**
 * Provides network calls for the "Request for Help" page.
 */
class RequestForHelpService {
    fun submitRequest(parcels: List<Parcel>, proposedMeetupPoint: String) {
        val db = Firebase.firestore
        val requestsCollection = db.collection("requests")

        val newRequest = hashMapOf(
            "date" to Instant.now().epochSecond,
            "help" to "",
            "driver_id" to "driver1",
            "parcel_ids" to parcels.map { it.trackingNumber },
            "proposedMeetupPoint" to proposedMeetupPoint
        )

        requestsCollection.document().set(newRequest)
    }
}