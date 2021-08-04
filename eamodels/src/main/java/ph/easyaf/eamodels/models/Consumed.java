package ph.easyaf.eamodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.ObservableArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import ph.easyaf.eamodels.utils.DateTimeConverter;

public class Consumed implements Parcelable {

    private int id, status = 0, sendStatus = 0;
    private String mongoId = "", origin, destination, personnelCode, tripNo, referenceNo,
            liner = "", merchant = "", vehicle = "Bus", name, consumedBy;
    private Date tripDateTime = Calendar.getInstance().getTime(),
            startDateTime = null, endDateTime = null;
    private ObservableArrayList<Validation> validations = new ObservableArrayList<>();

    public Consumed() {}

    public Consumed(JSONObject object) {
        try {
            if (object.has("_id")) mongoId = object.getString("_id");
            if (object.has("status")) status = object.getInt("status");
            if (object.has("origin")) origin = object.getString("origin");
            if (object.has("destination")) destination = object.getString("destination");
            if (object.has("personnel_code")) personnelCode = object.getString("personnel_code");
            if (object.has("trip_no")) tripNo = object.getString("trip_no");
            if (object.has("reference_number")) referenceNo = object.getString("reference_number");
            if (object.has("liner")) liner = object.getString("liner");
            if (object.has("merchant")) merchant = object.getString("merchant");
            if (object.has("vehicle")) vehicle = object.getString("vehicle");
            if (object.has("name")) name = object.getString("name");
            if (object.has("consumed_by")) consumedBy = object.getString("consumed_by");
            if (object.has("trip_date_time"))
                tripDateTime = DateTimeConverter.toDateUtc(object.getString("trip_date_time"));
            if (object.has("start_date_time"))
                startDateTime = DateTimeConverter.toDateUtc(object.getString("start_date_time"));
            if (object.has("end_date_time"))
                endDateTime = DateTimeConverter.toDateUtc(object.getString("end_date_time"));

            if (object.has("validations")) {
                JSONArray validationArray = object.getJSONArray("validations");
                for (int i = 0; i < validationArray.length(); i++) {
                    validations.add(new Validation(validationArray.getJSONObject(i)));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Consumed(Parcel parcel) {
        int[] ints = new int[3];
        parcel.readIntArray(ints);
        id = ints[0];
        status = ints[1];
        sendStatus = ints[2];

        long[] longs = new long[3];
        parcel.readLongArray(longs);
        tripDateTime = new Date(longs[0]);
        startDateTime = (longs[1] > 0) ? new Date(longs[1]) : null;
        endDateTime = (longs[2] > 0) ? new Date(longs[2]) : null;

        String[] strings = new String[11];
        parcel.readStringArray(strings);
        mongoId = strings[0];
        origin = strings[1];
        destination = strings[2];
        personnelCode = strings[3];
        tripNo = strings[4];
        referenceNo = strings[5];
        liner = strings[6];
        merchant = strings[7];
        vehicle = strings[8];
        name = strings[9];
        consumedBy = strings[10];

        ObservableArrayList<Validation> validationList = new ObservableArrayList<>();
        parcel.readTypedList(validationList, Validation.CREATOR);
        validations = validationList;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeIntArray(new int[] { id, status, sendStatus });
        parcel.writeLongArray(new long[] { tripDateTime.getTime(),
                (startDateTime != null) ? startDateTime.getTime() : 0,
                (endDateTime != null) ? endDateTime.getTime() : 0 });
        parcel.writeStringArray(new String[] { mongoId, origin, destination,
                personnelCode, tripNo, referenceNo, liner, merchant, vehicle, name, consumedBy });
        parcel.writeTypedList(validations);
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            if (!mongoId.isEmpty()) object.put("_id", mongoId);
            object.put("status", status);
            object.put("origin", origin);
            object.put("destination", destination);
            object.put("personnel_code", personnelCode);
            object.put("trip_no", tripNo);
            object.put("trip_date_time", DateTimeConverter.toISODateUtc(tripDateTime));
            object.put("reference_number", referenceNo);
            object.put("liner", liner);
            object.put("merchant", merchant);
            object.put("vehicle", vehicle);
            object.put("name", name);
            object.put("consumed_by", consumedBy);
            if (startDateTime != null) object.put("start_date_time", DateTimeConverter.toISODateUtc(startDateTime));
            if (endDateTime != null) object.put("end_date_time", DateTimeConverter.toISODateUtc(endDateTime));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public int describeContents() { return 0; }

    public int getId() { return id; }
    public int getStatus() { return status; }
    public int getSendStatus() { return sendStatus; }
    public String getMongoId() { return mongoId; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public String getPersonnelCode() { return personnelCode; }
    public String getTripNo() { return tripNo; }
    public String getReferenceNo() { return referenceNo; }
    public String getLiner() { return liner; }
    public String getMerchant() { return merchant; }
    public String getVehicle() { return vehicle; }
    public String getName() { return name; }
    public String getConsumedBy() { return consumedBy; }
    public Date getTripDateTime() { return tripDateTime; }
    public Date getStartDateTime() { return startDateTime; }
    public Date getEndDateTime() { return endDateTime; }
    public ObservableArrayList<Validation> getValidations() { return validations; }
    public void setId(int id) { this.id = id; }
    public void setStatus(int status) { this.status = status; }
    public void setSendStatus(int sendStatus) { this.sendStatus = sendStatus; }
    public void setMongoId(String mongoId) { this.mongoId = mongoId; }
    public void setOrigin(String origin) { this.origin = origin; }
    public void setDestination(String destination) { this.destination = destination; }
    public void setPersonnelCode(String personnelCode) { this.personnelCode = personnelCode; }
    public void setTripNo(String tripNo) { this.tripNo = tripNo; }
    public void setReferenceNo(String referenceNo) { this.referenceNo = referenceNo; }
    public void setLiner(String liner) { this.liner = liner; }
    public void setMerchant(String merchant) { this.merchant = merchant; }
    public void setVehicle(String vehicle) { this.vehicle = vehicle; }
    public void setName(String name) { this.name = name; }
    public void setConsumedBy(String consumedBy) { this.consumedBy = consumedBy; }
    public void setTripDateTime(Date tripDateTime) { this.tripDateTime = tripDateTime; }
    public void setStartDateTime(Date startDateTime) { this.startDateTime = startDateTime; }
    public void setEndDateTime(Date endDateTime) { this.endDateTime = endDateTime; }
    public void setValidations(ObservableArrayList<Validation> validations) { this.validations = validations; }

    public static final Creator<Consumed> CREATOR = new Creator<Consumed>() {
        public Consumed createFromParcel(Parcel parcel) { return new Consumed(parcel); }
        public Consumed[] newArray(int size) { return new Consumed[size]; }
    };
}
