package com.pedro.vlctestapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.pedro.vlc.VlcListener;
import com.pedro.vlc.VlcVideoLibrary;

import org.videolan.libvlc.MediaPlayer;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by pedro on 25/06/17.
 */
public class MainActivity extends AppCompatActivity implements VlcListener, View.OnClickListener {

    private static final String TAG = "MainActivity";
    private VlcVideoLibrary vlcVideoLibrary;
    //  private MyService.NotifyBinder mNotifyBinder;
    private BboxView mdrawview;
    private ArrayList<DetObj> mChoosenCate;
    private boolean mIsBinded = false;
    private Button bStartStop;
//  private Button bNotify;
//  private Intent mServiceIntent;

    //  private EditText etEndpoint;
    private String mrtsp_src;

    private String[] options = new String[]{":fullscreen"};
    int choice;
//  private ServiceConnection connection = new ServiceConnection() {
//    @Override
//    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//     //Log.e("MainActivity", "Successfully build connection with MainActivity");
//      mNotifyBinder = (MyService.NotifyBinder)iBinder;
//      mNotifyBinder.setDetect(mChoosenCate);
////      mdrawview.setBinder(mNotifyBinder);
//    }
//
//    @Override
//    public void onServiceDisconnected(ComponentName componentName) {
//     //Log.e("MainActivity", "Disconnected with MainActivity");
//    }
//  };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
//    mrtsp_src = "rtsp://admin:rcir219219@202.120.37.223";
        mrtsp_src = "rtsp://admin:rcir219219@192.168.1.209";
        mChoosenCate = (ArrayList<DetObj>) intent.getSerializableExtra("category");
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        mdrawview = (BboxView) findViewById(R.id.drawview);
        mdrawview.setDetect(mChoosenCate);
        mdrawview.setVisibility(View.INVISIBLE);
//    mServiceIntent = new Intent(this, MyService.class);


//    drawingView.setZOrderMediaOverlay(true);
//    SurfaceHolder drawingHolder = drawingView.getHolder();
//    drawingHolder.setFormat(PixelFormat.TRANSPARENT);
        bStartStop = findViewById(R.id.b_start_stop);
//    bNotify = (Button) findViewById(R.id.b_notify);
//    bNotify.setOnClickListener(this);
        bStartStop.setOnClickListener(this);
//    etEndpoint = (EditText) findViewById(R.id.et_endpoint);
        vlcVideoLibrary = new VlcVideoLibrary(this, this, surfaceView);
        vlcVideoLibrary.setOptions(Arrays.asList(options));
        /*
        boolean peopleFlag = mdrawview.getPeopleDetected();
        Log.i(TAG, "parameter got from BBOX_VIEW is " + peopleFlag);
        if (peopleFlag) {
            Log.i(TAG, "People found");
            Toast toast = Toast.makeText(this, "LYF is now at work", Toast.LENGTH_SHORT);
            toast.show();
        }*/
    }


    @Override
    public void onComplete() {
        Toast.makeText(this, "Playing", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError() {
        Toast.makeText(this, "Error, make sure your endpoint is correct", Toast.LENGTH_SHORT).show();
        vlcVideoLibrary.stop();
        bStartStop.setText(getString(R.string.start_player));
    }

    @Override
    public void onBuffering(MediaPlayer.Event event) {

    }

    @Override
    protected void onDestroy() {
//    stopService(mServiceIntent);
        super.onDestroy();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_start_stop:
                if (!vlcVideoLibrary.isPlaying()) {
//      vlcVideoLibrary.play(etEndpoint.getText().toString());
                    //Log.e("VLC", "The vlc size is: " + vlcVideoLibrary.getWidth() + " " + vlcVideoLibrary.getHeight());
                    int display_style = showDisplayStyleDialog();
                    mdrawview.setDisplayStyle(display_style);
                    mdrawview.setVLCAttr(vlcVideoLibrary.getHeight(), vlcVideoLibrary.getWidth());
                    vlcVideoLibrary.play(mrtsp_src);
                    mdrawview.setVisibility(View.VISIBLE);

//      mdrawview.doDraw();

                    bStartStop.setText(getString(R.string.stop_player));
                } else {
                    vlcVideoLibrary.stop();
                    bStartStop.setText(getString(R.string.start_player));
                    mdrawview.setVisibility(View.INVISIBLE);
                }
                break;
//      case R.id.b_notify:
//        if (mIsBinded) {
//          mdrawview.unbindWithService();
//          unbindService(connection);
//          stopService(mServiceIntent);
//          bNotify.setText("Enable Notify");
//        }else {
//          mdrawview.bindWithService();
//          startService(mServiceIntent);
//          bindService(mServiceIntent, connection, BIND_AUTO_CREATE);
//          bNotify.setText("Disable Notify");
//        }
//        break;
        }
    }


    public int showDisplayStyleDialog() {
        choice = 0;
        String[] items = {getString(R.string.select_display_time), getString(R.string.select_push_notification)};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setIcon(R.drawable.icon);
        builder.setTitle(getString(R.string.choose_display_style_dialog_title));
        //第二个参数是默认的选项
        builder.setSingleChoiceItems(items, 0, (dialog, which) -> {
            //@Override
            //public void onClick(DialogInterface dialog, int which) {
                choice = which;
            //}
        });
        builder.setPositiveButton("select", (dialog, which) -> {
            switch (choice) {
                case BboxView.DISPLAY_TIME_ON_SCREEN:
                    Toast.makeText(MainActivity.this,
                            "You chose to display on screen",
                            Toast.LENGTH_SHORT).show();
                    break;
                case BboxView.RECEIVE_NOTIFICATION:
                    Toast.makeText(MainActivity.this,
                            "You chose to receive notifications",
                            Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(MainActivity.this,
                            "Error while getting choice",
                            Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        Button btnPos = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        btnPos.setTextColor(Color.BLACK);
        return choice;
    }
}
