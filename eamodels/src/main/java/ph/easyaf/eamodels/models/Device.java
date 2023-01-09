package ph.easyaf.eamodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import org.json.JSONException;
import org.json.JSONObject;

import ph.easyaf.eamodels.BR;

public class Device extends BaseObservable implements Parcelable {

    @Bindable
    private boolean online;

    @Bindable
    private int status;

    @Bindable
    private String mongoId = "", name = "", plateNumber = "", secretKey = "",
            gate = "", merchant = "", socketId = "";

    public Device(Parcel parcel) {
        boolean[] booleans = new boolean[1];
        parcel.readBooleanArray(booleans);
        online = booleans[0];

        int[] ints = new int[1];
        parcel.readIntArray(ints);
        status = ints[0];

        String[] strings = new String[7];
        parcel.readStringArray(strings);
        mongoId = strings[0];
        name = strings[1];
        plateNumber = strings[2];
        secretKey = strings[3];
        gate = strings[4];
        merchant = strings[5];
        socketId = strings[6];
    }

    public Device(JSONObject object) {
        try {
            if (object.has("_id")) mongoId = object.getString("_id");
            if (object.has("online")) online = object.getBoolean("online");
            if (object.has("status")) status = object.getInt("status");
            if (object.has("name")) name = object.getString("name");
            if (object.has("plate_number")) plateNumber = object.getString("plate_number");
            if (object.has("secret_key")) secretKey = object.getString("secret_key");
            if (object.has("gate")) gate = object.getString("gate");
            if (object.has("merchant")) merchant = object.getString("merchant");
            if (object.has("socket_id")) socketId = object.getString("socket_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeBooleanArray(new boolean[] { online });
        parcel.writeIntArray(new int[] { status });
        parcel.writeStringArray(new String[] { mongoId, name, plateNumber, secretKey,
                gate, merchant, socketId });
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("_id", mongoId);
            object.put("online", online);
            object.put("status", status);
            object.put("name", name);
            object.put("plate_number", plateNumber);
            object.put("secret_key", secretKey);
            object.put("gate", gate);
            object.put("merchant", merchant);
            object.put("socket_id", socketId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public int describeContents() { return 0; }

    public boolean isOnline() { return online; }
    public int getStatus() { return status; }
    public String getMongoId() { return mongoId; }
    public String getName() { return name; }
    public String getPlateNumber() { return plateNumber; }
    public String getSecretKey() { return secretKey; }
    public String getGate() { return gate; }
    public String getMerchant() { return merchant; }
    public String getSocketId() { return socketId; }

    public void setOnline(boolean online) {
        this.online = online;
        notifyPropertyChanged(BR.online);
    }

    public void setStatus(int status) {
        this.status = status;
        notifyPropertyChanged(BR.status);
    }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
        notifyPropertyChanged(BR.mongoId);
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
        notifyPropertyChanged(BR.plateNumber);
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
        notifyPropertyChanged(BR.secretKey);
    }

    public void setGate(String gate) {
        this.gate = gate;
        notifyPropertyChanged(BR.gate);
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
        notifyPropertyChanged(BR.merchant);
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
        notifyPropertyChanged(BR.socketId);
    }

    public static final Creator<Device> CREATOR = new Creator<Device>() {
        public Device createFromParcel(Parcel parcel) { return new Device(parcel); }
        public Device[] newArray(int size) { return new Device[size]; }
    };
}
