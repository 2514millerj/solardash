package com.example.kathleenbaert.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.UUID;

/**
 * Created by kathleenbaert on 11/28/17.
 */

public class ConnectThread extends Thread {
    private static final UUID MY_UUID = UUID.fromString("5d76e709-c798-4f49-9637-706a060a4152");
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private final BluetoothAdapter mmBluetoothAdapter;
    private JSONData jsonData;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private byte[] mmBuffer; // mmBuffer store for the stream

    private final String TAG = "connectThread";

    public ConnectThread(BluetoothDevice device, BluetoothAdapter adapter) {
        // Use a temporary object that is later assigned to mmSocket
        // because mmSocket is final.
        BluetoothSocket tmp = null;
        mmDevice = device;
        mmBluetoothAdapter = adapter;
        try {
            // Get a BluetoothSocket to connect with the given BluetoothDevice.
            // MY_UUID is the app's UUID string, also used in the server code.
            tmp =(BluetoothSocket) device.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(device,1);
            //= device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        mmSocket = tmp;

        // Cancel discovery because it otherwise slows down the connection.
        mmBluetoothAdapter.cancelDiscovery();

        Log.e(TAG, "trying to connect");
        try {
            mmSocket.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "connected");

        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and output streams; using temp objects because
        // member streams are final.
        try {
            tmpIn = mmSocket.getInputStream();
        } catch (IOException e) {
            Log.e(TAG, "Error occurred when creating input stream", e);
        }
        try {
            tmpOut = mmSocket.getOutputStream();
        } catch (IOException e) {
            Log.e(TAG, "Error occurred when creating output stream", e);
        }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;

    }

    public void run() {
        // The connection attempt succeeded. Perform work associated with
        // the connection in a separate thread.
        mmBuffer = new byte[1024];
        int numBytes; // bytes returned from read()
        String data = "";


        // Keep listening to the InputStream until an exception occurs.
        while (true) {
            mmBuffer = new byte[1024];

            try {
                // Read from the InputStream.
                numBytes = mmInStream.read(mmBuffer);
                // Send the obtained bytes to the UI activity.

                String incoming = new String(Arrays.copyOfRange(mmBuffer, 0, numBytes), "UTF-8");
                //System.out.println(incoming);
                int count = 0;
                boolean newPacket = false;
                for (byte b : mmBuffer) {
                    if (b == ']') {
                        JSONData insertData = new JSONData();
                        data = data + new String(Arrays.copyOfRange(mmBuffer, 0, count + 1), "UTF-8");
                        //System.out.println(data);
                        JSONArray jsonArray = new JSONArray(data);


                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject jsonObj = jsonArray.getJSONObject(i);
                            Iterator<String> keys = jsonObj.keys();
                            String key = keys.next().toString();

                            if (key.equals("current_sensor")) {
                                JSONObject currObj = (JSONObject) jsonObj.get( "current_sensor" );
                                insertData.dataInstance.setAmps( (Double) currObj.get( "amps" ) );
                                insertData.dataInstance.addToAmpArray(Double.parseDouble( String.valueOf( currObj.get("amps" ) ) ));
                            }
                            else if(key.equals( "voltage_sensor" )){
                                JSONObject voltObj = (JSONObject) jsonObj.get( "voltage_sensor" );
                                insertData.dataInstance.setVoltage( (Double) voltObj.get( "voltage" ));
                                insertData.dataInstance.addToVoltArray(Double.parseDouble( String.valueOf( voltObj.get("voltage" ) ) ));

                            }
                            else if(key.equals( "temperature_sensor" )){
                                JSONObject tempObj = (JSONObject) jsonObj.get( "temperature_sensor" );
                                insertData.dataInstance.setTempC(Double.parseDouble( String.valueOf( tempObj.get("degreesC" ) ) ));
                                insertData.dataInstance.setTempF(Double.parseDouble( String.valueOf( tempObj.get("degreesF" ) ) ));
                                insertData.dataInstance.addToTempFArray(Double.parseDouble( String.valueOf( tempObj.get("degreesF" ) ) ));
                                insertData.dataInstance.addToTempCArray(Double.parseDouble( String.valueOf( tempObj.get("degreesC" ) ) ));

                            }
                        }

                        data = new String(Arrays.copyOfRange(mmBuffer, count + 1, numBytes), "UTF-8");
                        newPacket = true;
                    }
                    count++;
                }

                if (newPacket == false) {
                    data = data + new String(Arrays.copyOfRange(mmBuffer, 0, numBytes), "UTF-8");
                }

            } catch (IOException e) {
                Log.d(TAG, "Input stream was disconnected", e);
                cancel();
                break;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    // Closes the client socket and causes the thread to finish.
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not close the client socket", e);
        }
    }

    public void manageMyConnectedSocket(BluetoothSocket socket) {


    }
}