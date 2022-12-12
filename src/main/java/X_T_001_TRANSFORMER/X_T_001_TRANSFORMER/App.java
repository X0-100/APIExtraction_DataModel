package X_T_001_TRANSFORMER.X_T_001_TRANSFORMER;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * Hello world!
 *
 */
public class App 
{	
	/* Declaring  file path*/
	public static  String get_FILEPATH() {
		final String file_path = "D:\\TRANSFORMERAPIIMAGES";
		return file_path;
	}
    public static void main( String[] args ) throws IOException
    {
    	FileUtils.readFileToByteArray(new File(get_FILEPATH()));
    }
}
