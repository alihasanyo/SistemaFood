package mobile.sistema.com.foodlist.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mobile.sistema.com.foodlist.DetailFoodActivity;
import mobile.sistema.com.foodlist.Model.ListFoodModel;
import mobile.sistema.com.foodlist.R;

public class AllListFoodAdapter extends ArrayAdapter<ListFoodModel> {

    private final Activity context;
    private ArrayList<ListFoodModel> mData;

    public AllListFoodAdapter(Activity context, ArrayList<ListFoodModel> data) {
        super(context, R.layout.item_all_list_food, data);
        this.context = context;
        this.mData = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_all_list_food, null, true);
        rowView.setId(position);

        ImageView iv_food = rowView.findViewById(R.id.imgFood);
        TextView title_food = rowView.findViewById(R.id.titleFood);
        LinearLayout lay_Allfood = rowView.findViewById(R.id.lay_all_food);

        ListFoodModel m = mData.get(position);

        title_food.setText(m.getTitleFood());
        Glide.with(context)
                .load(m.getImgFood())
                .into(iv_food);

        lay_Allfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListFoodModel m = getItem(position);

                Intent detailFood = new Intent(context, DetailFoodActivity.class);
                detailFood.putExtra("id_FOOD", m.getIdFood());
                detailFood.putExtra("title_FOOD", m.getTitleFood());
                context.startActivity(detailFood);

            }
        });

        return rowView;
    }

}
