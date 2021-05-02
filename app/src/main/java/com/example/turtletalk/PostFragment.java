package com.example.turtletalk;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.turtletalk.database.AppDatabase;
import com.example.turtletalk.models.Profile;
import com.example.turtletalk.viewmodels.ProfileViewModel;

import java.util.concurrent.CountDownLatch;


public class PostFragment extends Fragment {

    Profile currentProfile;
    CountDownLatch latch = new CountDownLatch(1);
    Bitmap postBitmap;
    private static final int IMAGE_PICK = 100;

    ProfileViewModel viewModel;



    public PostFragment() {
        super(R.layout.fragment_post);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){

        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(ProfileViewModel.class);
        postBitmap = BitmapFactory.decodeStream(getContext().getResources().openRawResource(R.raw.beach));
        currentProfile = viewModel.getCurrentProfile();

        postBitmap = BitmapFactory.decodeStream(getContext().getResources().openRawResource(R.raw.beach));

        ImageView imageView = view.findViewById(R.id.post_image);

        Button uploadPhotoButton = view.findViewById(R.id.upload_photo_button);
        Button postButton = view.findViewById(R.id.upload_post_button);
        EditText captionEditText = view.findViewById(R.id.upload_caption_editText);

        uploadPhotoButton.setOnClickListener((buttonView) ->{
            goToPhotos();
            setPostImage(imageView);
        });




    }

    public void goToPhotos(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_PICK);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK && resultCode == Activity.RESULT_OK){
            String uri = data.getData().toString();
            this.postBitmap = BitmapFactory.decodeFile(uri);
        }
    }

    public void setPostImage(ImageView imageView){
        imageView.setImageBitmap(this.postBitmap);
    }

}