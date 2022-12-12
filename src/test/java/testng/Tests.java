package testng;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.*;
import constants.Constants;
/* IMPORT FACTORY CLASSES */
import factoryclasses.FetchFactory;
import restAssuredTests.Operations;
import utilities.Commons;
import org.apache.logging.log4j.*;
public class Tests{
	/* https://stackoverflow.com/
	 * questions/21206993/very-simple-log4j2-xml-configuration-file-using-console-and-file-appender */
	public static Logger log = LogManager.getLogger(Tests.class.getName());
	/* 
	INITIALIZE TEST 
	*/
	@BeforeClass
	public void T000_setUp() throws IOException {
		log.debug("Writing Base64 from Image : START");
		FetchFactory.fn_writeBase64();
		FetchFactory.fn_PostAPI(Constants.baseuri,
				Constants.endpoint);
		log.debug("Writing JSON RESPONSE - OK ");
	}
	/* TESTNG: 
	TEST ALL IMAGE REQUEST TO API STATUS CODE IS 200
	*/
	@Test
	public void T001_StatusCode() throws IOException {
		HashMap<String, Integer> hm_response = FetchFactory.fn_fetchStatusCodes();
		for(Map.Entry<String, Integer> entry : hm_response.entrySet()) {
			Assert.assertEquals(entry.getValue(),200);
		}
	}
	/*
	TEST IMAGE NAMES
	*/
	@Test
	public void T002_ImageName() throws IOException {
		File folder = new File(Constants.fn_fetchImagePath());
		for(File file : folder.listFiles()) {
			String fileName = file.getName();
			String base64 = Commons.fn_base64string(file);
			String imageName = Operations.fn_jsonParser_IMAGENAME(Constants.baseuri, Constants.endpoint, base64, fileName);
			Assert.assertEquals(fileName, imageName);
		}
	}
	/*
	TEST PAGE NUMBER
	*/
	@Test
	public void T003_PageNumber() throws IOException {
		File folder = new File(Constants.fn_fetchImagePath());
		HashMap<String, String> hm_result = new HashMap<String, String>();
		List<String> pages = new ArrayList<String>();
		for(File file : folder.listFiles()) {
			String fileName = file.getName();
			String base64 = Commons.fn_base64string(file);
			hm_result = Operations.fn_jsonParser_PAGENUMBER(Constants.baseuri, Constants.endpoint, base64, fileName);
			Assert.assertEquals(hm_result.size()>0, true);
			for(Map.Entry<String, String> pagenumber : hm_result.entrySet()) {
				pages.add(pagenumber.getKey());
				List<String> fields = new ArrayList<String>();
			}
		}
		Assert.assertEquals(pages.size()>0, true);
	}
	/*
	TEST FIELD NAMES
	*/
	@Test
	public void T003_Field() throws IOException {
		
	}
}
