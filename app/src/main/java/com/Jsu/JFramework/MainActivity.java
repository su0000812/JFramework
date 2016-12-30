package com.Jsu.JFramework;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.RippleDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Jsu.framework.image.imageChooser.BChooserPreferences;
import com.Jsu.framework.image.imageChooser.ChooserType;
import com.Jsu.framework.image.imageChooser.ChosenImage;
import com.Jsu.framework.image.imageChooser.ChosenImages;
import com.Jsu.framework.image.imageChooser.ImageChooserListener;
import com.Jsu.framework.image.imageChooser.ImageChooserManager;
import com.Jsu.framework.recyclerview.demo.TestRvActivity;
import com.Jsu.framework.utils.NotifiUtils;
import com.Jsu.framework.utils.TestActivity;
import com.Jsu.framework.utils.okhttp.OkHttpUtils;
import com.Jsu.framework.utils.okhttp.callback.StringCallback;
import com.Jsu.framework.widget.wheel.CityPickerDialog;

import java.io.File;

import okhttp3.Call;
import okhttp3.Request;

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
                chooseImage();
//                testCircularReveal();
//                testWheelDialog();
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

        Log.e("current", "threadId--" + Thread.currentThread().getId());

        new Thread(new Runnable() {
            @Override
            public void run() {
                testOkHttp();
            }
        }).start();


//        HandlerThread handlerThread = new HandlerThread("test Toast");
//        handlerThread.start();
//        Handler handler = new Handler(handlerThread.getLooper());
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//
//                Toast.makeText(MainActivity.this, "test Toast" + Thread.currentThread().getId(), Toast.LENGTH_LONG).show();
//            }
//        });


        findViewById(R.id.btn_test_gesture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TestActivity.class));
            }
        });

        Button anim = (Button) findViewById(R.id.btn_test_anim);
        final ObjectAnimator translationUp = ObjectAnimator.ofInt(anim, "backgroundColor",
                Color.RED, Color.BLUE, Color.GRAY, Color.GREEN);
        translationUp.setInterpolator(new DecelerateInterpolator());
        translationUp.setDuration(1500);
        translationUp.setRepeatCount(-1);
        translationUp.setRepeatMode(Animation.REVERSE);

        translationUp.setEvaluator(new ArgbEvaluator());

        anim.postDelayed(new Runnable() {
            @Override
            public void run() {
                translationUp.start();
            }
        }, 3000);
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

    @TargetApi(21)
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

    private String url = "http://test.www.xiguaauto.com/api/ios/v1/articles.jsp?channelId=83&type=1&page=1&pageSize=10";

    private void testOkHttp() {
        Log.e("okHttp", "threadId--" + Thread.currentThread().getId());
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request) {
            super.onBefore(request);
            setTitle("loading...");
        }

        @Override
        public void onAfter() {
            super.onAfter();
            setTitle("Sample-okHttp");
        }

        @Override
        public void onError(Call call, Exception e) {
            e.printStackTrace();
//            mTv.setText("onError:" + e.getMessage());
        }

        @Override
        public void onResponse(String response) {
            Log.e("test OkHttp on response", "onResponse：complete" + response);
//            mTv.setText("onResponse:" + response);
        }

        @Override
        public void inProgress(float progress) {
            Log.e("test OkHttp in progress", "inProgress:" + progress);
//            mProgressBar.setProgress((int) (100 * progress));
        }
    }
}
