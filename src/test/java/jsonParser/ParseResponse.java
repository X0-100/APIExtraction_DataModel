package jsonParser;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import constants.Constants;
import restAssuredTests.Operations;
import utilities.Commons;
import writejson.WriteMeTheJson;

public class ParseResponse extends JSONObject {

    /* Hashtable extends Dictionary and implements Map */
    private static Hashtable<String, Hashtable<String, Map<String, Map<String, Map<String, String>>>>> HOPPER = new Hashtable<String, Hashtable<String, Map<String, Map<String, Map<String, String>>>>>();

    private static final String key_COMPONENT_TEXT = "text";
    private static final String key_COMPONENT_MODELCONF = "model_confidence";
    private static final String key_COMPONENT_OCRCONF = "OCR_confidence";

    private static String prevtext;

    public static JSONObject fn_fetchresposne_asJson(File file) throws IOException {
	String base64 = Commons.fn_base64string(file);
	String filename = file.getName();
	String responsebody = Operations.fn_RestAssuredResponse(Constants.baseuri, Constants.endpoint, base64,
		filename);
	JSONObject json = new JSONObject(responsebody);

	/* WRITING JSON TO FILE */
	WriteMeTheJson.writingNow(json, filename);
	;
	return json;
    }

    private static Hashtable<String, Map<String, Map<String, Map<String, String>>>> fn_parseObject(JSONObject json,
	    String key, String uid, String pageNumber) {

	Map<String, Map<String, Map<String, String>>> EXTRACTED_DATA = new HashMap<String, Map<String, Map<String, String>>>();
	Hashtable<String, Map<String, Map<String, Map<String, String>>>> IMAGE = new Hashtable<String, Map<String, Map<String, Map<String, String>>>>();
	if (json.has(key) && json.get(key) instanceof String) {
	    System.out.println("IMAGE NAME" + " " + uid);
	    System.out.println(key + " : " + json.get(key));
	    System.out.println("-------------------------------");
	}

	if (json.has(key) && json.get(key) instanceof Integer) {
	    System.out.println("IMAGE NAME" + " " + uid);
	    System.out.println(key + " : " + json.get(key));
	    System.out.println("-------------------------------");
	}

	if (json.has(key) && json.get(key) instanceof JSONObject) {
	    System.out.println("IMAGE NAME" + " " + uid);
	    System.out.println(key + " : " + json.get(key));
	    System.out.println("-------------------------------");
	}

	double prevmcf = 0.0;

	/* EXTRACTED FROM IMAGE PRINTON CONSOLE */
	if (json.get(key) instanceof JSONArray) {

	    JSONArray jsonarray = json.getJSONArray(key);

	    for (int run = 0; run < jsonarray.length(); run++) {

		String element = jsonarray.get(run).toString();
		JSONObject innerJSON_ = new JSONObject(element);
		Map<String, Map<String, String>> appender_FIELD = new HashMap<String, Map<String, String>>();
		Map<String, String> appender = new HashMap<String, String>();
		double currentmcf = Double.parseDouble(innerJSON_.get(key_COMPONENT_MODELCONF).toString());

		if (prevmcf > currentmcf) {
		    /* WRITE PREVIOUS MODEL CON */
		    appender.put(key_COMPONENT_TEXT, prevtext);
		    appender.put(key_COMPONENT_MODELCONF, String.valueOf(prevmcf));
		    appender.put(key_COMPONENT_OCRCONF, innerJSON_.get(key_COMPONENT_OCRCONF).toString());
		    appender_FIELD.put(key, appender);

		} else if (prevmcf == currentmcf) {
		    appender.put(key_COMPONENT_TEXT, innerJSON_.get(key_COMPONENT_TEXT).toString());
		    appender.put(key_COMPONENT_MODELCONF, innerJSON_.get(key_COMPONENT_MODELCONF).toString());
		    appender.put(key_COMPONENT_OCRCONF, innerJSON_.get(key_COMPONENT_OCRCONF).toString());
		    appender_FIELD.put(key, appender);
		}

		else if (currentmcf >= prevmcf) {
		    /* WRITE CURRENT MODEL CON */
		    appender.put(key_COMPONENT_TEXT, innerJSON_.get(key_COMPONENT_TEXT).toString());
		    appender.put(key_COMPONENT_MODELCONF, String.valueOf(currentmcf));
		    appender.put(key_COMPONENT_OCRCONF, innerJSON_.get(key_COMPONENT_OCRCONF).toString());
		    prevmcf = currentmcf;
		    prevtext = innerJSON_.get(key_COMPONENT_TEXT).toString();
		    appender_FIELD.put(key, appender);

		}

		try {
		    EXTRACTED_DATA.put(pageNumber, appender_FIELD);
		} catch (Exception ex) {
		    System.out.println(ex);
		}
	    }
	}
	IMAGE.put(uid, EXTRACTED_DATA);
	return IMAGE;

    }

    /* Hashtable<String, Map<String, Map<String, String>>> */

    /* IM-000000011378857-AP */
    public static void fn_getKey(JSONObject json, String key, String uid, String pageNumber) {
	boolean exists = json.has(key);
	Iterator<?> keys;
	String nextKeys;
	// String pageNumber;
	if (!exists) {
	    /* Traverse entire tree */
	    keys = json.keys();
	    while (keys.hasNext()) {
		nextKeys = (String) keys.next();
		pageNumber = ((nextKeys.contains("Page Number")) ? nextKeys : "");
		try {

		    if (json.get(nextKeys) instanceof JSONObject) {
			if (exists == false) {
			    /* RECURSION IS SO POWERFUL */
			    fn_getKey(json.getJSONObject(nextKeys), key, uid, pageNumber);
			}
		    } else if (json.get(nextKeys) instanceof JSONArray) {
			JSONArray jsonarray = json.getJSONArray(nextKeys);
			for (int run = 0; run < jsonarray.length(); run++) {
			    String jsonarraySTRING = jsonarray.get(run).toString();
			    JSONObject innerJSON = new JSONObject(jsonarraySTRING);
			    /* HERE COMES THE POWER OF RECURSION AGAIN */
			    if (exists == false) {
				fn_getKey(innerJSON, key, uid, pageNumber);
			    }
			}
		    }
		} catch (Exception ex) {

		}

	    }

	} else {
	    HOPPER.put(RandomStringUtils.randomNumeric(5).toString(), fn_parseObject(json, key, uid, pageNumber));
	}

    }

    public static Hashtable<String, Hashtable<String, Map<String, Map<String, Map<String, String>>>>> get_DESTACKED(
	    String imageName) {
	return HOPPER;
    }

    private static Hashtable<String, Hashtable<String, Map<String, Map<String, Map<String, String>>>>> result;

    /*
     * Return Type of below method is Hashtable<String, Hashtable<String,
     * Map<String, Map<String, Map<String, String>>>>>
     */
    @Test
    public static Hashtable<String, Hashtable<String, Map<String, Map<String, Map<String, String>>>>> parseValidator()
	    throws IOException {
	/* ONLINE METHOD */
	File folder_IMG = new File(Constants.fn_fetchImagePath());

	for (File file_IMG : folder_IMG.listFiles()) {
	    System.out.println(file_IMG.getName() + "  " + "IS BEING PROCESSED NOW .. ");
	    JSONObject json = fn_fetchresposne_asJson(file_IMG);

	    for (String FIELD : Constants.fn_getFieldsList("INGRAM")) {
		fn_getKey(json, FIELD, file_IMG.getName(), "");
	    }
	    result = get_DESTACKED(file_IMG.getName());
	}

	return result;
    }

}
