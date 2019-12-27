package com.undefined.projectkasirbarokah;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText et1 = (EditText) findViewById(R.id.inputUsername);
        final EditText et2 = (EditText) findViewById(R.id.inputPassword);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et1.getText().toString().equals("admin") && et2.getText().toString().equals("admin")) {
                    Intent intent1 = new Intent(MainActivity.this, MenuUtama.class);
                    intent1.putExtra("userName", et1.getText().toString());
                    startActivity(intent1);
                    finish();
                } else {
                    AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                    adb.setMessage("Wrong Username / Password!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    AlertDialog alert = adb.create();
                    alert.show();

                }
            }
        });
    }
}
