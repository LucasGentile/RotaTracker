package br.edu.tasima.rotatracker.model;

import android.os.Parcel;
import android.os.Parcelable;

import br.edu.tasima.rotatracker.LogHelper;

public final class LocationInfo implements Parcelable {
    private int mId;
    private String mProvider;
    private double mLatitude;
    private double mLongitude;
    private float mAccuracy;
    private long mTime;

    public LocationInfo(int mId, String mProvider, double mLatitude, double mLongitude, float mAccuracy, long mTime) {
        this.mId = mId;
        this.mProvider = mProvider;
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
        this.mAccuracy = mAccuracy;
        this.mTime = mTime;
    }

    public LocationInfo(String mProvider, double mLatitude, double mLongitude, float mAccuracy, long mTime) {
        this.mProvider = mProvider;
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
        this.mAccuracy = mAccuracy;
        this.mTime = mTime;
    }

    private LocationInfo(Parcel in) {
        mId = in.readInt();
        mProvider = in.readString();
        mLatitude = in.readDouble();
        mLongitude = in.readDouble();
        mAccuracy = in.readFloat();
        mTime = in.readInt();
    }

    public int getId() {
        return mId;
    }

    public String getProvider() {
        return mProvider;
    }

    public void setProvider(String provider) {
        this.mProvider = provider;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        this.mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        this.mLongitude = longitude;
    }

    public float getAccuracy() {
        return mAccuracy;
    }

    public void setAccuracy(float accuracy) {
        this.mAccuracy = accuracy;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        this.mTime = time;
    }

    @Override
    public String toString() {
        return LogHelper.FormatLocationInfo(mProvider, mLatitude, mLongitude, mAccuracy, mTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mProvider);
        dest.writeDouble(mLatitude);
        dest.writeDouble(mLongitude);
        dest.writeFloat(mAccuracy);
        dest.writeLong(mTime);
    }

    public final static Parcelable.Creator<LocationInfo> CREATOR =
            new Parcelable.Creator<LocationInfo>() {

                @Override
                public LocationInfo createFromParcel(Parcel source) {
                    return new LocationInfo(source);
                }

                @Override
                public LocationInfo[] newArray(int size) {
                    return new LocationInfo[size];
                }
            };
}
