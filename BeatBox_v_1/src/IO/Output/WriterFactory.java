package IO.Output;

import java.io.*;

public class WriterFactory {
    static public AbstractWriter getWriter(File file){
        AbstractWriter writer = null;

        if(file.getPath().substring(file.getPath().indexOf('.') + 1).equals("txt")){
            writer = new TXT_Writer();

        }else if (file.getPath().substring(file.getPath().indexOf('.') + 1).equals("serl")){
            writer = new SERL_Writer();
        }
        return writer;
    }
}
