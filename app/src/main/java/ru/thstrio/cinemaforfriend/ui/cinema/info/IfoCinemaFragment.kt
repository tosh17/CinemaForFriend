package ru.thstrio.cinemaforfriend.ui.cinema.info

import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.activityViewModels
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import io.reactivex.disposables.Disposable

import kotlinx.android.synthetic.main.info_cinema_fragment.view.*
import kotlinx.android.synthetic.main.me_fragment.view.*
import kotlinx.android.synthetic.main.search_cinema_list_item.view.*
import ru.thstrio.cinemaforfriend.App
import ru.thstrio.cinemaforfriend.R
import ru.thstrio.cinemaforfriend.api.tmdb.util.getTmDbImageLink500
import ru.thstrio.cinemaforfriend.mvi.model.MviModel
import ru.thstrio.cinemaforfriend.mvi.ui.MviFragment
import ru.thstrio.cinemaforfriend.ui.cinema.info.mvi.InfoCinemaEffectAction
import ru.thstrio.cinemaforfriend.ui.cinema.info.mvi.InfoCinemaState
import ru.thstrio.cinemaforfriend.ui.cinema.info.mvi.InfoCinemaUiEvent
import ru.thstrio.cinemaforfriend.ui.cinema.info.mvi.InfoCinemaViewModal

class IfoCinemaFragment : MviFragment<InfoCinemaState, InfoCinemaUiEvent, InfoCinemaEffectAction.InfoCinemaNews>() {
    companion object {
        val argDataName = "id"
    }

    val model by activityViewModels<InfoCinemaViewModal>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.info_cinema_fragment, container, false)
        val id = requireArguments().getLong(argDataName)
        onNextEvent(InfoCinemaUiEvent.LoadInfoCinema(id))
        return view
    }

    override fun getModel(): MviModel<InfoCinemaState, InfoCinemaUiEvent, InfoCinemaEffectAction.InfoCinemaNews> = model

    override fun onUpdateUi(state: InfoCinemaState) {
        Log.d("TExt", "cinema " + state.cinema.toString())
        if (state.cinema == null) return
        val cinema = state.cinema!!
        requireView().run {
            info_cinema_title.text = cinema.title
            info_cinema_data.text = "(${cinema.releaseDate.split("-")[0]})"
            info_cinema_description.text = cinema.overview
            cinema.posterPath?.let { path ->
                Glide
                    .with(context).asBitmap()
                    .load(getTmDbImageLink500(path))
                    .listener(colorCalc)
                    .centerCrop()
                    .placeholder(R.drawable.holder_cinema).error(R.drawable.error_image)
                    .into(info_cinema_logo)

            }


        }
    }

    val colorCalc = object : RequestListener<Bitmap> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Bitmap>?,
            isFirstResource: Boolean
        ): Boolean {
            return false
        }

        override fun onResourceReady(
            resource: Bitmap,
            model: Any?,
            target: Target<Bitmap>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            Palette.from(resource).generate { palette ->
                palette?.dominantSwatch?.let {
                    changeCollor(it)
                }
            }
            return false
        }

    }

    private fun changeCollor(color: Palette.Swatch) {
        view?.run {
            info_cinema_share.setColorFilter(color.titleTextColor, PorterDuff.Mode.MULTIPLY)
            info_cinema_bg2.setColorFilter(color.rgb, PorterDuff.Mode.MULTIPLY)
            info_cinema_title.setTextColor(color.titleTextColor)
            info_cinema_description.setTextColor(color.bodyTextColor)
        }

    }

    override fun onNextNews(news: InfoCinemaEffectAction.InfoCinemaNews) = when(news){
        is InfoCinemaEffectAction.InfoCinemaNews.showEroor -> TODO()
    }

}