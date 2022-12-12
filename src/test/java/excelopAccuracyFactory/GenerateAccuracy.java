package excelopAccuracyFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import constants.Constants;
import excelop.OperationsXL;

public class GenerateAccuracy {

    private static DataFormatter df = new DataFormatter();

    private static Map<String, String> imageName = new HashMap<String, String>();
    private static Map<String, String> wb1_READ_mapImage = new HashMap<String, String>();
    private static FileOutputStream outputstreamAccuracy;

    private static Map<String, String> masterTAXAmount = new HashMap<String, String>();
    private static Map<String, String> outputTAXAmount = new HashMap<String, String>();
    private static Map<String, String> mastergrossAmount = new HashMap<String, String>();
    private static Map<String, String> outputgrossAmount = new HashMap<String, String>();
    private static Map<String, String> mastercustnumber = new HashMap<String, String>();
    private static Map<String, String> outputcustomernumber = new HashMap<String, String>();
    private static Map<String, String> masterpurchasenumber = new HashMap<String, String>();
    private static Map<String, String> outputpurchasenumber = new HashMap<String, String>();
    private static Map<String, String> mastervendorname = new HashMap<String, String>();
    private static Map<String, String> outputvendorname = new HashMap<String, String>();
    private static Map<String, String> masterdoccurr = new HashMap<String, String>();
    private static Map<String, String> outputdoccurrname = new HashMap<String, String>();
    private static Map<String, String> masterinvoicenumebr = new HashMap<String, String>();
    private static Map<String, String> outputinvoicenumber = new HashMap<String, String>();
    private static Map<String, String> mastershipto = new HashMap<String, String>();
    private static Map<String, String> outputshipto = new HashMap<String, String>();
    private static Map<String, String> masterinvoicedate = new HashMap<String, String>();
    private static Map<String, String> outputinvoicedate = new HashMap<String, String>();

    @BeforeClass
    public static void run() throws IOException {
	OperationsXL.writeMaster();
    }

    /*
     * SECRET 1 _
     * 1-----------------------------------------------------------------------
     * ---------
     */
    /*---------------------------------*/public static File fn_fetchfileMaster() throws Exception {
	/* HARD-CO-d-ED */return Constants.fn_fetchmasterfile("INGRAM", "D:\\Mapping Codes\\Ingram-MasterData.xlsx");
    }

    /* foutAccuracy */
    public static FileOutputStream fetchACCuracyOutputSTream() throws FileNotFoundException {
	outputstreamAccuracy = new FileOutputStream(".//API_ACC.xlsx");
	return outputstreamAccuracy;
    }

    /* foutModified */
    public static FileOutputStream fetchMODifiedFile() throws FileNotFoundException {
	FileOutputStream foutModified = new FileOutputStream(".//API_MOD.xlsx");
	return foutModified;
    }

    public static Map<String, String> readMasterSheet() throws Exception {
	File masterfile = fn_fetchfileMaster();
	XSSFWorkbook wb = new XSSFWorkbook(masterfile);
	XSSFSheet masterSheet = wb.getSheet("Non-Tabular");
	for (int i = 1; i <= masterSheet.getLastRowNum(); i++) {
	    XSSFRow row = masterSheet.getRow(i);
	    XSSFCell cell = row.getCell(0);
	    imageName.put("image" + i, cell.getStringCellValue());
	}
	wb.close();
	return imageName;
    }

    /* op_00 = WRITE IMAGE "COLUMN 0" */

    public static XSSFWorkbook fetch_AND_WRITE_ImageNames() throws Exception {
	Map<String, String> writeIMG = readMasterSheet();
	XSSFWorkbook wbWORK = new XSSFWorkbook();
	XSSFSheet xsfsheet = wbWORK.createSheet("GEN_ACC");
	XSSFRow rowHeader = xsfsheet.createRow(0);
	XSSFCell cellHeader = rowHeader.createCell(0);
	cellHeader.setCellValue("MASTER-IMAGE");

	int i = 1;
	while (i <= writeIMG.size()) {
	    for (Map.Entry<String, String> imageID : imageName.entrySet()) {
		// ("Writing Image : " + imageID.getValue());
		XSSFRow rowWrite = xsfsheet.createRow(i);
		XSSFCell cellWrite = rowWrite.createCell(0);
		cellWrite.setCellValue(imageID.getValue());
		i++;
	    }
	}
	return wbWORK;
    }

    /* op_01 = .//API_ACC.xlsx */
    @Test
    public static void op_01____WB1____IMAGE() throws Exception {
	XSSFWorkbook wbWORK = fetch_AND_WRITE_ImageNames();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	XSSFSheet wb1Sheet = wbWORK.getSheet("GEN_ACC");
	int mAXRow = wb1Sheet.getLastRowNum();
	XSSFRow wb1Row = wb1Sheet.getRow(0);
	XSSFCell wb2Cell = wb1Row.getCell(0);
	/* INTENDING TO GET IMAGES TO A MAPPED DATA STRUCTURE */
	for (int i = 1; i <= mAXRow; i++) {
	    wb1Row = wb1Sheet.getRow(i);
	    wb1_READ_mapImage.put("Image" + i, wb2Cell.getStringCellValue());
	}
	wbWORK.write(outputstreamAccuracy);
	wbWORK.close();
	outputstreamAccuracy.close();
    }

    /* op_02 = .//API_MOD.xlsx */
    public static Map<String, String> op_02____WB2____IMAGE() throws InvalidFormatException, IOException {
	File file = new File(".//API_MOD.xlsx");
	FileInputStream fis = new FileInputStream(file);
	XSSFWorkbook wb2Read = new XSSFWorkbook(fis);
	XSSFSheet wb2Sheet = wb2Read.getSheet("EXTRAC-RES");
	int mAXRow = wb2Sheet.getLastRowNum();
	XSSFRow wb2Row;
	XSSFCell wb2Cell;
	/* INTENDING TO GET IMAGES TO A MAPPED DATA STRUCTURE */
	Map<String, String> wb2_READ_mapImage = new HashMap<String, String>();
	for (int i = 1; i <= mAXRow; i++) {
	    wb2Row = wb2Sheet.getRow(i);
	    wb2Cell = wb2Row.getCell(0);
	    String value = wb2Cell.getStringCellValue();
	    wb2_READ_mapImage.put("Image" + i, value);
	}
	wb2Read.close();
	fis.close();
	return wb2_READ_mapImage;
    }

    /* op_03 = VER_IMAGE */
    public static void op_03____WB1____VER_IMAGE() throws Exception {
	op_01____WB1____IMAGE(); /* .//API_ACC.xlsx */
	Map<String, String> wb2_READ_mapImage = op_02____WB2____IMAGE(); /* .//API_MOD.xlsx */
	FileInputStream fis_accuracy = new FileInputStream(new File(".//API_ACC.xlsx"));
	XSSFWorkbook wb_acc = new XSSFWorkbook(fis_accuracy);
	XSSFSheet w_WSheet = wb_acc.getSheet("GEN_ACC");
	/* 0 - 1 */
	XSSFRow r_H = w_WSheet.getRow(0);
	XSSFCell c_H = r_H.createCell(1);
	c_H.setCellValue("OUTPUT-IMAGE");
	XSSFRow r_V;
	XSSFCell c_Image;
	String c_V;

	/* 1,2,3,4,5.... - 1 */
	for (int i = 1; i <= w_WSheet.getLastRowNum(); i++) {
	    r_V = w_WSheet.getRow(i);
	    c_Image = r_V.getCell(0);
	    c_V = c_Image.getStringCellValue();
	    for (Map.Entry<String, String> mapEntryProcessing : wb2_READ_mapImage.entrySet()) {
		if (mapEntryProcessing.getValue().contains(c_V)) {
		    XSSFCell cellValue = r_V.createCell(1);
		    cellValue.setCellValue(mapEntryProcessing.getValue());
		    break;
		} else {
		    XSSFCell cellValue_NF = r_V.createCell(1);
		    cellValue_NF.setCellValue("IMAGE NOT FOUND");
		}
	    }
	}
	fis_accuracy.close();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wb_acc.write(outputstreamAccuracy);
	wb_acc.close();
	outputstreamAccuracy.close();
    }

    /*
     * CHECK AND COMPARE
     *
     */

    public static void checkandcompare() {

    }

    // TRANSFORMER - MASTER
    // invoice_number - ven_doc_num
    // invoice_date - doc_date
    // amount - gross_amt
    // purchase_order_number - PO_No
    // ship_to - Ship_To
    // tax_amount - Tax_Amount
    // customer_no - cust_no
    // vendor_name - Vendor Name
    // doc_curr - doc_curr

    private static List<String> addHeaderList_apimod() {
	List<String> api_modlist = new ArrayList<String>();
	api_modlist.add("invoice_number");
	api_modlist.add("invoice_date");
	api_modlist.add("invoice_total");
	api_modlist.add("purchase_order_number");
	api_modlist.add("ship_to");
	api_modlist.add("tax_amount");
	api_modlist.add("customer_no");
	api_modlist.add("vendor_name");
	api_modlist.add("doc_curr");
	return api_modlist;
    }

    private static List<String> addHeaderList_master() {
	List<String> api_masterlist = new ArrayList<String>();
	api_masterlist.add("ven_doc_num");
	api_masterlist.add("doc_date");
	api_masterlist.add("gross_amt");
	api_masterlist.add("PO_No");
	api_masterlist.add("Ship_To");
	api_masterlist.add("Tax_Amount");
	api_masterlist.add("cust_no");
	api_masterlist.add("Vendor Name");
	api_masterlist.add("doc_curr");
	return api_masterlist;
    }

