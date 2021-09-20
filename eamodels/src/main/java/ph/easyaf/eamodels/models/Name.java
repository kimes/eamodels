package ph.easyaf.eamodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import org.json.JSONException;
import org.json.JSONObject;

public class Name extends BaseObservable implements Parcelable {

    @Bindable
    private String first = "", last = "";

    public Name() {}

    public Name(String first, String last) {
        this.first = first;
        this.last = last;
    }

    public Name(JSONObject object) {
        try {
            first = object.getString("first");
            last = object.getString("last");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Name(Parcel parcel) {
        String[] strings = new String[2];
        parcel.readStringArray(strings);
        first = strings[0];
        last = strings[1];
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("first", first);
            object.put("last", last);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeStringArray(new String[] { first, last });
    }

    public int describeContents() { return 0; }

    public String getFirst() { return first; }
    public String getLast() { return last; }
    public void setFirst(String first) {
        this.first = first;
        notifyPropertyChanged(BR.first);
    }

    public void setLast(String last) {
        this.last = last;
        notifyPropertyChanged(BR.last);
    }

    public static final Parcelable.Creator<Name> CREATOR = new Parcelable.Creator<>() {
        public Name createFromParcel(Parcel parcel) { return new Name(parcel); }
        public Name[] newArray(int size) { return new Name[size]; }
    };

}
