package IO.Input;

import java.io.File;

public class ReaderFactory {
    static public AbstractReader getReader(File file){
        AbstractReader reader = null;

        if(file.getPath().substring(file.getPath().indexOf('.') + 1).equals("txt")){
            reader = new TXT_Reader();

        }else if (file.getPath().substring(file.getPath().indexOf('.') + 1).equals("serl")){
            reader = new SERL_Reader();
        }

        return reader;
    }
}