    /* op_04 = .//API_MOD.xlsx */
    public static Map<String, String> op_02____MAP____HEADER() throws InvalidFormatException, IOException {

	List<String> apimod = addHeaderList_apimod();
	List<String> apimaster = addHeaderList_master();

	Map<String, String> mapHeader = new HashMap<String, String>();

	for (int i = 0; i < apimod.size(); i++) {
	    mapHeader.put(apimod.get(i), apimaster.get(i));
	}

	/*
	 * {tax_amount=Tax_Amount, amount=gross_amt, customer_no=cust_no,
	 * purchase_order_number=PO_No, vendor_name=Vendor Name, doc_curr=doc_curr,
	 * invoice_number=ven_doc_num, ship_to=Ship_To, invoice_date=doc_date}
	 */

	return mapHeader;

    }

    /* FIND CELL NUMBER IN OUTPUT */
    public static int returnCellCountFindInOutput(String cellname) throws Exception {
	File outputfile = new File(".//API_MOD.xlsx");
	FileInputStream fis_outputFile = new FileInputStream(outputfile);

	XSSFWorkbook wb = new XSSFWorkbook(fis_outputFile);
	XSSFSheet outputSheet = wb.getSheet("EXTRAC-RES");

	XSSFRow rowITERATE = outputSheet.getRow(0);
	XSSFCell cellITERATE;
	XSSFCell cellRETURN;
	int cell_number = 0;

	for (int cell = 0; cell <= rowITERATE.getLastCellNum(); cell++) {
	    cellITERATE = rowITERATE.getCell(cell);

	    if (cellITERATE.getStringCellValue().compareTo(cellname) == 0) {
		cellRETURN = rowITERATE.getCell(cell);

		cell_number = cell;
		break;
	    }
	}

	wb.close();
	fis_outputFile.close();
	return cell_number;

    }

    /* FIND CELL NUMBER IN MASTER */
    public static int returnCellCountFindInMaster(String cellname) throws Exception {
	File masterfile = fn_fetchfileMaster();
	FileInputStream fis_master = new FileInputStream(masterfile);

	XSSFWorkbook wb = new XSSFWorkbook(fis_master);
	XSSFSheet outputSheet = wb.getSheet("Non-Tabular");

	XSSFRow rowITERATE = outputSheet.getRow(0);
	XSSFCell cellITERATE;
	XSSFCell cellRETURN;
	int cell_number = 0;

	for (int cell = 0; cell <= rowITERATE.getLastCellNum(); cell++) {
	    cellITERATE = rowITERATE.getCell(cell);

	    if (cellITERATE.getStringCellValue().compareTo(cellname) == 0) {
		cellRETURN = rowITERATE.getCell(cell);

		cell_number = cell;
		break;
	    }
	}

	wb.close();
	fis_master.close();
	return cell_number;

    }

    /*
     * STARTS
     * =============================================================================
     */
    public static void tax_AmountMaster() throws Exception {
	/* IMAGE NAME AS THE KEY */
	/* TAX AMOUNT AS THE VALUE */
	File masterfile = fn_fetchfileMaster();
	FileInputStream fis_master = new FileInputStream(masterfile);

	XSSFWorkbook wb = new XSSFWorkbook(fis_master);
	XSSFSheet masterSheet = wb.getSheet("Non-Tabular");

	XSSFRow imageRow;
	XSSFCell imagecell;
	XSSFCell taxamountcell;
	String txamtValue;

	for (int i = 1; i <= masterSheet.getLastRowNum(); i++) {
	    imageRow = masterSheet.getRow(i);
	    imagecell = imageRow.getCell(0);
	    taxamountcell = imageRow.getCell(returnCellCountFindInMaster("Tax_Amount"));
	    if (taxamountcell != null) {
		txamtValue = df.formatCellValue(taxamountcell).toString();
	    } else {
		txamtValue = "";
	    }

	    masterTAXAmount.put(imagecell.getStringCellValue(), txamtValue);
	}

	fis_master.close();

    }

    public static void tax_Amount_Output() throws Exception {
	/* IMAGE NAME AS THE KEY */
	/* TAX AMOUNT AS THE VALUE */
	File outputfile = new File(".//API_MOD.xlsx");
	FileInputStream fis_outputFile = new FileInputStream(outputfile);

	XSSFWorkbook wb = new XSSFWorkbook(fis_outputFile);
	XSSFSheet outputSheet = wb.getSheet("EXTRAC-RES");

	XSSFRow imageRow;
	XSSFCell imagecell;
	XSSFCell taxamountcell;
	String txamtValue;

	for (int i = 1; i <= outputSheet.getLastRowNum(); i++) {
	    imageRow = outputSheet.getRow(i);
	    imagecell = imageRow.getCell(0);
	    taxamountcell = imageRow.getCell(returnCellCountFindInOutput("tax_amount"));
	    if (taxamountcell != null) {
		txamtValue = df.formatCellValue(taxamountcell).toString();
	    } else {
		txamtValue = "";
	    }

	    outputTAXAmount.put(imagecell.getStringCellValue(), txamtValue);
	}
	// (masterTAXAmount);

	fis_outputFile.close();

	// return masterTAXAmount;
    }

    public static void grossAmountMaster() throws Exception {
	/* IMAGE NAME AS THE KEY */
	/* TAX AMOUNT AS THE VALUE */
	File masterfile = fn_fetchfileMaster();
	FileInputStream fis_master = new FileInputStream(masterfile);

	XSSFWorkbook wb = new XSSFWorkbook(fis_master);
	XSSFSheet masterSheet = wb.getSheet("Non-Tabular");

	XSSFRow imageRow;
	XSSFCell imagecell;
	XSSFCell grossAmountCell;
	String txgrossamtValue;

	for (int i = 1; i <= masterSheet.getLastRowNum(); i++) {
	    imageRow = masterSheet.getRow(i);
	    imagecell = imageRow.getCell(0);
	    grossAmountCell = imageRow.getCell(returnCellCountFindInMaster("gross_amt"));
	    if (grossAmountCell != null) {
		txgrossamtValue = df.formatCellValue(grossAmountCell).toString();
	    } else {
		txgrossamtValue = "";
	    }

	    mastergrossAmount.put(imagecell.getStringCellValue(), txgrossamtValue);
	}

	// (masterTAXAmount);
	fis_master.close();
	// return masterTAXAmount;
    }

    /* GET CELL BY NAME - INVOICE TOTAL */

    /* Mapping of Gross_Amount to Invoice Total */
    public static void invoiceTotalOutput() throws Exception {
	/* IMAGE NAME AS THE KEY */
	/* TAX AMOUNT AS THE VALUE */
	File outputfile = new File(".//API_MOD.xlsx");
	FileInputStream fis_outputFile = new FileInputStream(outputfile);

	XSSFWorkbook wb = new XSSFWorkbook(fis_outputFile);
	XSSFSheet outputSheet = wb.getSheet("EXTRAC-RES");

	XSSFRow imageRow;
	XSSFCell imagecell;
	XSSFCell grossamountcell;
	String grossamtValue;

	for (int i = 1; i <= outputSheet.getLastRowNum(); i++) {
	    imageRow = outputSheet.getRow(i);
	    imagecell = imageRow.getCell(0);
	    grossamountcell = imageRow.getCell(returnCellCountFindInOutput("invoice_total"));
	    if (grossamountcell != null) {
		grossamtValue = df.formatCellValue(grossamountcell).toString();
	    } else {
		grossamtValue = "";
	    }

	    outputgrossAmount.put(imagecell.getStringCellValue(), grossamtValue);
	}
	// (masterTAXAmount);

	fis_outputFile.close();

	// return masterTAXAmount;
    }

    public static void cust_noMaster() throws Exception {
	/* IMAGE NAME AS THE KEY */
	/* TAX AMOUNT AS THE VALUE */
	File masterfile = fn_fetchfileMaster();
	FileInputStream fis_master = new FileInputStream(masterfile);

	XSSFWorkbook wb = new XSSFWorkbook(fis_master);
	XSSFSheet masterSheet = wb.getSheet("Non-Tabular");

	XSSFRow imageRow;
	XSSFCell imagecell;
	XSSFCell custnoCell;
	String custnoVAL;

	for (int i = 1; i <= masterSheet.getLastRowNum(); i++) {
	    imageRow = masterSheet.getRow(i);
	    imagecell = imageRow.getCell(0);
	    custnoCell = imageRow.getCell(returnCellCountFindInMaster("cust_no"));
	    if (custnoCell != null) {
		custnoVAL = df.formatCellValue(custnoCell).toString();
	    } else {
		custnoVAL = "";
	    }

	    mastercustnumber.put(imagecell.getStringCellValue(), custnoVAL);
	}

	fis_master.close();

    }

    public static void cust_noOutput() throws Exception {
	/* IMAGE NAME AS THE KEY */
	/* TAX AMOUNT AS THE VALUE */
	File outputfile = new File(".//API_MOD.xlsx");
	FileInputStream fis_outputFile = new FileInputStream(outputfile);

	XSSFWorkbook wb = new XSSFWorkbook(fis_outputFile);
	XSSFSheet outputSheet = wb.getSheet("EXTRAC-RES");

	XSSFRow imageRow;
	XSSFCell imagecell;

	XSSFCell custnocell;
	String custnoValue;

	for (int i = 1; i <= outputSheet.getLastRowNum(); i++) {
	    imageRow = outputSheet.getRow(i);
	    imagecell = imageRow.getCell(0);
	    custnocell = imageRow.getCell(returnCellCountFindInOutput("customer_no"));
	    if (custnocell != null) {
		custnoValue = df.formatCellValue(custnocell).toString();
	    } else {
		custnoValue = "";
	    }

	    outputcustomernumber.put(imagecell.getStringCellValue(), custnoValue);
	}
	// (masterTAXAmount);

	fis_outputFile.close();

	// return masterTAXAmount;

    }

