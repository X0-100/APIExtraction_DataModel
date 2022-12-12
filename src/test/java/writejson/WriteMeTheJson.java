package writejson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

import org.json.JSONObject;

public class WriteMeTheJson {
    public static FileWriter file;

    public static void writingNow(JSONObject obj, String filename) {
	try {
	    file = new FileWriter(".//crunchifyJSON-" + filename + ".txt");
	    file.write(obj.toString());
	    CrunchifyLog("---------------------------PROCESSED--------------------------------");
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    try {
		file.flush();
		file.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }

    static public void CrunchifyLog(String str) {
	System.out.println(str);
    }

    public static void writingNowLargerJSON(
	    Hashtable<String, Hashtable<String, Hashtable<String, Hashtable<String, String>>>> resultant) {
	try {
	    file = new FileWriter(".//largerJSON-" + ".txt");
	    file.write(resultant.toString());
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    try {
		file.flush();
		file.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }
}
