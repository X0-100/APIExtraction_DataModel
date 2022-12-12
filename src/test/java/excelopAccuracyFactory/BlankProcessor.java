package excelopAccuracyFactory;

import com.spire.xls.ExcelVersion;
import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;

public class BlankProcessor {

    public static void mainProcess() {

	Workbook wb = new Workbook();

	wb.loadFromFile(".//API_ACC.xlsx");

	Worksheet sheet = wb.getWorksheets().get(1);

	for (int i = sheet.getLastRow(); i >= 1; i--)

	{

	    if (sheet.getRows()[i - 1].isBlank())

	    {

		sheet.deleteRow(i);

	    }

	}

	for (int j = sheet.getLastColumn(); j >= 1; j--)

	{

	    if (sheet.getColumns()[j - 1].isBlank())

	    {

		sheet.deleteColumn(j);

	    }

	}

	wb.saveToFile("API_ACC.xlsx", ExcelVersion.Version2016);

    }

}
