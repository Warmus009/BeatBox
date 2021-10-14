package App;

import javax.sound.midi.*;
import javax.swing.*;
import java.io.Serializable;

public class Midi_System implements Serializable{
    static private Midi_System _instance = null;
    private Sequencer sequencer;
    private Sequence sequence;
    private Track track;
    public int[] instrumentCode = {35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 63};
    private GUI guiSource;

    private Midi_System(){
        try{
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setTempoInBPM(90);
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
        }catch(Exception e){
            System.out.println("Вызвано исключение!");
            e.printStackTrace();
        }
    }

    static public Midi_System getInstance(){
        if(_instance == null){
            _instance = new Midi_System();
        }

        return _instance;
    }

    public void setGuiSource(GUI guiSource) {
        this.guiSource = guiSource;
    }

    public Sequencer getSequencer() {
        return sequencer;
    }

    public void setUpMidi(){
        try{
            sequencer = MidiSystem.getSequencer();
            sequencer.open();

            sequencer.setTempoInBPM(120);

        }catch(Exception e){
            System.out.println("Выызвано исключение!");
            e.printStackTrace();
        }
    }

    public void buildTrackAndStart(){
        int[] trackList = new int[guiSource.getGrid().getColumns()];

        try{
            sequence = new Sequence(Sequence.PPQ, 4);
        }catch(Exception e){
            System.out.println("Выызвано исключение!");
            e.printStackTrace();
        }

        sequence.deleteTrack(track);
        track = sequence.createTrack();

        for(int i = 0; i < guiSource.getGrid().getRows(); i++){
            int key = instrumentCode[i];

            for(int j = 0; j < trackList.length; j++){
                JCheckBox jc = (JCheckBox) guiSource.getCheckBoxList().get(j + (i * guiSource.getGrid().getColumns()));

                if(jc.isSelected()){
                    trackList[j] = key;
                }else{
                    trackList[j] = 0;
                }
            }

            makeTracks(trackList);
            track.add(makeEvent(192, 9, 1, 0, 16));
        }

        try{
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();

        }catch(Exception e){
            System.out.println("Вызвано исключение!");
            e.printStackTrace();
        }
    }

    public void makeTracks(int[] list){
        for(int i = 0; i < guiSource.getGrid().getColumns(); i++){
            int key = list[i];

            if(key != 0){
                track.add(makeEvent(144, 9, key, 100, i));
                track.add(makeEvent(128, 9, key, 100, i + 1));
            }
        }
    }

    public MidiEvent makeEvent(int command, int channel, int data1, int data2, int tick){
        MidiEvent event = null;

        try{
            ShortMessage a = new ShortMessage();
            a.setMessage(command, channel, data1, data2);
            event = new MidiEvent(a, tick);

        }catch(Exception e){
            System.out.println("Вызвыно исключение!");
            e.printStackTrace();
        }

        return event;
    }
}
