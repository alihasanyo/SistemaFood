package mobile.sistema.com.foodlist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DetailFoodActivity extends AppCompatActivity {

    private Activity activity;

    private TextView tv_catFood, tv_areaFood, tv_ingredients, tv_detilInstruct, tv_titleFood, tv_detilViewFood;
    private ImageView iv_detailFood;
    private LinearLayout lay_moreYoutube, lay_progFood, lay_content;

    private String getIDFOOD, getTITLEFOOD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_food);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity = this;

        getIDFOOD = getIntent().getStringExtra("id_FOOD");
        getTITLEFOOD = getIntent().getStringExtra("title_FOOD");

        getSupportActionBar().setTitle(getTITLEFOOD);

        tv_catFood = findViewById(R.id.tv_catFood);
        tv_areaFood = findViewById(R.id.tv_areaFood);
        tv_ingredients = findViewById(R.id.tv_ingredients);
        tv_detilInstruct = findViewById(R.id.tv_detilInstruct);
        iv_detailFood = findViewById(R.id.iv_detailFood);
        lay_moreYoutube = findViewById(R.id.lay_moreYoutube);
        tv_titleFood = findViewById(R.id.tv_titleFood);
        lay_progFood = findViewById(R.id.lay_progFood);
        lay_content = findViewById(R.id.lay_content);
        tv_detilViewFood = findViewById(R.id.tv_detilViewFood);

        getDetailFood(getIDFOOD);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return false;
    }

    private void getDetailFood(String idFood) {
        lay_progFood.setVisibility(View.VISIBLE);
        lay_content.setVisibility(View.GONE);

        String url = "https://www.themealdb.com/api/json/v1/1/lookup.php"+"?i="+idFood;

        JsonObjectRequest oGetDetil = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    Log.e("RESPONSE", response.toString());
                    lay_progFood.setVisibility(View.GONE);
                    lay_content.setVisibility(View.VISIBLE);

                    JSONArray arrData = response.getJSONArray("meals");
                    if (arrData.length() > 0){
                        for (int i = 0; i < arrData.length(); i++){

                            JSONObject arrDetil = arrData.getJSONObject(i);

                            String detil_title = arrDetil.getString("strMeal");
                            String detil_cat = arrDetil.getString("strCategory");
                            String detil_area = arrDetil.getString("strArea");
                            String detil_instr = arrDetil.getString("strInstructions");
                            String detil_img = arrDetil.getString("strMealThumb");
                            String detil_yt = arrDetil.getString("strYoutube");
                            String detil_view = arrDetil.getString("strSource");

                            ShowContentFood(detil_title, detil_cat, detil_area, detil_instr, detil_img, detil_yt, detil_view);
                        }
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                lay_progFood.setVisibility(View.GONE);
                lay_content.setVisibility(View.GONE);
            }
        }) ;

        Volley.newRequestQueue(activity).add(oGetDetil);
    }

    private void ShowContentFood(String detil_title, String detil_cat, String detil_area, String detil_instr, String detil_img, String detil_yt, String detil_view) {

        tv_titleFood.setText(detil_title);
        tv_catFood.setText(": "+detil_cat);
        tv_areaFood.setText(": "+detil_area);
        tv_detilInstruct.setText(Html.fromHtml(detil_instr));

        lay_moreYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(detil_yt));
                    startActivity(browserIntent);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        tv_detilViewFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent browserDetil = new Intent(Intent.ACTION_VIEW, Uri.parse(detil_view));
                    startActivity(browserDetil);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        Glide.with(activity)
                .load(detil_img)
                .into(iv_detailFood);
        
    }
}