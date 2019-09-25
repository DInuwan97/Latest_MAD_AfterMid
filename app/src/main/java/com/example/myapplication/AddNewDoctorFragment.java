package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Database.DBHandler;

import java.io.FileNotFoundException;


public class AddNewDoctorFragment extends Fragment {

    private FragmentAddNewDoctorListner listner;

    DBHandler myDb;
    EditText txtUserName,txtUserEmail,txtDesignation,txtPassword,txtConfirmPassword;
    EditText txtDoctorName,txtDoctorEmail,txtHospital,txtDoctorMobile,txtSpecilization,txtDoctorNic;
    Button btnAddDoctor;

    public static final String DATA_RECIEVE = "data_recieve";
    private static final int GALLERY_REQUEST = 1888;
    View v1;
    private static final int CAMERA_REQUEST = 1999;
    ImageView imageView;
    Button btnGallery;
    Button btnCamera;

    byte[] imageByte;
    String imgStr;
    Boolean imageChanged= false;


    DoctorsInformation item;
    DoctorsInformation item2;




    public interface FragmentAddNewDoctorListner{
        void onInputAddNewDoctorSent(CharSequence input);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_REQUEST) {

            Bitmap bitmap;

            try {
                Uri target =data.getData();
                bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(target));
                // imageView.setImageBitmap(bitmap);
                int nh = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                imageView.setImageBitmap(scaled);

                imageChanged = true;


            }catch (FileNotFoundException e){
                Log.i("testing file","Filenot found exception");
                e.printStackTrace();
            }catch (Exception e){
                Log.i("testing file"," exception");
                e.printStackTrace();
            }
        }
        if(requestCode == CAMERA_REQUEST){

            Bitmap bitmap;
            try {
                Bundle extras = data.getExtras();
                bitmap = (Bitmap)extras.get("data");
                imageView.setImageBitmap(bitmap);
                imageChanged = true;

            }catch (Exception e){
                Log.i("testing file"," exception camrea");
                e.printStackTrace();
            }
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_new_doctor,container,false);


        imageView           =v.findViewById(R.id.doctorImageView);
        btnGallery          =v.findViewById(R.id.btnGallery);
        btnCamera           =v.findViewById(R.id.btnCamera);




        myDb = new DBHandler(getActivity().getApplicationContext());

        txtDoctorName = (EditText) v.findViewById(R.id.editText_doctorName);
        txtDoctorEmail = (EditText) v.findViewById(R.id.editText_doctorEmail);
        txtHospital = (EditText) v.findViewById(R.id.editText_Hospital);
        txtDoctorMobile = (EditText) v.findViewById(R.id.editText_doctorMobile);
        txtSpecilization = (EditText) v.findViewById(R.id.editText_specialiation);
        txtDoctorNic = (EditText) v.findViewById(R.id.editText_doctorNIC);

        btnAddDoctor = (Button) v.findViewById(R.id.btn_AddNewDoc);



        btnAddDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                //Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                //ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                //bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                // item.setImage(outputStream.toByteArray());

                boolean isInserted = (boolean) myDb.addDoctor(txtDoctorName.getText().toString(),
                        txtDoctorEmail.getText().toString(),
                        txtHospital.getText().toString(),
                        txtDoctorMobile.getText().toString(),
                        txtSpecilization.getText().toString(),
                        txtDoctorNic.getText().toString()
                        /* item.setImage(outputStream.toByteArray())*/);


                if (isInserted == true) {
                    Toast.makeText(getActivity().getApplicationContext(), "Sucessfully Registered!!!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity().getApplicationContext(), SignInActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "Insertion Failiure!!!", Toast.LENGTH_LONG).show();
                }

            }
        });



//image onclicklistners


        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,GALLERY_REQUEST);
            }
        });
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAMERA_REQUEST);
            }
        });

//image onclicklistners



        return v;
    }






}