    public static void po_noMaster() throws Exception {
	/* IMAGE NAME AS THE KEY */
	/* TAX AMOUNT AS THE VALUE */
	File masterfile = fn_fetchfileMaster();
	FileInputStream fis_master = new FileInputStream(masterfile);

	XSSFWorkbook wb = new XSSFWorkbook(fis_master);
	XSSFSheet masterSheet = wb.getSheet("Non-Tabular");

	XSSFRow imageRow;
	XSSFCell imagecell;
	XSSFCell ponoCell;
	String ponoVAL;

	for (int i = 1; i <= masterSheet.getLastRowNum(); i++) {
	    imageRow = masterSheet.getRow(i);
	    imagecell = imageRow.getCell(0);
	    ponoCell = imageRow.getCell(returnCellCountFindInMaster("PO_No"));
	    if (ponoCell != null) {
		ponoVAL = df.formatCellValue(ponoCell).toString();
	    } else {
		ponoVAL = "";
	    }

	    masterpurchasenumber.put(imagecell.getStringCellValue(), ponoVAL);
	}

	fis_master.close();

    }

    public static void purchasenumber_Output() throws Exception {

	/* IMAGE NAME AS THE KEY */
	/* TAX AMOUNT AS THE VALUE */
	File outputfile = new File(".//API_MOD.xlsx");
	FileInputStream fis_outputFile = new FileInputStream(outputfile);

	XSSFWorkbook wb = new XSSFWorkbook(fis_outputFile);
	XSSFSheet outputSheet = wb.getSheet("EXTRAC-RES");

	XSSFRow imageRow;
	XSSFCell imagecell;

	XSSFCell ponocell;
	String ponoValue;

	for (int i = 1; i <= outputSheet.getLastRowNum(); i++) {
	    imageRow = outputSheet.getRow(i);
	    imagecell = imageRow.getCell(0);
	    ponocell = imageRow.getCell(returnCellCountFindInOutput("purchase_order_number"));
	    if (ponocell != null) {
		ponoValue = df.formatCellValue(ponocell).toString();
	    } else {
		ponoValue = "";
	    }

	    outputpurchasenumber.put(imagecell.getStringCellValue(), ponoValue);
	}
	// (masterTAXAmount);

	fis_outputFile.close();

	// return masterTAXAmount;

    }

    public static void vendorNameMaster() throws Exception {

	/* IMAGE NAME AS THE KEY */
	/* TAX AMOUNT AS THE VALUE */
	File masterfile = fn_fetchfileMaster();
	FileInputStream fis_master = new FileInputStream(masterfile);

	XSSFWorkbook wb = new XSSFWorkbook(fis_master);
	XSSFSheet masterSheet = wb.getSheet("Non-Tabular");

	XSSFRow imageRow;
	XSSFCell imagecell;
	XSSFCell vendorNameCell;
	String vendorNameVAL;

	for (int i = 1; i <= masterSheet.getLastRowNum(); i++) {
	    imageRow = masterSheet.getRow(i);
	    imagecell = imageRow.getCell(0);
	    vendorNameCell = imageRow.getCell(returnCellCountFindInMaster("Vendor Name"));
	    if (vendorNameCell != null) {
		vendorNameVAL = df.formatCellValue(vendorNameCell).toString();
	    } else {
		vendorNameVAL = "";
	    }

	    mastervendorname.put(imagecell.getStringCellValue(), vendorNameVAL);
	}

	fis_master.close();

    }

    public static void vendornameOUTPUT() throws Exception {
	/* IMAGE NAME AS THE KEY */
	/* TAX AMOUNT AS THE VALUE */
	File outputfile = new File(".//API_MOD.xlsx");
	FileInputStream fis_outputFile = new FileInputStream(outputfile);

	XSSFWorkbook wb = new XSSFWorkbook(fis_outputFile);
	XSSFSheet outputSheet = wb.getSheet("EXTRAC-RES");

	XSSFRow imageRow;
	XSSFCell imagecell;

	XSSFCell vendornamecell;
	XSSFCell lut_remit_to_organizationcell;
	String vendornameValue = "";
	String lut_remit_to_organizationvalue = "";

	for (int i = 1; i <= outputSheet.getLastRowNum(); i++) {
	    imageRow = outputSheet.getRow(i);
	    imagecell = imageRow.getCell(0);
	    vendornamecell = imageRow.getCell(returnCellCountFindInOutput("vendor_name"));
	    lut_remit_to_organizationcell = imageRow.getCell(returnCellCountFindInOutput("LUT_remit_to-organization"));
	    if (vendornamecell != null) {
		vendornameValue = df.formatCellValue(vendornamecell).toString();
		outputvendorname.put(imagecell.getStringCellValue(), vendornameValue);

	    } else {
		lut_remit_to_organizationvalue = df.formatCellValue(lut_remit_to_organizationcell).toString();
		outputvendorname.put(imagecell.getStringCellValue(), lut_remit_to_organizationvalue);
	    }

	}
	// (masterTAXAmount);

	fis_outputFile.close();

	// return masterTAXAmount;

    }

    public static void doccurrmaster() throws Exception {
	/* IMAGE NAME AS THE KEY */
	/* TAX AMOUNT AS THE VALUE */
	File masterfile = fn_fetchfileMaster();
	FileInputStream fis_master = new FileInputStream(masterfile);

	XSSFWorkbook wb = new XSSFWorkbook(fis_master);
	XSSFSheet masterSheet = wb.getSheet("Non-Tabular");

	XSSFRow imageRow;
	XSSFCell imagecell;
	XSSFCell doccurrCell;
	String doccurrVAL;

	for (int i = 1; i <= masterSheet.getLastRowNum(); i++) {
	    imageRow = masterSheet.getRow(i);
	    imagecell = imageRow.getCell(0);
	    doccurrCell = imageRow.getCell(returnCellCountFindInMaster("doc_curr"));
	    if (doccurrCell != null) {
		doccurrVAL = df.formatCellValue(doccurrCell).toString();
	    } else {
		doccurrVAL = "";
	    }

	    masterdoccurr.put(imagecell.getStringCellValue(), doccurrVAL);
	}

	fis_master.close();

    }

    public static void doccurrnameOUTPUT() throws Exception {
	/* IMAGE NAME AS THE KEY */
	/* TAX AMOUNT AS THE VALUE */
	File outputfile = new File(".//API_MOD.xlsx");
	FileInputStream fis_outputFile = new FileInputStream(outputfile);

	XSSFWorkbook wb = new XSSFWorkbook(fis_outputFile);
	XSSFSheet outputSheet = wb.getSheet("EXTRAC-RES");

	XSSFRow imageRow;
	XSSFCell imagecell;

	XSSFCell doccurrnamecell;
	String doccurrValue;

	for (int i = 1; i <= outputSheet.getLastRowNum(); i++) {
	    imageRow = outputSheet.getRow(i);
	    imagecell = imageRow.getCell(0);
	    doccurrnamecell = imageRow.getCell(returnCellCountFindInOutput("doc_curr"));
	    if (doccurrnamecell != null) {
		doccurrValue = df.formatCellValue(doccurrnamecell).toString();
	    } else {
		doccurrValue = "";
	    }

	    outputdoccurrname.put(imagecell.getStringCellValue(), doccurrValue);
	}
	// (masterTAXAmount);

	fis_outputFile.close();

	// return masterTAXAmount;

    }

    public static void invoicenumbermaster() throws Exception {
	/* IMAGE NAME AS THE KEY */
	/* TAX AMOUNT AS THE VALUE */
	File masterfile = fn_fetchfileMaster();
	FileInputStream fis_master = new FileInputStream(masterfile);

	XSSFWorkbook wb = new XSSFWorkbook(fis_master);
	XSSFSheet masterSheet = wb.getSheet("Non-Tabular");

	XSSFRow imageRow;
	XSSFCell imagecell;
	XSSFCell invoicenumberCell;
	String invoicenumberVAL;

	for (int i = 1; i <= masterSheet.getLastRowNum(); i++) {
	    imageRow = masterSheet.getRow(i);
	    imagecell = imageRow.getCell(0);
	    invoicenumberCell = imageRow.getCell(returnCellCountFindInMaster("ven_doc_num"));
	    if (invoicenumberCell != null) {
		invoicenumberVAL = df.formatCellValue(invoicenumberCell).toString();
	    } else {
		invoicenumberVAL = "";
	    }

	    masterinvoicenumebr.put(imagecell.getStringCellValue(), invoicenumberVAL);
	}

	fis_master.close();

    }

    public static void invoicenumberOUTPUT() throws Exception {
	/* IMAGE NAME AS THE KEY */
	/* TAX AMOUNT AS THE VALUE */
	File outputfile = new File(".//API_MOD.xlsx");
	FileInputStream fis_outputFile = new FileInputStream(outputfile);

	XSSFWorkbook wb = new XSSFWorkbook(fis_outputFile);
	XSSFSheet outputSheet = wb.getSheet("EXTRAC-RES");

	XSSFRow imageRow;
	XSSFCell imagecell;

	XSSFCell invoicenumbercell;
	String invoicenumberValue;

	for (int i = 1; i <= outputSheet.getLastRowNum(); i++) {
	    imageRow = outputSheet.getRow(i);
	    imagecell = imageRow.getCell(0);
	    invoicenumbercell = imageRow.getCell(returnCellCountFindInOutput("invoice_number"));
	    if (invoicenumbercell != null) {
		invoicenumberValue = df.formatCellValue(invoicenumbercell).toString();
	    } else {
		invoicenumberValue = "";
	    }

	    outputinvoicenumber.put(imagecell.getStringCellValue(), invoicenumberValue);
	}
	// (masterTAXAmount);

	fis_outputFile.close();

	// return masterTAXAmount;

    }

