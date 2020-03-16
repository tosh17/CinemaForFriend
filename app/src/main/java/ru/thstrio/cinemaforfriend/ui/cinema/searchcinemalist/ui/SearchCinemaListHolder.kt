package ru.thstrio.cinemaforfriend.ui.cinema.searchcinemalist.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.search_cinema_list_item.view.*
import ru.thstrio.cinemaforfriend.R
import ru.thstrio.cinemaforfriend.api.tmdb.util.getTmDbImageLink500

class SearchCinemaListHolder(val view: View) : RecyclerView.ViewHolder(view) {
    fun setTitle(title:String){
        view.search_cinema_item_title.text =title
    }
    fun setLogo(url:String){
        Glide
            .with(view.context)
            .load(getTmDbImageLink500(url))
            .centerCrop()
            .placeholder(R.drawable.holder_cinema).error(R.drawable.error_image)
            .into(view.search_cinema_item_logo);
    }
 fun setLogoError(){
     view.search_cinema_item_logo.setImageResource(R.drawable.error_image)
 }
    fun setNumber(number:Int){
        view.search_cinema_item_number.text="#$number"
    }
    fun setDescription(text:String){ view.search_cinema_item_description.text=text}
    fun setRate(text:String){view.search_cinema_item_rate.text=text}
}