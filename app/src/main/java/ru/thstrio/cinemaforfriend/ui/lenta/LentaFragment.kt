package ru.thstrio.cinemaforfriend.ui.lenta

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.me_fragment.view.*
import ru.thstrio.cinemaforfriend.App
import ru.thstrio.cinemaforfriend.R

class LentaFragment  : Fragment(){
    val counter=App.counter++

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view= inflater.inflate(R.layout.me_fragment,container,false)
        view.textView.text="Leeente " + counter
        return view
    }
}