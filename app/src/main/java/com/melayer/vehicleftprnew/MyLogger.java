package com.melayer.vehicleftprnew;

/**
 * Created by twtech on 5/7/17.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by tw on 9/23/2016.
 */
public class MyLogger {

    File myFile = new File("/sdcard/AppProcessLogger.txt");

    public void storeMassage(String tag, String msg){

        Calendar caldar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
        SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
        /*sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        stf.setTimeZone(TimeZone.getTimeZone("GMT"));*/
        String date1 = sdf.format(caldar.getTime());
        String time1 = stf.format(caldar.getTime());

        String logMsg = date1+", "+time1+" = "+tag+" -: "+msg+"\n";

        if (myFile.exists()) {
        } else {
            try {
                myFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            RandomAccessFile raf = new RandomAccessFile(myFile, "rw");
            long fileLength = myFile.length();
            raf.seek(fileLength);
            raf.writeBytes(logMsg);
            raf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
