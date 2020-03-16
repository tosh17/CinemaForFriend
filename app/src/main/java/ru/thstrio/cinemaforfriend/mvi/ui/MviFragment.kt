package ru.thstrio.cinemaforfriend.mvi.ui

import android.widget.Toast
import androidx.fragment.app.Fragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import ru.thstrio.cinemaforfriend.mvi.entity.MviNews
import ru.thstrio.cinemaforfriend.mvi.entity.MviState
import ru.thstrio.cinemaforfriend.mvi.entity.MviUiEvent
import ru.thstrio.cinemaforfriend.mvi.model.MviModel

abstract class MviFragment<S : MviState, E : MviUiEvent, N : MviNews> : Fragment(), MviUi<S, E, N> {

    abstract fun getModel(): MviModel<S, E, N>
    var mIntent: Disposable? = null
    var mNews: Disposable? = null

    override fun onNextEvent(eventUI: E) {
        getModel().onNextUiEvent(eventUI)
    }

    override fun onResume() {
        val (intent, news) = getModel().subscribe()
        mIntent = intent.observeOn(AndroidSchedulers.mainThread())
            .subscribe { state -> onUpdateUi(state) }
        mNews = news.observeOn(AndroidSchedulers.mainThread())
            .subscribe { news -> onNextNews(news) }
        super.onResume()
    }

    override fun onPause() {
        mIntent?.dispose()
        mNews?.dispose()
        super.onPause()
    }

    fun printToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}