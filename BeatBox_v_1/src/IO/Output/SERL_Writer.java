package IO.Output;

import App.TrackListLoadOut;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class SERL_Writer extends AbstractWriter{
    public void write(File file){
        try{
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(file));
            os.writeObject(TrackListLoadOut.getInstance());
        }catch(Exception e){
            System.out.println("Вызвано иксключение!");
            e.printStackTrace();
        }
    }
}
