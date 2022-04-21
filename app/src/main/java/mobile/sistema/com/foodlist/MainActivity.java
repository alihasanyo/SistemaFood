package mobile.sistema.com.foodlist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mobile.sistema.com.foodlist.Adapter.AllListFoodAdapter;
import mobile.sistema.com.foodlist.Model.ListFoodModel;

public class MainActivity extends AppCompatActivity {

    private Activity activity;
    
    private LinearLayout lay_top;
    private ListView list_food;
    private ArrayList<ListFoodModel> mList;
    private AllListFoodAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;

        lay_top = findViewById(R.id.lay_top);
        list_food = findViewById(R.id.list_all_food);
        
        getAllListFood();
    }

    private void getAllListFood() {
        lay_top.setVisibility(View.VISIBLE);
        list_food.setVisibility(View.GONE);

        mList = new ArrayList<>();
        mAdapter = new AllListFoodAdapter(activity, mList);
        list_food.setAdapter(mAdapter);

        String url = "https://www.themealdb.com/api/json/v1/1/filter.php?c=Seafood";

        JsonObjectRequest oGetListFood = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    Log.e("RESPONSE", response.toString());
                    lay_top.setVisibility(View.GONE);
                    list_food.setVisibility(View.VISIBLE);

                    JSONArray arrData = response.getJSONArray("meals");
                    if (arrData.length() > 0){
                        for (int i = 0; i < arrData.length(); i++){

                            JSONObject arrDetil = arrData.getJSONObject(i);

                            String img_Food = arrDetil.getString("strMealThumb");
                            String title_Food = arrDetil.getString("strMeal");
                            String id_Food = arrDetil.getString("idMeal");

                            mList.add(new ListFoodModel(id_Food, title_Food, img_Food));
                        }
                    }

                    mAdapter = new AllListFoodAdapter(activity, mList);
                    mAdapter.notifyDataSetChanged();

                }catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                lay_top.setVisibility(View.GONE);
                list_food.setVisibility(View.GONE);
            }
        }) ;

        Volley.newRequestQueue(activity).add(oGetListFood);
    }
}