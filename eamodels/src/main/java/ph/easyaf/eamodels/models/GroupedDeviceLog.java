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

public class GroupedDeviceLog extends BaseObservable implements Parcelable {

    @Bindable
    private String id = "";

    @Bindable
    private ArrayList<DeviceLog> deviceLogs = new ArrayList<>();

    public GroupedDeviceLog() {}

    public GroupedDeviceLog(JSONObject object) {
        try {
            if (object.has("_id")) id = object.getString("_id");
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
        id = parcel.readString();

        ArrayList<DeviceLog> logsList = new ArrayList<>();
        parcel.readTypedList(logsList, DeviceLog.CREATOR);
        deviceLogs = logsList;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(id);
        parcel.writeTypedList(deviceLogs);
    }

    public int describeContents() { return 0; }

    public String getId() { return id; }
    public ArrayList<DeviceLog> getDeviceLogs() { return deviceLogs; }
    public void setId(String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
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
