package team.stonks.buzoku.ui.clan

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import team.stonks.buzoku.leaderboards.Clan
import team.stonks.buzoku.leaderboards.ClanMember

class ClanService {
    /**
     * Get details about a clan.
     * @param id: The ID of the clan to get.
     * @param callback: The callback to call with the Clan object, once the network call is done.
     */
    fun getClan(clan_name: String, context: Context, callback: (Clan) -> Unit) {

        val queue = Volley.newRequestQueue(context)
        val url = "https://ninja-van-stonks.uc.r.appspot.com/calc_clan_scores/$clan_name"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val clanScore = response.getDouble("clanScore")
                val members = response.getJSONObject("drivers_rankings")
                var clanMembers = arrayListOf<ClanMember>()
                val memberKeys = members.keys()
                for (key in memberKeys) {
                    val name = members.get(key).toString()
                    clanMembers.add(ClanMember(name, key.toInt()))
                }
                val clan = Clan(clan_name, clanScore, clanMembers)
                callback(clan)
                Log.e("DEBUG", "CLAN $clan $clanMembers")
            },
            { error ->
                error.printStackTrace()
            }
        )
        queue.add(request)

    }

    fun getAllClans(context: Context, callback: (ArrayList<Clan>) -> Unit) {

        val queue = Volley.newRequestQueue(context)
        val url = "https://ninja-van-stonks.uc.r.appspot.com/calc_all_clan_scores/"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val members = response.getJSONObject("sorted_clan_dict")
                var allClans = arrayListOf<Clan>()
                val memberKeys = members.keys()
                for (key in memberKeys) {
                    val name = members.get(key).toString()
                    allClans.add(Clan(name, key.toDouble()))
                }
                callback(allClans)
                Log.e("DEBUG", "CLAN $allClans $memberKeys")
            },
            { error ->
                error.printStackTrace()
            }
        )
        queue.add(request)

    }
}