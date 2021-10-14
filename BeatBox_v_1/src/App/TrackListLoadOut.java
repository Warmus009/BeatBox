package App;

import java.io.Serializable;
import java.util.ArrayList;

public class TrackListLoadOut implements Serializable {
    private static TrackListLoadOut _instance = null;
    private ArrayList<Boolean> LoadOut;

    private TrackListLoadOut() {
        LoadOut = new ArrayList<Boolean>();
    }

    public static TrackListLoadOut getInstance(){
        if(_instance == null){
            _instance = new TrackListLoadOut();
        }

        return _instance;
    }

    public static void set_instance(TrackListLoadOut _instance) {
        TrackListLoadOut._instance = _instance;
    }

    public void setLoadOut(ArrayList<Boolean> loadOut) {
        LoadOut = loadOut;
    }

    public ArrayList<Boolean> getLoadOut() {
        return LoadOut;
    }

    public void SaveState(){
        for(int i = 0; i < GUI.getInstance().getCheckBoxList().size(); i++){
            LoadOut.add(GUI.getInstance().getCheckBoxList().get(i).isSelected());
        }
    }

    public void LoadState(){
        for(int i = 0; i < GUI.getInstance().getCheckBoxList().size(); i++){
            GUI.getInstance().getCheckBoxList().get(i).setSelected(LoadOut.get(i));
        }
    }
}
