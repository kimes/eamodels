package ph.easyaf.eamodels.models;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class Area extends EasyAFModel {

    @Bindable
    private String mongoId, name, merchant;

    @Bindable
    private ArrayList<Trip> trips;

    public Area() {};

    public Area(int id) {
        super(id);
    }

    public Area(Parcel parcel) {
        String[] strings = new String[3];
        parcel.readStringArray(strings);
        mongoId = strings[0];
        name = strings[1];
        merchant = strings[2];

        Trip[] tripsArray = (Trip[])parcel.readParcelableArray(Trip.class.getClassLoader());

        trips = new ArrayList<>();
        Collections.addAll(trips, tripsArray);
    }

    public Area(JSONObject object) {
        try {
            if (object.has("_id")) mongoId = object.getString("_id");
            if (object.has("name")) name = object.getString("name");
            if (object.has("merchant")) merchant = object.getString("merchant");

            if (object.has("trips")) {
                JSONArray array = object.getJSONArray("trips");
                trips = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    trips.add(new Trip(array.getJSONObject(i)));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("_id", mongoId);
            object.put("name", name);
            object.put("merchant", merchant);

            JSONArray tripsArray = new JSONArray();
            for (int i = 0; i < trips.size(); i++) {
                tripsArray.put(trips.get(i).toJSON());
            }
            object.put("trips", tripsArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeStringArray(new String[] { mongoId, name, merchant });

        Trip[] tripsArray = new Trip[trips.size()];
        for (int i = 0; i < trips.size(); i++) {
            tripsArray[i] = trips.get(i);
        }
        dest.writeParcelableArray(tripsArray, flags);
        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dest.writeParcelableList(trips, flags);
        } else {
        } */
    }

    @Override
    public int describeContents() { return 0; }

    public String getMongoId() { return mongoId; }
    public String getName() { return name; }
    public String getMerchant() { return merchant; }
    public ArrayList<Trip> getTrips() { return trips; }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
        notifyPropertyChanged(BR.mongoId);
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
        notifyPropertyChanged(BR.merchant);
    }

    public void setTrips(ArrayList<Trip> trips) {
        this.trips = trips;
        notifyPropertyChanged(BR.trips);
    }

    public static final Parcelable.Creator<Area> CREATOR = new Creator<>() {
        @Override
        public Area createFromParcel(Parcel source) {
            return new Area(source);
        }

        @Override
        public Area[] newArray(int size) { return new Area[size]; }
    };
}
