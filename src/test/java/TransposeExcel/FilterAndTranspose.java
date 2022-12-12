package TransposeExcel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import constants.Constants;

public class FilterAndTranspose {

    public static FileOutputStream fos;
    public static FileInputStream fis;
    public static Set<String> images = new HashSet<String>();

    /*
     * 1 - Remove the duplicate entries in Image Names
     */

    @Test(priority = 0)
    public static void rearrange_images() throws IOException {
	fis = new FileInputStream("D://Mapping Codes//Master_Data_Saurabh.xlsx");
	XSSFWorkbook wb_IN = new XSSFWorkbook("D://Mapping Codes//Master_Data_Saurabh.xlsx");
	XSSFSheet sheet_IN = wb_IN.getSheetAt(0);

	for (int i = 1; i <= sheet_IN.getLastRowNum(); i++) {
	    XSSFRow row = sheet_IN.getRow(i);
	    XSSFCell cell_image = row.getCell(1);

	    images.add(cell_image.getStringCellValue());

	}

	wb_IN.close();
	fis.close();

    }

    @Test(priority = 1)
    public static void write_image_names() throws Exception {
	fos = new FileOutputStream("./Master_Data_Transpose_.xlsx");

	XSSFWorkbook wb_write = new XSSFWorkbook();
	XSSFSheet sheet_write = wb_write.createSheet("Master-Transpose-Format");
	int row_no = 1;

	for (String image : images) {
	    // System.out.println(image);
	    XSSFRow rowWrite = sheet_write.createRow(row_no);
	    XSSFCell cellWrite = rowWrite.createCell(0);
	    cellWrite.setCellValue(image);
	    row_no = row_no + 1;
	}

	wb_write.write(fos);
	wb_write.close();
	fos.close();

    }

    @Test(priority = 2)
    public static void create_header() throws Exception {
	fis = new FileInputStream("./Master_Data_Transpose_.xlsx");
	XSSFWorkbook wbREAD = new XSSFWorkbook(fis);

	fos = new FileOutputStream("./Master_Data_Transpose_.xlsx");
	XSSFWorkbook wbwrite = new XSSFWorkbook();

	XSSFSheet wbreadsheet = wbREAD.getSheetAt(0);
	XSSFRow wbwriterow = wbreadsheet.createRow(0);
	XSSFCell imagename = wbwriterow.createCell(0);
	imagename.setCellValue("IMAGE");

	String[] fieldlist = Constants.fn_getFieldsList("INGRAM");
	int cell_no = 1;

	for (String field : fieldlist) {
	    XSSFCell cell_iterate = wbwriterow.createCell(cell_no);
	    cell_iterate.setCellValue(field);
	    cell_no = cell_no + 1;
	}

    }

    @Test(priority = 3)
    public static void process_image_details() throws Exception {
	fis = new FileInputStream("D://Mapping Codes//Master_Data_Saurabh.xlsx");
	XSSFWorkbook wbread = new XSSFWorkbook(fis);
	XSSFSheet wbsheet = wbread.getSheetAt(0);

	fos = new FileOutputStream("./Master_Data_Transpose_.xlsx");
	XSSFWorkbook wbwrite = new XSSFWorkbook();
	// XSSFSheet wbwritesheet = wbwrite.getSheetAt(0);

	Map<String, String> mapped_entries = new HashMap<String, String>();

	for (int i = 1; i <= wbsheet.getLastRowNum(); i++) {
	    XSSFRow row = wbsheet.getRow(i);
	    XSSFCell cell = row.getCell(1);
	    for (String image : images) {
		if (cell.getStringCellValue().compareTo(image) == 0) {

		    DataFormatter df = new DataFormatter();

		    System.out.println(cell.getStringCellValue() + "|   " + df.formatCellValue(row.getCell(2)) + "|   "
			    + df.formatCellValue(row.getCell(3)));

		    // XSSFRow row_create = wbwritesheet.createRow(i);

		    break;
		}
	    }
	}
    }

}
