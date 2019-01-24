package com.jaregier.nbateamviewer

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.jaregier.nbateamviewer.ui.TeamListAdapter
import com.jaregier.nbateamviewer.ui.TeamListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var teamListViewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: TeamListViewModel

    private val compositeDisposable = CompositeDisposable()

    private val teamListAdapter = TeamListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MyApplication.getInstance().getMyComponent().inject(this)

        viewModel = ViewModelProviders.of(this, teamListViewModelFactory)[TeamListViewModel::class.java]
        viewModel.fetchTeams()

        setContentView(R.layout.activity_main)

        teams_recycler_view.adapter = teamListAdapter
        teams_recycler_view.layoutManager = LinearLayoutManager(this)

        setSupportActionBar(toolbar)
    }

    override fun onResume() {
        super.onResume()

        compositeDisposable.addAll(viewModel.teamsSubject
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe { teams ->
                    teams_recycler_view.post { teamListAdapter.teams = teams }
                })
    }

    override fun onPause() {
        super.onPause()

        compositeDisposable.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
