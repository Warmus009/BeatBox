package IO.Input;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class SERL_Reader extends AbstractReader{
    public Object read(File file){
        try{
            ObjectInputStream os = new ObjectInputStream(new FileInputStream(file));
            return os.readObject();

        }catch(Exception e){
            System.out.println("Вызвано иксключение!");
            e.printStackTrace();
        }

        return null;
    }
}
