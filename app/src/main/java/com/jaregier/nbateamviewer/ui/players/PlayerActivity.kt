package com.jaregier.nbateamviewer.ui.players

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.jaregier.nbateamviewer.MyApplication
import com.jaregier.nbateamviewer.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_player.*
import javax.inject.Inject

class PlayerActivity : AppCompatActivity() {

    companion object {
        private const val TEAM_ID = "TEAM_ID"

        fun start(context: Context, teamId: Int) {
            val intent = Intent(context, PlayerActivity::class.java)
            intent.putExtra(TEAM_ID, teamId)
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: PlayerViewModel

    private val compositeDisposable = CompositeDisposable()

    private val playerListAdapter = PlayerListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MyApplication.getInstance().getMyComponent().inject(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[PlayerViewModel::class.java]
        viewModel.teamId = intent.getIntExtra(TEAM_ID, 0)
        viewModel.fetchPlayers()
        viewModel.getTeam()

        setContentView(R.layout.activity_player)

        players_recycler_view.adapter = playerListAdapter
        players_recycler_view.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()

        compositeDisposable.addAll(viewModel.playersSubject
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    playerListAdapter.players = it
                }
        )

        compositeDisposable.addAll(viewModel.teamSubject
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    team_name.text = it.fullName
                }
        )
    }

    override fun onPause() {
        super.onPause()

        compositeDisposable.clear()
    }
}