package android.example.com.movies;

import android.app.Activity;
import android.content.SharedPreferences;
import android.example.com.movies.database.MoviesContract;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

/**
 * Created by Pritam on 15-12-2017.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements
        SharedPreferences.OnSharedPreferenceChangeListener{
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_movies);

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen =getPreferenceScreen();
        int count = preferenceScreen.getPreferenceCount();
        for(int i =0 ; i<count;i++)
        {
            Preference p=preferenceScreen.getPreference(i);
            if(!(p instanceof CheckBoxPreference))
            {
                String value = sharedPreferences.getString(p.getKey(),"");
                setPreferenceSummary(p, value);
            }
        }
    }

    private void setPreferenceSummary(Preference preference,String value)
    {
        if(preference instanceof ListPreference)
        {
            ListPreference listPreference =(ListPreference) preference;
            int preIndex = listPreference.findIndexOfValue(value);
            if(preIndex >= 0)
            {
                listPreference.setSummary(listPreference.getEntries()[preIndex]);
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if( preference != null)
        {
            if(!(preference instanceof CheckBoxPreference))
            {
                String value = sharedPreferences.getString(preference.getKey(),"");
                setPreferenceSummary(preference,value);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
