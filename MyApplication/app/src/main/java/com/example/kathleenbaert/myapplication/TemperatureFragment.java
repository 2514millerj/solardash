package com.example.kathleenbaert.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
/**
 * Created by kathleenbaert on 11/1/17.
 */
public class TemperatureFragment extends Fragment {

    View myView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate( R.layout.temperature_dash, container, false );
        myView.getId();

        
        return myView;
    }


}
