package restAssuredTests;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.defaultParser;
import static io.restassured.RestAssured.given;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import constants.Constants;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/*
 * Base URI in this example
 * http://idp.centralindia.cloudapp.azure.com:8181//
 * End point is : extract
 */
public class Operations {
    public static Logger log = LogManager.getLogger(Operations.class.getName());
    private static HashMap<String, Integer> hashmap_statuscodes = new HashMap<String, Integer>();

    /* POST REQUEST METHOD */
    /* FETCH RESPONSE AS PRETTY STRING */
    @SuppressWarnings("unchecked")
    public static String fn_PostRequest(String baseuri, String endpoint, String base64, String fileName) {
	baseURI = baseuri + endpoint;
	RequestSpecification request = given();
	JSONObject request_body_parameters = new JSONObject();
	request_body_parameters.put("Data", base64);
	/* CONSTRUCT HEADER */
	request.headers(Constants.fn_requestHeader(fileName));
	/* POST REQUEST */
	request.body(request_body_parameters.toJSONString());
	Response response = request.post(baseURI);
	fn_addStatusCodes(fileName, response.getStatusCode());
	return response.asPrettyString();
    }

    /* POST REQUEST METHOD */
    /* FETCH RESPOSNE AS JSON OBJECT */
    public static String fn_RestAssuredResponse(String baseuri, String endpoint, String base64, String fileName) {
	baseURI = baseuri + endpoint;
	RequestSpecification request = given();
	JSONObject request_body_parameters = new JSONObject();
	request_body_parameters.put("Data", base64);
	/* CONSTRUCT HEADER */
	request.headers(Constants.fn_requestHeader(fileName));
	/* POST REQUEST */
	request.body(request_body_parameters.toJSONString());
	Response response = request.post(baseURI);
	String body = response.getBody().asString();
	return body;
    }

    /*
     * PARSER METHOD fn_jsonParser_IMAGENAME fn_jsonParser_PAGENUMBER
     * fn_jsonParser_FIELDNAME fn_jsonParser_FIELDVALUE
     * fn_jsonParser_MODELCONFIDENCE fn_jsonParser_OCRCONFIDENCE
     * 
     * /* fn_jsonParser_IMAGENAME
     */
    @SuppressWarnings("unchecked")
    public static String fn_jsonParser_IMAGENAME(String baseuri, String endpoint, String base64, String fileName) {
	/* io.restassured.parsing.Parser */
	defaultParser = Parser.JSON;
	JSONObject request_body_parameters = new JSONObject();
	request_body_parameters.put("Data", base64);
	String imageName = given().headers(Constants.fn_requestHeader(fileName)).and()
		.body(request_body_parameters.toJSONString()).when().post(baseuri + endpoint).then()
		.contentType(ContentType.JSON).extract().path("uid");
	return imageName;
    }

    /* fn_jsonParser_PAGENUMBER */
    @SuppressWarnings("unchecked")
    public static HashMap<String, String> fn_jsonParser_PAGENUMBER(String baseuri, String endpoint, String base64,
	    String fileName) {
	/* io.restassured.parsing.Parser */
	defaultParser = Parser.JSON;
	JSONObject request_body_parameters = new JSONObject();
	request_body_parameters.put("Data", base64);
	HashMap<String, String> hm_result = new HashMap<String, String>();
	hm_result = given().headers(Constants.fn_requestHeader(fileName)).and()
		.body(request_body_parameters.toJSONString()).when().post(baseuri + endpoint).then()
		.contentType(ContentType.JSON).extract().path("result");
	return hm_result;
    }

    /* fn_jsonParser_FIELDNAME */
    @SuppressWarnings("unchecked")
    public static void fn_jsonParser_FIELDNAME(String baseuri, String endpoint, String base64, String fileName) {
	baseURI = baseuri + endpoint;
	RequestSpecification request = given();
	JSONObject request_body_parameters = new JSONObject();
	request_body_parameters.put("Data", base64);
	/* CONSTRUCT HEADER */
	request.headers(Constants.fn_requestHeader(fileName));
	/* POST REQUEST */
	request.body(request_body_parameters.toJSONString());
	Response response = request.post(baseURI);
    }

    /* INTERNAL METHODS NON REST ASSURED */
    public static void fn_addStatusCodes(String key, Integer value) {
	hashmap_statuscodes.put(key, value);
    }

    public static HashMap<String, Integer> fn_getStatusCodes() {
	return hashmap_statuscodes;
    }
}