    public static void shiptomaster() throws Exception {
	/* IMAGE NAME AS THE KEY */
	/* TAX AMOUNT AS THE VALUE */
	File masterfile = fn_fetchfileMaster();
	FileInputStream fis_master = new FileInputStream(masterfile);

	XSSFWorkbook wb = new XSSFWorkbook(fis_master);
	XSSFSheet masterSheet = wb.getSheet("Non-Tabular");

	XSSFRow imageRow;
	XSSFCell imagecell;
	XSSFCell shiptomastercell;
	String shiptomasterval;

	for (int i = 1; i <= masterSheet.getLastRowNum(); i++) {
	    imageRow = masterSheet.getRow(i);
	    imagecell = imageRow.getCell(0);
	    shiptomastercell = imageRow.getCell(returnCellCountFindInMaster("Ship_To"));
	    if (shiptomastercell != null) {
		shiptomasterval = df.formatCellValue(shiptomastercell).toString();
	    } else {
		shiptomasterval = "";
	    }

	    mastershipto.put(imagecell.getStringCellValue(), shiptomasterval);
	}

	fis_master.close();

    }

    public static void shiptoOUTPUT() throws Exception {
	/* IMAGE NAME AS THE KEY */
	/* TAX AMOUNT AS THE VALUE */
	File outputfile = new File(".//API_MOD.xlsx");
	FileInputStream fis_outputFile = new FileInputStream(outputfile);

	XSSFWorkbook wb = new XSSFWorkbook(fis_outputFile);
	XSSFSheet outputSheet = wb.getSheet("EXTRAC-RES");

	XSSFRow imageRow;
	XSSFCell imagecell;

	XSSFCell shiptocell;
	String shiptovalue;

	for (int i = 1; i <= outputSheet.getLastRowNum(); i++) {
	    imageRow = outputSheet.getRow(i);
	    imagecell = imageRow.getCell(0);
	    shiptocell = imageRow.getCell(returnCellCountFindInOutput("ship_to"));
	    if (shiptocell != null) {
		shiptovalue = df.formatCellValue(shiptocell).toString();
	    } else {
		shiptovalue = "";
	    }

	    outputshipto.put(imagecell.getStringCellValue(), shiptovalue);
	}
	// (masterTAXAmount);

	fis_outputFile.close();

	// return masterTAXAmount;

    }

    public static void invoicedatemaster() throws Exception {
	/* IMAGE NAME AS THE KEY */
	/* TAX AMOUNT AS THE VALUE */
	File masterfile = fn_fetchfileMaster();
	FileInputStream fis_master = new FileInputStream(masterfile);

	XSSFWorkbook wb = new XSSFWorkbook(fis_master);
	XSSFSheet masterSheet = wb.getSheet("Non-Tabular");

	XSSFRow imageRow;
	XSSFCell imagecell;
	XSSFCell invoicedateCell;
	String invoicedateVAL;

	for (int i = 1; i <= masterSheet.getLastRowNum(); i++) {
	    imageRow = masterSheet.getRow(i);
	    imagecell = imageRow.getCell(0);
	    invoicedateCell = imageRow.getCell(returnCellCountFindInMaster("doc_date"));
	    if (invoicedateCell != null) {
		invoicedateVAL = df.formatCellValue(invoicedateCell).toString();
	    } else {
		invoicedateVAL = "";
	    }

	    masterinvoicedate.put(imagecell.getStringCellValue(), invoicedateVAL);
	}

	fis_master.close();

    }

    public static void invoicedateOUTPUT() throws Exception {
	/* IMAGE NAME AS THE KEY */
	/* TAX AMOUNT AS THE VALUE */
	File outputfile = new File(".//API_MOD.xlsx");
	FileInputStream fis_outputFile = new FileInputStream(outputfile);

	XSSFWorkbook wb = new XSSFWorkbook(fis_outputFile);
	XSSFSheet outputSheet = wb.getSheet("EXTRAC-RES");

	XSSFRow imageRow;
	XSSFCell imagecell;

	XSSFCell invoicedatecell;
	String invoicedateValue;

	for (int i = 1; i <= outputSheet.getLastRowNum(); i++) {
	    imageRow = outputSheet.getRow(i);
	    imagecell = imageRow.getCell(0);
	    invoicedatecell = imageRow.getCell(returnCellCountFindInOutput("invoice_date"));
	    if (invoicedatecell != null) {
		invoicedateValue = df.formatCellValue(invoicedatecell).toString();
	    } else {
		invoicedateValue = "";
	    }

	    outputinvoicedate.put(imagecell.getStringCellValue(), invoicedateValue);
	}
	// (masterTAXAmount);

	fis_outputFile.close();

	// return masterTAXAmount;

    }

    /*
     * ENDS
     * =============================================================================
     */

    public static void createHeader() throws Exception {

	op_03____WB1____VER_IMAGE();
	tax_AmountMaster();

	Map<String, String> headers = op_02____MAP____HEADER();

	File file_acc = new File(".//API_ACC.xlsx");
	FileInputStream faccinStream = new FileInputStream(file_acc);

	XSSFWorkbook wbGET = new XSSFWorkbook(faccinStream);
	XSSFSheet wbSheet = wbGET.getSheet("GEN_ACC");

	XSSFRow rowHeader = wbSheet.getRow(0);
	int i = 2;
	for (Map.Entry<String, String> mapSet : headers.entrySet()) {
	    XSSFCell cellHeader_accuracy = rowHeader.createCell(i);
	    cellHeader_accuracy.setCellValue("MASTER-" + mapSet.getValue());
	    i = i + 1;
	    XSSFCell cellHeader_transformer = rowHeader.createCell(i);
	    cellHeader_transformer.setCellValue("OUTPUT-" + mapSet.getKey());
	    i = i + 1;
	    XSSFCell cellHeader_result = rowHeader.createCell(i);
	    cellHeader_result.setCellValue("RESULT-" + mapSet.getKey());
	    i = i + 1;
	}

	faccinStream.close();

	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wbGET.write(outputstreamAccuracy);
	wbGET.close();
	outputstreamAccuracy.close();

    }

    public static void taxAmountMasterPUT() throws Exception {
	createHeader();
	File file_acc = new File(".//API_ACC.xlsx");
	FileInputStream faccinStream = new FileInputStream(file_acc);

	XSSFWorkbook wbGET = new XSSFWorkbook(faccinStream);
	XSSFSheet wbSheet = wbGET.getSheet("GEN_ACC");

	XSSFRow taxmASTERrow;

	XSSFCell imagenameCell;

	XSSFCell taxtamtcellMASTER;

	for (int i = 1; i <= wbSheet.getLastRowNum(); i++) {
	    taxmASTERrow = wbSheet.getRow(i);

	    imagenameCell = taxmASTERrow.getCell(0);

	    taxtamtcellMASTER = taxmASTERrow.createCell(2);

	    String check_imageNAME = imagenameCell.getStringCellValue();

	    for (Map.Entry<String, String> mappedEntries : masterTAXAmount.entrySet()) {

		if (mappedEntries.getKey().contains(check_imageNAME)) {
		    taxtamtcellMASTER.setCellValue(mappedEntries.getValue());
		}
	    }

	}

	faccinStream.close();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wbGET.write(outputstreamAccuracy);
	wbGET.close();
	outputstreamAccuracy.close();
    }

    public static void taxAmountOutput() throws Exception {
	tax_Amount_Output();
	taxAmountMasterPUT();
	File file_acc = new File(".//API_ACC.xlsx");
	FileInputStream faccinStream = new FileInputStream(file_acc);

	XSSFWorkbook wbGET = new XSSFWorkbook(faccinStream);
	XSSFSheet wbSheet = wbGET.getSheet("GEN_ACC");

	XSSFRow taxmASTERrow;

	XSSFCell imagenameCell;

	XSSFCell taxtamtcellMASTER;

	for (int i = 1; i <= wbSheet.getLastRowNum(); i++) {
	    taxmASTERrow = wbSheet.getRow(i);

	    imagenameCell = taxmASTERrow.getCell(0);

	    taxtamtcellMASTER = taxmASTERrow.createCell(3);

	    String check_imageNAME = imagenameCell.getStringCellValue();

	    for (Map.Entry<String, String> mappedEntries : outputTAXAmount.entrySet()) {

		if (mappedEntries.getKey().contains(check_imageNAME)) {
		    taxtamtcellMASTER.setCellValue(mappedEntries.getValue());
		}
	    }

	}

	faccinStream.close();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wbGET.write(outputstreamAccuracy);
	wbGET.close();
	outputstreamAccuracy.close();
    }

    public static void generateResultTAXAMOUNT() throws Exception {
	taxAmountOutput();
	File file_acc = new File(".//API_ACC.xlsx");
	FileInputStream faccinStream = new FileInputStream(file_acc);

	XSSFWorkbook wbGET = new XSSFWorkbook(faccinStream);
	XSSFSheet wbSheet = wbGET.getSheet("GEN_ACC");

	XSSFRow resultRow;
	XSSFCell masterCell;
	XSSFCell outputCell;

	String masterCellVAL;
	String outputCellVAL;

	XSSFCell outputImageFile;

	for (int i = 1; i <= wbSheet.getLastRowNum(); i++) {
	    resultRow = wbSheet.getRow(i);
	    outputImageFile = resultRow.getCell(1);
	    masterCell = resultRow.getCell(2);
	    outputCell = resultRow.getCell(3);

	    masterCellVAL = masterCell.getStringCellValue();
	    outputCellVAL = outputCell.getStringCellValue();

	    XSSFCell cellResult = resultRow.createCell(4);

	    if (outputImageFile.getStringCellValue() != "") {
		if (masterCellVAL.compareTo(outputCellVAL) == 0 || masterCellVAL == "") {

		    cellResult.setCellValue("positive");

		} else {
		    cellResult.setCellValue("negative");

		}
	    }

	}

	faccinStream.close();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wbGET.write(outputstreamAccuracy);
	wbGET.close();
	outputstreamAccuracy.close();

    }

