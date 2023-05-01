package io.vsapan.followme;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private CameraManager cameraManager;
    private String cameraId;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.torchtoggle).setOnClickListener(this::toggleFlashLight);

        cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            cameraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            Toast.makeText(this, "Get camera id failed", Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void toggleFlashLight(View view) {
        changeTorchState(true);

        new Handler().postDelayed(() -> {
            changeTorchState(false);
            finishAndRemoveTask();
        }, 3000);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void changeTorchState(boolean on) {
        String onOff = on ? "ON" : "OFF";
        try {
            cameraManager.setTorchMode(cameraId, on);
            Toast.makeText(MainActivity.this, "Torch turned " + onOff, Toast.LENGTH_SHORT).show();
        } catch (CameraAccessException e) {
            Toast.makeText(MainActivity.this, "Torch " + onOff + " failed; " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}