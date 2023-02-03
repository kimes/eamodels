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

public class DeviceLog extends BaseObservable implements Parcelable {

    @Bindable
    private int type = 0;

    @Bindable
    private Date logDate = Calendar.getInstance().getTime();

    @Bindable
    private Device device;

    @Bindable
    private User user;

    public DeviceLog() {}

    public DeviceLog(JSONObject object) {
        try {
            if (object.has("type")) type = object.getInt("type");
            if (object.has("log_date")) logDate = DateTimeConverter.toDateUtc(object.getString("log_date"));
            if (object.has("device")) device = new Device(object.getJSONObject("device"));
            if (object.has("user")) user = new User(object.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public DeviceLog(Parcel parcel) {
        int[] ints = new int[1];
        parcel.readIntArray(ints);
        type = ints[0];

        long[] longs = new long[1];
        parcel.readLongArray(longs);
        logDate = (longs[0] > 0) ? new Date(longs[0]) : null;

        device = parcel.readParcelable(Device.class.getClassLoader());
        user = parcel.readParcelable(User.class.getClassLoader());
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeIntArray(new int[] { type });
        parcel.writeLongArray(new long[] { (logDate != null) ? logDate.getTime() : - 1});
        parcel.writeParcelable(device, flags);
        parcel.writeParcelable(user, flags);
    }

    public int describeContents() { return 0; }

    public int getType() { return type; }
    public Date getLogDate() { return logDate; }
    public Device getDevice() { return device; }
    public User getUser() { return user; }

    public void setType(int type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }
    public void setLogDate(Date logDate) {
        this.logDate = logDate;
        notifyPropertyChanged(BR.logDate);
    }
    public void setDevice(Device device) {
        this.device = device;
        notifyPropertyChanged(BR.device);
    }
    public void setUser(User user) {
        this.user = user;
        notifyPropertyChanged(BR.user);
    }

    public static final Parcelable.Creator<DeviceLog> CREATOR = new Parcelable.Creator<DeviceLog>() {
        public DeviceLog createFromParcel(Parcel parcel) { return new DeviceLog(parcel); }
        public DeviceLog[] newArray(int size) { return new DeviceLog[size]; }
    };
}
