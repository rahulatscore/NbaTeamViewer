package com.jaregier.nbateamviewer.ui.teams

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jaregier.nbateamviewer.R
import com.jaregier.nbateamviewer.data.network.Team
import kotlinx.android.synthetic.main.layout_team.view.*

typealias TeamClickListener = (teamId: Int) -> Unit

class TeamListAdapter(private val teamClickListener: TeamClickListener) : RecyclerView.Adapter<TeamViewHolder>() {

    var teams: List<Team> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            TeamViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_team, parent, false), teamClickListener)

    override fun getItemCount() = teams.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) = holder.bind(teams[position])

}

class TeamViewHolder(private val view: View, private val teamClickListener: TeamClickListener) : RecyclerView.ViewHolder(view) {
    fun bind(team: Team) {
        view.team_name.text = team.fullName

        val winsLossesString = when {
            team.wins != null && team.losses != null -> "${team.wins} - ${team.losses}"
            else -> null
        }

        view.wins_losses_textview.text = winsLossesString

        view.team_layout.setOnClickListener {
            team.id?.let {teamId ->
                teamClickListener.invoke(teamId)
            }
        }
    }
}
