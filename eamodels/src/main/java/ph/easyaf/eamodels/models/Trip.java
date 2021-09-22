package ph.easyaf.eamodels.models;

import android.os.Parcel;

import androidx.databinding.Bindable;
import androidx.databinding.ObservableArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import ph.easyaf.eamodels.BR;
import ph.easyaf.eamodels.utils.DateTimeConverter;

public class Trip extends EasyAFModel {

    @Bindable
    private boolean enabled = true;

    @Bindable
    private int maxSeats = 0, reservationCount = 0;

    @Bindable
    private double fare;

    @Bindable
    private String mongoId = "", origin = "", destination = "", liner = "",
            merchant = "", vehicle = "Bus", code = "";

    @Bindable
    private Date date, time, startDate, expiresDate;

    @Bindable
    private ObservableArrayList<Route> boardings = new ObservableArrayList<>(),
        dropOffs = new ObservableArrayList<>();

    @Bindable
    private Route selectedBoarding;

    public Trip() {}
    public Trip(int id) { super(id); }
    public Trip(String vehicle) { this.vehicle = vehicle; }

    public Trip(Parcel parcel) {
        boolean[] booleans = new boolean[1];
        parcel.readBooleanArray(booleans);
        enabled = booleans[0];

        int[] ints = new int[3];
        parcel.readIntArray(ints);
        id = ints[0];
        maxSeats = ints[1];
        reservationCount = ints[2];

        fare = parcel.readDouble();

        long[] longs = new long[4];
        parcel.readLongArray(longs);
        date = (longs[0] > 0) ? new Date(longs[0]) : null;
        time = (longs[1] > 1) ? new Date(longs[1]) : null;
        startDate = (longs[2] > 0) ? new Date(longs[2]) : null;
        expiresDate = (longs[3] > 0) ? new Date(longs[3]) : null;

        String[] strings = new String[7];
        parcel.readStringArray(strings);
        mongoId = strings[0];
        origin = strings[1];
        destination = strings[2];
        liner = strings[3];
        merchant = strings[4];
        vehicle = strings[5];
        code = strings[6];

        ObservableArrayList<Route> boardingList = new ObservableArrayList<>();
        parcel.readTypedList(boardingList, Route.CREATOR);
        boardings = boardingList;

        ObservableArrayList<Route> dropOffList = new ObservableArrayList<>();
        parcel.readTypedList(dropOffList, Route.CREATOR);
        dropOffs = dropOffList;
    }

    public Trip(Trip trip) {
        id = trip.getId();
        enabled = trip.isEnabled();
        maxSeats = trip.getMaxSeats();
        reservationCount = trip.getReservationCount();
        fare = trip.getFare();
        mongoId = trip.getMongoId();
        origin = trip.getOrigin();
        destination = trip.getDestination();
        liner = trip.getLiner();
        merchant = trip.getMerchant();
        vehicle = trip.getVehicle();
        selectedBoarding = trip.getSelectedBoarding();

        for (int i = 0; i < trip.getBoardings().size(); i++) {
            boardings.add(new Route(trip.getBoardings().get(i)));
        }
        for (int i = 0; i < trip.getDropOffs().size(); i++) {
            dropOffs.add(new Route(trip.getDropOffs().get(i)));
        }
    }

