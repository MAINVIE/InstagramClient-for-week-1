package com.codepath.instagramclient;


//import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
//import android.support.v7.app.ActionBarActivity;
//import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PhotosActivity extends ActionBarActivity {
    public static final String client_Id ="c2a22a4c3af843e7bbfd20bf63f9d695";
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aphotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        // SEND OUT API REQUEST to Popular Photos
        photos= new ArrayList<>();
        //create adapter linking it to the
        aphotos= new InstagramPhotosAdapter(this,photos);
        // find the list view
        ListView LVPhotos= (ListView) findViewById(R.id.LVPhotos);
        // set the adapter binding it to listview
        LVPhotos.setAdapter(aphotos);
        // fetch pop photos
        fetchPopularPhotos();
    }
    // api request

    public void fetchPopularPhotos() {
       /*Client ID
        c2a22a4c3af843e7bbfd20bf63f9d695


        https://api.instagram.com/v1/media/popular?access_token=ACCESS-TOKE
        response:
        -type: {data=> [x] => type}(image or video)
        -url:{data=> images=>standard_resolution=>url}
        -caption:{data=> [x] => caption =>text}}
        -author:{data=>[x]=>user=>username}*/
        String url = "https://api.instagram.com/v1/media/popular?client_id" + client_Id;
        AsyncHttpClient Client = new AsyncHttpClient();
        Client.get(url, null, new JsonHttpResponseHandler() {
            @Override
            //returning thing
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray photosJson = null;
                try {
                    photosJson = response.getJSONArray("data"); // getting Array of item;
                    //iterate array post
                    for (int i = 0; i < photosJson.length(); i++) {
                        // get Json object
                        JSONObject photoJson = photosJson.getJSONObject(i);
                        //decoding attributes
                        InstagramPhoto photo = new InstagramPhoto();
                        // get ther username from data
                        photo.username = photoJson.getJSONObject("user").getString("username");
                        // get caption
                        photo.caption = photoJson.getJSONObject("caption").getString("text");
                        // type
                        // photo.type =photoJson.getJSONObject("caption").getString("text");
                        photo.imageurl = photoJson.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        //height
                        photo.imageheight = photoJson.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        //likes
                        photo.likescount = photoJson.getJSONObject("likes").getInt("count");
                        // add decoded object to the photos
                        photos.add(photo);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
                // call bac
                aphotos.notifyDataSetChanged();

            }
            //returning nada
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
