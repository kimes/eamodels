package ph.easyaf.eamodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ph.easyaf.eamodels.utils.DateTimeConverter;

public class GroupedDeviceLog extends BaseObservable implements Parcelable {

    @Bindable
    private Date date = Calendar.getInstance().getTime();

    @Bindable
    private ArrayList<DeviceLog> deviceLogs = new ArrayList<>();

    public GroupedDeviceLog() {}

    public GroupedDeviceLog(JSONObject object) {
        try {
            if (object.has("_id")) {
                date = DateTimeConverter.toDate(object.getString("_id"), "yyyy-MM-dd");
            }
            if (object.has("logs")) {
                JSONArray logsArray = object.getJSONArray("logs");
                for (int i = 0; i < logsArray.length(); i++) {
                    deviceLogs.add(new DeviceLog(logsArray.getJSONObject(i)));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public GroupedDeviceLog(Parcel parcel) {
        long dateLong = parcel.readLong();
        date = new Date(dateLong);

        ArrayList<DeviceLog> logsList = new ArrayList<>();
        parcel.readTypedList(logsList, DeviceLog.CREATOR);
        deviceLogs = logsList;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeLong(date.getTime());
        parcel.writeTypedList(deviceLogs);
    }

    public int describeContents() { return 0; }

    public Date getDate() { return date; }
    public ArrayList<DeviceLog> getDeviceLogs() { return deviceLogs; }
    public void setDate(Date date) {
        this.date = date;
        notifyPropertyChanged(BR.date);
    }
    public void setDeviceLogs(ArrayList<DeviceLog> deviceLogs) {
        this.deviceLogs = deviceLogs;
        notifyPropertyChanged(BR.deviceLogs);
    }

    public static final Parcelable.Creator<GroupedDeviceLog> CREATOR = new Parcelable.Creator<GroupedDeviceLog>() {
        public GroupedDeviceLog createFromParcel(Parcel parcel) { return new GroupedDeviceLog(parcel); }
        public GroupedDeviceLog[] newArray(int size) { return new GroupedDeviceLog[size]; }
    };
}
