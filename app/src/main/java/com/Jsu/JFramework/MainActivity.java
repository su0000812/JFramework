package com.Jsu.JFramework;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.RippleDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.Jsu.framework.image.imageChooser.BChooserPreferences;
import com.Jsu.framework.image.imageChooser.ChooserType;
import com.Jsu.framework.image.imageChooser.ChosenImage;
import com.Jsu.framework.image.imageChooser.ChosenImages;
import com.Jsu.framework.image.imageChooser.ImageChooserListener;
import com.Jsu.framework.image.imageChooser.ImageChooserManager;
import com.Jsu.framework.widget.wheel.CityPickerDialog;

import java.io.File;

public class MainActivity extends AppCompatActivity implements ImageChooserListener {

    private ImageChooserManager imageChooserManager;
    private int chooserType;
    private String mediaPath;

    private Button btnChoose, btnTake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        BChooserPreferences preferences = new BChooserPreferences(getApplicationContext());
        preferences.setFolderName("ICL");

        btnChoose = (Button) findViewById(R.id.btn_choose);
        btnTake = (Button) findViewById(R.id.btn_take);
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                chooseImage();
//                testCircularReveal();
                testWheelDialog();
            }
        });


        btnTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });

        textViewFile = (TextView) findViewById(R.id.tv_content);

        imageViewThumbnail = (ImageView) findViewById(R.id.img);

        imageViewThumbSmall = (ImageView) findViewById(R.id.img_thumb);
    }

    private void takePicture() {
        chooserType = ChooserType.REQUEST_CAPTURE_PICTURE;
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_CAPTURE_PICTURE, true);
        imageChooserManager.setImageChooserListener(this);
        try {
            mediaPath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testCircularReveal() {
        int cx = (btnTake.getLeft() + btnTake.getRight()) / 2;
        int cy = (btnTake.getTop() + btnTake.getBottom()) / 2;
        int finalRadius = Math.max(btnTake.getWidth(), btnTake.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(btnTake, 0, 0, 0, finalRadius);
        anim.start();
    }

    private void testWheelDialog() {
        new CityPickerDialog(MainActivity.this, "广东省 广州市 天河区", true).show();
    }

    private void chooseImage() {
        chooserType = ChooserType.REQUEST_PICK_PICTURE;
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_PICK_PICTURE, true);
        imageChooserManager.setImageChooserListener(this);
        try {
            mediaPath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(getClass().getName(), requestCode + "");
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (imageChooserManager == null) {
                imageChooserManager = new ImageChooserManager(this, requestCode, true);
                imageChooserManager.setImageChooserListener(this);
                imageChooserManager.reinitialize(mediaPath);
            }
            imageChooserManager.submit(requestCode, data);
        }
    }

    private String finalPath;
    private String thumbPath;
    private String thumbPathSmall;

    private ImageView imageViewThumbnail;

    private ImageView imageViewThumbSmall;

    private TextView textViewFile;

    @Override
    public void onImageChosen(final ChosenImage image) {
        finalPath = image.getFilePathOriginal();
        thumbPath = image.getFileThumbnail();
        thumbPathSmall = image.getFileThumbnailSmall();
        MainActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (image != null) {
                    textViewFile.setText(image.getFilePathOriginal());
                    imageViewThumbnail.setImageURI(Uri.parse(new File(image
                            .getFileThumbnail()).toString()));
                    imageViewThumbSmall.setImageURI(Uri.parse(new File(image
                            .getFileThumbnailSmall()).toString()));
                }
            }
        });
    }

    @Override
    public void onImagesChosen(ChosenImages images) {
    }

    @Override
    public void onError(String reason) {

    }
}
