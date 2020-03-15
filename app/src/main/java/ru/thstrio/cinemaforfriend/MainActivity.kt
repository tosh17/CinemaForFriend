package ru.thstrio.cinemaforfriend

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.get
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.thstrio.cinemaforfriend.navigation.NavRouter
import ru.thstrio.cinemaforfriend.navigation.Screens

class MainActivity : FragmentActivity(){
    lateinit var router: NavRouter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        router = get()
       if(savedInstanceState==null) router.initChain()
    }

    override fun onResume() {
        super.onResume()
        router.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        router.removeNavigator()
    }

    private val navigator = SupportAppNavigator(this, R.id.mainContainer)
}
