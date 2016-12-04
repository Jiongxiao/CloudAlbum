package com.room2201.camerafileupload;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.VideoView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class WatchVideo extends Activity {
    /** Called when the activity is first created. */

    Button playButton ;
    VideoView videoView ;
    EditText rtspUrl ;
    RadioButton radioStream;
    RadioButton radioFile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_video_view_demo);
        String fileName = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/"+Config.IMAGE_DIRECTORY_NAME+"/"+
                Config.DOC_NAME;
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        InputStream inputStream = getResources().openRawResource(R.raw.li);
        //ScrollView scrollView=(ScrollView)this.findViewById(R.id.scrollview);
        ScrollView scrollView=new ScrollView(this);

        LinearLayout linearLayout=new LinearLayout(this);

        //  ScrollView
        // scrollView.setLayoutParams(lp);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams videolp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,600 );
        LinearLayout.LayoutParams empty = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,100 );
        lp.setMargins(100, 30, 0, 0);
        linearLayout.setLayoutParams(lp);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        ArrayList<String> url=getString(inputStream);
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<ImageView> image=new ArrayList<ImageView>();
        for(int i=0;i<url.size();i++){
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//获取网络视频
            retriever.setDataSource(url.get(i), new HashMap<String, String>());
//获取本地视频
//retriever.setDataSource(url);
            //   Bitmap bitmap2= ThumbnailUtils.createVideoThumbnail(this,, MediaStore.Video.Thumbnails.MINI_KIND);

            //Bitmap bitmap2=createVideoThumbnail("http://i.cs.hku.hk/~yxchen/AndroidFileUpload/Upload/VID_20161127_203946.mp4", 50, 30) ;
            Bitmap bitmap = retriever.getFrameAtTime();
            // bitmap.createBitmap(bitmap,200,30,30,10);

         /*   Rect rect = new Rect((int) distance, (int) endHeight,
                    (int) (distance + viewWidth), (int) startHeight);
            canvas.drawBitmap(bar_bitmap, null, rect, null);
*/

            //Log.i("a",bitmap2+"");
            ImageView temp=new ImageView(this);
            temp.setBackgroundColor(Color.BLACK);
            temp.setImageBitmap(bitmap);
            final String j=url.get(i);

            temp.setOnClickListener(new View.OnClickListener() {
                //int j=i;

                @Override
                public void onClick(View arg0) {
                    Uri uri = Uri.parse(j);
                    // 调用系统自带的播放器来播放流媒体视频
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Log.v("URI:::::::::", uri.toString());
                    intent.setDataAndType(uri, "video/mp4");
                    startActivity(intent);
                    //videoViews.get(i).start();
                    //  TextView textview = (TextView)findViewById(R.id.textView1);
                    //textview.setText("你点击了Button");
                }
            });
            image.add(temp);

            retriever.release();
        }

      /*  String path  = Environment.getExternalStorageDirectory().getPath();
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(path+"/123.rmvb");

        Bitmap bitmap = media.getFrameAtTime();

        image = (ImageView)this.findViewById(R.id.imageView1);
        image.setImageBitmap(bitmap);
*/

      /*  final ArrayList<VideoView> videoViews =new ArrayList<VideoView>();
        for(int i=0;i<url.size();i++){
            String videoUrl2 =url.get(i);
            Uri uri = Uri.parse( videoUrl2 );
            VideoView temp=new VideoView(this);
            temp.setVideoURI(uri);
            videoViews.add(temp);


        }*/
        // linearLayout.addView(scrollView,videolp);
        // final ArrayList<Integer> place =new ArrayList<Integer>();
        for(int i=0;i<image.size();i++){
            //   place.add(i);
            // ScrollView scrollView=(ScrollView)this.findViewById(R.id.scrollview);
            //   scrollView.addView(videoViews.get(i),videolp);
            linearLayout.addView(image.get(i),videolp);
            LinearLayout linearLayout1=new LinearLayout(this);
            linearLayout.addView(linearLayout1,empty);
            /*linearLayout.addView(image.get(i),videolp);
            linearLayout.addView(image.get(i),videolp);
            linearLayout.addView(image.get(i),videolp);
            linearLayout.addView(image.get(i),videolp);
            linearLayout.addView(image.get(i),videolp);
            linearLayout.addView(image.get(i),videolp);
            linearLayout.addView(image.get(i),videolp);*/
            //  linearLayout.addView(videoViews.get(i),videolp);
            //  Button button=new Button(this);

            //final int j=1;
            /*button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    videoViews.get(i).start();
                  //  TextView textview = (TextView)findViewById(R.id.textView1);
                    //textview.setText("你点击了Button");
                }
            });*/
            //linearLayout.addView(button);

            //    videoViews[0].start();

        }
        scrollView.addView(linearLayout,lp);
        //   linearLayout.addView(scrollView,videolp);
        // scrollView.addView(linearLayout,lp);
        setContentView(scrollView);
        //  videoViews.get(1).start();
        //   videoView = (VideoView)this.findViewById(R.id.rtsp_player);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)

    private Bitmap createVideoThumbnail(String url, int width, int height) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }

    public static ArrayList<String> getString(InputStream inputStream) {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "gbk");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        ArrayList<String> sb = new ArrayList<String>();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.add(line);
                //sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb;
    }

    //play rtsp stream
    private void PlayRtspStream(String rtspUrl){
        videoView.setVideoURI(Uri.parse(rtspUrl));
        videoView.requestFocus();
        videoView.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(WatchVideo.this, MainActivity.class);
        startActivity(i);
    }
}
