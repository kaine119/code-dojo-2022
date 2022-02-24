package team.stonks.buzoku.ui.leaderboard

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import team.stonks.buzoku.R
import java.util.*
import kotlin.math.roundToInt

private const val ARG_LEADERBOARD_NAME = "leaderboardName"
private const val ARG_ENTRIES = "entries"

class LeaderboardAdapter(val entries: List<Pair<Double, String>>) :
    RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtPosition: TextView = itemView.findViewById(R.id.txtLeaderboardPosition)
        var txtName: TextView = itemView.findViewById(R.id.txtLeaderboardName)
        var txtScore: TextView = itemView.findViewById(R.id.txtLeaderboardScore)
        var icnMedal: ImageView = itemView.findViewById(R.id.icnLeaderboardMedal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layout = layoutInflater.inflate(R.layout.leaderboard_entry, parent, false)
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtPosition.text = (position + 1).toString()
        holder.txtName.text = entries[position].second
        holder.txtScore.text = entries[position].first.toString()
        holder.icnMedal.setImageResource(when (position) {
            0 -> R.drawable.bookmark_gold
            1 -> R.drawable.bookmark_silver
            2 -> R.drawable.bookmark_bronze
            else -> R.drawable.ic_baseline_bookmark_24
        })
    }

    override fun getItemCount(): Int = entries.size

}

/**
 * A simple [Fragment] subclass.
 * Use the [LeaderboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LeaderboardFragment : Fragment() {
    // TODO: Pass the items of the leaderboard down too
    private var leaderboardName: String? = null
    private lateinit var entries: List<Pair<Double, String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            leaderboardName = it.getString(ARG_LEADERBOARD_NAME)
            val type = object : TypeToken<List<Pair<Double, String>>>() {}.type
            entries = Gson().fromJson(it.getString(ARG_ENTRIES), type)
        }
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            scrimColor = Color.TRANSPARENT
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_leaderboard, container, false)
        val leaderboardTitle = layout.findViewById<TextView>(R.id.txtLeaderboardTitle)
        leaderboardTitle.text = leaderboardName

        val leaderboardAdapter = LeaderboardAdapter(entries)
        layout.findViewById<RecyclerView>(R.id.leaderboardRecyclerView).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = leaderboardAdapter
        }

        return layout
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param leaderboardName: The title of the leaderboard page.
         * @return A new instance of fragment LeaderboardFragment.
         */
        @JvmStatic
        fun newInstance(leaderboardName: String, entries: List<Pair<Double, String>>) =
            LeaderboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_LEADERBOARD_NAME, leaderboardName)
                    putString(ARG_ENTRIES, Gson().toJson(entries))
                }
            }
    }
}
