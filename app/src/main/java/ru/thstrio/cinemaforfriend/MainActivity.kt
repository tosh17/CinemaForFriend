package ru.thstrio.cinemaforfriend

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.get
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.thstrio.cinemaforfriend.navigation.NavRouter
import ru.thstrio.cinemaforfriend.navigation.Screens

class MainActivity : AppCompatActivity() {
    lateinit var router: NavRouter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        router = get()
        router.setNavigator(navigator)
        router.initChain()

        button1.setOnClickListener { router.goTo(Screens.Me) }
        button2.setOnClickListener { router.goTo(Screens.Lenta) }
    }

    private val navigator = SupportAppNavigator(this, R.id.mainContainer)
}
