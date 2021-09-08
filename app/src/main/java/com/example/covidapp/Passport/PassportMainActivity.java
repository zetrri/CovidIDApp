package com.example.covidapp.Passport;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import com.example.covidapp.R;

public class PassportMainActivity extends AppCompatActivity {

    private String [] account = new String[9]; //TEMP
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passport_main);
        //TEMP manuel inläggning av data
        account[2] = "199708281111";
        account[3] = "Anton";
        account[4] = "Weiberg";

        ImageView qr_image = findViewById(R.id.imageViewQrCode);

        Point p = new Point();
        WindowManager window_manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = window_manager.getDefaultDisplay();
        display.getSize(p);

        //Skapar en QR kod och toppar in den i en bitmap
        QRGEncoder qrgEncoder = new QRGEncoder(account[2], null, QRGContents.Type.TEXT, (300 * 3 / 4));
        try {
            Bitmap qr_bitmap = qrgEncoder.encodeAsBitmap();
            qr_image.setImageBitmap(qr_bitmap);
        } catch (Exception e){
            Log.e("Error", e.toString());
        }


        //TEMP manuel inläggning av data
        TextView text_personNr = findViewById(R.id.textViewPersonNr);
        String text = "Person nummer: " + account[2];
        text_personNr.setText(text);

        TextView text_forNamn = findViewById(R.id.textViewName);
        text = "Namn: " + account[3] + " " + account[4];
        text_forNamn.setText(text);

    }
}