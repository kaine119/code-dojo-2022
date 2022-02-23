package team.stonks.buzoku.ui.clan

import android.os.Handler
import team.stonks.buzoku.leaderboards.Clan

class ClanService {
    /**
     * Get details about a clan.
     * @param id: The ID of the clan to get.
     * @param callback: The callback to call with the Clan object, once the network call is done.
     */
    fun getClan(id: String, callback: (Clan) -> Unit) {
        // TODO: Add actual network code here
        val handler = Handler()
        handler.postDelayed({
            callback(Clan("Eastern Tigers"))
        }, 1000)
    }
}