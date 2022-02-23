package team.stonks.buzoku.leaderboards

class Clan(val name: String, val members: ArrayList<ClanMember> = arrayListOf()) {
    var numberOfMembers = members.size

    var total = 0
    var result = 0

    fun points(): Int {
        for (i in members) {
            total += i.points
            result = total / members.size
        }
        return result
    }
}

