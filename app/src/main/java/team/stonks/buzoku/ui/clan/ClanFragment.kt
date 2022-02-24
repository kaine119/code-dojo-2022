package team.stonks.buzoku.ui.clan

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.fragment.app.commit
import com.google.android.material.card.MaterialCardView
import com.google.android.material.transition.MaterialElevationScale
import team.stonks.buzoku.R
import team.stonks.buzoku.ui.leaderboard.LeaderboardFragment
import team.stonks.buzoku.ui.requestForHelp.RequestForHelpActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ClanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClanFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var layout: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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

        ClanService().getClan("hello") {
            clanName?.text = it.name
            clanPosition?.text = "#1 in Singapore"
            clanMemberCount?.text = "${it.numberOfMembers} members"
        }

        val clanLeaderboardTitle: TextView? = layout?.findViewById(R.id.txtClanLeaderboardTitle)
        val clanLeaderboardCard: MaterialCardView? = layout?.findViewById(R.id.cardClanLeaderboard)
        val countryLeaderboardTitle: TextView? = layout?.findViewById(R.id.txtCountryLeaderboardTitle)
        val countryLeaderboardCard: MaterialCardView? = layout?.findViewById(R.id.cardCountryLeaderboard)

        if (clanLeaderboardTitle != null && clanLeaderboardCard != null) {
            clanLeaderboardCard.setOnClickListener {
                val fragment = LeaderboardFragment.newInstance("Your Clan", listOf(Pair(89.0f, "Europe")))
                parentFragmentManager.commit {
//                    addSharedElement(clanLeaderboardTitle, "leaderboardTitle")
                    replace(R.id.nav_host_fragment_content_main, fragment)
                    addToBackStack(null)
                }
            }
        }

        if (countryLeaderboardTitle != null && countryLeaderboardCard != null) {
            countryLeaderboardCard.setOnClickListener {
                val fragment = LeaderboardFragment.newInstance("Clans in Singapore", listOf(Pair(89.0f, "Europe")))
                parentFragmentManager.commit {
//                    addSharedElement(countryLeaderboardTitle, "countryLeaderboardTitle")
                    replace(R.id.nav_host_fragment_content_main, fragment)
                    addToBackStack(null)
                }
            }
        }


        return layout
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ClanFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClanFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}