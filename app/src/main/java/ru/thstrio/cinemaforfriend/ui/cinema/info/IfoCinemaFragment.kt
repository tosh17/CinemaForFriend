package ru.thstrio.cinemaforfriend.ui.cinema.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.me_fragment.view.*
import ru.thstrio.cinemaforfriend.App
import ru.thstrio.cinemaforfriend.R

class IfoCinemaFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view= inflater.inflate(R.layout.info_cinema_fragment,container,false)

        return view
    }
}