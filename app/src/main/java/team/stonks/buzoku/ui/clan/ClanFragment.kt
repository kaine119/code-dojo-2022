package team.stonks.buzoku.ui.clan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.transition.MaterialElevationScale
import team.stonks.buzoku.R
import team.stonks.buzoku.ui.leaderboard.LeaderboardFragment
import team.stonks.buzoku.ui.requestForHelp.RequestForHelpActivity

class SmallLeaderboardAdapter(val entries: List<String>) :
    RecyclerView.Adapter<SmallLeaderboardAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtPosition: TextView = itemView.findViewById(R.id.txtLeaderboardPositionSmall)
        val icnMedal: ImageView = itemView.findViewById(R.id.icnLeaderboardMedalSmall)
        val txtName: TextView = itemView.findViewById(R.id.txtLeaderboardNameSmall)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layout = inflater.inflate(R.layout.leaderboard_entry_small, parent, false)
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtPosition.text = (position + 1).toString()
        holder.txtName.text = entries[position]
        holder.icnMedal.setImageResource(
            when (position) {
                0 -> R.drawable.bookmark_gold
                1 -> R.drawable.bookmark_silver
                2 -> R.drawable.bookmark_bronze
                else -> R.drawable.ic_baseline_bookmark_24
            }
        )
    }

    override fun getItemCount(): Int = entries.size
}

/**
 * A simple [Fragment] subclass.
 * Use the [ClanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClanFragment : Fragment() {

    var layout: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_clan, container, false)

        // Launch activity for requestForHelp
        // TODO: should this be an activity or a fragment?
        layout?.findViewById<Button>(R.id.btn_request_help)?.setOnClickListener {
            val intent = Intent(activity, RequestForHelpActivity::class.java)
            startActivity(intent)
        }

        // These TextViews can be programmatically changed before returning the view to be rendered
        val clanName: TextView? = layout?.findViewById(R.id.ClanName)
        val clanPosition: TextView? = layout?.findViewById(R.id.ClanPosition)
        val clanMemberCount: TextView? = layout?.findViewById(R.id.ClanMemberCount)


        ClanService().getClan("Eastern Tigers", requireContext()) {
            clanName?.text = it.name
            clanPosition?.text = "#1 in Singapore"
            clanMemberCount?.text = "${it.numberOfMembers} members"

            val clanLeaderboard = it.members.map { clanMember ->
                Pair(clanMember.points.toDouble(), clanMember.firstName)
            }.sortedBy { entry -> entry.first }

            val clanLeaderboardCard: MaterialCardView? =
                layout?.findViewById(R.id.cardClanLeaderboard)

            clanLeaderboardCard?.setOnClickListener {
                val fragment =
                    LeaderboardFragment.newInstance("Your Clan", clanLeaderboard)
                parentFragmentManager.commit {
                    replace(R.id.nav_host_fragment_content_main, fragment)
                    addToBackStack(null)
                }
            }

            val smallClanLeaderboard = clanLeaderboard
                .sortedBy { it.first }
                .reversed()
                .map { it.second }
                .take(3)

            val smallClanAdapter = SmallLeaderboardAdapter(smallClanLeaderboard)
            layout?.findViewById<RecyclerView>(R.id.clanLeaderboard)?.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = smallClanAdapter
            }
        }

        ClanService().getAllClans(requireContext()) {

            val countryLeaderboard = it.map { clan ->
                Pair(clan.clanScore, clan.name)
            }.sortedBy { entry -> entry.first }

            val countryLeaderboardCard: MaterialCardView? =
                layout?.findViewById(R.id.cardCountryLeaderboard)

            countryLeaderboardCard?.setOnClickListener {
                val fragment =
                    LeaderboardFragment.newInstance("Clans in Singapore", countryLeaderboard)
                parentFragmentManager.commit {
                    replace(R.id.nav_host_fragment_content_main, fragment)
                    addToBackStack(null)
                }
            }

            val smallCountryLeaderboard = countryLeaderboard
                .sortedBy { it.first }
                .reversed()
                .map { it.second }
                .take(3)

            val smallCountryAdapter = SmallLeaderboardAdapter(smallCountryLeaderboard)

            layout?.findViewById<RecyclerView>(R.id.countryLeaderboard)?.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = smallCountryAdapter
            }


        }


        //region Leaderboard onClickListeners and RecyclerList Adaptors


        //endregion

        return layout
    }
}
