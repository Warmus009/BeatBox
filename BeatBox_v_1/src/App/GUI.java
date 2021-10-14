package App;

import IO.Input.ReaderFactory;
import IO.Output.WriterFactory;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.io.*;

public final class GUI implements Serializable{
    static private GUI _instance = null;
    private JFrame frame;
    private JMenuBar menuBar;
    private JPanel mainPanel;
    private GridLayout grid;
    private JPanel background;
    private Box nameBox;
    private Box buttonBox;
    private ArrayList<JCheckBox> checkBoxList;
    private ArrayList<JButton> buttonList;
    private String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat", "Acoustic Snare", "Crash Cymbal", "Hand Clap",
            "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibraslap", "Low-mid Tom", "High Agogo", "Open High Conda"};;
    private Midi_System midiSource = null;

    private GUI(){
        frame = new JFrame("BeatBox v 1.0.2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new JPanel();
        grid = new GridLayout();
        background = new JPanel();
        checkBoxList = new ArrayList<JCheckBox>();
        buttonBox = new Box(BoxLayout.Y_AXIS);
        buttonBox.add(Box.createVerticalStrut(5));
        nameBox = new Box(BoxLayout.Y_AXIS);
        buttonList = new ArrayList<JButton>();
        menuBar = new JMenuBar();
    }

    static public GUI getInstance(){
        if(_instance == null){
            _instance = new GUI();
        }

        return _instance;
    }

    public void setMidiSource(Midi_System midiSource) {
        this.midiSource = midiSource;
    }

    public ArrayList<JCheckBox> getCheckBoxList() {
        return checkBoxList;
    }

    public GridLayout getGrid() {
        return grid;
    }

    private void addButton(String name){
        JButton a = new JButton(name);
        buttonList.add(a);
        buttonBox.add(a);
        buttonBox.add(Box.createVerticalStrut(5));
    }

    public void buildGUI(){
        JMenu menu = new JMenu("File");
        BorderLayout layout = new BorderLayout();
        background = new JPanel(layout);
        background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        addButton("Start");
        buttonList.get(buttonList.size() - 1).addActionListener(new StartListener());

        addButton("Stop");
        buttonList.get(buttonList.size() - 1).addActionListener(new StopListener());

        addButton("Up Tempo");
        buttonList.get(buttonList.size() - 1).addActionListener(new UpTempoListener());

        addButton("Tempo Down");
        buttonList.get(buttonList.size() - 1).addActionListener(new DownTempoListener());

        addButton("Clear");
        buttonList.get(buttonList.size() - 1).addActionListener(new ClearListener());

        JMenuItem save = new JMenuItem("Save as");
        save.addActionListener(new SaveListener());

        JMenuItem load = new JMenuItem("Load");
        load.addActionListener(new LoadListener());

        menu.add(save);
        menu.add(load);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        for(int i = 0; i < instrumentNames.length; i++){
            nameBox.add(new Label(instrumentNames[i]));
        }

        background.add(BorderLayout.EAST, buttonBox);
        background.add(BorderLayout.WEST, nameBox);

        grid = new GridLayout(16, 16, 1, 2);
        mainPanel = new JPanel(grid);
        background.add(BorderLayout.CENTER, mainPanel);

        for(int i = 0; i < grid.getColumns() * grid.getRows(); i++){
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            checkBoxList.add(c);
            mainPanel.add(c);
        }

        frame.getContentPane().add(background);
        frame.setBounds(50, 50, 300, 300);
        frame.pack();
        frame.setVisible(true);
    }

    public class StartListener implements ActionListener {
        public void actionPerformed(ActionEvent a){
            midiSource.buildTrackAndStart();
        }
    }

    public class StopListener implements ActionListener{
        public void actionPerformed(ActionEvent a){
            midiSource.getSequencer().stop();
        }
    }

    public class UpTempoListener implements ActionListener{
        public void actionPerformed(ActionEvent a){
            float tempoFactor = midiSource.getSequencer().getTempoFactor();
            midiSource.getSequencer().setTempoFactor((float) (tempoFactor * 1.05));
        }
    }

    public class DownTempoListener implements ActionListener{
        public void actionPerformed(ActionEvent a){
            float tempoFactor = midiSource.getSequencer().getTempoFactor();
            midiSource.getSequencer().setTempoFactor((float) (tempoFactor * 0.95));
        }
    }

    public class ClearListener implements ActionListener{
        public void actionPerformed(ActionEvent a){
            int amountOfCheckBoxes = grid.getColumns() * grid.getRows();

            for(int i = 0; i < amountOfCheckBoxes; i++){
                checkBoxList.get(i).setSelected(false);
            }
            mainPanel.updateUI();
        }
    }

    public class SaveListener implements ActionListener{
        public void actionPerformed(ActionEvent a){
            JFileChooser fileSave = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "txt files", "txt");
            FileNameExtensionFilter filter1 = new FileNameExtensionFilter(
                    "serl files", "serl");
            fileSave.setFileFilter(filter);
            fileSave.setFileFilter(filter1);

            fileSave.showSaveDialog(frame);

            TrackListLoadOut.getInstance().SaveState();

            WriterFactory.getWriter(fileSave.getSelectedFile()).write(fileSave.getSelectedFile());
        }
    }
    public class LoadListener implements ActionListener{
        public void actionPerformed(ActionEvent a){
            JFileChooser fileLoad = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "txt files", "txt");
            FileNameExtensionFilter filter1 = new FileNameExtensionFilter(
                    "serl files", "serl");
            fileLoad.setFileFilter(filter);
            fileLoad.setFileFilter(filter1);

            fileLoad.setDialogTitle("Load");
            fileLoad.showSaveDialog(frame);

            TrackListLoadOut.getInstance().SaveState();
            TrackListLoadOut.getInstance().set_instance((TrackListLoadOut) ReaderFactory.getReader(fileLoad.getSelectedFile()).read(fileLoad.getSelectedFile()));
            TrackListLoadOut.getInstance().LoadState();

            mainPanel.updateUI();
        }
    }
}
