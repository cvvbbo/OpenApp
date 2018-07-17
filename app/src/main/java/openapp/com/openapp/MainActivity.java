package openapp.com.openapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //系统中相册获取文件夹里面分享
        //ShareAtSystem();

        //在其他应用中添加打开方式 
        AddOpenStyleForOtherApp();
    }

    private void AddOpenStyleForOtherApp() {
        Intent intent = getIntent();
        String action = intent.getAction();
        if(intent.ACTION_VIEW.equals(action)) {
           // Log.e("",intent.getDataString());
            Uri data = intent.getData();
            //处理一下编码的路径 7.17
            String path = Uri.decode(data.getEncodedPath());
            Log.e("",path);
        }
    }

    private void ShareAtSystem() {
        Intent itnIn = getIntent();
        Bundle extras = itnIn.getExtras();
        String action = itnIn.getAction();
        if (Intent.ACTION_SEND.equals(action)) {
            if (extras.containsKey(Intent.EXTRA_STREAM)) {
                try {
                    // Get resource path from intent
                    Uri uri2 = extras.getParcelable(Intent.EXTRA_STREAM);
                    String path = getRealPathFromURI(MainActivity.this, uri2);
                    Log.e("",path);
                } catch (Exception e) {
                    Log.e(this.getClass().getName(), e.toString());
                }
            }
        }
    }


    public String getRealPathFromURI(Activity act, Uri contentUri) {

        // can post image
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = act.managedQuery(contentUri, proj, // Which columns to return
                null, // WHERE clause; which rows to return (all rows)
                null, // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        if (cursor==null) {
            String path = contentUri.getPath();
            return path;
        }

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);

    }
}
