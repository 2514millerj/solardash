package com.example.kathleenbaert.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by kathleenbaert on 11/1/17.
 */
public class BlueToothFragment extends Fragment {
    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate( R.layout.bluetooth_dash, container, false );
        super.onCreate( savedInstanceState );


        return myView;
    }
}
