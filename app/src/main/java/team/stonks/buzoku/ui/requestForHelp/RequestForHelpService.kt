package team.stonks.buzoku.ui.requestForHelp

import android.util.Log
import team.stonks.buzoku.helpRequests.Parcel

/**
 * Provides network calls for the "Request for Help" page.
 */
class RequestForHelpService {
    fun submitRequest(parcels: List<Parcel>, proposedMeetupPoint: String) {
        // TODO: actually do a network call here
            Log.d(
                "RequestForHelpActivity",
                "Parcels to drop off: $parcels"
            )
            Log.d(
                "RequestForHelpActivity",
                "Meeting point: $proposedMeetupPoint"
            )
    }
}