package uk.co.surveyorforyou.surveyorforyou;

import android.content.Intent;
import android.graphics.Bitmap;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.Toast;

import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;

public class UploadImage extends AppCompatActivity {

    private final String TAG = this.getClass().getName();
    Button btnCamera, btnGallery, btnUpload;
    ImageView  upimageView;
    CameraPhoto cameraPhoto;
    GalleryPhoto galleryPhoto;
    final int CAMERA_REQUEST = 12345;
    final int GALLERY_REQUEST = 22222;
    String selectedPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        btnCamera = (Button) findViewById(R.id.btnCamera);
        btnGallery = (Button) findViewById(R.id.btnGallery);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        upimageView = (ImageView)findViewById(R.id.upimageView);

        cameraPhoto = new CameraPhoto(getApplicationContext());
        galleryPhoto = new GalleryPhoto(getApplicationContext());

        Intent i= getIntent();

        final  String firstname = i.getStringExtra("firstname");
        final  String lastname = i.getStringExtra("lastname");
        final  String email = i.getStringExtra("email");
        final  String contact = i.getStringExtra("contact");
        final  String username = i.getStringExtra("username");
        final  String password = i.getStringExtra("password");
        final  String address = i.getStringExtra("address");
        final  String postcode = i.getStringExtra("postcode");

        btnCamera.setOnClickListener(new View.OnClickListener() {
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


        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivityForResult(galleryPhoto.openGalleryIntent(),GALLERY_REQUEST);

            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Bitmap bitmap = ImageLoader.init().from(selectedPhoto).requestSize(512,512).getBitmap();
                    String encodeImage = ImageBase64.encode(bitmap);
                    Log.d(TAG,encodeImage);

                    HashMap<String, String> postData = new HashMap<String, String>();
                    postData.put("image",encodeImage);
                    postData.put("firstname",firstname);
                    postData.put("lastname",lastname);
                    postData.put("email",email);
                    postData.put("contact",contact);
                    postData.put("username",username);
                    postData.put("password",password);
                    postData.put("address",address);
                    postData.put("postcode",postcode);


                    PostResponseAsyncTask task = new PostResponseAsyncTask(UploadImage.this, postData, new AsyncResponse() {
                        @Override
                        public void processFinish(String s) {
                            if(s.contains("Upload success")){
                                Toast.makeText(getApplicationContext(),"Image Uploaded Successfully.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UploadImage.this,LoginActivity.class);
                                UploadImage.this.startActivity(intent);
                            }else if(s.contains("Image Not Found")){
                                Toast.makeText(getApplicationContext(),"Image Not found.", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(),"Error while Uploading", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    task.execute("http://www.surveyor-for-you.co.uk/Android/upload2.php");
                    task.setEachExceptionsHandler(new EachExceptionsHandler() {
                        @Override
                        public void handleIOException(IOException e) {
                            Toast.makeText(getApplicationContext(),"Cannot connect to server", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void handleMalformedURLException(MalformedURLException e) {
                            Toast.makeText(getApplicationContext(),"URL Error", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void handleProtocolException(ProtocolException e) {
                            Toast.makeText(getApplicationContext(),"Protocol Error", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {
                            Toast.makeText(getApplicationContext(),"Encoding Error", Toast.LENGTH_SHORT).show();
                        }
                    });

                }catch (FileNotFoundException e){
                    Toast.makeText(getApplicationContext(),"Something wrong while encoding photo",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(resultCode == CAMERA_REQUEST){
                String photoPath = cameraPhoto.getPhotoPath();
                selectedPhoto = photoPath;
                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512,512).getBitmap();
                    upimageView.setImageBitmap(bitmap);
                }catch (FileNotFoundException e){
                    Toast.makeText(getApplicationContext(),"Something wrong while loading photo",Toast.LENGTH_SHORT).show();
                }

                Log.d(TAG, photoPath);
            }else if(requestCode == GALLERY_REQUEST){

                Uri uri = data.getData();
                galleryPhoto.setPhotoUri(uri);
                String photoPath = galleryPhoto.getPath();
                selectedPhoto = photoPath;
                //String galleryPath = galleryPhoto.getPath();
                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512,512).getBitmap();
                    upimageView.setImageBitmap(bitmap);
                }catch (FileNotFoundException e){
                    Toast.makeText(getApplicationContext(),"Something wrong while choosing photo",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    //private Bitmap getRotatedBitmap(Bitmap source, float angle){
      //  Matrix matrix = new Matrix();
        //matrix.postRotate(angle);
       // Bitmap bitmap = Bitmap.createBitmap(source,0,0,source.getWidth(),source.getHeight(),matrix,true);
       // return bitmap;
    //}

}
