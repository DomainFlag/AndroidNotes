package com.example.cchiv.androidnotes.notes;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cchiv.androidnotes.R;
import com.example.cchiv.androidnotes.utilities.ComponentRender;

import java.io.FileNotFoundException;

public class IntentsActivity extends AppCompatActivity {

    static final int PICK_IMAGE_REQUEST = 1;

    private String component;
    private String demo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component);

        this.component = getIntent().getStringExtra("className");
        this.demo = getIntent().getStringExtra("snippetName");

        ComponentRender componentRender = new ComponentRender(this, R.id.component_list, this.component, this.demo);
        updateUI();
    }

    public void updateUI() {
        ((LinearLayout) findViewById(R.id.upload_image))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            ContentResolver contentResolver = getContentResolver();

            if(data.getData() == null)
                return;

            try {
                Bitmap bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(data.getData()));

                ((TextView) findViewById(R.id.upload_image_description)).setVisibility(View.GONE);
                ImageView imageView = (ImageView) findViewById(R.id.empty_package);
                imageView.setImageBitmap(bitmap);
                imageView.setAlpha((float) 1.0);
            } catch(FileNotFoundException e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
