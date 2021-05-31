package ph.easyaf.eamodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import ph.easyaf.eamodels.utils.DateTimeConverter;

public class Validation extends EasyAFModel {

    private int consumedId, passengerType = 0, sendStatus = 0;
    private double fare = 0;
    private String mongoId = "", consumedMongoId = "", referenceNo = "", name = "",
        boarding = "", dropping = "", transactionId = "";
    private Date validationDate = Calendar.getInstance().getTime();

    public Validation() {}

    public Validation(JSONObject object) {
        try {
            if (object.has("_id")) mongoId = object.getString("_id");
            if (object.has("passenger_type")) passengerType = object.getInt("passenger_type");
            if (object.has("fare")) fare = object.getDouble("fare");
            if (object.has("reference_number")) referenceNo = object.getString("reference_number");
            if (object.has("name")) name = object.getString("name");
            if (object.has("boarding")) boarding = object.getString("boarding");
            if (object.has("dropping")) dropping = object.getString("dropping");
            if (object.has("transaction_id")) transactionId = object.getString("transaction_id");
            if (object.has("consumed")) consumedMongoId = object.getString("consumed");
            if (object.has("validation_date"))
                validationDate = DateTimeConverter.toDateUtc(object.getString("validation_date"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Validation(Parcel parcel) {
        int[] ints = new int[4];
        parcel.readIntArray(ints);
        id = ints[0];
        consumedId = ints[1];
        passengerType = ints[2];
        sendStatus = ints[3];

        double[] doubles = new double[1];
        parcel.readDoubleArray(doubles);
        fare = doubles[0];

        long[] longs = new long[1];
        parcel.readLongArray(longs);
        validationDate = new Date(longs[0]);

        String[] strings = new String[7];
        parcel.readStringArray(strings);
        mongoId = strings[0];
        consumedMongoId = strings[1];
        referenceNo = strings[2];
        name = strings[3];
        boarding = strings[4];
        dropping = strings[5];
        transactionId = strings[6];
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeIntArray(new int[] { id, consumedId, passengerType, sendStatus });
        parcel.writeDoubleArray(new double[] { fare });
        parcel.writeLongArray(new long[] { validationDate.getTime() });
        parcel.writeStringArray(new String[] { mongoId, consumedMongoId, referenceNo, name,
            boarding, dropping, transactionId });
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            if (!mongoId.isEmpty()) object.put("_id", mongoId);
            object.put("passenger_type", passengerType);
            object.put("fare", fare);
            object.put("reference_number", referenceNo);
            object.put("name", name);
            object.put("boarding", boarding);
            object.put("dropping", dropping);
            object.put("transaction_id", transactionId);
            object.put("consumed", consumedMongoId);
            object.put("validation_date", DateTimeConverter.toISODateUtc(validationDate));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public int describeContents() { return 0; }

    public int getConsumedId() { return consumedId; }
    public int getPassengerType() { return passengerType; }
    public int getSendStatus() { return sendStatus; }
    public double getFare() { return fare; }
    public String getMongoId() { return mongoId; }
    public String getConsumedMongoId() { return consumedMongoId; }
    public String getReferenceNo() { return referenceNo; }
    public String getName() { return name; }
    public String getBoarding() { return boarding; }
    public String getDropping() { return dropping; }
    public String getTransactionId() { return transactionId; }
    public Date getValidationDate() { return validationDate; }
    public void setConsumedId(int consumedId) { this.consumedId = consumedId; }
    public void setPassengerType(int passengerType) { this.passengerType = passengerType; }
    public void setSendStatus(int sendStatus) { this.sendStatus = sendStatus; }
    public void setFare(double fare) { this.fare = fare; }
    public void setMongoId(String mongoId) { this.mongoId = mongoId; }
    public void setConsumedMongoId(String consumedMongoId) { this.consumedMongoId = consumedMongoId; }
    public void setReferenceNo(String referenceNo) { this.referenceNo = referenceNo; }
    public void setName(String name) { this.name = name; }
    public void setBoarding(String boarding) { this.boarding = boarding; }
    public void setDropping(String dropping) { this.dropping = dropping; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public void setValidationDate(Date validationDate) { this.validationDate = validationDate; }

    public static final Parcelable.Creator<Validation> CREATOR = new Parcelable.Creator<Validation>() {
        public Validation createFromParcel(Parcel parcel) { return new Validation(parcel); }
        public Validation[] newArray(int size) { return new Validation[size]; }
    };
}
