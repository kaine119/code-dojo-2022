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
import team.stonks.buzoku.R
import team.stonks.buzoku.databinding.ActivityMainBinding
import team.stonks.buzoku.databinding.ActivityPromptRequestBinding
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
        holder.parcelTrackingNumberText.text = parcel.trackingNumber
        holder.parcelAddressText.text = parcel.destination
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

        // TODO: receive a JSON string from the service and parse it, then display parcels
        Log.d("PromptHelpRequest", "${intent.getStringExtra("ParcelsToPickUp")}")

        //region Attach adapter to RecyclerList
        val parcels = listOf(
            Parcel("NV2001101", "313 Somerset Rd"),
            Parcel("AMZ234512", "500 Airport Rd")
        )

        // Update these two number fields when the selection set is updated.

        val parcelListAdapter = ParcelListAdapter(parcels)
        findViewById<RecyclerView>(R.id.parcelReceiverRecyclerView).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = parcelListAdapter
        }
        findViewById<TextView>(R.id.promptExistingCount).text = 10.toString()
        findViewById<TextView>(R.id.promptReceivingCount).text = parcels.size.toString()
        //endregion

        findViewById<Button>(R.id.btnIgnorePrompt).setOnClickListener {
            finish()
        }
    }
}