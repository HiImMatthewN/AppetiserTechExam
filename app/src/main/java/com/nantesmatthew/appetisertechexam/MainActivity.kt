package com.nantesmatthew.appetisertechexam

import android.os.Bundle
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binder = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binder.root)


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

    override fun onDestroy() {
        super.onDestroy()

    }
}