package com.aands.wefamily.Contact;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.aands.wefamily.Family.Family;
import com.aands.wefamily.R;
import com.aands.wefamily.Record.MyDialog;
import com.aands.wefamily.Contact.ContactActivity;
import com.aands.wefamily.Contact.PhotoDialog;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static com.aands.wefamily.Constants.ADD_CONTACT_PERSON;
import static com.aands.wefamily.Constants.CHOOSE_PHOTO;
import static com.aands.wefamily.Constants.CROP_PHOTO;
import static com.aands.wefamily.Constants.EDIT_CONTACT_PERSON;
import static com.aands.wefamily.Constants.TAKE_PHOTO;

/**
 * Created by LemonNoel on 2017/5/14.
 */

public class ContactActivity extends AppCompatActivity {
    private Family family = null;
    private Context mContext = this;
    private Button save, delete, back;
    private ImageView photo;
    private EditText name, label, number, location, weather;

    private Uri imageUri;//第一个uri是用来存储拍照后得到的照片
    private Uri imageUri2;//第二个用来存储拍照后照片裁剪得到的图片
    private Uri imageUri3;//第三个用来存储从相册中获取的图片裁剪后得到的图片
    private static int image_name = 0;
    final private String image_pre = "WeFamily";

    private Context getContext() {
        return mContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        back = (Button) findViewById(R.id.return_home);
        save = (Button) findViewById(R.id.save_contact);
        delete = (Button) findViewById(R.id.delete_contact);
        photo = (ImageView) findViewById(R.id.contact_photo);
        name = (EditText) findViewById(R.id.contact_name);
        label = (EditText) findViewById(R.id.contact_label);
        number = (EditText) findViewById(R.id.contact_number);
        location = (EditText) findViewById(R.id.contact_location);
        weather = (EditText) findViewById(R.id.contact_weather);

        Intent intent = getIntent();
        int type = intent.getIntExtra("type", ADD_CONTACT_PERSON);
        final String contact_name = intent.getStringExtra("name");

        if (type == EDIT_CONTACT_PERSON){
            List<Family> tmpFamily = DataSupport.where("name = ?", contact_name).find(Family.class);
            save.setEnabled(true);
            if (!tmpFamily.isEmpty()) {
                family = tmpFamily.get(0);
                name.setText(family.getName());
                label.setText(family.getLabel());
                number.setText(family.getNumber());
                location.setText(family.getLocation());
                weather.setText(family.getWeather());
                if (family.getImageNew() == null){
                    photo.setImageResource(family.getImageId());
                } else {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(family.getImageNew()));
                        photo.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
                delete.setEnabled(true);
            } else {
                family = new Family();
                name.setHint(R.string.default_name);
                label.setHint(R.string.default_label);
                number.setHint(R.string.default_number);
                location.setHint(R.string.default_location);
                weather.setHint(R.string.default_weather);
                photo.setImageResource(R.drawable.default_portrait);
                delete.setEnabled(false);
            }
        } else {
            family = new Family();
            name.setHint(R.string.default_name);
            label.setHint(R.string.default_label);
            number.setHint(R.string.default_number);
            location.setHint(R.string.default_location);
            weather.setHint(R.string.default_weather);
            photo.setImageResource(R.drawable.default_portrait);
            delete.setEnabled(false);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (save.isEnabled()) {
                    family.save();
                    finish();
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (delete.isEnabled()) {
                    final MyDialog myDialog = new MyDialog(getContext(), R.style.MyDialog);//设置自定义背景
                    myDialog.setTitle("删除");
                    myDialog.setMsg("确认删除" + contact_name + "？");
                    myDialog.setOnNegateListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myDialog.dismiss();
                        }
                    });
                    myDialog.setOnPositiveListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            family.delete();
                            myDialog.dismiss();
                            finish();
                        }
                    });
                    myDialog.show();
                }
            }
        });
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PhotoDialog myDialog = new PhotoDialog(getContext(), R.style.MyDialog);//设置自定义背景
                myDialog.setOnNegateListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //相册选取后裁剪，存储的图片
                        image_name = image_name + 1;
                        File outputImage3 = new File(Environment.getExternalStorageDirectory(), image_pre + image_name + ".jpg");
                        try {
                            if (outputImage3.exists()) {
                                outputImage3.delete();
                            }
                            outputImage3.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        imageUri3 = Uri.fromFile(outputImage3);
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(intent, CHOOSE_PHOTO);
                        myDialog.dismiss();
                    }
                });
                myDialog.setOnPositiveListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        image_name = image_name + 1;
                        File outputImage = new File(Environment.getExternalStorageDirectory(), image_pre + image_name + ".jpg");
                        try {
                            if (outputImage.exists()) {
                                outputImage.delete();
                            }
                            outputImage.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //拍照后裁剪，存储的图片
                        image_name = image_name + 1;
                        File outputImage2 = new File(Environment.getExternalStorageDirectory(), image_pre + image_name + ".jpg");
                        try {
                            if (outputImage2.exists()) {
                                outputImage2.delete();
                            }
                            outputImage2.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        imageUri = Uri.fromFile(outputImage);
                        imageUri2 = Uri.fromFile(outputImage2);
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, TAKE_PHOTO);
                        myDialog.dismiss();
                    }
                });
                myDialog.show();
            }
        });
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {  }

            @Override
            public void afterTextChanged(Editable s) {
                String tmpStr = name.getText().toString();
                if (!tmpStr.isEmpty()) {
                    family.setName(getContext(), tmpStr);
                    save.setEnabled(true);
                }
            }
        });
        label.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {  }

            @Override
            public void afterTextChanged(Editable s) {
                String tmpStr = label.getText().toString();
                if (!tmpStr.isEmpty()) {
                    family.setLabel(getContext(), tmpStr);
                    save.setEnabled(true);
                }
            }
        });
        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {  }

            @Override
            public void afterTextChanged(Editable s) {
                String tmpStr = number.getText().toString();
                if (!tmpStr.isEmpty()) {
                    if (family.setNumber(getContext(), tmpStr)) {
                        save.setEnabled(true);
                        if (!family.getLocation().isEmpty()) {
                            location.setText(family.getLocation());
                        }
                        if (!family.getWeather().isEmpty()) {
                            weather.setText(family.getWeather());
                        }
                    }
                }
            }
        });
        location.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {  }

            @Override
            public void afterTextChanged(Editable s) {
                String tmpStr = location.getText().toString();
                if (!tmpStr.isEmpty()) {
                    family.setLocation(getContext(), tmpStr);
                    save.setEnabled(true);
                }
            }
        });
        weather.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {  }

            @Override
            public void afterTextChanged(Editable s) {
                String tmpStr = weather.getText().toString();
                if (!tmpStr.isEmpty()) {
                    family.setWeather(getContext(), tmpStr);
                    save.setEnabled(true);
                }
            }
        });
    }

    public static void actionStart(Context context, int type, String name) {
        Intent intent = new Intent(context, ContactActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("name", name);
        context.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("crop", "true");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri2);
                    intent.putExtra("return-data", false);
                    intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                    intent.putExtra("noFaceDetection", true); //是否去除面部检测， 如果你需要特定的比例去裁剪图片，那么这个一定要去掉，因为它会破坏掉特定的比例。
                    startActivityForResult(intent, CROP_PHOTO); //启动裁剪程序
                }
                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(imageUri2));
                        photo.setImageBitmap(bitmap); //返回imageView
                        family.setImageNew(imageUri2);
                        family.save();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    cropImage(handleImage(data));//从相册中选取图片，调用裁剪方法
                }
                break;
            case 4:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(imageUri3));
                        photo.setImageBitmap(bitmap);// 返回imageView
                        family.setImageNew(imageUri3);
                        family.save();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    private Uri handleImage(Intent data) { //解析Uri
        String imagePath = null;
        Uri uri = data.getData();
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(getContext(), uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                    String id = docId.split(":")[1];
                    String selecion = MediaStore.Images.Media._ID + "=" + id;
                    imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selecion);
                } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                            Long.valueOf(docId));
                    imagePath = getImagePath(contentUri, null);
                }
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                imagePath = getImagePath(uri, null);
            } else {                                //解析Gallery中图片的Uri
                imagePath = getImagePath(uri, null);
            }
        } else {
            imagePath = getImagePath(uri, null);
        }
        return Uri.fromFile(new File(imagePath)); //返回getImagePath方法得到的真实路径所转化的Uri
//        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) { //得到真实路径
        String path = null;
        Cursor cursor = getContext().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) { //第一行代码书中的代码，实际上并没有用
        if (imagePath != null) {
            Log.d("MainActivity", imagePath);
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            photo.setImageBitmap(bitmap);
            family.setImageNew(Uri.parse(imagePath));
            family.save();

        } else {
            Toast.makeText(getContext(), "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    private void cropImage(Uri srcUri) { //为相册中图片所准备的裁剪方法
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(srcUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri3);
        intent.putExtra("return-data", false);
        startActivityForResult(intent, 4);
    }
}
