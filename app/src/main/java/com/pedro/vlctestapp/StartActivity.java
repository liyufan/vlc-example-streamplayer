package com.pedro.vlctestapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {
    private static final String TAG = "StartActivity";
    private EditText meditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        setContentView(R.layout.activity_start);
        Button startBu = (Button) findViewById(R.id.StartButton);
        meditText = (EditText) findViewById(R.id.edit_text);
        Button aboutBu = (Button) findViewById(R.id.about_us);
        startBu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String rtsp_src = meditText.getText().toString();
                if (rtsp_src.isEmpty()) {
                    Toast.makeText(StartActivity.this, "Please Enter the Rtsp Sources!", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(StartActivity.this, "Welcome Use Intelligent Lab APP", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(StartActivity.this, CategoryActivity.class);
                intent.putExtra("rtsp_src", rtsp_src);
               //Log.d(TAG, "send rtsp source: " + rtsp_src);
                startActivity(intent);
            }
        });

        aboutBu.setOnClickListener(view -> {
            Intent intent = new Intent(StartActivity.this, AboutActivity.class);
           //Log.d(TAG, "into about activity");
            startActivity(intent);
        });
        Button exitBu = (Button) findViewById(R.id.ExitButton);
        exitBu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(StartActivity.this);
                dialog.setTitle("RCIR Intelligent Lab");
                dialog.setMessage("Are you sure to leave this App?");
                dialog.setCancelable(false);
                dialog.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        finish();
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}