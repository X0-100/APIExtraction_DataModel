package factoryclasses;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import constants.Constants;
public class FetchFactory {
	public static void fn_writeBase64() throws IOException {
		String base64_string;
		String base64Path = Constants.fn_fetchBase64Path();
		List<String> fileNames = new ArrayList<String>();
		File folder = new File(Constants.fn_fetchImagePath());
		File[] listOfFiles = folder.listFiles();
		for(File file: listOfFiles) {		
			if(file.isFile()) {
				fileNames.add(file.getName());
			}
		}
		for(String fileName : fileNames) {
			String image_path = Constants.fn_fetchImagePath();
			image_path = image_path+fileName;
			byte [] encoded = FileUtils.readFileToByteArray(new File(image_path));
			base64_string = Base64.encodeBase64String(encoded);		
			FileWriter writeFile = new FileWriter(base64Path+fileName+".txt");
			writeFile.write(base64_string);
			writeFile.close();
		}
	}
	public static void fn_PostAPI(String baseuri, String endpoint) throws IOException {
		String BAS64 = Constants.fn_fetchBase64Path();
		List<String> fileNames = new ArrayList<String>();
		File folder = new File(BAS64);
		File[] listOfFiles = folder.listFiles();
		for(File file: listOfFiles){ 
			if(file.isFile())
			{
				fileNames.add(file.getName());
			} 
		}
		for(String fileName: fileNames) {
			BAS64 = BAS64+fileName;
			String xread = FileUtils.readFileToString(new File(BAS64), StandardCharsets.UTF_8);
			/* Call Rest Assured Operation class*/
			String response_post = restAssuredTests.Operations.fn_PostRequest(baseuri,endpoint, xread, fileName);
			fn_writeResponse(fileName,response_post);
			BAS64 = Constants.fn_fetchBase64Path();
		}
	}
	
	public static void fn_writeResponse(String fileName, String response_post) throws IOException {
		String writeJSONPath = Constants.fn_fetchOutputJSONPath();
		FileWriter writeJson = new FileWriter(new File(writeJSONPath+fileName+".json"));
		writeJson.write(response_post);
		writeJson.close();
	}
	public static HashMap<String, Integer> fn_fetchStatusCodes(){
		return (restAssuredTests.Operations.fn_getStatusCodes());
	}
}
