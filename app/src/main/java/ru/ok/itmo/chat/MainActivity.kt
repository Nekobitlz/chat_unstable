package ru.ok.itmo.chat

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.ok.itmo.chat.features.message.presentation.view.MessagesFragment
import java.lang.ref.WeakReference

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MessagesFragment.newFragment("1@ch"))
                .commit()
        }
    }
}

object Router {

    private var activity = WeakReference<AppCompatActivity>()

    fun onAttached(activity: Activity)
    fun onDetached(activity: Activity)

    fun navigate(fragment: Fragment) = activity.get().supportFragmentManager.beginTransaction()
        .replace(R.id.container, fragment)
        .commit()


}