    public static void grossAmountMasterPUT() throws Exception {
	generateResultTAXAMOUNT();
	grossAmountMaster();
	File file_acc = new File(".//API_ACC.xlsx");
	FileInputStream faccinStream = new FileInputStream(file_acc);

	XSSFWorkbook wbGET = new XSSFWorkbook(faccinStream);
	XSSFSheet wbSheet = wbGET.getSheet("GEN_ACC");

	XSSFRow grossamtmASTERrow;

	XSSFCell imagenameCell;

	XSSFCell grossamtcellMASTER;

	for (int i = 1; i <= wbSheet.getLastRowNum(); i++) {
	    grossamtmASTERrow = wbSheet.getRow(i);

	    imagenameCell = grossamtmASTERrow.getCell(0);

	    grossamtcellMASTER = grossamtmASTERrow.createCell(5);

	    String check_imageNAME = imagenameCell.getStringCellValue();

	    for (Map.Entry<String, String> mappedEntries : mastergrossAmount.entrySet()) {

		if (mappedEntries.getKey().contains(check_imageNAME)) {
		    grossamtcellMASTER.setCellValue(mappedEntries.getValue());
		}
	    }

	}

	faccinStream.close();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wbGET.write(outputstreamAccuracy);
	wbGET.close();
	outputstreamAccuracy.close();
    }

    public static void grossAmountOutput() throws Exception {
	grossAmountMasterPUT();
	invoiceTotalOutput();
	File file_acc = new File(".//API_ACC.xlsx");
	FileInputStream faccinStream = new FileInputStream(file_acc);

	XSSFWorkbook wbGET = new XSSFWorkbook(faccinStream);
	XSSFSheet wbSheet = wbGET.getSheet("GEN_ACC");

	XSSFRow grossamtOUTPUTrow;

	XSSFCell imagenameCell;

	XSSFCell grossamtcellOUTPUT;

	for (int i = 1; i <= wbSheet.getLastRowNum(); i++) {
	    grossamtOUTPUTrow = wbSheet.getRow(i);

	    imagenameCell = grossamtOUTPUTrow.getCell(0);

	    grossamtcellOUTPUT = grossamtOUTPUTrow.createCell(6);

	    String check_imageNAME = imagenameCell.getStringCellValue();

	    for (Map.Entry<String, String> mappedEntries : outputgrossAmount.entrySet()) {

		if (mappedEntries.getKey().contains(check_imageNAME)) {
		    grossamtcellOUTPUT.setCellValue(mappedEntries.getValue());
		}
	    }

	}

	faccinStream.close();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wbGET.write(outputstreamAccuracy);
	wbGET.close();
	outputstreamAccuracy.close();
    }

    public static void generateResultGROSSAMOUNT() throws Exception {
	grossAmountOutput();
	File file_acc = new File(".//API_ACC.xlsx");
	FileInputStream faccinStream = new FileInputStream(file_acc);

	XSSFWorkbook wbGET = new XSSFWorkbook(faccinStream);
	XSSFSheet wbSheet = wbGET.getSheet("GEN_ACC");

	XSSFRow resultRow;
	XSSFCell masterCell;
	XSSFCell outputCell;

	String masterCellVAL;
	String outputCellVAL;

	XSSFCell outputImageFile;

	for (int i = 1; i <= wbSheet.getLastRowNum(); i++) {
	    resultRow = wbSheet.getRow(i);
	    outputImageFile = resultRow.getCell(1);
	    masterCell = resultRow.getCell(5);
	    outputCell = resultRow.getCell(6);

	    masterCellVAL = masterCell.getStringCellValue();
	    outputCellVAL = outputCell.getStringCellValue();

	    XSSFCell cellResult = resultRow.createCell(7);

	    if (outputImageFile.getStringCellValue() != "") {
		masterCellVAL = masterCellVAL.replaceAll("\\s+", "");
		outputCellVAL = outputCellVAL.replaceAll("\\s+", "");
		if (masterCellVAL.compareTo(outputCellVAL) == 0 || masterCellVAL == "") {

		    cellResult.setCellValue("positive");

		} else {
		    cellResult.setCellValue("negative");

		}
	    }

	}

	faccinStream.close();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wbGET.write(outputstreamAccuracy);
	wbGET.close();
	outputstreamAccuracy.close();

    }

    /* CUSTOMER NUMBER */

    public static void customerNumberMasterPUT() throws Exception {
	generateResultGROSSAMOUNT();
	cust_noMaster();
	File file_acc = new File(".//API_ACC.xlsx");
	FileInputStream faccinStream = new FileInputStream(file_acc);

	XSSFWorkbook wbGET = new XSSFWorkbook(faccinStream);
	XSSFSheet wbSheet = wbGET.getSheet("GEN_ACC");

	XSSFRow customernumbermASTERrow;

	XSSFCell imagenameCell;

	XSSFCell customernumbercellMASTER;

	for (int i = 1; i <= wbSheet.getLastRowNum(); i++) {
	    customernumbermASTERrow = wbSheet.getRow(i);

	    imagenameCell = customernumbermASTERrow.getCell(0);

	    customernumbercellMASTER = customernumbermASTERrow.createCell(8);

	    String check_imageNAME = imagenameCell.getStringCellValue();

	    for (Map.Entry<String, String> mappedEntries : mastercustnumber.entrySet()) {

		if (mappedEntries.getKey().contains(check_imageNAME)) {
		    customernumbercellMASTER.setCellValue(mappedEntries.getValue());
		}
	    }

	}

	faccinStream.close();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wbGET.write(outputstreamAccuracy);
	wbGET.close();
	outputstreamAccuracy.close();
    }

    public static void customerNumberOutput() throws Exception {
	customerNumberMasterPUT();
	cust_noOutput();
	File file_acc = new File(".//API_ACC.xlsx");
	FileInputStream faccinStream = new FileInputStream(file_acc);

	XSSFWorkbook wbGET = new XSSFWorkbook(faccinStream);
	XSSFSheet wbSheet = wbGET.getSheet("GEN_ACC");

	XSSFRow customernumberOUTPUTrow;

	XSSFCell imagenameCell;

	XSSFCell customernumbercellOUTPUT;

	for (int i = 1; i <= wbSheet.getLastRowNum(); i++) {
	    customernumberOUTPUTrow = wbSheet.getRow(i);

	    imagenameCell = customernumberOUTPUTrow.getCell(0);

	    customernumbercellOUTPUT = customernumberOUTPUTrow.createCell(9);

	    String check_imageNAME = imagenameCell.getStringCellValue();

	    for (Map.Entry<String, String> mappedEntries : outputcustomernumber.entrySet()) {

		if (mappedEntries.getKey().contains(check_imageNAME)) {
		    customernumbercellOUTPUT.setCellValue(mappedEntries.getValue());
		}
	    }

	}

	faccinStream.close();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wbGET.write(outputstreamAccuracy);
	wbGET.close();
	outputstreamAccuracy.close();
    }

    @Test
    public static void generateResultCUSTOMERNUMBER() throws Exception {
	customerNumberOutput();
	File file_acc = new File(".//API_ACC.xlsx");
	FileInputStream faccinStream = new FileInputStream(file_acc);

	XSSFWorkbook wbGET = new XSSFWorkbook(faccinStream);
	XSSFSheet wbSheet = wbGET.getSheet("GEN_ACC");

	XSSFRow resultRow;
	XSSFCell masterCell;
	XSSFCell outputCell;

	String masterCellVAL;
	String outputCellVAL;

	XSSFCell outputImageFile;

	for (int i = 1; i <= wbSheet.getLastRowNum(); i++) {
	    resultRow = wbSheet.getRow(i);
	    outputImageFile = resultRow.getCell(1);
	    masterCell = resultRow.getCell(8);
	    outputCell = resultRow.getCell(9);

	    masterCellVAL = masterCell.getStringCellValue();
	    outputCellVAL = outputCell.getStringCellValue();

	    XSSFCell cellResult = resultRow.createCell(10);

	    if (outputImageFile.getStringCellValue() != "") {
		if (masterCellVAL.compareTo(outputCellVAL) == 0 || masterCellVAL == "") {

		    cellResult.setCellValue("positive");

		} else {
		    cellResult.setCellValue("negative");

		}
	    }

	}

	faccinStream.close();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wbGET.write(outputstreamAccuracy);
	wbGET.close();
	outputstreamAccuracy.close();

    }

    /* PURCHASE ORDER NUMBER */

    public static void PONumberMasterPUT() throws Exception {
	generateResultCUSTOMERNUMBER();
	po_noMaster();
	File file_acc = new File(".//API_ACC.xlsx");
	FileInputStream faccinStream = new FileInputStream(file_acc);

	XSSFWorkbook wbGET = new XSSFWorkbook(faccinStream);
	XSSFSheet wbSheet = wbGET.getSheet("GEN_ACC");

	XSSFRow customernumbermASTERrow;

	XSSFCell imagenameCell;

	XSSFCell customernumbercellMASTER;

	for (int i = 1; i <= wbSheet.getLastRowNum(); i++) {
	    customernumbermASTERrow = wbSheet.getRow(i);

	    imagenameCell = customernumbermASTERrow.getCell(0);

	    customernumbercellMASTER = customernumbermASTERrow.createCell(11);

	    String check_imageNAME = imagenameCell.getStringCellValue();

	    for (Map.Entry<String, String> mappedEntries : masterpurchasenumber.entrySet()) {

		if (mappedEntries.getKey().contains(check_imageNAME)) {
		    customernumbercellMASTER.setCellValue(mappedEntries.getValue());
		}
	    }

	}

	faccinStream.close();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wbGET.write(outputstreamAccuracy);
	wbGET.close();
	outputstreamAccuracy.close();
    }

