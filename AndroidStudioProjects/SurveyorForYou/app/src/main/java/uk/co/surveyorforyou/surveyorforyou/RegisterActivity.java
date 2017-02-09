package uk.co.surveyorforyou.surveyorforyou;

import android.content.Intent;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;

import com.android.volley.toolbox.Volley;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;


public class RegisterActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getName();
    ImageView ivCamera, ivGallery, ivUpload, ivimageView;
    CameraPhoto cameraPhoto;
    GalleryPhoto galleryPhoto;
    final int CAMERA_REQUEST = 12345;
    final int GALLERY_REQUEST = 22222;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText rgFname = (EditText)findViewById(R.id.regFirstname);
        final EditText rgLname = (EditText)findViewById(R.id.regLastname);
        final EditText rgEmail = (EditText)findViewById(R.id.regEmail);
        final EditText rgUser = (EditText)findViewById(R.id.regUsername);
        final EditText rgPass = (EditText)findViewById(R.id.regPassword);
        final EditText rgConfirmpass = (EditText)findViewById(R.id.regConfirmpassword);
        final EditText rgAddress = (EditText)findViewById(R.id.regAddress);
        final EditText rgPostcode = (EditText)findViewById(R.id.regPostcode);
        final Button register = (Button)findViewById(R.id.btnReg);


        ivCamera = (ImageView)findViewById(R.id.ivCamera);
        ivGallery = (ImageView) findViewById(R.id.ivGallery);
        ivUpload = (ImageView)findViewById(R.id.ivUpload);
        ivimageView = (ImageView)findViewById(R.id.ivimageView);

        cameraPhoto = new CameraPhoto(getApplicationContext());
        galleryPhoto = new GalleryPhoto(getApplicationContext());

        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    startActivityForResult(cameraPhoto.takePhotoIntent(),CAMERA_REQUEST);
                    cameraPhoto.addToGallery();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivityForResult(galleryPhoto.openGalleryIntent(),GALLERY_REQUEST);

            }
        });




        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final  String firstname = rgFname.getText().toString();
                final  String lastname = rgLname.getText().toString();
                final  String email = rgEmail.getText().toString();
                final  String username = rgUser.getText().toString();
                final  String password = rgPass.getText().toString();
                final  String address = rgAddress.getText().toString();
                final  String postcode = rgPostcode.getText().toString();

                Response.Listener<String> responseListner = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Register Failed")
                                        .setNegativeButton("Retry",null)
                                        .create()
                                        .show();
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                };

                RegisterRequest rgRequest = new RegisterRequest(firstname,lastname,email,username,password,address,postcode,responseListner);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(rgRequest);
            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(resultCode == CAMERA_REQUEST){
                String photoPath = cameraPhoto.getPhotoPath();
                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512,512).getBitmap();
                    ivimageView.setImageBitmap(bitmap);
                }catch (FileNotFoundException e){
                    Toast.makeText(getApplicationContext(),"Something wrong while loading photo",Toast.LENGTH_SHORT).show();
                }

                Log.d(TAG, photoPath);
            }else if(requestCode == GALLERY_REQUEST){

                Uri uri = data.getData();
                galleryPhoto.setPhotoUri(uri);

                String galleryPath = galleryPhoto.getPath();
                try {
                    Bitmap bitmap = ImageLoader.init().from(galleryPath).requestSize(512,512).getBitmap();
                    ivimageView.setImageBitmap(bitmap);
                }catch (FileNotFoundException e){
                    Toast.makeText(getApplicationContext(),"Something wrong while choosing photo",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
