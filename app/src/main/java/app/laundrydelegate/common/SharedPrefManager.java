package app.laundrydelegate.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import app.laundrydelegate.models.users.DelegatedData;

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mCtx;
    private static final String SHARED_NAMES = "LuandrySharedPref";
    private static final String SHARED_PREF_NAME = "myshared";

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void userLogin(DelegatedData userData) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userData);
        editor.putString("MyObject", json);
        editor.apply();

    }

    public String addUserData() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("MyObject", null);

    }

    public DelegatedData getUserData() {
        Gson gson = new Gson();
        String json = addUserData();
        DelegatedData obj = gson.fromJson(json, DelegatedData.class);
        return obj;
    }

    public boolean loggout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
    public void saveToken(String token) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("TOKEN", token);
        editor.apply();

    }

    public String getToken() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("TOKEN", null);

    }
    public   void setLanguage(Context context, String language) {
        SharedPreferences userDetails = context.getSharedPreferences("languageData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userDetails.edit();
        editor.putString("language", language);
        editor.putBoolean("haveLanguage", true);
        editor.apply();
    }

    public   String getCurrentLanguage(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("languageData", Context.MODE_PRIVATE);
        if (!sharedPreferences.getBoolean("haveLanguage", false)) return "en";
        return sharedPreferences.getString("language", "en");
    }
}
