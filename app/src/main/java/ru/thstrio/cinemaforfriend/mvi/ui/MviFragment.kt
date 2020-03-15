package ru.thstrio.cinemaforfriend.mvi.ui

import androidx.fragment.app.Fragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import ru.thstrio.cinemaforfriend.mvi.entity.MviState
import ru.thstrio.cinemaforfriend.mvi.entity.MviUiEvent
import ru.thstrio.cinemaforfriend.mvi.model.MviModel

abstract class MviFragment<S: MviState,E: MviUiEvent> : Fragment(),MviUi<S,E> {
    abstract fun getModel(): MviModel<S,E>

    var mIntent: Disposable? = null

    override fun onNextEvent(eventUI: E) {
        getModel().onNextUiEvent(eventUI)
    }
    override fun onResume() {
        mIntent = getModel().subscribe().observeOn(AndroidSchedulers.mainThread())
            .subscribe { state -> onUpdateUi(state) }
        super.onResume()
    }

    override fun onPause() {
        mIntent?.dispose()
        super.onPause()
    }
}