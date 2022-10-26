package com.example.healthappauth;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MealRVAdapter extends RecyclerView.Adapter<MealRVAdapter.ViewHolder> {
    // creating variables for our list, context, interface and position.
    private ArrayList<MealRVModal> mealRVModalArrayList;
    private Context context;
    private MealClickInterface mealClickInterface;
    int lastPos = -1;

    // creating a constructor.
    public MealRVAdapter(ArrayList<MealRVModal> mealRVModalArrayList, Context context, MealClickInterface mealClickInterface) {
        this.mealRVModalArrayList = mealRVModalArrayList;
        this.context = context;
        this.mealClickInterface = mealClickInterface;
    }


    @NonNull
    @Override
    public MealRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout file on below line.
        View view =LayoutInflater.from(context).inflate(R.layout.course_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull MealRVAdapter.ViewHolder holder, int position) {
        // setting data to our recycler view item on below line.
        MealRVModal mealRVModal = mealRVModalArrayList.get(position);
        holder.mealTV.setText(mealRVModal.getMealName());
        holder.mealPriceTV.setText(("Cal:-" + mealRVModal.getMealPrice()));
        Picasso.get().load(mealRVModal.getMealImg()).into(holder.mealIV);
        // adding animation to recycler view item on below line.
        setAnimation(holder.itemView, position);
        holder.mealIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealClickInterface.onMealClick(position);
            }
        });
    }

    private void setAnimation(View itemView, int position) {
        if (position > lastPos) {
            // on below line we are setting animation.
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }

    @Override
    public int getItemCount() {
        return mealRVModalArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // creating variable for our image view and text view on below line.
        private ImageView mealIV;
        private TextView mealTV, mealPriceTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing all our variables on below line.
            mealIV = itemView.findViewById(R.id.idIVMeal);
            mealTV = itemView.findViewById(R.id.idTVMealName);
            mealPriceTV = itemView.findViewById(R.id.idTVMealPrice);
        }
    }

    // creating a interface for on click
    public interface MealClickInterface {
        void onMealClick(int position);
    }
}