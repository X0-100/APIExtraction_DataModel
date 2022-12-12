package utilities;
import java.io.File;
import java.io.IOException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
public class Commons {
	public static String fn_base64string(File image_file) throws IOException {
		String base64_of_image = "";
		if(image_file.isFile()) {
			byte[] byte_image = FileUtils.readFileToByteArray(image_file);
			base64_of_image = Base64.encodeBase64String(byte_image);
		}
		return base64_of_image;
	}	
}
