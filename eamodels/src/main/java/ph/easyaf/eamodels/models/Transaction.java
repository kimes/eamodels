package ph.easyaf.eamodels.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ph.easyaf.eamodels.utils.DateTimeConverter;

public class Transaction extends EasyAFModel {

    private int status;
    private String mongoId = "", boarding, dropping, reservedBy,
            referenceNo, paymentType, paymentRemarks, merchant;
    private Date reservedDate = Calendar.getInstance().getTime();
    private Trip trip = new Trip();
    private ArrayList<Reservation> reservations = new ArrayList<>();

    public Transaction() {}

    public Transaction(JSONObject object) {
        try {
            if (object.has("_id")) mongoId = object.getString("_id");
            if (object.has("status")) status = object.getInt("status");
            if (object.has("reserved_by")) reservedBy = object.getString("reserved_by");
            if (object.has("reference_number")) referenceNo = object.getString("reference_number");
            if (object.has("payment_type")) paymentType = object.getString("payment_type");
            if (object.has("payment_remarks")) paymentRemarks = object.getString("payment_remarks");
            if (object.has("merchant")) merchant = object.getString("merchant");
            if (object.has("reserved_date"))
                reservedDate = DateTimeConverter.toDateUtc(object.getString("reserved_date"));

            if (object.has("reservations")) {
                JSONArray reservationList = object.getJSONArray("reservations");
                for (int i = 0; i < reservationList.length(); i++) {
                    reservations.add(new Reservation(reservationList.getJSONObject(i)));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Transaction(Parcel parcel) {
        int[] ints = new int[1];
        parcel.readIntArray(ints);
        status = ints[0];

        long[] longs = new long[1];
        parcel.readLongArray(longs);
        reservedDate = new Date(longs[0]);

        String[] strings = new String[6];
        parcel.readStringArray(strings);
        mongoId = strings[0];
        reservedBy = strings[1];
        referenceNo = strings[2];
        paymentType = strings[3];
        paymentRemarks = strings[4];
        merchant = strings[5];

        ArrayList<Reservation> reservationList = new ArrayList<>();
        parcel.readTypedList(reservationList, Reservation.CREATOR);
        reservations = reservationList;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeIntArray(new int[] { status });
        parcel.writeLongArray(new long[] { reservedDate.getTime() });
        parcel.writeStringArray(new String[] { mongoId, reservedBy, referenceNo,
            paymentType, paymentRemarks, merchant });
        parcel.writeTypedList(reservations);
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            if (!mongoId.isEmpty()) object.put("_id", mongoId);
            object.put("status", status);
            object.put("reserved_by", reservedBy);
            object.put("reference_number", referenceNo);
            object.put("payment_type", paymentType);
            object.put("payment_remarks", paymentRemarks);
            object.put("merchant", merchant);
            object.put("reserved_date", DateTimeConverter.toISODateUtc(reservedDate));
            object.put("transaction_number", getTransactionNo());

            JSONArray reservationList = new JSONArray();
            for (int i = 0; i < reservations.size(); i++) {
                reservationList.put(reservations.get(i).toJSON());
            }
            object.put("reservations", reservationList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public int describeContents() { return 0; }

    public int getStatus() { return status; }
    public String getMongoId() { return mongoId; }
    public String getBoarding() { return boarding; }
    public String getDropping() { return dropping; }
    public String getReservedBy() { return reservedBy; }
    public String getReferenceNo() { return referenceNo; }
    public String getPaymentType() { return paymentType; }
    public String getPaymentRemarks() { return paymentRemarks; }
    public String getMerchant() { return merchant; }
    public Trip getTrip() { return trip; }
    public Date getReservedDate() { return reservedDate; }
    public ArrayList<Reservation> getReservations() { return reservations; }
    public void setStatus(int status) { this.status = status; }
    public void setMongoId(String mongoId) { this.mongoId = mongoId; }
    public void setBoarding(String boarding) { this.boarding = boarding; }
    public void setDropping(String dropping) { this.dropping = dropping; }
    public void setReservedBy(String reservedBy) { this.reservedBy = reservedBy; }
    public void setReferenceNo(String referenceNo) { this.referenceNo = referenceNo; }
    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }
    public void setPaymentRemarks(String paymentRemarks) { this.paymentRemarks = paymentRemarks; }
    public void setMerchant(String merchant) { this.merchant = merchant; }
    public void setTrip(Trip trip) { this.trip = trip; }
    public void setReservedDate(Date reservedDate) { this.reservedDate = reservedDate; }
    public void setReservations(ArrayList<Reservation> reservations) { this.reservations = reservations; }

    public String getTransactionNo() {
        return String.format(Locale.getDefault(), "%s-%08d", reservedBy, id);
    }

    public double getTotalFare() {
        double totalFare = 0d;
        for (int i = 0; i < reservations.size(); i++) {
            totalFare += reservations.get(i).getFare();
        }
        return totalFare;
    }

    public double getOriginalFare() {
        double originalFare = 0;
        for (int i = 0; i < reservations.size(); i++) {
            double fare = reservations.get(i).getFare();
            if (reservations.get(i).getPassengerType() == 1)
                fare = (reservations.get(i).getFare() / 0.8d);

            originalFare += fare;
        }
        return originalFare;
    }

    public double getDiscountedFare() {
        double discountedFare = 0d;
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getPassengerType() == 1) {
                double originalFare = (reservations.get(i).getFare() / 0.8d);
                discountedFare += (originalFare * 0.2d);
            }
        }
        return discountedFare;
    }

    public double getServiceFee() {
        double serviceFee = 0d;
        for (int i = 0; i < reservations.size(); i++) {
            serviceFee += reservations.get(i).getServiceFee();
        }
        return serviceFee;
    }

    public boolean isDiscounted() {
        boolean discount = false;
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getPassengerType() == 1) {
                discount = true;
                break;
            }
        }
        return discount;
    }

    public int getDiscountedPassengerCount() {
        int count = 0;
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getPassengerType() == 1) count++;
        }
        return count;
    }

    public int getPassengerCount() {
        int count = 0;
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getPassengerType() == 0) count++;
        }
        return count;
    }

    public String getOrigin() {
        String origin = "";
        if (trip != null) origin = trip.getOrigin();
        return origin;
    }

    public String getDestination() {
        String destination = "";
        if (trip != null) destination = trip.getDestination();
        return destination;
    }

    public String getPassengersSummary() {
        int passenger = 0, discount = 0;
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getPassengerType() == 0) passenger++;
            else discount++;
        }

        String summary = "";
        if (passenger > 0) summary += passenger + " Regular";
        if (discount > 0) {
            if (passenger > 0) summary += ", ";
            summary += discount + " Discounted";
        }
        return summary;
    }

    public static final Parcelable.Creator<Transaction> CREATOR = new Parcelable.Creator<Transaction>() {
        public Transaction createFromParcel(Parcel parcel) { return new Transaction(parcel); }
        public Transaction[] newArray(int size) { return new Transaction[size]; }
    };
}