    public static void PONumberOUTPUT() throws Exception {
	PONumberMasterPUT();
	purchasenumber_Output();
	File file_acc = new File(".//API_ACC.xlsx");
	FileInputStream faccinStream = new FileInputStream(file_acc);

	XSSFWorkbook wbGET = new XSSFWorkbook(faccinStream);
	XSSFSheet wbSheet = wbGET.getSheet("GEN_ACC");

	XSSFRow customernumbermASTERrow;

	XSSFCell imagenameCell;

	XSSFCell customernumbercellMASTER;

	for (int i = 1; i <= wbSheet.getLastRowNum(); i++) {
	    customernumbermASTERrow = wbSheet.getRow(i);

	    imagenameCell = customernumbermASTERrow.getCell(0);

	    customernumbercellMASTER = customernumbermASTERrow.createCell(12);

	    String check_imageNAME = imagenameCell.getStringCellValue();

	    for (Map.Entry<String, String> mappedEntries : outputpurchasenumber.entrySet()) {

		if (mappedEntries.getKey().contains(check_imageNAME)) {
		    customernumbercellMASTER.setCellValue(mappedEntries.getValue());
		}
	    }

	}

	faccinStream.close();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wbGET.write(outputstreamAccuracy);
	wbGET.close();
	outputstreamAccuracy.close();
    }

    public static void generateResultPURCHASEORDERNUMBER() throws Exception {
	PONumberOUTPUT();
	File file_acc = new File(".//API_ACC.xlsx");
	FileInputStream faccinStream = new FileInputStream(file_acc);

	XSSFWorkbook wbGET = new XSSFWorkbook(faccinStream);
	XSSFSheet wbSheet = wbGET.getSheet("GEN_ACC");

	XSSFRow resultRow;
	XSSFCell masterCell;
	XSSFCell outputCell;

	String masterCellVAL;
	String outputCellVAL;

	XSSFCell outputImageFile;

	for (int i = 1; i <= wbSheet.getLastRowNum(); i++) {
	    resultRow = wbSheet.getRow(i);
	    outputImageFile = resultRow.getCell(1);
	    masterCell = resultRow.getCell(11);
	    outputCell = resultRow.getCell(12);

	    masterCellVAL = masterCell.getStringCellValue();
	    outputCellVAL = outputCell.getStringCellValue();

	    XSSFCell cellResult = resultRow.createCell(13);

	    if (outputImageFile.getStringCellValue() != "") {
		masterCellVAL = masterCellVAL.replaceAll("\\s+", "");
		outputCellVAL = outputCellVAL.replaceAll("\\s+", "");
		if (masterCellVAL.compareTo(outputCellVAL) == 0 || masterCellVAL == "") {

		    cellResult.setCellValue("positive");

		} else {
		    cellResult.setCellValue("negative");

		}
	    }

	}

	faccinStream.close();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wbGET.write(outputstreamAccuracy);
	wbGET.close();
	outputstreamAccuracy.close();

    }

    /* MASTER-Vendor Name */

    public static void vendorNameMasterPUT() throws Exception {
	generateResultPURCHASEORDERNUMBER();
	vendorNameMaster();
	File file_acc = new File(".//API_ACC.xlsx");
	FileInputStream faccinStream = new FileInputStream(file_acc);

	XSSFWorkbook wbGET = new XSSFWorkbook(faccinStream);
	XSSFSheet wbSheet = wbGET.getSheet("GEN_ACC");

	XSSFRow customernumbermASTERrow;

	XSSFCell imagenameCell;

	XSSFCell customernumbercellMASTER;

	for (int i = 1; i <= wbSheet.getLastRowNum(); i++) {
	    customernumbermASTERrow = wbSheet.getRow(i);

	    imagenameCell = customernumbermASTERrow.getCell(0);

	    customernumbercellMASTER = customernumbermASTERrow.createCell(14);

	    String check_imageNAME = imagenameCell.getStringCellValue();

	    for (Map.Entry<String, String> mappedEntries : mastervendorname.entrySet()) {

		if (mappedEntries.getKey().contains(check_imageNAME)) {
		    customernumbercellMASTER.setCellValue(mappedEntries.getValue());
		}
	    }

	}

	faccinStream.close();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wbGET.write(outputstreamAccuracy);
	wbGET.close();
	outputstreamAccuracy.close();
    }

    public static void vendor_nameOUTPUT() throws Exception {
	vendornameOUTPUT();
	vendorNameMasterPUT();
	File file_acc = new File(".//API_ACC.xlsx");
	FileInputStream faccinStream = new FileInputStream(file_acc);

	XSSFWorkbook wbGET = new XSSFWorkbook(faccinStream);
	XSSFSheet wbSheet = wbGET.getSheet("GEN_ACC");

	XSSFRow vendor_namerow;

	XSSFCell imagenameCell;

	XSSFCell vendornamecellMASTER;

	for (int i = 1; i <= wbSheet.getLastRowNum(); i++) {
	    vendor_namerow = wbSheet.getRow(i);

	    imagenameCell = vendor_namerow.getCell(0);

	    vendornamecellMASTER = vendor_namerow.createCell(15);

	    String check_imageNAME = imagenameCell.getStringCellValue();

	    for (Map.Entry<String, String> mappedEntries : outputvendorname.entrySet()) {

		if (mappedEntries.getKey().contains(check_imageNAME)) {
		    vendornamecellMASTER.setCellValue(mappedEntries.getValue());
		}
	    }

	}

	faccinStream.close();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wbGET.write(outputstreamAccuracy);
	wbGET.close();
	outputstreamAccuracy.close();
    }

    public static void generateResultVENDORNAME() throws Exception {
	vendor_nameOUTPUT();
	File file_acc = new File(".//API_ACC.xlsx");
	FileInputStream faccinStream = new FileInputStream(file_acc);

	XSSFWorkbook wbGET = new XSSFWorkbook(faccinStream);
	XSSFSheet wbSheet = wbGET.getSheet("GEN_ACC");

	XSSFRow resultRow;
	XSSFCell masterCell;
	XSSFCell outputCell;

	String masterCellVAL;
	String outputCellVAL;

	XSSFCell outputImageFile;

	for (int i = 1; i <= wbSheet.getLastRowNum(); i++) {
	    resultRow = wbSheet.getRow(i);
	    outputImageFile = resultRow.getCell(1);
	    masterCell = resultRow.getCell(14);
	    outputCell = resultRow.getCell(15);

	    masterCellVAL = masterCell.getStringCellValue();
	    outputCellVAL = outputCell.getStringCellValue();

	    XSSFCell cellResult = resultRow.createCell(16);

	    if (outputImageFile.getStringCellValue() != "") {
		masterCellVAL = masterCellVAL.replaceAll("\\s+", "");
		outputCellVAL = outputCellVAL.replaceAll("\\s+", "");
		if (masterCellVAL.compareTo(outputCellVAL) == 0 || masterCellVAL == "") {

		    cellResult.setCellValue("positive");

		} else {

		    cellResult.setCellValue("negative");

		}
	    }

	}

	faccinStream.close();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wbGET.write(outputstreamAccuracy);
	wbGET.close();
	outputstreamAccuracy.close();

    }

    /* MASTER-doc_curr */

    public static void doc_currMasterPUT() throws Exception {
	doccurrmaster();
	generateResultVENDORNAME();
	File file_acc = new File(".//API_ACC.xlsx");
	FileInputStream faccinStream = new FileInputStream(file_acc);

	XSSFWorkbook wbGET = new XSSFWorkbook(faccinStream);
	XSSFSheet wbSheet = wbGET.getSheet("GEN_ACC");

	XSSFRow doccurrmASTERrow;

	XSSFCell imagenameCell;

	XSSFCell doccurrcellMASTER;

	for (int i = 1; i <= wbSheet.getLastRowNum(); i++) {
	    doccurrmASTERrow = wbSheet.getRow(i);

	    imagenameCell = doccurrmASTERrow.getCell(0);

	    doccurrcellMASTER = doccurrmASTERrow.createCell(17);

	    String check_imageNAME = imagenameCell.getStringCellValue();

	    for (Map.Entry<String, String> mappedEntries : masterdoccurr.entrySet()) {

		if (mappedEntries.getKey().contains(check_imageNAME)) {
		    doccurrcellMASTER.setCellValue(mappedEntries.getValue());
		}
	    }

	}

	faccinStream.close();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wbGET.write(outputstreamAccuracy);
	wbGET.close();
	outputstreamAccuracy.close();
    }

    public static void doc_currOUTPUT() throws Exception {
	doccurrnameOUTPUT();

	doc_currMasterPUT();
	File file_acc = new File(".//API_ACC.xlsx");
	FileInputStream faccinStream = new FileInputStream(file_acc);

	XSSFWorkbook wbGET = new XSSFWorkbook(faccinStream);
	XSSFSheet wbSheet = wbGET.getSheet("GEN_ACC");

	XSSFRow vendor_namerow;

	XSSFCell imagenameCell;

	XSSFCell vendornamecellMASTER;

	for (int i = 1; i <= wbSheet.getLastRowNum(); i++) {
	    vendor_namerow = wbSheet.getRow(i);

	    imagenameCell = vendor_namerow.getCell(0);

	    vendornamecellMASTER = vendor_namerow.createCell(18);

	    String check_imageNAME = imagenameCell.getStringCellValue();

	    for (Map.Entry<String, String> mappedEntries : outputdoccurrname.entrySet()) {

		if (mappedEntries.getKey().contains(check_imageNAME)) {
		    vendornamecellMASTER.setCellValue(mappedEntries.getValue());
		}
	    }

	}

	faccinStream.close();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wbGET.write(outputstreamAccuracy);
	wbGET.close();
	outputstreamAccuracy.close();
    }

