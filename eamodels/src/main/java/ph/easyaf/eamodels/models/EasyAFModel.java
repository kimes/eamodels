package ph.easyaf.eamodels.models;

import android.os.Parcelable;

import androidx.databinding.BaseObservable;

public abstract class EasyAFModel extends BaseObservable implements Parcelable {

    protected int id = 0;

    public EasyAFModel() {}
    public EasyAFModel(int id) { this.id = id; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
}
