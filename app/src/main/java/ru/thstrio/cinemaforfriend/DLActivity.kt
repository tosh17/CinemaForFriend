package ru.thstrio.cinemaforfriend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.thstrio.cinemaforfriend.firebase.dynamiclink.FDynamicLink

class DLActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d_l)
        FDynamicLink(intent,this).subscribe { uri->
            Log.d("TExt","url  "+uri.toString())
        }

    }
}
