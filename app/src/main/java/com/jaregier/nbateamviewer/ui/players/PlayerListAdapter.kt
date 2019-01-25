package com.jaregier.nbateamviewer.ui.players

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jaregier.nbateamviewer.R
import com.jaregier.nbateamviewer.data.database.PlayerEntity
import kotlinx.android.synthetic.main.layout_player.view.*

class PlayerListAdapter : RecyclerView.Adapter<PlayerViewHolder>() {

    var players: List<PlayerEntity> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            PlayerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_player, parent, false))

    override fun getItemCount() = players.size

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) = holder.bind(players[position])

}

class PlayerViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(player: PlayerEntity) {
        view.player_name.text = StringBuilder().apply {
            player.firstName?.let {
                append(it)
                append(" ")
            }
            player.lastName?.let {
                append(it)
            }
        }

        view.player_position.text = player.position

        view.player_number.text = player.number?.toString()
    }
}
