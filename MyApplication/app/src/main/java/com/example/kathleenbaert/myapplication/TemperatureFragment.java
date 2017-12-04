package com.example.kathleenbaert.myapplication;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by kathleenbaert on 11/1/17.
 */
public class TemperatureFragment extends Fragment {

    View myView;
    JSONData d;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate( R.layout.temperature_dash, container, false );
        myView.getId();
        final Context context = myView.getContext();

        final JSONData d = new JSONData();

        TextView firstTemp = myView.findViewById(R.id.textView1);
        firstTemp.setText("Celsius: " + Double.toString( d.dataInstance.getTempC() ) );
        TextView secondTemp = myView.findViewById(R.id.temp2);
        secondTemp.setText( "Fahrenheit: " + Double.toString( d.dataInstance.getTempF() ));

        Button fButton1 = myView.findViewById( R.id.fButton1 );
        Button cButton1 = myView.findViewById( R.id.cButton1 );
        // add button listener


        fButton1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set title
                alertDialogBuilder.setTitle("Sensor 1 Fahrenheit Recent Activity");

                double [] tempFArray = d.dataInstance.getTempFArray();
                String message = "";
                    for (int i = 0; i < tempFArray.length; i++) {
                        message += ("\n" + tempFArray[i]);
                    }


                alertDialogBuilder.setMessage(message);




                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });
        cButton1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set title
                alertDialogBuilder.setTitle("Sensor 1 Celcius Recent Activity");

                double [] tempCArray = d.dataInstance.getTempCArray();
                String message = "";
                for (int i = 0; i < tempCArray.length; i++) {
                    message += ("\n" + tempCArray[i]);
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
