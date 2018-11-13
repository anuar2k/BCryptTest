package com.anuar2k.bcrypttest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.mindrot.jbcrypt.BCrypt;

import static android.os.SystemClock.elapsedRealtime;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText et1 = findViewById(R.id.editText1);
        final EditText et2 = findViewById(R.id.editText2);
        final Button button = findViewById(R.id.button);
        final TextView tx1 = findViewById(R.id.textView1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pswdToHash = et1.getText().toString();
                String pswdToCheck = et2.getText().toString();

                long timestamp1 = elapsedRealtime();
                String salt = BCrypt.gensalt();
                long timestamp2 = elapsedRealtime();

                long time1 = timestamp2 - timestamp1;

                String hashedPassword = BCrypt.hashpw(pswdToHash, salt);
                long timestamp3 = elapsedRealtime();

                long time2 = timestamp3 - timestamp2;

                boolean valid = BCrypt.checkpw(pswdToCheck, hashedPassword);
                long timestamp4 = elapsedRealtime();

                long time3 = timestamp4 - timestamp3;

                tx1.setText("");
                tx1.append(salt + "\r\n");
                tx1.append(time1 + "\r\n\r\n");
                tx1.append(hashedPassword + "\r\n");
                tx1.append(time2 + "\r\n\r\n");
                tx1.append((valid ? "valid" : "invalid") + "\r\n");
                tx1.append(String.valueOf(time3));
            }
        });
    }
}
