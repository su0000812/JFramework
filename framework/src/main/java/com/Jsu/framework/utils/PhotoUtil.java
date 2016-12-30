package com.Jsu.framework.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.Toast;

import com.Jsu.framework.widget.wheel.FileUtil;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by JSu on 2016/7/19.
 */
public class PhotoUtil {

    public static final int REQUEST_CODE = 4329;
    public static final String KEY_RETURN_PATHS = "PATHS";
    public static final int PIC_TAKE_CODE = 102;
    private static final String COMPRESS_FILE_NAME = "_Jsu_compress.jpg";
    private static final String FILE_NAME_END = "_water_print.jpg";
    private String PIC_PATH = "";

    public Intent takePhoto(Context appContext) {
        File file = new File(createTakePhotoPath(appContext));
        try {
            if (file.exists()) {
                file.delete();
            } else {
                file.createNewFile();
            }

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

            return intent;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Intent openAlbum() {
        Intent intent = null;
        if (Build.VERSION.SDK_INT == 16) {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            intent = Intent.createChooser(i, "File Chooser");
        } else if (Build.VERSION.SDK_INT >= 11) {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            intent = Intent.createChooser(i, "File Browser");
        } else if (Build.VERSION.SDK_INT < 11) {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            intent = Intent.createChooser(i, "File Chooser");
        }
        return intent;
    }

    public String createTakePhotoPath(Context appContext) {
        PIC_PATH = FileUtil.getAppResourcePath(appContext) + System.currentTimeMillis() + ".jpg";
        return PIC_PATH;
    }

    public File onActivityResult(int requestCode, Intent data) {
        // 相册选择图片
        if (requestCode == REQUEST_CODE && data != null) {
            String[] paths = data.getStringArrayExtra(KEY_RETURN_PATHS);
            if (paths.length > 0) {
                File file = new File(paths[0]);
                if (file.exists()) {
                    return tryDecodeFile(file);
                }
            }
        }
        // 拍照返回
        else if (requestCode == PIC_TAKE_CODE) {
            File file = new File(PIC_PATH);
            if (file.exists()) {
                if (file.length() > 100) {
                    // 拍照成功
                    return tryDecodeFile(file);
                } else {
                    file.delete();
                }
            }
        }

        return null;
    }

    private File tryDecodeFile(final File file) {
        if (file.getAbsolutePath().endsWith(FILE_NAME_END)) {
            return file;
        }

        return file;
    }

//    private File addWaterPrint(Bitmap bitmap, File file) {
//        // 获取资源文件，水印照片
//        BitmapDrawable bd = (BitmapDrawable) (appContext.getResources().getDrawable(R.drawable.icon_water_print));
//        Bitmap water_print = bd.getBitmap();
//
//        // 加载图片成功，尝试给图片增加水印
//        if (bitmap != null) {
//            Bitmap target = ImageUtil.watermarkBitmap(bitmap, water_print, "仅我是车主使用复印无效", WATER_PRINT_ALPHA);
//            if (target != null) {
//                return createWaterPrintFile(target, file);
//            }
//        }
//        return null;
//    }

    public String compressFile(Context appContext, File src) {
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(src.getAbsolutePath());
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            float scaleWidth = ((float) width) / width;
            float scaleHeight = ((float) height) / height;

            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);

            String path = src.getAbsolutePath() + COMPRESS_FILE_NAME;
            File file = new File(path);

            FileOutputStream fos = new FileOutputStream(file);
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);

            // copy paste exif information from original file to new
            // file
            ExifInterface oldexif = new ExifInterface(src.getAbsolutePath());
            ExifInterface newexif = new ExifInterface(file.getAbsolutePath());

            int build = Build.VERSION.SDK_INT;


            // From API 11
            if (build >= 11) {
                if (oldexif.getAttribute("FNumber") != null) {
                    newexif.setAttribute("FNumber",
                            oldexif.getAttribute("FNumber"));
                }
                if (oldexif.getAttribute("ExposureTime") != null) {
                    newexif.setAttribute("ExposureTime",
                            oldexif.getAttribute("ExposureTime"));
                }
                if (oldexif.getAttribute("ISOSpeedRatings") != null) {
                    newexif.setAttribute("ISOSpeedRatings",
                            oldexif.getAttribute("ISOSpeedRatings"));
                }
            }
            // From API 9
            if (build >= 9) {
                if (oldexif.getAttribute("GPSAltitude") != null) {
                    newexif.setAttribute("GPSAltitude",
                            oldexif.getAttribute("GPSAltitude"));
                }
                if (oldexif.getAttribute("GPSAltitudeRef") != null) {
                    newexif.setAttribute("GPSAltitudeRef",
                            oldexif.getAttribute("GPSAltitudeRef"));
                }
            }
            // From API 8
            if (build >= 8) {
                if (oldexif.getAttribute("FocalLength") != null) {
                    newexif.setAttribute("FocalLength",
                            oldexif.getAttribute("FocalLength"));
                }
                if (oldexif.getAttribute("GPSDateStamp") != null) {
                    newexif.setAttribute("GPSDateStamp",
                            oldexif.getAttribute("GPSDateStamp"));
                }
                if (oldexif.getAttribute("GPSProcessingMethod") != null) {
                    newexif.setAttribute(
                            "GPSProcessingMethod",
                            oldexif.getAttribute("GPSProcessingMethod"));
                }
                if (oldexif.getAttribute("GPSTimeStamp") != null) {
                    newexif.setAttribute("GPSTimeStamp", ""
                            + oldexif.getAttribute("GPSTimeStamp"));
                }
            }
            if (oldexif.getAttribute("DateTime") != null) {
                newexif.setAttribute("DateTime",
                        oldexif.getAttribute("DateTime"));
            }
            if (oldexif.getAttribute("Flash") != null) {
                newexif.setAttribute("Flash",
                        oldexif.getAttribute("Flash"));
            }
            if (oldexif.getAttribute("GPSLatitude") != null) {
                newexif.setAttribute("GPSLatitude",
                        oldexif.getAttribute("GPSLatitude"));
            }
            if (oldexif.getAttribute("GPSLatitudeRef") != null) {
                newexif.setAttribute("GPSLatitudeRef",
                        oldexif.getAttribute("GPSLatitudeRef"));
            }
            if (oldexif.getAttribute("GPSLongitude") != null) {
                newexif.setAttribute("GPSLongitude",
                        oldexif.getAttribute("GPSLongitude"));
            }
            if (oldexif.getAttribute("GPSLatitudeRef") != null) {
                newexif.setAttribute("GPSLongitudeRef",
                        oldexif.getAttribute("GPSLongitudeRef"));
            }
            //Need to update it, with your new height width
            newexif.setAttribute("ImageLength",
                    "200");
            newexif.setAttribute("ImageWidth",
                    "200");

            if (oldexif.getAttribute("Make") != null) {
                newexif.setAttribute("Make",
                        oldexif.getAttribute("Make"));
            }
            if (oldexif.getAttribute("Model") != null) {
                newexif.setAttribute("Model",
                        oldexif.getAttribute("Model"));
            }
            if (oldexif.getAttribute("Orientation") != null) {
                newexif.setAttribute("Orientation",
                        oldexif.getAttribute("Orientation"));
            }
            if (oldexif.getAttribute("WhiteBalance") != null) {
                newexif.setAttribute("WhiteBalance",
                        oldexif.getAttribute("WhiteBalance"));
            }

            newexif.saveAttributes();

            return path;
        } catch (Exception e) {
            Toast.makeText(appContext, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return null;
    }
}
