package com.example.healthappauth;


import android.os.Parcel;
import android.os.Parcelable;

public class MealRVModal implements Parcelable {

    private String mealName;
    private String mealDescription;
    private String mealPrice;
    private String mealImg;
    private String mealId;


    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }


    // creating an empty constructor.
    public MealRVModal() {

    }

    protected MealRVModal(Parcel in) {
        mealName = in.readString();
        mealId = in.readString();
        mealDescription = in.readString();
        mealPrice = in.readString();
        mealImg = in.readString();

    }

    public static final Creator<MealRVModal> CREATOR = new Creator<MealRVModal>() {
        @Override
        public MealRVModal createFromParcel(Parcel in) {
            return new MealRVModal(in);
        }

        @Override
        public MealRVModal[] newArray(int size) {
            return new MealRVModal[size];
        }
    };

    // creating getter and setter methods.
    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getMealDescription() {
        return mealDescription;
    }

    public void setMealDescription(String mealDescription) {
        this.mealDescription = mealDescription;
    }

    public String getMealPrice() {
        return mealPrice;
    }

    public void setMealPrice(String mealPrice) {
        this.mealPrice = mealPrice;
    }


    public String getMealImg() {
        return mealImg;
    }

    public void setMealImg(String mealImg) {
        this.mealImg = mealImg;
    }


    public MealRVModal(String mealId, String mealName, String mealDescription, String mealPrice, String mealImg) {
        this.mealName = mealName;
        this.mealId = mealId;
        this.mealDescription = mealDescription;
        this.mealPrice = mealPrice;
        this.mealImg = mealImg;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mealName);
        dest.writeString(mealId);
        dest.writeString(mealDescription);
        dest.writeString(mealPrice);
        dest.writeString(mealImg);

    }
}