package com.anuar2k.bcrypttest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.mindrot.jbcrypt.BCrypt;

import java.security.MessageDigest;

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
                MessageDigest md = null;
                try {
                    md = MessageDigest.getInstance("SHA-256");
                }
                catch (Exception e) {

                }
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

                md.update(pswdToHash.getBytes());
                byte[] digest = md.digest();
                String sha256hash = bytesToHex(digest);
                long timestamp5 = elapsedRealtime();

                long time4 = timestamp5 - timestamp4;

                md.reset();
                md.update(pswdToCheck.getBytes());
                digest = md.digest();
                String sha256check = bytesToHex(digest);
                long timestamp6 = elapsedRealtime();

                long time5 = timestamp6 - timestamp5;

                boolean validSHA = sha256hash.equals(sha256check);
                long timestamp7 = elapsedRealtime();

                long time6 = timestamp7 - timestamp6;

                tx1.setText("");
                tx1.append("BCrypt times: salt gen, hash gen, pswd check\r\n");
                tx1.append(salt + "\r\n");
                tx1.append(time1 + "\r\n\r\n");
                tx1.append(hashedPassword + "\r\n");
                tx1.append(time2 + "\r\n\r\n");
                tx1.append((valid ? "valid" : "invalid") + "\r\n");
                tx1.append(time3 + "\r\n\r\n");
                tx1.append("SHA256 times: hash gen, input hash gen, pswd check\r\n");
                tx1.append(sha256hash + "\r\n");
                tx1.append(time4 + "\r\n\r\n");
                tx1.append(sha256check + "\r\n");
                tx1.append(time5 + "\r\n\r\n");
                tx1.append((valid ? "valid" : "invalid") + "\r\n");
                tx1.append(String.valueOf(time6));
            }
        });
    }

    private static String bytesToHex(byte[] data) {
        if (data == null) {
            return null;
        }

        int len = data.length;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            if ((data[i] & 0xFF) < 16) {
                stringBuilder.append('0');
                stringBuilder.append(java.lang.Integer.toHexString(data[i] & 0xFF));
            } else {
                stringBuilder.append(java.lang.Integer.toHexString(data[i] & 0xFF));
            }
        }
        return stringBuilder.toString();
    }
}
