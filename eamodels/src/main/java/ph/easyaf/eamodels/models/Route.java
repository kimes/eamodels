package ph.easyaf.eamodels.models;

import android.os.Parcel;

import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import org.json.JSONException;
import org.json.JSONObject;

public class Route extends EasyAFModel {

    @Bindable
    private boolean selected;

    // 0 = Boarding, 1 = Drop Off
    private int type;
    @Bindable
    private double fareDeduct = 0;
    @Bindable
    private String name;

    public Route() {}
    public Route(int id) { super(id); }
    public Route(int id, int type) {
        super(id);
        this.type = type;
    }

    public Route(Parcel parcel) {
        boolean[] booleans = new boolean[1];
        parcel.readBooleanArray(booleans);
        selected = booleans[0];

        int[] ints = new int[2];
        parcel.readIntArray(ints);
        id = ints[0];
        type = ints[1];

        fareDeduct = parcel.readDouble();
        name = parcel.readString();
    }

    public Route(Route route) {
        id = route.getId();
        type = route.getType();
        fareDeduct = route.getFareDeduct();
        name = route.getName();
    }

    public Route(JSONObject object) {
        try {
            if (object.has("fare_deduct")) fareDeduct = object.getDouble("fare_deduct");
            if (object.has("name")) name = object.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isSame(Route route) {
        if (id != route.getId()) return false;
        if (type != route.getType()) return false;
        if (fareDeduct != route.getFareDeduct()) return false;
        if (!name.equals(route.getName())) return false;
        return true;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeBooleanArray(new boolean[] { selected });
        parcel.writeIntArray(new int[] { id, type });
        parcel.writeDouble(fareDeduct);
        parcel.writeString(name);
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("fare_deduct", fareDeduct);
            object.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public int describeContents() { return 0; }

    public boolean isSelected() { return selected; }
    public int getType() { return type; }
    public double getFareDeduct() { return fareDeduct; }
    public String getName() { return name; }
    public void setSelected(boolean selected) {
        this.selected = selected;
        notifyPropertyChanged(BR.selected);
    }
    public void setType(int type) { this.type = type; }
    public void setFareDeduct(double fareDeduct) {
        this.fareDeduct = fareDeduct;
        notifyPropertyChanged(BR.fareDeduct);
    }
    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public static final Creator<Route> CREATOR = new Creator<Route>() {
        public Route createFromParcel(Parcel parcel) { return new Route(parcel); }
        public Route[] newArray(int size) { return new Route[size]; }
    };
}
