package team.stonks.buzoku.ui.promptHelpRequest

import android.app.*
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import team.stonks.buzoku.R
import team.stonks.buzoku.helpRequests.Parcel

class PromptHelpRequestService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }


    override fun onCreate() {
        super.onCreate()
        val nm = (getSystemService(NOTIFICATION_SERVICE) as NotificationManager);
        val serviceChannel =
            NotificationChannel("555", "Clan Request Service", NotificationManager.IMPORTANCE_LOW)
        serviceChannel.description = "Listens for clan members requesting help"
        val requestChannel =
            NotificationChannel("101", "Request for help", NotificationManager.IMPORTANCE_HIGH)
        requestChannel.description = "Requests for help from fellow ninjas "
        nm.createNotificationChannel(serviceChannel)
        nm.createNotificationChannel(requestChannel)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification: Notification = Notification.Builder(this, "555")
            .setContentTitle("Clan Request Service")
            .setContentText("Listening for requests from fellow ninjas!")
            .setSmallIcon(R.drawable.ic_menu_camera)
            .build()
        startForeground(1000001, notification)

        val db = Firebase.firestore
        var firstTimeQuery = true

        val requestsRef = db.collection("requests")
        requestsRef.addSnapshotListener { snapshots, e ->
            when {
                firstTimeQuery -> {
                    firstTimeQuery = false
                }
                e != null -> {
                    Log.w("PromptHelpRequestService", "listen:error", e)
                    return@addSnapshotListener
                }
                snapshots != null && snapshots.metadata.hasPendingWrites() -> {
                    // Writes immediately trigger snapshot listeners
                    // If the snapshot is still waiting to be written to server, ignore it
                    return@addSnapshotListener
                }
                else -> {
                    for (dc in snapshots!!.documentChanges) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            Log.d("PromptHelpRequestService", "Document added: ${dc.document.data}")
                            val parcelIDs = dc.document["parcel_ids"] as List<String>
                            val requesterId = dc.document["driver_id"] as String
                            db.collection("drivers")
                                .document(requesterId)
                                .get()
                                .addOnSuccessListener {
                                    sendRequestNotification(parcelIDs, it, requesterId)
                                }
                        }
                    }
                }
            }
        }


        return super.onStartCommand(intent, flags, startId)
    }

    fun sendRequestNotification(parcelIDs: List<String>, requester: DocumentSnapshot, requesterId: String) {
        val requesterName = requester.get("fullName") as String
        val lat = (requester.get("location.lat") as String).toFloat()
        val lng = (requester.get("location.long") as String).toFloat()

        val promptIntent = Intent(this, PromptHelpRequestActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("ParcelsToPickUp", Gson().toJson(parcelIDs))
            putExtra("RequesterName", requesterName)
            putExtra("RequesterLat", lat)
            putExtra("RequesterLng", lng)
            putExtra("RequesterId", requesterId)
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, promptIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val nm = (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
        val notification: Notification = Notification.Builder(this, "101")
            .setContentTitle("$requesterName needs help!")
            .setContentText("Tap to see if you can help share the delivery load")
            .setSmallIcon(R.drawable.ic_menu_camera)
            .setContentIntent(pendingIntent)
            .build()
        nm.notify(1000001, notification)
    }
}