    public static void generateResult_doc_curr() throws Exception {
	doc_currOUTPUT();
	File file_acc = new File(".//API_ACC.xlsx");
	FileInputStream faccinStream = new FileInputStream(file_acc);

	XSSFWorkbook wbGET = new XSSFWorkbook(faccinStream);
	XSSFSheet wbSheet = wbGET.getSheet("GEN_ACC");

	XSSFRow resultRow;
	XSSFCell masterCell;
	XSSFCell outputCell;

	String masterCellVAL;
	String outputCellVAL;

	XSSFCell outputImageFile;

	for (int i = 1; i <= wbSheet.getLastRowNum(); i++) {
	    resultRow = wbSheet.getRow(i);
	    outputImageFile = resultRow.getCell(1);
	    masterCell = resultRow.getCell(17);
	    outputCell = resultRow.getCell(18);

	    masterCellVAL = masterCell.getStringCellValue();
	    outputCellVAL = outputCell.getStringCellValue();

	    XSSFCell cellResult = resultRow.createCell(19);

	    if (outputImageFile.getStringCellValue() != "") {
		if (masterCellVAL.compareTo(outputCellVAL) == 0 || masterCellVAL == "") {

		    cellResult.setCellValue("positive");

		} else {
		    cellResult.setCellValue("negative");

		}
	    }

	}

	faccinStream.close();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wbGET.write(outputstreamAccuracy);
	wbGET.close();
	outputstreamAccuracy.close();

    }

    public static void master_ven_doc_num() throws Exception {
	invoicenumbermaster();
	generateResult_doc_curr();
	File file_acc = new File(".//API_ACC.xlsx");
	FileInputStream faccinStream = new FileInputStream(file_acc);

	XSSFWorkbook wbGET = new XSSFWorkbook(faccinStream);
	XSSFSheet wbSheet = wbGET.getSheet("GEN_ACC");

	XSSFRow invoicenumbermASTERrow;

	XSSFCell imagenameCell;

	XSSFCell invoicenumberMASTER;

	for (int i = 1; i <= wbSheet.getLastRowNum(); i++) {
	    invoicenumbermASTERrow = wbSheet.getRow(i);

	    imagenameCell = invoicenumbermASTERrow.getCell(0);

	    invoicenumberMASTER = invoicenumbermASTERrow.createCell(20);

	    String check_imageNAME = imagenameCell.getStringCellValue();

	    for (Map.Entry<String, String> mappedEntries : masterinvoicenumebr.entrySet()) {

		if (mappedEntries.getKey().contains(check_imageNAME)) {
		    invoicenumberMASTER.setCellValue(mappedEntries.getValue());
		}
	    }

	}

	faccinStream.close();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wbGET.write(outputstreamAccuracy);
	wbGET.close();
	outputstreamAccuracy.close();
    }

    public static void output_ven_doc_num() throws Exception {
	master_ven_doc_num();
	invoicenumberOUTPUT();

	File file_acc = new File(".//API_ACC.xlsx");
	FileInputStream faccinStream = new FileInputStream(file_acc);

	XSSFWorkbook wbGET = new XSSFWorkbook(faccinStream);
	XSSFSheet wbSheet = wbGET.getSheet("GEN_ACC");

	XSSFRow invoicenumberrow;

	XSSFCell imagenameCell;

	XSSFCell invoicenumberoutputcell;

	for (int i = 1; i <= wbSheet.getLastRowNum(); i++) {
	    invoicenumberrow = wbSheet.getRow(i);

	    imagenameCell = invoicenumberrow.getCell(0);

	    invoicenumberoutputcell = invoicenumberrow.createCell(21);

	    String check_imageNAME = imagenameCell.getStringCellValue();

	    for (Map.Entry<String, String> mappedEntries : outputinvoicenumber.entrySet()) {

		if (mappedEntries.getKey().contains(check_imageNAME)) {
		    invoicenumberoutputcell.setCellValue(mappedEntries.getValue());
		}
	    }

	}

	faccinStream.close();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wbGET.write(outputstreamAccuracy);
	wbGET.close();
	outputstreamAccuracy.close();
    }

    public static void generateResult_invoicenumber() throws Exception {
	output_ven_doc_num();
	File file_acc = new File(".//API_ACC.xlsx");
	FileInputStream faccinStream = new FileInputStream(file_acc);

	XSSFWorkbook wbGET = new XSSFWorkbook(faccinStream);
	XSSFSheet wbSheet = wbGET.getSheet("GEN_ACC");

	XSSFRow resultRow;
	XSSFCell masterCell;
	XSSFCell outputCell;

	String masterCellVAL;
	String outputCellVAL;

	XSSFCell outputImageFile;

	for (int i = 1; i <= wbSheet.getLastRowNum(); i++) {
	    resultRow = wbSheet.getRow(i);
	    outputImageFile = resultRow.getCell(1);
	    masterCell = resultRow.getCell(20);
	    outputCell = resultRow.getCell(21);

	    masterCellVAL = masterCell.getStringCellValue();
	    outputCellVAL = outputCell.getStringCellValue();

	    XSSFCell cellResult = resultRow.createCell(22);

	    if (outputImageFile.getStringCellValue() != "") {
		masterCellVAL = masterCellVAL.replaceAll("\\s+", "");
		outputCellVAL = outputCellVAL.replaceAll("\\s+", "");
		if (masterCellVAL.compareTo(outputCellVAL) == 0 || masterCellVAL == "" || masterCellVAL == "NULL") {

		    cellResult.setCellValue("positive");

		} else {
		    cellResult.setCellValue("negative");

		}
	    }

	}

	faccinStream.close();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wbGET.write(outputstreamAccuracy);
	wbGET.close();
	outputstreamAccuracy.close();

    }

    public static void master_shipto() throws Exception {
	shiptomaster();
	generateResult_invoicenumber();
	File file_acc = new File(".//API_ACC.xlsx");
	FileInputStream faccinStream = new FileInputStream(file_acc);

	XSSFWorkbook wbGET = new XSSFWorkbook(faccinStream);
	XSSFSheet wbSheet = wbGET.getSheet("GEN_ACC");

	XSSFRow shiptomASTERrow;

	XSSFCell imagenameCell;

	XSSFCell shiptoMASTER;

	for (int i = 1; i <= wbSheet.getLastRowNum(); i++) {
	    shiptomASTERrow = wbSheet.getRow(i);

	    imagenameCell = shiptomASTERrow.getCell(0);

	    shiptoMASTER = shiptomASTERrow.createCell(23);

	    String check_imageNAME = imagenameCell.getStringCellValue();

	    for (Map.Entry<String, String> mappedEntries : mastershipto.entrySet()) {

		if (mappedEntries.getKey().contains(check_imageNAME)) {
		    shiptoMASTER.setCellValue(mappedEntries.getValue());
		}
	    }

	}

	faccinStream.close();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wbGET.write(outputstreamAccuracy);
	wbGET.close();
	outputstreamAccuracy.close();
    }

    public static void output_shipto() throws Exception {

	master_shipto();

	shiptoOUTPUT();

	File file_acc = new File(".//API_ACC.xlsx");
	FileInputStream faccinStream = new FileInputStream(file_acc);

	XSSFWorkbook wbGET = new XSSFWorkbook(faccinStream);
	XSSFSheet wbSheet = wbGET.getSheet("GEN_ACC");

	XSSFRow shiptorow;

	XSSFCell imagenameCell;

	XSSFCell shiptooutput;

	for (int i = 1; i <= wbSheet.getLastRowNum(); i++) {
	    shiptorow = wbSheet.getRow(i);

	    imagenameCell = shiptorow.getCell(0);

	    shiptooutput = shiptorow.createCell(24);

	    String check_imageNAME = imagenameCell.getStringCellValue();

	    for (Map.Entry<String, String> mappedEntries : outputshipto.entrySet()) {

		if (mappedEntries.getKey().contains(check_imageNAME)) {
		    shiptooutput.setCellValue(mappedEntries.getValue());
		}
	    }

	}

	faccinStream.close();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wbGET.write(outputstreamAccuracy);
	wbGET.close();
	outputstreamAccuracy.close();
    }

    public static void generateResult_shipto() throws Exception {
	output_shipto();
	File file_acc = new File(".//API_ACC.xlsx");
	FileInputStream faccinStream = new FileInputStream(file_acc);

	XSSFWorkbook wbGET = new XSSFWorkbook(faccinStream);
	XSSFSheet wbSheet = wbGET.getSheet("GEN_ACC");

	XSSFRow resultRow;
	XSSFCell masterCell;
	XSSFCell outputCell;

	String masterCellVAL;
	String outputCellVAL;

	XSSFCell outputImageFile;

	for (int i = 1; i <= wbSheet.getLastRowNum(); i++) {
	    resultRow = wbSheet.getRow(i);
	    outputImageFile = resultRow.getCell(1);
	    masterCell = resultRow.getCell(23);
	    outputCell = resultRow.getCell(24);

	    masterCellVAL = masterCell.getStringCellValue();
	    outputCellVAL = outputCell.getStringCellValue();

	    XSSFCell cellResult = resultRow.createCell(25);

	    if (outputImageFile.getStringCellValue() != "") {
		masterCellVAL = masterCellVAL.replaceAll("\\s+", "");
		outputCellVAL = outputCellVAL.replaceAll("\\s+", "");
		if (outputCellVAL.compareTo(masterCellVAL) > 1 || outputCellVAL.compareTo(masterCellVAL) == 0
			|| masterCellVAL == "") {

		    cellResult.setCellValue("positive");

		} else {
		    cellResult.setCellValue("negative");

		}
	    }

	}

	faccinStream.close();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wbGET.write(outputstreamAccuracy);
	wbGET.close();
	outputstreamAccuracy.close();

    }

