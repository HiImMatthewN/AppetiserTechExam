package com.nantesmatthew.appetisertechexam

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.nantesmatthew.appetisertechexam.databinding.ActivityMainBinding
import com.nantesmatthew.movie.presentation.MoviesFragmentDirections
import com.nantesmatthew.user_session.domain.model.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binder: ActivityMainBinding
    private val viewModelMain by viewModels<MainViewModel>()
    companion object{
        val IS_SCREEN_RESTORED = "IsScreenRestored"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binder = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binder.root)



        //Only Restore Last Visited on startup/restore
        //Check if there's a saved state to prevent restoring on rotating
        if (savedInstanceState == null){
            viewModelMain.getLastUserSession { userSession ->
                when (userSession.screenOpened) {
                    Screen.Category -> {

                    }
                    Screen.Detail -> {
                        val navHostFragment =
                            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                        navHostFragment.navController.navigate(
                            MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(
                                userSession.screenInfo,
                                null
                            )
                        )
                    }
                    Screen.Invalid -> {

                    }
                }

            }
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        //Save state on restore
        outState.putBoolean(IS_SCREEN_RESTORED,true)
    }


}