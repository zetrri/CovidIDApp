package com.example.covidapp.Dashboard;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.covidapp.MainActivity;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class ExcelDownloader implements Serializable {
    //public String[][] buffer = new String [2000][100];
    public String[][] cumulativeUptakeArray = new String[3000][8];
    public String[][] dosesAdministratedArray = new String[512][8];
    public String[][] dosesDistributedArray = new String[128][128];
    public int counter=0;
    public int column =1;
    public boolean newline = false;

    public ExcelDownloader(){

    }

    public void startDownload(FragmentActivity activity){

        BroadcastReceiver onComplete = new BroadcastReceiver() {
            public void onReceive(Context ctxt, Intent intent) {
                counter++;
                if (counter == 2) {
                    Log.i("DOWNLOAD", "ALL " + counter + "DOWNLOADS FINISHED, STARTING LOAD");
                    File file1 = new File(activity.getExternalFilesDir("Download"), "Folkhalsomyndigheten_Covid19_Vaccine.xlsx");
                    File file2 = new File(activity.getExternalFilesDir("Download"), "v38-leveranser-av-covid-vaccin-till-och-med-vecka-40.xlsx");
                    FileInputStream fileInputStream = null;
                    Workbook workbook = null;

                    try {
                        fileInputStream = new FileInputStream(file1);
                        Log.i("info", "Reading from Excel " + file1);
                        workbook = new XSSFWorkbook(fileInputStream);
                        cumulativeUptake(workbook.getSheet("Vaccinerade tidsserie"), cumulativeUptakeArray);

                        dosesAdministrated(workbook.getSheet("Vaccinerade ålder"), dosesAdministratedArray);

                        fileInputStream.close();
                        workbook.close();

                        fileInputStream = new FileInputStream(file2);
                        Log.i("info", "Reading from Excel " + file2);
                        workbook = new XSSFWorkbook(fileInputStream);
                        dosesDistributed(workbook.getSheet("Antal doser av vaccin"), dosesDistributedArray);

                    } catch (IOException e) {
                        Log.i("info", "Error reading exception: ", e);
                    }

                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Intent doneIntent = new Intent();
                    doneIntent.setAction("EXCEL_DOWNLOAD_COMPLETE");
                    activity.sendBroadcast(doneIntent);
                }

                /*
                BroadcastReceiver br = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {

                    }
                };
                registerReceiver(br, new IntentFilter("EXCEL_DOWNLOAD_COMPLETE"));
                */
                return;
            }
        };
        activity.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        downloadFiles(activity);
    }

    //******************************* Downloads the files **************************************//
    public void downloadFiles(FragmentActivity activity){
        Log.i("info", "Laddar ner filer");
        DownloadManager downloadManager= (DownloadManager) activity.getSystemService(activity.DOWNLOAD_SERVICE);
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse("https://fohm.maps.arcgis.com/sharing/rest/content/items/fc749115877443d29c2a49ea9eca77e9/data"));
        request.setTitle("Voyager1")
                .setDescription("File is downloading...")
                .setDestinationInExternalFilesDir(activity, "Download", "Folkhalsomyndigheten_Covid19_Vaccine.xlsx")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        long downLoadId1=downloadManager.enqueue(request);

        request=new DownloadManager.Request(Uri.parse("https://www.folkhalsomyndigheten.se/contentassets/ad481fe4487f4e6a8d1bcd95a370bc1a/v38-leveranser-av-covid-vaccin-till-och-med-vecka-40.xlsx"));
        request.setTitle("Voyager2")
                .setDescription("File is downloading...")
                .setDestinationInExternalFilesDir(activity, "Download", "v38-leveranser-av-covid-vaccin-till-och-med-vecka-40.xlsx")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        long downLoadId2=downloadManager.enqueue(request);
    }

    //******************************* Adds cell to buffer **************************************//
    public void addCell(String str, String row, ArrayList<Integer> exceptions){
        if(!exceptions.contains(column)) row = str;
        column++;
        newline = false;
    }

    //******************************* Get cell from file ***************************************//
    public void cellExtraction(Sheet sheet, ArrayList<Integer> exceptions, String [][] buffer){
        for(int i = 0; i < sheet.getLastRowNum()+1; i++){
            Row row = sheet.getRow(i);
            if(row == null)
                return;
            column = 1;
            newline = true;
            int j = 0;
            if(row.getRowNum()>0){
                Iterator<Cell> cellIterator = row.cellIterator();
                while(cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            //Log.i("IIII", String.valueOf(cell.getStringCellValue()));
                            addCell(String.valueOf(cell.getStringCellValue()), buffer[i+1][j], exceptions);
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            //Log.i("IIII", String.valueOf(cell.getNumericCellValue()));
                            addCell(String.valueOf(cell.getNumericCellValue()), buffer[i+1][j], exceptions);
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            addCell(String.valueOf(cell.getBooleanCellValue()), buffer[i+1][j], exceptions);
                            break;
                        default:
                    }
                    j++;
                }
            }
        }
    }

    /* Cumulative uptake (%) of at least one vaccine dose and full vaccination among adults in Sweden and counties
- there should be a filter that sorts the result by week and month */
    public void cumulativeUptake(Sheet sheet, String [][] buffer){
        buffer[0][0] = "Vecka";
        buffer[0][1] = "År";
        buffer[0][2] = "Region";
        buffer[0][3] = "Andel vaccinerade";
        buffer[0][4] = "Vaccinationsstatus";
        ArrayList<Integer> exceptions = new ArrayList<Integer>();
        exceptions.add(4);
        cellExtraction(sheet, exceptions, buffer);
    }
    /* Total doses administered in Counties and Sweden
        - filter: counties, age groups, 1 vs 2 */
    //TODO saknas filter för produkt
    public void dosesAdministrated(Sheet sheet, String [][] buffer){
        buffer[0][0] = "Region";
        buffer[0][1] = "Åldersgrupp";
        buffer[0][2] = "Antal vaccinterade";
        buffer[0][3] = "Vaccinationsstatus";
        ArrayList<Integer> exceptions = new ArrayList<Integer>();
        exceptions.add(4);
        cellExtraction(sheet, exceptions, buffer);
    }

    /* Total cases and total deaths
    - filter: counties */
    public void casesPerRegion(Sheet sheet, String [][] buffer){
        buffer[0][0] = "Region";
        buffer[0][1] = "Totalt antal fall";
        buffer[0][2] = "Totalt antal avlidna";
        ArrayList<Integer> exceptions = new ArrayList<Integer>();
        exceptions.add(3);
        exceptions.add(4);
        cellExtraction(sheet, exceptions, buffer);
    }
    /* Total cases and total deaths
    - filter: age groups */
    public void casesPerAge(Sheet sheet, String [][] buffer){
        buffer[0][0] = "Åldersgrupp";
        buffer[0][1] = "Totalt antal fall";
        buffer[0][2] = "Totalt antal avlidna";
        ArrayList<Integer> exceptions = new ArrayList<Integer>();
        exceptions.add(3);
        cellExtraction(sheet, exceptions, buffer);
    }

    public void dosesDistributed(Sheet sheet, String [][] buffer){
        ArrayList<Integer> exceptions = new ArrayList<Integer>();
        cellExtraction(sheet, exceptions, buffer);
    }

    public String[][] getCumulativeUptakeArray(){
        return cumulativeUptakeArray;
    }

    public String[][] getDosesAdministratedArray(){
        return dosesAdministratedArray;
    }

    public String[][] getDosesDistributedArray(){
        return dosesDistributedArray;
    }


}