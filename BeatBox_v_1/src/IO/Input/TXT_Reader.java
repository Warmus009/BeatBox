package IO.Input;

import App.TrackListLoadOut;

import java.io.*;
import java.util.ArrayList;

public class TXT_Reader extends AbstractReader {
    public Object read(File file){
        try{
            ArrayList<Boolean> arr = new ArrayList<>();
            String a;
            Boolean b;

            BufferedReader reader = new BufferedReader(new FileReader(file));

            for(int i = 0; i < TrackListLoadOut.getInstance().getLoadOut().size(); i++){
                a = reader.readLine();

                if(a.equals("false")){
                    b = false;
                    arr.add(b);
                }else if(a.equals("true")){
                    b = true;
                    arr.add(b);
                }
            }

            TrackListLoadOut.getInstance().setLoadOut(arr);
            return TrackListLoadOut.getInstance();

        }catch(Exception e){
            System.out.println("Вызвано иксключение!");
            e.printStackTrace();
        }

        return null;
    }
}
