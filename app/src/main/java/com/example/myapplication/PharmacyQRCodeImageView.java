package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import static android.content.Context.WINDOW_SERVICE;


public class PharmacyQRCodeImageView extends Fragment {
    String inputValue;
    QRGEncoder qrgEncoder;
    ImageView qrImage;
    Bitmap bitmap;
    String TAG;

    public static final String DATA_RECIEVE_KEY = "datarecievekey";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pharmacy_qrcode_image_view, container, false);

        Bundle args = getArguments();

        Log.i("PharmacyDeliveryDetails",args.getString(DATA_RECIEVE_KEY));
        qrImage = v.findViewById(R.id.imageViewQRCodeView);
        inputValue =  args.getString(DATA_RECIEVE_KEY);
        WindowManager manager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;

        qrgEncoder = new QRGEncoder(
                inputValue, null,
                QRGContents.Type.TEXT,
                smallerDimension);
        try {
            bitmap = qrgEncoder.encodeAsBitmap();
            qrImage.setImageBitmap(bitmap);
        } catch (WriterException e) {
            Log.v(TAG, e.toString());

        }

        return v;


    }
}
