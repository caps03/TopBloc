package com.code;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.File;
import java.util.Iterator;
import java.io.IOException;
public class App {
	public static void main( String[] args ) throws IOException {
		int data1NumberSetOne[], data1NumberSetTwo[], data2NumberSetOne[], data2NumberSetTwo[], numberSetOne[], numberSetTwo[];
		String data1WordSetOne[], data2WordSetOne[], wordSetOne[];
		data1NumberSetOne = new int[4];
		data1NumberSetTwo = new int[4];
		data1WordSetOne = new String[4];
		data2NumberSetOne = new int[4];
		data2NumberSetTwo = new int[4];
		data2WordSetOne = new String[4];
		numberSetOne = new int[4];
		numberSetTwo = new int[4];
		wordSetOne = new String[4];
		try {
			Workbook workbook = WorkbookFactory.create(new File("./src/main/java/com/code/Data1.xlsx"));
			Sheet sheet = workbook.getSheetAt(0);
			Iterator < Row > rowIterator = sheet.iterator();
			int i = -1;
			while (rowIterator.hasNext()) {
				Row row = (Row) rowIterator.next();
				Iterator < Cell > cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						if (cell.getRowIndex() > 0) {
							if (cell.getColumnIndex() == 0) {
								data1NumberSetOne[i] = (int) cell.getNumericCellValue();
							} else if (cell.getColumnIndex() == 1) {
								data1NumberSetTwo[i] = (int) cell.getNumericCellValue();
							} else if (cell.getColumnIndex() == 2) {
								data1WordSetOne[i] = cell.getStringCellValue();
							}
						}
					}
					i++;
				}
			workbook = WorkbookFactory.create(new File("./src/main/java/com/code/Data2.xlsx"));
			sheet = workbook.getSheetAt(0);
			rowIterator = sheet.iterator();
			i = -1;
			while (rowIterator.hasNext()) {
				Row row = (Row) rowIterator.next();
				Iterator < Cell > cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						if (cell.getRowIndex() > 0) {
							if (cell.getColumnIndex() == 0) {
								data2NumberSetOne[i] = (int) cell.getNumericCellValue();
							} else if (cell.getColumnIndex() == 1) {
								data2NumberSetTwo[i] = (int) cell.getNumericCellValue();
							} else if (cell.getColumnIndex() == 2) {
								data2WordSetOne[i] = cell.getStringCellValue();
							}
						}
					}
					i++;
				}
		} catch (Exception e) {
			System.out.println("Exception caught");
		}
		for (int j = 0; j < 4; j++)	{
			numberSetOne[j] = data1NumberSetOne[j] * data2NumberSetOne[j];
			numberSetTwo[j] = data1NumberSetTwo[j] / data2NumberSetTwo[j];
			wordSetOne[j] = data1WordSetOne[j] + " " + data2WordSetOne[j];
		}		
        JSONObject json = new JSONObject();
        JSONArray numSetOne = new JSONArray();
        for (int j = 0; j < 4; j++) {
        	numSetOne.add(numberSetOne[j]);
        }
        JSONArray numSetTwo = new JSONArray();
        for (int j = 0; j < 4; j++) {
        	numSetTwo.add(numberSetTwo[j]);
        }
        
        JSONArray wSetOne = new JSONArray();
        for (int j = 0; j < 4; j++) {
        	wSetOne.add(wordSetOne[j]);
        }
        json.put("id", "capsteven1@gmail.com");
        json.put("numberSetOne", numSetOne);
        json.put("numberSetTwo", numSetTwo);
        json.put("wordSetOne", wSetOne);        
        CloseableHttpClient client = HttpClients.createDefault();
        try {
	        HttpPost httpPost = new HttpPost("http://34.239.125.159:5000/challenge");
	        StringEntity entity = new StringEntity(json.toJSONString());
	        httpPost.setEntity(entity);
	        httpPost.setHeader("Accept", "application/json");
	        httpPost.setHeader("Content-type", "application/json");
	     
	        CloseableHttpResponse response = client.execute(httpPost);
	        try {
	        	System.out.println(EntityUtils.toString(response.getEntity()));
	        } finally {
	        	response.close();
	        }
        } finally {
        	client.close();
        }
    }
}