package team.stonks.buzoku.ui.promptHelpRequest

import android.app.*
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import team.stonks.buzoku.R

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

        // TODO: test
        val handler = Handler()
        handler.postDelayed({
            sendRequestNotification("test!")
        }, 1000)

        return super.onStartCommand(intent, flags, startId)
    }

    fun sendRequestNotification(text: String) {
        val promptIntent = Intent(this, PromptHelpRequestActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, promptIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )



        val nm = (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
        val notification: Notification = Notification.Builder(this, "101")
            .setContentTitle(text)
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_menu_camera)
            .setContentIntent(pendingIntent)
            .build()
        nm.notify(1000001, notification)
    }
}
