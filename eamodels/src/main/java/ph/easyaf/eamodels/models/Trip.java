package ph.easyaf.eamodels.models;

import android.os.Parcel;

import androidx.databinding.Bindable;
import androidx.databinding.ObservableArrayList;

import ph.easyaf.eamodels.BR;

public class Trip extends EasyAFModel {

    @Bindable
    private boolean enabled = true;
    @Bindable
    private double fare;
    @Bindable
    private String origin = "", destination = "", vehicle = "Bus";

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

        id = parcel.readInt();
        fare = parcel.readDouble();

        String[] strings = new String[3];
        parcel.readStringArray(strings);
        origin = strings[0];
        destination = strings[1];
        vehicle = strings[2];

        ObservableArrayList<Route> boardingList = new ObservableArrayList<>();
        parcel.readTypedList(boardingList, Route.CREATOR);
        boardings = boardingList;

        ObservableArrayList<Route> dropOffList = new ObservableArrayList<>();
        parcel.readTypedList(dropOffList, Route.CREATOR);
        dropOffs = dropOffList;
    }

    public Trip(Trip trip) {
        enabled = trip.isEnabled();
        id = trip.getId();
        fare = trip.getFare();
        origin = trip.getOrigin();
        destination = trip.getDestination();
        vehicle = trip.getVehicle();
        selectedBoarding = trip.getSelectedBoarding();

        for (int i = 0; i < trip.getBoardings().size(); i++) {
            boardings.add(new Route(trip.getBoardings().get(i)));
        }
        for (int i = 0; i < trip.getDropOffs().size(); i++) {
            dropOffs.add(new Route(trip.getDropOffs().get(i)));
        }
    }

    public void reset(Trip trip) {
        id = trip.getId();

        enabled = trip.isEnabled();
        notifyPropertyChanged(BR.enabled);

        fare = trip.getFare();
        notifyPropertyChanged(BR.fare);

        origin = trip.getOrigin();
        notifyPropertyChanged(BR.origin);

        destination = trip.getDestination();
        notifyPropertyChanged(BR.destination);

        vehicle = trip.getVehicle();
        notifyPropertyChanged(BR.vehicle);

        boardings.clear();
        for (int i = 0; i < trip.getBoardings().size(); i++) {
            boardings.add(new Route(trip.getBoardings().get(i)));
        }

        dropOffs.clear();
        for (int i = 0; i < trip.getDropOffs().size(); i++) {
            dropOffs.add(new Route(trip.getDropOffs().get(i)));
        }
    }

    public boolean isSame(Trip trip) {
        if (enabled != trip.isEnabled()) return false;
        if (id != trip.getId()) return false;
        if (fare != trip.getFare()) return false;
        if (!origin.equals(trip.getOrigin())) return false;
        if (!destination.equals(trip.getDestination())) return false;
        if (vehicle != null) {
            if (!vehicle.equals(trip.getVehicle())) return false;
        } else return false;

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
        parcel.writeInt(id);
        parcel.writeDouble(fare);
        parcel.writeStringArray(new String[] { origin, destination, vehicle });
        parcel.writeTypedList(boardings);
        parcel.writeTypedList(dropOffs);
    }

    public int describeContents() { return 0; }

    public boolean isEnabled() { return enabled; }
    public double getFare() { return fare; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public String getVehicle() { return vehicle; }
    public ObservableArrayList<Route> getBoardings() { return boardings; }
    public ObservableArrayList<Route> getDropOffs() { return dropOffs; }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        notifyPropertyChanged(BR.enabled);
    }
    public void setFare(double fare) {
        this.fare = fare;
        notifyPropertyChanged(BR.fare);
    }
    public void setOrigin(String origin) {
        this.origin = origin;
        notifyPropertyChanged(BR.origin);
    }
    public void setDestination(String destination) {
        this.destination = destination;
        notifyPropertyChanged(BR.destination);
    }
    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
        notifyPropertyChanged(BR.vehicle);
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
