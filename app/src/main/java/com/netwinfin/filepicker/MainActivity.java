package com.netwinfin.filepicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.netwinfin.filepicker.databinding.ActivityMainBinding;
import com.netwinfin.filepicker.utils.Constants;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ActivityMainBinding mainBinding;
    private final int REQUEST_CODE_PERMISSIONS = 101;
    private final String[] REQUIRED_PERMISSIONS = new String[]{
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE",

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mainBinding.getRoot();
        setContentView(view);


        mainBinding.btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



             if (allPermissionsGranted()) {
                    Toast.makeText(MainActivity.this, "Permissions granted by the user.", Toast.LENGTH_SHORT).show();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                }

                FilePicker.from(MainActivity.this)
                        .addConfigBuilder()
                        .selectMultipleFiles(true)
                        .setRootDirectory(Environment.getExternalStorageDirectory().getAbsolutePath())
                        .showHiddenFiles(false)
                        .setFilters(new String[]{"pdf", "png", "jpg", "jpeg"})
                        .addItemDivider(true)
                        .theme(R.style.FilePicker_Default)
                        .build()
                        .forResult(Constants.REQ_FILE);

            }
        });


    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("Inside ","onactivity Result"+resultCode);
        if(requestCode == Constants.REQ_FILE && resultCode == RESULT_OK){
            if(data!=null){
                ArrayList<String> files = data.getStringArrayListExtra("filePaths");
                for(String file : files){
                    Log.e(TAG, file);
                    File f= new File(file);
                    Log.e(TAG, "size "+f.length());

                }
            }
        }
    }

    private boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                Toast.makeText(MainActivity.this, "Permissions granted by the user.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}