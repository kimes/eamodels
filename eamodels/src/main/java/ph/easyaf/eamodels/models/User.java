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
    private String email = "", password = "", code = "", merchant = "";

    @Bindable
    private Name name = new Name();

    @Bindable
    private ArrayList<String> permissions = new ArrayList<>();

    public User() {}

    public User(JSONObject object) {
        try {
            if (object.has("role")) role = object.getInt("role");
            if (object.has("position")) position = object.getInt("position");

            if (object.has("email")) email = object.getString("email");
            if (object.has("password")) password = object.getString("password");
            if (object.has("code")) code = object.getString("code");
            if (object.has("merchant")) merchant = object.getString("merchant");

            if (object.has("name")) name = new Name(object.getJSONObject("name"));

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

        String[] strings = new String[4];
        parcel.readStringArray(strings);
        email = strings[0];
        password = strings[1];
        code = strings[2];
        merchant = strings[3];

        name = parcel.readParcelable(Name.class.getClassLoader());

        parcel.readStringList(permissions);
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
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
        parcel.writeStringArray(new String[] { email, password, code, merchant });

        parcel.writeParcelable(name, flags);
        parcel.writeStringList(permissions);
    }

    public int describeContents() { return 0; }

    public int getRole() { return role; }
    public int getPosition() { return position; }
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
