package com.example.andriod.tmdb;

import android.os.Bundle;
import android.text.InputType;

import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class MySettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.prefscreen, rootKey);

        EditTextPreference editPref = findPreference("et_pageNumber");
        ListPreference listPreference = findPreference("listPrefSortBy");
        listPreference.setSummaryProvider(ListPreference.SimpleSummaryProvider.getInstance());

        listPreference.setOnPreferenceChangeListener(this);

        if(editPref!=null){
            editPref.setOnBindEditTextListener(
                    editText -> editText.setInputType(InputType.TYPE_CLASS_NUMBER));
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

//        String stringValue = newValue.toString();
//        int intNewValue = Integer.parseInt(stringValue);
//        Log.e("String Value: ",stringValue);
//
//        SharedPreferences sharedPreferences = preference.getSharedPreferences();



//         sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);

//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt(preference.getKey(),intNewValue);
//        editor.commit();

//        preference.setSummaryProvider(EditTextPreference.SimpleSummaryProvider.getInstance());
//        SharedPreferences.Editor editor =

//        SharedPreferences.Editor prefsEditr = ;
//        prefsEditr.putString(preference.getKey(), stringValue);
//        prefsEditr.commit();

        return true;
    }
}