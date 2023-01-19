package ph.easyaf.eamodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import ph.easyaf.eamodels.utils.DateTimeConverter;

public class Dispatch extends BaseObservable implements Parcelable {

    @Bindable
    private int status = 0;

    @Bindable
    private String mongoId = "", deviceName = "";

    @Bindable
    private Date tripDate, dispatchStartDate, dispatchEndDate;

    @Bindable
    private User dispatcherStart, dispatcherEnd, driver, conductor;

    @Bindable
    private Trip trip;

    public Dispatch() {}

    public Dispatch(JSONObject object) {
        try {
            if (object.has("status")) status = object.getInt("status");
            if (object.has("_id")) mongoId = object.getString("_id");
            if (object.has("device_name")) deviceName = object.getString("device_name");

            if (object.has("trip_date"))
                tripDate = DateTimeConverter.toDate(object.getString("trip_date"));
            if (object.has("dispatch_start_date"))
                dispatchStartDate = DateTimeConverter.toDateUtc(object.getString("dispatch_start_date"));
            if (object.has("dispatch_end_date"))
                dispatchEndDate = DateTimeConverter.toDateUtc(object.getString("dispatch_end_date"));

            if (object.has("dispatcher_start"))
                dispatcherStart = new User(object.getJSONObject("dispatcher_start"));
            if (object.has("dispatcher_end"))
                dispatcherEnd = new User(object.getJSONObject("dispatcher_end"));
            if (object.has("driver"))
                driver = new User(object.getJSONObject("driver"));
            if (object.has("conductor"))
                conductor = new User(object.getJSONObject("conductor"));

            if (object.has("trip"))
                trip = new Trip(object.getJSONObject("trip"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Dispatch(Parcel parcel) {
        int[] ints = new int[1];
        parcel.readIntArray(ints);
        status = ints[0];

        String[] strings = new String[2];
        parcel.readStringArray(strings);
        mongoId = strings[0];
        deviceName = strings[1];

        long[] dates = new long[3];
        parcel.readLongArray(dates);
        tripDate = (dates[0] > 0) ? new Date(dates[0]) : null;
        dispatchStartDate = (dates[1] > 0) ? new Date(dates[1]) : null;
        dispatchEndDate = (dates[2] > 0) ? new Date(dates[2]) : null;

        User[] users = (User[])parcel.readParcelableArray(User.class.getClassLoader());
        dispatcherStart = users[0];
        dispatcherEnd = users[1];
        driver = users[2];
        conductor = users[3];

        trip = parcel.readParcelable(Trip.class.getClassLoader());
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("status", status);
            object.put("_id", mongoId);
            object.put("device_name", deviceName);

            if (tripDate != null) object.put("trip_date", DateTimeConverter.toISODate(tripDate));
            if (dispatchStartDate != null)
                object.put("dispatch_start_date", DateTimeConverter.toISODateUtc(dispatchStartDate));
            if (dispatchEndDate != null)
                object.put("dispatch_end_date", DateTimeConverter.toISODateUtc(dispatchEndDate));

            if (dispatcherStart != null)
                object.put("dispatcher_start", dispatcherStart.toJSON());
            if (dispatcherEnd != null)
                object.put("dispatcher_end", dispatcherEnd.toJSON());
            if (driver != null) object.put("driver", driver.toJSON());
            if (conductor != null) object.put("conductor", conductor.toJSON());

            if (trip != null) object.put("trip", trip.toJSON());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeIntArray(new int[] { status });
        parcel.writeStringArray(new String[] { mongoId, deviceName });
        parcel.writeLongArray(new long[] {
                tripDate != null ? tripDate.getTime() : -1,
                dispatchStartDate != null ? dispatchStartDate.getTime() : -1,
                dispatchEndDate != null ? dispatchEndDate.getTime() : -1
        });

        parcel.writeParcelableArray(new User[] {
                dispatcherStart, dispatcherEnd, driver, conductor
        }, flags);
        parcel.writeParcelable(trip, flags);
    }

    public int describeContents() { return 0; }

    public int getStatus() { return status; }
    public String getMongoId() { return mongoId; }
    public String getDeviceName() { return deviceName; }
    public Date getTripDate() { return tripDate; }
    public Date getDispatchStartDate() { return dispatchStartDate; }
    public Date getDispatchEndDate() { return dispatchEndDate; }
    public User getDispatcherStart() { return dispatcherStart; }
    public User getDispatcherEnd() { return dispatcherEnd; }
    public User getDriver() { return driver; }
    public User getConductor() { return conductor; }
    public Trip getTrip() { return trip; }

    public void setStatus(int status) {
        this.status = status;
        notifyPropertyChanged(BR.status);
    }
    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
        notifyPropertyChanged(BR.mongoId);
    }
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
        notifyPropertyChanged(BR.deviceName);
    }
    public void setTripDate(Date tripDate) {
        this.tripDate = tripDate;
        notifyPropertyChanged(BR.tripDate);
    }
    public void setDispatchStartDate(Date dispatchStartDate) {
        this.dispatchStartDate = dispatchStartDate;
        notifyPropertyChanged(BR.dispatchStartDate);
    }
    public void setDispatchEndDate(Date dispatchEndDate) {
        this.dispatchEndDate = dispatchEndDate;
        notifyPropertyChanged(BR.dispatchEndDate);
    }
    public void setDispatcherStart(User dispatcherStart) {
        this.dispatcherStart = dispatcherStart;
        notifyPropertyChanged(BR.dispatcherStart);
    }
    public void setDispatcherEnd(User dispatcherEnd) {
        this.dispatcherEnd = dispatcherEnd;
        notifyPropertyChanged(BR.dispatcherEnd);
    }
    public void setDriver(User driver) {
        this.driver = driver;
        notifyPropertyChanged(BR.driver);
    }
    public void setConductor(User conductor) {
        this.conductor = conductor;
        notifyPropertyChanged(BR.conductor);
    }
    public void setTrip(Trip trip) {
        this.trip = trip;
        notifyPropertyChanged(BR.trip);
    }

    public static final Parcelable.Creator<Dispatch> CREATOR = new Parcelable.Creator<>() {
        public Dispatch createFromParcel(Parcel parcel) { return new Dispatch(parcel); }
        public Dispatch[] newArray(int size) { return new Dispatch[size]; }
    };
}
