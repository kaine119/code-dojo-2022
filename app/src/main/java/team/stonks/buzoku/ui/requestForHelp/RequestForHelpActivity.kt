package team.stonks.buzoku.ui.requestForHelp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import team.stonks.buzoku.R
import team.stonks.buzoku.helpRequests.Parcel

data class SelectableParcel(var value: Parcel, var selected: Boolean)

class ParcelListAdapter(
    var parcelList: List<SelectableParcel>,
    val selectionChangeCallback: (List<SelectableParcel>) -> Unit
) :
    RecyclerView.Adapter<ParcelListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var parcelTrackingNumberText: TextView = itemView.findViewById(R.id.parcelTrackingNumber)
        var parcelAddressText: TextView = itemView.findViewById(R.id.parcelAddress)
        var parcelSelectedCheckBox: CheckBox = itemView.findViewById(R.id.parcelSelectedCheckbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.parcel_item_view, parent, false)
        selectionChangeCallback(parcelList)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val parcel = parcelList[position]
        parcel.value.getInfo {
            holder.parcelTrackingNumberText.text = parcel.value.trackingNumber
            holder.parcelAddressText.text = parcel.value.address
        }

        holder.parcelSelectedCheckBox.isSelected = parcel.selected

        holder.itemView.setOnClickListener {
            holder.parcelSelectedCheckBox.toggle()
            parcel.selected = !parcel.selected
            selectionChangeCallback(parcelList)
        }

        holder.parcelSelectedCheckBox.setOnClickListener {
            parcel.selected = !parcel.selected
            selectionChangeCallback(parcelList)
        }
    }

    override fun getItemCount() = parcelList.size

}

class RequestForHelpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_for_help)

        //region Attach adapter to RecyclerList
        val parcels = listOf(
            Parcel("NLSGD298572184"),
            Parcel("SHPM24495818742")
        )
        val parcelModels = parcels.map { SelectableParcel(it, false) }

        // Update these two number fields when the selection set is updated.
        val dropOffCountText: TextView = findViewById(R.id.txtDropOffCount)
        val remainingCountText: TextView = findViewById(R.id.txtRemainingCount)

        val parcelListAdapter = ParcelListAdapter(parcelModels) { parcels ->
            // This callback gets called when the selection set of the list changes.
            dropOffCountText.text = "${parcels.filter { it.selected }.count()} parcels"
            remainingCountText.text = "${parcels.filter { !it.selected }.count()} parcels"
        }
        findViewById<RecyclerView>(R.id.parcelRecyclerView).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = parcelListAdapter
        }
        //endregion

        //region Populate pickup selector
        val pickupSelector: TextInputLayout = findViewById(R.id.selection_pickup)
        val pickupMenuItems = listOf(
            getString(R.string.pickup_my_location),
            getString(R.string.pickup_halfway),
            getString(R.string.pickup_their_location)
        )
        val pickupMenuAdapter = ArrayAdapter(baseContext, R.layout.list_item, pickupMenuItems)
        (pickupSelector.editText as? AutoCompleteTextView)?.setAdapter(pickupMenuAdapter)
        (pickupSelector.editText as? AutoCompleteTextView)?.setText(pickupMenuItems[0], false)
        //endregion

        findViewById<Button>(R.id.btnSubmitRequest).setOnClickListener {
            // Look for parcel that have been selected for transfer
            val selectedParcels: List<Parcel> =
                parcelListAdapter.parcelList.filter { it.selected }.map { it.value }

            // TODO: find a less hacky dropdown! This is really jank
            val pickupSelection = (pickupSelector.editText)?.text.toString()

            RequestForHelpService().submitRequest(selectedParcels, pickupSelection)
        }
    }
}