package jsonParser;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.testng.annotations.Test;

import constants.Constants;

public class ParseResponse_OFFLINE {
    private static Hashtable<String, Hashtable<String, Map<String, Map<String, Map<String, String>>>>> result;

    @Test
    public static Hashtable<String, Hashtable<String, Map<String, Map<String, Map<String, String>>>>> invoke_OFFLINE() {
	/* OFFLINE METHOD */
	String inputString = "{\r\n" + "    \"status\": 200,\r\n" + "    \"uid\": \"1227.TIF.txt\",\r\n"
		+ "    \"model_id\": \"MODEL2\",\r\n" + "    \"license_id\": \"ox\",\r\n" + "    \"result\": {\r\n"
		+ "        \"Page Number 1\": {\r\n" + "            \"vendor_name\": [\r\n" + "                {\r\n"
		+ "                    \"text\": \"VOITTO\",\r\n" + "                    \"coordinates\": [\r\n"
		+ "                        159,\r\n" + "                        162,\r\n"
		+ "                        159,\r\n" + "                        130\r\n" + "                    ],\r\n"
		+ "                    \"model_confidence\": 93.53699999999999,\r\n"
		+ "                    \"OCR_confidence\": \"NA\"\r\n" + "                }\r\n" + "            ],\r\n"
		+ "            \"remit_to\": [\r\n" + "                {\r\n"
		+ "                    \"text\": \"Idea Cellular Limited \\n PSA Fort , Plot No. A1 , A2 and B , Nehru Nagar , \\n 1st Main Road , Off Old Mahabalipuram Road , \\n Chennai - 603104 \\n S.No\",\r\n"
		+ "                    \"coordinates\": [\r\n" + "                        136,\r\n"
		+ "                        139,\r\n" + "                        136,\r\n"
		+ "                        677\r\n" + "                    ],\r\n"
		+ "                    \"model_confidence\": 86.7255,\r\n"
		+ "                    \"OCR_confidence\": \"NA\"\r\n" + "                },\r\n"
		+ "                {\r\n"
		+ "                    \"text\": \"Thirunelveli Diocese College \\n Tirunelveli\",\r\n"
		+ "                    \"coordinates\": [\r\n" + "                        1128,\r\n"
		+ "                        1131,\r\n" + "                        1128,\r\n"
		+ "                        543\r\n" + "                    ],\r\n"
		+ "                    \"model_confidence\": 58.1305,\r\n"
		+ "                    \"OCR_confidence\": \"NA\"\r\n" + "                }\r\n" + "            ],\r\n"
		+ "            \"invoice_date\": [\r\n" + "                {\r\n"
		+ "                    \"text\": \"January 21 , 2016\",\r\n"
		+ "                    \"coordinates\": [\r\n" + "                        1128,\r\n"
		+ "                        1131,\r\n" + "                        1128,\r\n"
		+ "                        212\r\n" + "                    ],\r\n"
		+ "                    \"model_confidence\": 88.07449999999999,\r\n"
		+ "                    \"OCR_confidence\": \"NA\"\r\n" + "                }\r\n" + "            ],\r\n"
		+ "            \"purchase_order_number\": [\r\n" + "                {\r\n"
		+ "                    \"text\": \"VA - VT / 040 \\n 19-01-2016\",\r\n"
		+ "                    \"coordinates\": [\r\n" + "                        1128,\r\n"
		+ "                        1131,\r\n" + "                        1128,\r\n"
		+ "                        371\r\n" + "                    ],\r\n"
		+ "                    \"model_confidence\": 91.77,\r\n"
		+ "                    \"OCR_confidence\": \"NA\"\r\n" + "                }\r\n" + "            ],\r\n"
		+ "            \"invoice_number\": [\r\n" + "                {\r\n"
		+ "                    \"text\": \"319956\",\r\n" + "                    \"coordinates\": [\r\n"
		+ "                        1131,\r\n" + "                        1134,\r\n"
		+ "                        1131,\r\n" + "                        284\r\n" + "                    ],\r\n"
		+ "                    \"model_confidence\": 91.2285,\r\n"
		+ "                    \"OCR_confidence\": \"NA\"\r\n" + "                }\r\n" + "            ],\r\n"
		+ "            \"sales_order_number\": [\r\n" + "                {\r\n"
		+ "                    \"text\": \"12715401762\",\r\n" + "                    \"coordinates\": [\r\n"
		+ "                        1133,\r\n" + "                        1136,\r\n"
		+ "                        1133,\r\n" + "                        324\r\n" + "                    ],\r\n"
		+ "                    \"model_confidence\": 65.7495,\r\n"
		+ "                    \"OCR_confidence\": \"NA\"\r\n" + "                }\r\n" + "            ],\r\n"
		+ "            \"invoice_total\": [\r\n" + "                {\r\n"
		+ "                    \"text\": \"31,221.48\",\r\n" + "                    \"coordinates\": [\r\n"
		+ "                        1435,\r\n" + "                        1438,\r\n"
		+ "                        1435,\r\n" + "                        1686\r\n"
		+ "                    ],\r\n" + "                    \"model_confidence\": 92.815,\r\n"
		+ "                    \"OCR_confidence\": \"NA\"\r\n" + "                }\r\n" + "            ]\r\n"
		+ "        }\r\n" + "    },\r\n" + "    \"time_taken(ms)\": 2148.488\r\n" + "}";
	JSONObject json = new JSONObject(inputString);
	// fn_getKey(json,"remit_to","IM-000000011378574-AP.pdf.txt");
	String[] FIELDS = new String[] { "vendor_name", "invoice_number", "invoice_date", "purchase_order_number",
		"invoice_total", "email", "swift_code", "amount", "vendor_vat_no", "service_tax", "vat_tin",
		"service_tax_reg_no", "cst", "swach_bharat", "krishi_kalyan_cess", "excise_duty_reg_no",
		"excise_Amount", "sales_tax" };
	for (String key : FIELDS) {
	    System.out.println("PROCESSING ...." + key);
	    ParseResponse.fn_getKey(json, key, "1227.TIF", "");
	    result = ParseResponse.get_DESTACKED("1227.TIF");
	}
	// System.out.println(result);
	return result;
    }

    /*
     * RETURN TYPE OF BELOW METHOD : Hashtable<String, Hashtable<String, Map<String,
     * Map<String, Map<String, String>>>>>
     */
    @Test
    public static Hashtable<String, Hashtable<String, Map<String, Map<String, Map<String, String>>>>> invoke_OFFLINE_AUTO()
	    throws IOException {
	/* OFFLINE METHOD */
	File folder = new File(Constants.fn_fetchOutputJSONPath());
	for (File file : folder.listFiles()) {
	    String inputStringGET = FileUtils.readFileToString(file, "UTF-8");
	    JSONObject json = new JSONObject(inputStringGET);
	    for (String key : Constants.fn_getFieldsList("INGRAM")) {
		ParseResponse.fn_getKey(json, key, file.getName(), "");
		result = ParseResponse.get_DESTACKED(file.getName());
	    }
	    System.out.println("PROCESING FILE:   " + file.getName());
	}
	return result;
    }

}
