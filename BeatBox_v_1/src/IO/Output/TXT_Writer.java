package IO.Output;

import App.GUI;
import App.TrackListLoadOut;

import java.io.*;


public class TXT_Writer extends AbstractWriter {
    public void write(File file){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            for(int i = 0; i < TrackListLoadOut.getInstance().getLoadOut().size(); i++){
                writer.write(TrackListLoadOut.getInstance().getLoadOut().get(i).toString() + '\n');
            }

            writer.close();

        }catch(Exception e){
            System.out.println("Вызвано иксключение!");
            e.printStackTrace();
        }
    }
}
