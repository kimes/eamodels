package ph.easyaf.eamodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import ph.easyaf.eamodels.utils.DateTimeConverter;

public class Inspect extends BaseObservable implements Parcelable {

    @Bindable
    private String mongoId = "";

    @Bindable
    private Date inspectedDate = Calendar.getInstance().getTime();

    @Bindable
    private User inspector;

    public Inspect() {}

    public Inspect(JSONObject object) {
        try {
            if (object.has("_id")) mongoId = object.getString("_id");
            if (object.has("inspected_date"))
                inspectedDate = DateTimeConverter.toDateUtc(object.getString("inspected_date"));
            if (object.has("inspector")) inspector = new User(object.getJSONObject("inspector"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Inspect(Parcel parcel) {
        String[] strings = new String[1];
        parcel.readStringArray(strings);
        mongoId = strings[0];

        long[] longs = new long[1];
        parcel.readLongArray(longs);
        inspectedDate = (longs[0] > 0) ? new Date(longs[0]) : null;

        inspector = parcel.readParcelable(User.class.getClassLoader());
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            if (!mongoId.isEmpty()) object.put("_id", mongoId);
            if (inspectedDate != null) object.put("inspected_date", DateTimeConverter.toISODateUtc(inspectedDate));
            if (inspector != null) object.put("inspector", inspector.toJSON());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeStringArray(new String[] { mongoId });
        parcel.writeLongArray(new long[] { inspectedDate != null ? inspectedDate.getTime() : -1 });
        parcel.writeParcelable(inspector, flags);
    }

    public int describeContents() { return 0; }

    public String getMongoId() { return mongoId; }
    public Date getInspectedDate() { return inspectedDate; }
    public User getInspector() { return inspector; }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
        notifyPropertyChanged(BR.mongoId);
    }
    public void setInspectedDate(Date inspectedDate) {
        this.inspectedDate = inspectedDate;
        notifyPropertyChanged(BR.inspectedDate);
    }
    public void setInspector(User inspector) {
        this.inspector = inspector;
        notifyPropertyChanged(BR.inspector);
    }

    public static final Parcelable.Creator<Inspect> CREATOR = new Parcelable.Creator<Inspect>() {
        public Inspect createFromParcel(Parcel parcel) { return new Inspect(parcel); }

        public Inspect[] newArray(int size) { return new Inspect[size]; }
    };
}