    public Trip(JSONObject object) {
        try {
            if (object.has("_id")) mongoId = object.getString("_id");
            if (object.has("max_seats")) maxSeats = object.getInt("max_seats");
            if (object.has("reservation_count")) reservationCount = object.getInt("reservation_count");
            if (object.has("fare")) fare = object.getDouble("fare");
            if (object.has("origin")) origin = object.getString("origin");
            if (object.has("destination")) destination = object.getString("destination");
            if (object.has("liner")) liner = object.getString("liner");
            if (object.has("merchant")) merchant = object.getString("merchant");
            if (object.has("vehicle")) vehicle = object.getString("vehicle");
            if (object.has("code")) code = object.getString("code");

            if (object.has("date"))
                date = DateTimeConverter.toDate(object.getString("date"));
            if (object.has("time"))
                time = DateTimeConverter.toDateUtc(object.getString("time"));
            if (object.has("start_date"))
                startDate = DateTimeConverter.toDateUtc(object.getString("start_date"));
            if (object.has("expires_date"))
                expiresDate = DateTimeConverter.toDateUtc(object.getString("expires_date"));

            if (object.has("boardings")) {
                JSONArray boardingsList = object.getJSONArray("boardings");
                for (int i = 0; i < boardingsList.length(); i++) {
                    boardings.add(new Route(boardingsList.getJSONObject(i)));
                }
            }

            if (object.has("dropOffs")) {
                JSONArray dropOffsList = object.getJSONArray("dropOffs");
                for (int i = 0; i < dropOffsList.length(); i++) {
                    dropOffs.add(new Route(dropOffsList.getJSONObject(i)));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void reset(Trip trip) {
        id = trip.getId();

        enabled = trip.isEnabled();
        //notifyPropertyChanged(BR.enabled);

        maxSeats = trip.getMaxSeats();

        reservationCount = trip.getReservationCount();

        fare = trip.getFare();
        //notifyPropertyChanged(BR.fare);

        mongoId = trip.getMongoId();

        origin = trip.getOrigin();
        //notifyPropertyChanged(BR.origin);

        destination = trip.getDestination();
        //notifyPropertyChanged(BR.destination);

        liner = trip.getLiner();
        merchant = trip.getMerchant();

        vehicle = trip.getVehicle();
        //notifyPropertyChanged(BR.vehicle);
        code = trip.getCode();

        time = trip.getTime();
        startDate = trip.getStartDate();
        expiresDate = trip.getExpiresDate();

        boardings.clear();
        for (int i = 0; i < trip.getBoardings().size(); i++) {
            boardings.add(new Route(trip.getBoardings().get(i)));
        }

        dropOffs.clear();
        for (int i = 0; i < trip.getDropOffs().size(); i++) {
            dropOffs.add(new Route(trip.getDropOffs().get(i)));
        }

        notifyAll();
    }

    public boolean isSame(Trip trip) {
        if (id != trip.getId()) return false;
        if (enabled != trip.isEnabled()) return false;
        if (fare != trip.getFare()) return false;
        if (!origin.equals(trip.getOrigin())) return false;
        if (!destination.equals(trip.getDestination())) return false;
        if (!liner.equals(trip.getLiner())) return false;
        if (!merchant.equals(trip.getMerchant())) return false;
        if (!vehicle.equals(trip.getVehicle())) return false;
        if (!code.equals(trip.getCode())) return false;

        if (!mongoId.isEmpty()) {
            if (!mongoId.equals(trip.getMongoId())) return false;
        }

        if (boardings.size() != trip.getBoardings().size()) return false;
        for (int i = 0; i < boardings.size(); i++) {
            if (!boardings.get(i).isSame(trip.getBoardings().get(i))) return false;
        }

        if (dropOffs.size() != trip.getDropOffs().size()) return false;
        for (int i = 0; i < dropOffs.size(); i++) {
            if (!dropOffs.get(i).isSame(trip.getDropOffs().get(i))) return false;
        }
        /*
        if (routes.size() != trip.getRoutes().size()) return false;
        for (int i = 0; i < routes.size(); i++) {
            if (!routes.get(i).isSame(trip.getRoutes().get(i))) return false;
        } */
        return true;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeBooleanArray(new boolean[] { enabled });
        parcel.writeIntArray(new int[] { id, maxSeats, reservationCount });
        parcel.writeDouble(fare);
        parcel.writeLongArray(new long[] {
                (date != null) ? date.getTime() : 0,
                (time != null) ? time.getTime() : 0,
                (startDate != null) ? startDate.getTime() : 0,
                (expiresDate != null) ? expiresDate.getTime() : 0 });
        parcel.writeStringArray(new String[] { mongoId, origin, destination, liner, merchant, vehicle, code });
        parcel.writeTypedList(boardings);
        parcel.writeTypedList(dropOffs);
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            if (!mongoId.isEmpty()) object.put("_id", mongoId);
            object.put("max_seats", maxSeats);
            object.put("fare", fare);

            object.put("origin", origin);
            object.put("destination", destination);
            object.put("liner", liner);
            object.put("merchant", merchant);
            object.put("vehicle", vehicle);
            object.put("code", code);

            if (time != null) object.put("time", DateTimeConverter.toISODateUtc(time));
            if (startDate != null) object.put("start_date", DateTimeConverter.toISODateUtc(startDate));
            if (expiresDate != null) object.put("expires_date", DateTimeConverter.toISODateUtc(expiresDate));

            JSONArray boardingsList = new JSONArray();
            for (int i = 0; i < boardings.size(); i++) {
                boardingsList.put(boardings.get(i).toJSON());
            }
            object.put("boardings", boardingsList);

            JSONArray dropOffsList = new JSONArray();
            for (int i = 0; i < dropOffs.size(); i++) {
                dropOffsList.put(dropOffs.get(i).toJSON());
            }
            object.put("dropOffs", dropOffsList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public int describeContents() { return 0; }

    public boolean isEnabled() { return enabled; }
    public int getMaxSeats() { return maxSeats; }
    public int getReservationCount() { return reservationCount; }
    public double getFare() { return fare; }
    public String getMongoId() { return mongoId; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public String getLiner() { return liner; }
    public String getMerchant() { return merchant; }
    public String getVehicle() { return vehicle; }
    public String getCode() { return code; }
    public Date getDate() { return date; }
    public Date getTime() { return time; }
    public Date getStartDate() { return startDate; }
    public Date getExpiresDate() { return expiresDate; }
    public ObservableArrayList<Route> getBoardings() { return boardings; }
    public ObservableArrayList<Route> getDropOffs() { return dropOffs; }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        notifyPropertyChanged(BR.enabled);
    }
    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
        notifyPropertyChanged(BR.maxSeats);
    }
    public void setReservationCount(int reservationCount) {
        this.reservationCount = reservationCount;
        notifyPropertyChanged(BR.reservationCount);
    }
    public void setFare(double fare) {
        this.fare = fare;
        notifyPropertyChanged(BR.fare);
    }
    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
        notifyPropertyChanged(BR.origin);
    }
    public void setDestination(String destination) {
        this.destination = destination;
        notifyPropertyChanged(BR.destination);
    }
    public void setLiner(String liner) {
        this.liner = liner;
        notifyPropertyChanged(BR.liner);
    }
    public void setMerchant(String merchant) {
        this.merchant = merchant;
        notifyPropertyChanged(BR.merchant);
    }
    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
        notifyPropertyChanged(BR.vehicle);
    }
    public void setCode(String code) {
        this.code = code;
        notifyPropertyChanged(BR.code);
    }
    public void setDate(Date date) {
        this.date = date;
        notifyPropertyChanged(BR.date);
    }
    public void setTime(Date time) {
        this.time = time;
        notifyPropertyChanged(BR.time);
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
        notifyPropertyChanged(BR.startDate);
    }
    public void setExpiresDate(Date expiresDate) {
        this.expiresDate = expiresDate;
        notifyPropertyChanged(BR.expiresDate);
    }
    public void setBoardings(ObservableArrayList<Route> boardings) {
        this.boardings = boardings;
        notifyPropertyChanged(BR.boardings);
    }
    public void setDropOffs(ObservableArrayList<Route> dropOffs) {
        this.dropOffs = dropOffs;
        notifyPropertyChanged(BR.dropOffs);
    }

    public Route getSelectedBoarding() { return selectedBoarding; }
    public void setSelectedBoarding(Route selectedBoarding) {
        this.selectedBoarding = selectedBoarding;
        notifyPropertyChanged(BR.selectedBoarding);
    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        public Trip createFromParcel(Parcel parcel) { return new Trip(parcel); }
        public Trip[] newArray(int size) { return new Trip[size]; }
    };
}
