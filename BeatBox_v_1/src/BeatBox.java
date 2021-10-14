import App.GUI;
import App.Midi_System;

public class BeatBox {
    public static void main(String[] args){
        GUI.getInstance().setMidiSource(Midi_System.getInstance());
        Midi_System.getInstance().setGuiSource(GUI.getInstance());
        GUI.getInstance().buildGUI();
        Midi_System.getInstance().setUpMidi();
    }
}
