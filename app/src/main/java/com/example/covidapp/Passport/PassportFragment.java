package com.example.covidapp.Passport;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.covidapp.R;
import com.example.covidapp.databinding.FragmentPassportBinding;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PassportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PassportFragment extends Fragment {

    private FragmentPassportBinding binding;
    private String [] account = new String[9]; //TEMP

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PassportFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PassportFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PassportFragment newInstance(String param1, String param2) {
        PassportFragment fragment = new PassportFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPassportBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TEMP manuel inläggning av data
        account[2] = "199708281111";
        account[3] = "Anton";
        account[4] = "Weiberg";

        ImageView qr_image = binding.imageViewQrCode;

        Point p = new Point();
        WindowManager window_manager = (WindowManager) getActivity().getSystemService(getContext().WINDOW_SERVICE);
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
        TextView text_personNr = binding.textViewPersonNr;
        String text = "Person nummer: " + account[2];
        text_personNr.setText(text);

        TextView text_forNamn = binding.textViewName;
        text = "Namn: " + account[3] + " " + account[4];
        text_forNamn.setText(text);
    }
}