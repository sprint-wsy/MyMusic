package com.silence.mymusic.bean.movie;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wushiyu on 2017/7/6.
 */

public class RatingBean implements Parcelable{
    /**
     * max : 10
     * average : 6.9
     * stars : 35
     * min : 0
     */
    private int max;
    private double average;
    private String stars;
    private int min;

    protected RatingBean(Parcel in) {
        max = in.readInt();
        average = in.readDouble();
        stars = in.readString();
        min = in.readInt();
    }

    public static final Creator<RatingBean> CREATOR = new Creator<RatingBean>() {
        @Override
        public RatingBean createFromParcel(Parcel in) {
            return new RatingBean(in);
        }

        @Override
        public RatingBean[] newArray(int size) {
            return new RatingBean[size];
        }
    };

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(max);
        dest.writeDouble(average);
        dest.writeString(stars);
        dest.writeInt(min);
    }
}
