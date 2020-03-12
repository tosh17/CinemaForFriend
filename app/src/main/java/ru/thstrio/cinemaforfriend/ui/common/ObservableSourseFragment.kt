package ru.thstrio.cinemaforfriend.ui.common

import androidx.fragment.app.Fragment
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject

open class ObservableSourseFragment<T> : Fragment(), ObservableSource<T> {
    private val source = PublishSubject.create<T>()

    protected fun onNext(t: T) {
        source.onNext(t)
    }
    override fun subscribe(observer: Observer<in T>) {
        source.subscribe(observer)
    }
}