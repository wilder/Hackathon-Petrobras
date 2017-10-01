package wilderpereira.com.brbox

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

/**
 * Created by Wilder on 30/09/17.
 */
/**
 * Manages dealing with SharedPreferences
 */
class PreferencesManager (context: Context) {
    val PREFS_FILENAME = "neo.prefs"
    val USER_ID = "user_obj"
    val INTRO_KEY = "intro_key"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0);

    var user: User
        get() = Gson().fromJson(prefs.getString(USER_ID, Gson().toJson(null)), User::class.java)
        set(user) = prefs.edit().putString(USER_ID, Gson().toJson(user)).apply()


    var haveSeenIntro: Boolean
        get() = prefs.getBoolean(INTRO_KEY, false)
        set(value) = prefs.edit().putBoolean(INTRO_KEY, value).apply()

}