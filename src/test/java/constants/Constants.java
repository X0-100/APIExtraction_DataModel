package constants;

import java.io.File;

import io.restassured.http.Header;
import io.restassured.http.Headers;

public class Constants {
    public final static String baseuri = "http://idp.centralindia.cloudapp.azure.com:8181/";
    public final static String endpoint = "extract";
    private final static String IMAG = "D:\\TRANSFORMERAPIIMAGES\\";
    private final static String BAS64 = "D:\\writeBase64\\";
    private final static String writeJSONPath = "D:\\writeJSON2\\";
    private static File fileINGRAM_ACCURACY;

    public static final String fn_fetchImagePath() {
	return IMAG;
    }

    public static final String fn_fetchBase64Path() {
	return BAS64;
    }

    public static final String fn_fetchOutputJSONPath() {
	return writeJSONPath;
    }

    public static Headers fn_requestHeader(String filename) {
	// double uid_var = Math.random();
	Header Content_Type = new Header("Content-Type", "application/json");
	// Header uid = new Header("uid", "ox %s".formatted(uid_var));
	Header uid = new Header("uid", filename);
	Header model_id = new Header("model_id", "INVOICE_MODEL");
	Header lic_id = new Header("lic_id", "ox");
	Headers headers = new Headers(Content_Type, uid, model_id, lic_id);
	return headers;
    }

    public static String[] fn_getFieldsList(String imageClassification) {
	String[] FIELDS = {};
	if (imageClassification.compareTo("IDEA") == 0) {
	    FIELDS = new String[] { "vendor_name", "invoice_number", "invoice_date", "purchase_order_number",
		    "invoice_total", "email", "swift_code", "amount", "vendor_vat_no", "service_tax", "vat_tin",
		    "service_tax_reg_no", "cst", "swach_bharat", "krishi_kalyan_cess", "excise_duty_reg_no",
		    "excise_Amount", "sales_tax", "time_taken(ms)" };

	} else if (imageClassification.compareTo("INGRAM") == 0) {
	    FIELDS = new String[] { "invoice_number", "swift_code", "due_date", "vat_code", "invoice_total",
		    "order_number", "invoice_date", "customer_order_number", "page_number", "account_number", "bill_to",
		    "ship_date", "order_date", "remit_to", "sales_order_number", "net_amount", "cash_discount",
		    "amount", "doc_curr", "purchase_order_number", "charge_type", "customer_no", "vendor_vat_no",
		    "vendor_name", "tax_percent", "tax_amount", "ship_to", "LUT_remit_to-organization" };
	} else {
	    FIELDS = new String[] {};
	}
	return FIELDS;
    }

    public static File fn_fetchmasterfile(String fileMasterClient, String fileMasterLocation) throws Exception {
	File fileINGRAM_MASTER = new File(fileMasterLocation);
	if (!fileINGRAM_MASTER.isFile() && fileMasterClient.contentEquals("INGRAM")) {
	    throw new Exception("File is an invalid file");
	}
	return fileINGRAM_MASTER;
    }

    public static void fn_createaccuracyfile(String nameClient) throws Exception {
	if (nameClient.compareTo("INGRAM") != 0) {
	    throw new Exception("EXIT 001 - CIENT NAME NOT SUPPORTED");
	}
	fileINGRAM_ACCURACY = new File(".//API_ACC.xlsx");
    }

    public static File fn_fetchaccuracyfile(String nameClient) throws Exception {
	if (nameClient.compareTo("INGRAM") != 0) {
	    throw new Exception("EXIT 001 - CIENT NAME NOT SUPPORTED");
	}
	return fileINGRAM_ACCURACY;
    }

}
