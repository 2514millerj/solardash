package com.example.kathleenbaert.myapplication;

import android.app.AlertDialog;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by kathleenbaert on 11/1/17.
 */

public class VoltageFragment extends Fragment {

    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.voltage_dash, container, false);

        final Context context = myView.getContext();

        final JSONData d = new JSONData();

        TextView firstAmp = myView.findViewById(R.id.volt1);
        firstAmp.setText("Amps: " + Double.toString( d.dataInstance.getAmps() ) );

        final Button voltButton1 = myView.findViewById( R.id.voltButton1);

        voltButton1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set title
                alertDialogBuilder.setTitle("Sensor 1 Amps Recent Activity");

                double [] ampsArray = d.dataInstance.getAmpsArray();
                String message = "";
                for (int i = 0; i < ampsArray.length; i++) {
                    message += ("\n" + ampsArray[i]);
                }


                alertDialogBuilder.setMessage(message);




                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });
        return myView;
    }
}