    public static void master_invoicedate() throws Exception {
	invoicedatemaster();
	generateResult_shipto();
	File file_acc = new File(".//API_ACC.xlsx");
	FileInputStream faccinStream = new FileInputStream(file_acc);

	XSSFWorkbook wbGET = new XSSFWorkbook(faccinStream);
	XSSFSheet wbSheet = wbGET.getSheet("GEN_ACC");

	XSSFRow invoicedatemASTERrow;

	XSSFCell imagenameCell;

	XSSFCell invoicedateMASTER;

	for (int i = 1; i <= wbSheet.getLastRowNum(); i++) {
	    invoicedatemASTERrow = wbSheet.getRow(i);

	    imagenameCell = invoicedatemASTERrow.getCell(0);

	    invoicedateMASTER = invoicedatemASTERrow.createCell(26);

	    String check_imageNAME = imagenameCell.getStringCellValue();

	    for (Map.Entry<String, String> mappedEntries : masterinvoicedate.entrySet()) {

		if (mappedEntries.getKey().contains(check_imageNAME)) {
		    invoicedateMASTER.setCellValue(mappedEntries.getValue());
		}
	    }

	}

	faccinStream.close();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wbGET.write(outputstreamAccuracy);
	wbGET.close();
	outputstreamAccuracy.close();
    }

    public static void output_invoicedate() throws Exception {

	master_invoicedate();

	invoicedateOUTPUT();

	File file_acc = new File(".//API_ACC.xlsx");
	FileInputStream faccinStream = new FileInputStream(file_acc);

	XSSFWorkbook wbGET = new XSSFWorkbook(faccinStream);
	XSSFSheet wbSheet = wbGET.getSheet("GEN_ACC");

	XSSFRow invoicedaterow;

	XSSFCell invoicedateCell;

	XSSFCell invoicedateoutput;

	for (int i = 1; i <= wbSheet.getLastRowNum(); i++) {
	    invoicedaterow = wbSheet.getRow(i);

	    invoicedateCell = invoicedaterow.getCell(0);

	    invoicedateoutput = invoicedaterow.createCell(27);

	    String check_imageNAME = invoicedateCell.getStringCellValue();

	    for (Map.Entry<String, String> mappedEntries : outputinvoicedate.entrySet()) {

		if (mappedEntries.getKey().contains(check_imageNAME)) {
		    invoicedateoutput.setCellValue(mappedEntries.getValue());
		}
	    }

	}

	faccinStream.close();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wbGET.write(outputstreamAccuracy);
	wbGET.close();
	outputstreamAccuracy.close();
    }

    public static void generateResult_invoicedate() throws Exception {

	output_invoicedate();
	File file_acc = new File(".//API_ACC.xlsx");
	FileInputStream faccinStream = new FileInputStream(file_acc);

	XSSFWorkbook wbGET = new XSSFWorkbook(faccinStream);
	XSSFSheet wbSheet = wbGET.getSheet("GEN_ACC");

	XSSFRow resultRow;
	XSSFCell masterCell;
	XSSFCell outputCell;

	String masterCellVAL;
	String outputCellVAL;

	XSSFCell outputImageFile;

	for (int i = 1; i <= wbSheet.getLastRowNum(); i++) {
	    resultRow = wbSheet.getRow(i);
	    outputImageFile = resultRow.getCell(1);
	    masterCell = resultRow.getCell(26);
	    outputCell = resultRow.getCell(27);

	    masterCellVAL = masterCell.getStringCellValue();
	    outputCellVAL = outputCell.getStringCellValue();

	    XSSFCell cellResult = resultRow.createCell(28);

	    if (outputImageFile.getStringCellValue() != "") {

		masterCellVAL = masterCellVAL.replaceAll("\\s+", "");
		outputCellVAL = outputCellVAL.replaceAll("\\s+", "");
		if (masterCellVAL.compareTo(outputCellVAL) == 0 || masterCellVAL == "") {

		    cellResult.setCellValue("positive");

		} else {

		    cellResult.setCellValue("negative");

		}
	    }

	}

	faccinStream.close();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wbGET.write(outputstreamAccuracy);
	wbGET.close();
	outputstreamAccuracy.close();

    }

    public static void processing() throws Exception {
	generateResult_invoicedate();
	File file_acc = new File(".//API_ACC.xlsx");
	FileInputStream faccinStream = new FileInputStream(file_acc);

	XSSFWorkbook wbGET = new XSSFWorkbook(faccinStream);
	XSSFSheet wbSheet_generateaccuracy = wbGET.getSheet("GEN_ACC");
	XSSFSheet wbSheet_generatefinalaccuracy = wbGET.createSheet("GEN_RESULT");
	int maxrow = wbSheet_generateaccuracy.getLastRowNum();

	for (int i = 0; i < maxrow; i++) {
	    XSSFRow rowaccuracy = wbSheet_generateaccuracy.getRow(i);
	    XSSFCell cell = rowaccuracy.getCell(1);

	    if (cell.getStringCellValue().compareTo("IMAGE NOT FOUND") != 0) {
		XSSFRow rowfinalaccuracy_nonempty = wbSheet_generatefinalaccuracy.createRow(i + 1);
		for (int j = 0; j < rowaccuracy.getLastCellNum(); j++) {
		    XSSFCell cellaccuracy = rowaccuracy.getCell(j);
		    XSSFCell cell_nonempty = rowfinalaccuracy_nonempty.createCell(j);
		    String celval = df.formatCellValue(cellaccuracy).toString();
		    cell_nonempty.setCellValue(celval);
		}

	    }

	}

	faccinStream.close();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wbGET.write(outputstreamAccuracy);

	wbGET.close();
	outputstreamAccuracy.close();

	BlankProcessor.mainProcess();
    }

    public static void processFinalSheet() throws Exception {
	processing();
	File file_acc = new File(".//API_ACC.xlsx");
	FileInputStream faccinStream = new FileInputStream(file_acc);

	XSSFWorkbook wbGET = new XSSFWorkbook(faccinStream);

	wbGET.removeSheetAt(0);
	wbGET.removeSheetAt(1);

	faccinStream.close();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wbGET.write(outputstreamAccuracy);

	wbGET.close();
	outputstreamAccuracy.close();

    }

    public static void setBorder() throws Exception {
	processFinalSheet();

	File file_acc = new File(".//API_ACC.xlsx");
	FileInputStream faccinStream = new FileInputStream(file_acc);

	XSSFWorkbook wb = new XSSFWorkbook(faccinStream);
	XSSFSheet sheet = wb.getSheet("GEN_RESULT");
	/* SETTING BORDER */
	CellStyle style = wb.createCellStyle();
	style.setBorderBottom(BorderStyle.THIN);
	style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	style.setBorderRight(BorderStyle.THIN);
	style.setRightBorderColor(IndexedColors.BLUE.getIndex());
	style.setBorderTop(BorderStyle.MEDIUM_DASHED);
	style.setTopBorderColor(IndexedColors.BLACK.getIndex());

	for (int i = 1; i <= sheet.getLastRowNum(); i++) {
	    XSSFRow row = sheet.getRow(i);
	    for (int j = 0; j < row.getLastCellNum(); j++) {
		XSSFCell cell = row.getCell(j);
		cell.setCellStyle(style);
	    }
	}

	faccinStream.close();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wb.write(outputstreamAccuracy);

	wb.close();
	outputstreamAccuracy.close();

    }

    /*
     * https://stackoverflow.com/questions/17546179/setting-cell-style-not-working
     *
     */

    @Test
    public static void processingPOSTMark() throws Exception {

	setBorder();

	int positivecount = 0;
	int negativecount = 0;

	File file_acc = new File(".//API_ACC.xlsx");
	FileInputStream faccinStream = new FileInputStream(file_acc);

	XSSFWorkbook wb = new XSSFWorkbook(faccinStream);

	XSSFCellStyle cs1 = wb.createCellStyle();
	cs1.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
	cs1.setFillPattern(FillPatternType.BIG_SPOTS);

	XSSFCellStyle cs2 = wb.createCellStyle();
	cs2.setFillBackgroundColor(IndexedColors.RED.getIndex());
	cs2.setFillPattern(FillPatternType.BIG_SPOTS);

	XSSFFont f1 = wb.createFont();
	f1.setBold(false);
	f1.setColor(IndexedColors.BLACK.getIndex());
	cs1.setFont(f1);

	XSSFFont f2 = wb.createFont();
	f2.setBold(true);
	f2.setColor(IndexedColors.BLACK.getIndex());
	cs2.setFont(f2);

	XSSFSheet sheet = wb.getSheet("GEN_RESULT");

	for (int i = 1; i <= sheet.getLastRowNum(); i++) {
	    XSSFRow row = sheet.getRow(i);
	    for (int j = 0; j < row.getLastCellNum(); j++) {
		XSSFCell cell = row.getCell(j);

		if (cell.getStringCellValue().compareTo("positive") == 0) {
		    cell.setCellStyle(cs1);

		    positivecount = positivecount + 1;
		} else if (cell.getStringCellValue().compareTo("negative") == 0) {
		    cell.setCellStyle(cs2);

		    negativecount = negativecount + 1;
		}

	    }
	}

	System.out.println("Total Positive Cases Found : " + positivecount);
	System.out.println("Total Negative Cases Found : " + negativecount);
	System.out.println("Total Cases Traversed : " + (positivecount + negativecount));

	faccinStream.close();
	outputstreamAccuracy = fetchACCuracyOutputSTream();
	wb.write(outputstreamAccuracy);

	wb.close();
	outputstreamAccuracy.close();

    }

}
