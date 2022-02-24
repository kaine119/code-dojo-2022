package team.stonks.buzoku.ui.promptHelpRequest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import team.stonks.buzoku.R
import team.stonks.buzoku.helpRequests.Parcel

class ParcelListAdapter(var parcelList: List<Parcel>) :
    RecyclerView.Adapter<ParcelListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var parcelTrackingNumberText: TextView = itemView.findViewById(R.id.parcelTrackingNumber)
        var parcelAddressText: TextView = itemView.findViewById(R.id.parcelAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.parcel_item_view, parent, false)
        itemView.findViewById<CheckBox>(R.id.parcelSelectedCheckbox).visibility = View.INVISIBLE
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val parcel = parcelList[position]
        parcel.getInfo {
            holder.parcelTrackingNumberText.text = parcel.trackingNumber
            holder.parcelAddressText.text = parcel.address
        }

    }

    override fun getItemCount() = parcelList.size
}

class PromptHelpRequestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prompt_request)

        if (!intent.hasExtra("ParcelsToPickUp")) {
            throw IllegalArgumentException("Activity should be called with the ParcelsToPickUp extra")
        }

        val parcelIDs = Gson().fromJson<List<String>>(
            intent.getStringExtra("ParcelsToPickUp"),
            List::class.java
        )
        val parcels = parcelIDs.map { Parcel(it) }

        val requesterName = intent.getStringExtra("RequesterName")
        val lat = intent.getStringExtra("RequesterLat")
        val lng = intent.getStringExtra("RequesterLng")
        val requesterId = intent.getStringExtra("RequesterId")

        // Setup RecyclerView
        val parcelListAdapter = ParcelListAdapter(parcels)
        findViewById<RecyclerView>(R.id.parcelReceiverRecyclerView).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = parcelListAdapter
        }

        // Update text fields on the view
        findViewById<TextView>(R.id.requestPromptTitleText).text = "$requesterName needs help!"
        findViewById<TextView>(R.id.promptExistingCount).text = 10.toString()
        findViewById<TextView>(R.id.promptReceivingCount).text = parcels.size.toString()

        findViewById<Button>(R.id.btnIgnorePrompt).setOnClickListener {
            finish()
        }
    }
}