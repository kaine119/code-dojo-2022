package team.stonks.buzoku.ui.clan

import android.content.Context
import android.os.Handler
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

import team.stonks.buzoku.leaderboards.Clan
import team.stonks.buzoku.leaderboards.ClanMember

class ClanService {
    /**
     * Get details about a clan.
     * @param id: The ID of the clan to get.
     * @param callback: The callback to call witgh the Clan object, once the network call is done.
     */
    fun getClan(clan_name: String, context :Context, callback: (Clan) -> Unit) {
        val handler = Handler()

        // Make a simple request with volley
        val queue = Volley.newRequestQueue(context)
        val url = "https://ninja-van-stonks.uc.r.appspot.com/calc_clan_scores/$clan_name"
        // make the request and parse json into Clan object
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response -> {
                    val clanScore = response.getDouble("clanScore")
                    val members = response.getJSONObject("driver_rankings")
                    var clanMembers = arrayListOf<ClanMember>()
                    val memberKeys = members.names()
                    for (i in 0..members.length()) {
                        val key = memberKeys.getInt(i)
                        val name = members.getString(key.toString())
                        clanMembers.add(ClanMember(name, key))
                    }
                    val clan = Clan(clan_name,clanScore,clanMembers)
                    callback(clan)
                }


            },
            { error ->
                error.printStackTrace()
            }
        )
        queue.add(request)


    }
}
