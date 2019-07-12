package com.davidlacho.andoridpermissions;

import android.Manifest;
import android.Manifest.permission;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

  private static final int PERMISSIONS_REQUEST_CODE = 11;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button callButton = findViewById(R.id.callButton);
    callButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(permission.CALL_PHONE)) {
              new Builder(MainActivity.this)
                  .setTitle("Call Permission")
                  .setMessage("Hi there! We can't call anyone without the call permission, could you please grant it?")
                  .setPositiveButton("Yep", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                      requestPermissions(new String[]{permission.CALL_PHONE}, PERMISSIONS_REQUEST_CODE);
                    }
                  })
                  .setNegativeButton("No thanks", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                      Toast.makeText(MainActivity.this, ":(", Toast.LENGTH_SHORT).show();
                    }
                  }).show();
            } else {
              requestPermissions(new String[]{permission.CALL_PHONE}, PERMISSIONS_REQUEST_CODE);
            }
          } else {
            makeCall();
          }
        } else {
          makeCall();
        }
      }
    });
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    if (requestCode == PERMISSIONS_REQUEST_CODE) {
      if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        makeCall();
      }
    }
  }

  private void makeCall() {
    Intent intent = new Intent(Intent.ACTION_CALL);
    intent.setData(Uri.parse("tel:" + "12345678"));
    startActivity(intent);
  }
}
