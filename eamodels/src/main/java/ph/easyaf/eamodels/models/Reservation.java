package ph.easyaf.eamodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import ph.easyaf.eamodels.utils.DateTimeConverter;

public class Reservation extends EasyAFModel {

    private int passengerType = 0, seat, status = 0;
    private double fare = 0, serviceFee = 0;
    private String mongoId = "", origin = "", destination = "",
            boarding = "", dropping = "", referenceNo = "", vehicle = "", transactionId = "";
    private Date reservedDate = Calendar.getInstance().getTime(),
            tripDate = null;

    public Reservation() {}

    public Reservation(int seat, int passengerType, double fare) {
        this.seat = seat;
        this.passengerType = passengerType;
        this.fare = fare;
    }

    public Reservation(Parcel parcel) {
        int[] ints = new int[4];
        parcel.readIntArray(ints);
        id = ints[0];
        passengerType = ints[1];
        seat = ints[2];
        status = ints[3];

        double[] doubles = new double[2];
        parcel.readDoubleArray(doubles);
        fare = doubles[0];
        serviceFee = doubles[1];

        long[] longs = new long[1];
        parcel.readLongArray(longs);
        reservedDate = new Date(longs[0]);

        String[] strings = new String[8];
        parcel.readStringArray(strings);
        mongoId = strings[0];
        origin = strings[1];
        destination = strings[2];
        boarding = strings[3];
        dropping = strings[4];
        referenceNo = strings[5];
        vehicle = strings[6];
        transactionId = strings[7];
    }

    public Reservation(JSONObject object) {
        try {
            // if object is in qr code
            if (object.has("pt")) passengerType = object.getInt("pt");
            if (object.has("s")) seat = object.getInt("s");
            if (object.has("f")) fare = object.getDouble("f");
            if (object.has("o")) origin = object.getString("o");
            if (object.has("d")) destination = object.getString("d");
            if (object.has("b")) boarding = object.getString("b");
            if (object.has("do")) dropping = object.getString("do");
            if (object.has("rn")) referenceNo = object.getString("rn");
            if (object.has("v")) vehicle = object.getString("v");
            if (object.has("tid")) transactionId = object.getString("tid");
            if (object.has("rd")) reservedDate = DateTimeConverter.toDateUtc(object.getString("rd"));

            // if in server
            if (object.has("_id")) mongoId = object.getString("_id");
            if (object.has("seat")) seat = object.getInt("seat");
            if (object.has("passenger_type")) passengerType = object.getInt("passenger_type");
            if (object.has("status")) status = object.getInt("status");
            if (object.has("fare")) fare = object.getDouble("fare");
            if (object.has("service_fee")) serviceFee = object.getDouble("service_fee");
            if (object.has("origin")) origin = object.getString("origin");
            if (object.has("destination")) destination = object.getString("destination");
            if (object.has("boarding")) boarding = object.getString("boarding");
            if (object.has("dropping")) dropping = object.getString("dropping");
            if (object.has("reference_number")) referenceNo = object.getString("reference_number");
            if (object.has("vehicle")) vehicle = object.getString("vehicle");
            if (object.has("transaction_id")) transactionId = object.getString("transaction_id");
            if (object.has("reserved_date"))
                reservedDate = DateTimeConverter.toDateUtc(object.getString("reserved_date"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            if (!mongoId.isEmpty()) object.put("_id", mongoId);
            object.put("seat", seat);
            object.put("status", status);
            object.put("passenger_type", passengerType);
            object.put("origin", origin);
            object.put("destination", destination);
            object.put("boarding", boarding);
            object.put("dropping", dropping);
            object.put("fare", fare);
            object.put("service_fee", serviceFee);
            object.put("reference_number", referenceNo);
            object.put("vehicle", vehicle);
            object.put("transaction_id", transactionId);
            object.put("reserved_date", DateTimeConverter.toISODateUtc(reservedDate));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeIntArray(new int[] { id, passengerType, seat, status });
        parcel.writeDoubleArray(new double[] { fare, serviceFee });
        parcel.writeLongArray(new long[] { reservedDate.getTime() });
        parcel.writeStringArray(new String[] { mongoId, origin, destination, boarding,
                dropping, referenceNo, vehicle, transactionId });
    }

    public int describeContents() { return 0; }

    public int getPassengerType() { return passengerType; }
    public int getSeat() { return seat; }
    public int getStatus() { return status; }
    public double getFare() { return fare; }
    public double getServiceFee() { return serviceFee; }
    public String getMongoId() { return mongoId; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public String getBoarding() { return boarding; }
    public String getDropping() { return dropping; }
    public String getReferenceNo() { return referenceNo; }
    public String getVehicle() { return vehicle; }
    public String getTransactionId() { return transactionId; }
    public Date getReservedDate() { return reservedDate; }
    public Date getTripDate() { return tripDate; }
    public void setPassengerType(int passengerType) { this.passengerType = passengerType; }
    public void setSeat(int seat) { this.seat = seat; }
    public void setStatus(int status) { this.status = status; }
    public void setFare(double fare) { this.fare = fare; }
    public void setServiceFee(double serviceFee) { this.serviceFee = serviceFee; }
    public void setMongoId(String mongoId) { this.mongoId = mongoId; }
    public void setOrigin(String origin) { this.origin = origin; }
    public void setDestination(String destination) { this.destination = destination; }
    public void setBoarding(String boarding) { this.boarding = boarding; }
    public void setDropping(String dropping) { this.dropping = dropping; }
    public void setReferenceNo(String referenceNo) { this.referenceNo = referenceNo; }
    public void setVehicle(String vehicle) { this.vehicle = vehicle; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public void setReservedDate(Date reservedDate) { this.reservedDate = reservedDate; }
    public void setTripDate(Date tripDate) { this.tripDate = tripDate; }

    public String getSalt() {
        return seat + "-" + DateTimeConverter.toISODateUtc(reservedDate);
    }

    public static final Parcelable.Creator<Reservation> CREATOR = new Parcelable.Creator<Reservation>() {
        public Reservation createFromParcel(Parcel parcel) { return new Reservation(parcel); }
        public Reservation[] newArray(int size) { return new Reservation[size]; }
    };
}
