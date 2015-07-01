package com.flipkart.layoutengine.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.flipkart.layoutengine.EventType;
import com.flipkart.layoutengine.ParserContext;
import com.flipkart.layoutengine.builder.DefaultLayoutBuilderFactory;
import com.flipkart.layoutengine.builder.LayoutBuilder;
import com.flipkart.layoutengine.builder.LayoutBuilderCallback;
import com.flipkart.layoutengine.provider.GsonProvider;
import com.flipkart.networking.API;
import com.flipkart.networking.request.BaseRequest;
import com.flipkart.networking.request.HomeRequest;
import com.flipkart.networking.request.components.OnRequestErrorListener;
import com.flipkart.networking.request.components.OnRequestFinishListener;
import com.flipkart.networking.request.components.RequestError;
import com.flipkart.networking.response.HomeResponse;
import com.flipkart.preview.ImageGeneratorService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;



public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler handler = new Handler();
        if(savedInstanceState==null) {

           fireRequest();
        }
        Intent i=new Intent(this, ImageGeneratorService.class);
        startService(i);

    }

    private void fireRequest() {
        HomeRequest request = new HomeRequest();
        request.setOnResponseListener(createOnResponse());
        request.setOnErrorListener(new OnRequestErrorListener<HomeResponse>() {
            @Override
            public void onRequestError(BaseRequest<HomeResponse> request, RequestError error) {
                Toast.makeText(MainActivity.this,"Request error "+error.getReason(),Toast.LENGTH_LONG).show();
            }
        });
        API.getInstance(this.getApplicationContext()).processAsync(request);
    }

    private OnRequestFinishListener<HomeResponse> createOnResponse() {
        return new OnRequestFinishListener<HomeResponse>() {
            @Override
            public void onRequestFinish(final BaseRequest<HomeResponse> request) {

                MainActivity.this.getWindow().getDecorView().postDelayed(new Runnable() {
                    @Override
                    public void run() {



                        HomeResponse response = request.getResponse();
                        JsonObject layout = response.getResponse().getLayout();
                        long startTimeMillis = System.currentTimeMillis();
                        Log.d(TAG,"layout build start "+startTimeMillis);
                        LayoutBuilder builder = new DefaultLayoutBuilderFactory().createDataParsingLayoutBuilder(MainActivity.this, new GsonProvider(response.getResponse().getData()));
                        builder.setListener(createCallback());
                        FrameLayout container = new FrameLayout(MainActivity.this);
                        View view = builder.build((ViewGroup)MainActivity.this.getWindow().getDecorView(),layout);
                        long endTimeMillis = System.currentTimeMillis();
                        Log.d(TAG,"layout build end "+endTimeMillis);
                        long timeTaken = endTimeMillis - startTimeMillis;
                        Log.d(TAG,"time taken "+timeTaken+" ms");
                        Toast.makeText(MainActivity.this,"Time taken to render "+timeTaken+" ms",Toast.LENGTH_SHORT).show();
                        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                        if(view!=null)
                        {
                            container.addView(view,layoutParams);
                        }
                        MainActivity.this.setContentView(container);
                    }
                },0);

            }
        };
    }

    private LayoutBuilderCallback createCallback()
    {
        return new LayoutBuilderCallback() {

            @Override
            public void onUnknownAttribute(ParserContext context, String attribute, JsonElement element, JsonObject object, View view, int childIndex) {

            }

            @Override
            public View onUnknownViewType(ParserContext context, String viewType, JsonObject object, ViewGroup parent, int childIndex) {
                return null;
            }

            @Override
            public View onEvent(ParserContext context, View view, JsonElement attributeValue, EventType eventType) {
                return null;
            }
        };
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            fireRequest();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG,"key down "+keyCode);
        if(keyCode == KeyEvent.KEYCODE_R)
        {
            MainActivity.this.setContentView(new FrameLayout(this));
            fireRequest();
        }
        return super.onKeyDown(keyCode, event);
    }
}
