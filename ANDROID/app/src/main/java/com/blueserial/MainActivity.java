/*
 * Released under MIT License http://opensource.org/licenses/MIT
 * Copyright (c) 2013 Plasty Grove
 * Refer to file LICENSE or URL above for full text 
 */

package com.blueserial;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class MainActivity extends Activity {
    private static final String TAG = "BlueTest5-MainActivity";
    private int mMaxChars = 50000;//Default
    private UUID mDeviceUUID;
    private BluetoothSocket mBTSocket;
    private ReadInput mReadThread = null;

    private boolean mIsUserInitiatedDisconnect = false;

    // All controls here
    private TextView mTxtReceive;
    private EditText mEditSend;
    private Button mBtnDisconnect;
    private Button pattern1Btn;
    private Button pattern2Btn;
    private Button pattern3Btn;
    private Button pattern4Btn;
    private Button pattern5Btn;
    private Button mBtnTurnOff;
    private Button mBtnClearInput;
    private ScrollView scrollView;
    private CheckBox chkScroll;
    private CheckBox chkReceiveText;

    private boolean mIsBluetoothConnected = false;

    private BluetoothDevice mDevice;

    private ProgressDialog progressDialog;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public void sendMessage(String s) {
        try {
            mBTSocket.getOutputStream().write(s.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void setOnClickForCHeckBoxes(){

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityHelper.initialize(this);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        mDevice = b.getParcelable(Homescreen.DEVICE_EXTRA);
        mDeviceUUID = UUID.fromString(b.getString(Homescreen.DEVICE_UUID));
        mMaxChars = b.getInt(Homescreen.BUFFER_SIZE);

        Log.d(TAG, "Ready");

        mBtnDisconnect = (Button) findViewById(R.id.btnDisconnect);
        pattern1Btn = (Button) findViewById(R.id.pattern1Button);
        pattern2Btn = (Button) findViewById((R.id.pattern2Button));
        pattern3Btn = (Button) findViewById(R.id.pattern3Button);
        pattern4Btn = (Button) findViewById(R.id.pattern4Button);
        pattern5Btn = (Button) findViewById(R.id.pattern5Button);

        mBtnTurnOff = (Button) findViewById(R.id.allOffButton);
    /*	mTxtReceive = (TextView) findViewById(R.id.txtReceive);
        mEditSend = (EditText) findViewById(R.id.editSend);
		scrollView = (ScrollView) findViewById(R.id.viewScroll); */
        chkScroll = (CheckBox) findViewById(R.id.chkScroll);
        chkReceiveText = (CheckBox) findViewById(R.id.chkReceiveText);
        mBtnClearInput = (Button) findViewById(R.id.btnClearInput);

        mBtnDisconnect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsUserInitiatedDisconnect = true;
                new DisConnectBT().execute();
            }
        });
        sendMessage("BEGIN");
        /* TODO REFERENCE FOR FUTURE ME, IT'S LATE AND I JUST WANNA PUSH MY CHANGES
        * So here's the deal, all this below has to be rewritten. The Presets can stay, but for individual LEDs, it has to be more complex
        * So each LED has an ID of 00-77 radix 8, once that box is selected, it sends a signal to the O/P stream
        * The signal is a string, or number depending on what the API we're using can work with
        * The signal sent is a combination of the ID and the color associated with it for example:
        * 64255000000 or whatever we choose to go with turns on:
        * the LED @ Row 6, column 4
        * with the color rgb 255 000 000 so basically a flat red.
        * */


        pattern1Btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendMessage("1");
            }
        });

        pattern2Btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendMessage("2");
            }
        });


        pattern3Btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage("3");
            }
        });
        pattern4Btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage("4");
            }
        });

        pattern5Btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage("5");
            }
        });

        mBtnTurnOff.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                sendMessage("0");
            }
        });

        mBtnClearInput.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mTxtReceive.setText("");
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }


    private class ReadInput implements Runnable {

        private boolean bStop = false;
        private Thread t;

        public ReadInput() {
            t = new Thread(this, "Input Thread");
            t.start();
        }

        public boolean isRunning() {
            return t.isAlive();
        }

        @Override
        public void run() {
            InputStream inputStream;

            try {
                inputStream = mBTSocket.getInputStream();
                while (!bStop) {
                    byte[] buffer = new byte[256];
                    if (inputStream.available() > 0) {
                        inputStream.read(buffer);
                        int i = 0;
						/*
						 * This is needed because new String(buffer) is taking the entire buffer i.e. 256 chars on Android 2.3.4 http://stackoverflow.com/a/8843462/1287554
						 */
                        for (i = 0; i < buffer.length && buffer[i] != 0; i++) {
                        }
                        final String strInput = new String(buffer, 0, i);

						/*
						 * If checked then receive text, better design would probably be to stop thread if unchecked and free resources, but this is a quick fix
						 */
                        //TODO: Make some semblance of sanity from this crazy mess called code
                      /*  if (chkReceiveText.isChecked()) {
                            mTxtReceive.post(new Runnable() {
                                @Override
                                public void run() {
                                    mTxtReceive.append(strInput);
                                    //Uncomment below for testing
                                    //mTxtReceive.append("\n");
                                    //mTxtReceive.append("Chars: " + strInput.length() + " Lines: " + mTxtReceive.getLineCount() + "\n");

                                    int txtLength = mTxtReceive.getEditableText().length();
                                    if (txtLength > mMaxChars) {
                                        mTxtReceive.getEditableText().delete(0, txtLength - mMaxChars);
                                    }

                                    if (chkScroll.isChecked()) { // Scroll only if this is checked
                                        scrollView.post(new Runnable() { // Snippet from http://stackoverflow.com/a/4612082/1287554
                                            @Override
                                            public void run() {
                                                scrollView.fullScroll(View.FOCUS_DOWN);
                                            }
                                        });
                                    }
                                }
                            });
                        }*/

                    }
                    Thread.sleep(500);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        public void stop() {
            bStop = true;
        }

    }

    private class DisConnectBT extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... params) {

            if (mReadThread != null) {
                mReadThread.stop();
                while (mReadThread.isRunning())
                    ; // Wait until it stops
                mReadThread = null;

            }

            try {
                mBTSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            mIsBluetoothConnected = false;
            if (mIsUserInitiatedDisconnect) {
                finish();
            }
        }

    }

    private void msg(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        if (mBTSocket != null && mIsBluetoothConnected) {
            new DisConnectBT().execute();
        }
        Log.d(TAG, "Paused");
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (mBTSocket == null || !mIsBluetoothConnected) {
            new ConnectBT().execute();
        }
        Log.d(TAG, "Resumed");
        super.onResume();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "Stopped");
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void> {
        private boolean mConnectSuccessful = true;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity.this, "Hold on", "Connecting");// http://stackoverflow.com/a/11130220/1287554
        }

        @Override
        protected Void doInBackground(Void... devices) {

            try {
                if (mBTSocket == null || !mIsBluetoothConnected) {
                    mBTSocket = mDevice.createInsecureRfcommSocketToServiceRecord(mDeviceUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    mBTSocket.connect();
                }
            } catch (IOException e) {
                // Unable to connect to device
                e.printStackTrace();
                mConnectSuccessful = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (!mConnectSuccessful) {
                Snackbar.make(findViewById(android.R.id.content), "Could not connect to device. Is it a Serial device? Also check if the UUID is correct in the settings", Snackbar.LENGTH_LONG).show();
                finish();
            } else {
                msg("Connected to device");
                mIsBluetoothConnected = true;
                mReadThread = new ReadInput(); // Kick off input reader
            }

            progressDialog.dismiss();
        }

    }

}

