package ph.easyaf.eamodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ph.easyaf.eamodels.BR;

public class User extends BaseObservable implements Parcelable {

    @Bindable
    private int role = 0, position = 0;

    @Bindable
    private double totalTransactionsAmount = 0, totalTransactionsService = 0;

    @Bindable
    private String mongoId = "", email = "", password = "", code = "", merchant = "";

    @Bindable
    private Name name = new Name();

    @Bindable
    private ArrayList<String> permissions = new ArrayList<>();

    public User() {}

    public User(JSONObject object) {
        try {
            if (object.has("_id")) mongoId = object.getString("_id");

            if (object.has("role")) role = object.getInt("role");
            if (object.has("position")) position = object.getInt("position");

            if (object.has("email")) email = object.getString("email");
            if (object.has("password")) password = object.getString("password");
            if (object.has("code")) code = object.getString("code");
            if (object.has("merchant")) merchant = object.getString("merchant");

            if (object.has("name")) name = new Name(object.getJSONObject("name"));

            if (object.has("total_transactions_amount"))
                totalTransactionsAmount = object.getDouble("total_transactions_amount");

            if (object.has("total_transactions_service"))
                totalTransactionsService = object.getDouble("total_transactions_service");

            if (object.has("permissions")) {
                JSONArray perm = object.getJSONArray("permissions");

                for (int i = 0; i < perm.length(); i++) {
                    permissions.add(perm.getString(i));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public User(Parcel parcel) {
        int[] ints = new int[2];
        parcel.readIntArray(ints);
        role = ints[0];
        position = ints[1];

        double[] doubles = new double[2];
        parcel.readDoubleArray(doubles);
        totalTransactionsAmount = doubles[0];
        totalTransactionsService = doubles[1];

        String[] strings = new String[5];
        parcel.readStringArray(strings);
        mongoId = strings[0];
        email = strings[1];
        password = strings[2];
        code = strings[3];
        merchant = strings[4];

        name = parcel.readParcelable(Name.class.getClassLoader());

        parcel.readStringList(permissions);
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("_id", mongoId);

            object.put("role", role);
            object.put("position", position);

            object.put("email", email);
            object.put("password", password);
            object.put("code", code);
            object.put("merchant", merchant);

            object.put("name", name.toJSON());

            JSONArray perm = new JSONArray();
            for (int i = 0; i < permissions.size(); i++) {
                perm.put(permissions.get(i));
            }
            object.put("permissions", perm);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeIntArray(new int[] { role, position });
        parcel.writeDoubleArray(new double[] { totalTransactionsAmount, totalTransactionsService });
        parcel.writeStringArray(new String[] { mongoId, email, password, code, merchant });

        parcel.writeParcelable(name, flags);
        parcel.writeStringList(permissions);
    }

    public int describeContents() { return 0; }

    public int getRole() { return role; }
    public int getPosition() { return position; }
    public double getTotalTransactionsAmount() { return totalTransactionsAmount; }
    public double getTotalTransactionsService() { return totalTransactionsService; }
    public String getMongoId() { return mongoId; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getCode() { return code; }
    public String getMerchant() { return merchant; }
    public Name getName() { return name; }
    public ArrayList<String> getPermissions() { return permissions; }

    public void setRole(int role) {
        this.role = role;
        notifyPropertyChanged(BR.role);
    }
    public void setPosition(int position) {
        this.position = position;
        notifyPropertyChanged(BR.position);
    }

    public void setTotalTransactionsAmount(double totalTransactionsAmount) {
        this.totalTransactionsAmount = totalTransactionsAmount;
        notifyPropertyChanged(BR.totalTransactionsAmount);
    }

    public void setTotalTransactionsService(double totalTransactionsService) {
        this.totalTransactionsService = totalTransactionsService;
        notifyPropertyChanged(BR.totalTransactionsService);
    }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
        notifyPropertyChanged(BR.mongoId);
    }
    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }
    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }
    public void setCode(String code) {
        this.code = code;
        notifyPropertyChanged(BR.code);
    }
    public void setMerchant(String merchant) {
        this.merchant = merchant;
        notifyPropertyChanged(BR.merchant);
    }
    public void setName(Name name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
    public void setPermissions(ArrayList<String> permissions) {
        this.permissions = permissions;
        notifyPropertyChanged(BR.permissions);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<>() {
        public User createFromParcel(Parcel parcel) { return new User(parcel); }

        public User[] newArray(int size) { return new User[size]; }
    };
}
