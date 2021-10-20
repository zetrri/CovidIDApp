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
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

public class ExcelDownloader implements Serializable {
    //public String[][] buffer = new String [2000][100];
    public String[][] cumulativeUptakeArray = new String[3000][6];
    public String[][] dosesAdministratedArray = new String[441][5];
    public String[][] dosesDistributedArray = new String[30][128];
    public int counter=0;
    private long downLoadId1;
    private long downLoadId2;
    private DownloadManager downloadManager;
    BroadcastReceiver onComplete;
    private int remove_flag = 0;

    public ExcelDownloader(){

    }

    public void startDownload(FragmentActivity activity){

        onComplete = new BroadcastReceiver() {
            public void onReceive(Context ctxt, Intent intent) {
                counter++;
                Log.i("ExcelDownloader", counter +" Downloads completed!");
                if (counter == 2) {
                    if(remove_flag==1) return;
                    Log.i("ExcelDownloader", "ALL " + counter + "DOWNLOADS FINISHED, STARTING POI WORK");
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

                return;
            }
        };
        activity.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        downloadFiles(activity);
    }

    //******************************* Downloads the files **************************************//
    public void downloadFiles(FragmentActivity activity){
        Log.i("info", "Laddar ner filer");
        remove_flag = 0;
        counter=0;
        downloadManager= (DownloadManager) activity.getSystemService(activity.DOWNLOAD_SERVICE);
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse("https://fohm.maps.arcgis.com/sharing/rest/content/items/fc749115877443d29c2a49ea9eca77e9/data"));
        request.setTitle("Voyager1")
                .setDescription("File is downloading...")
                .setDestinationInExternalFilesDir(activity, "Download", "Folkhalsomyndigheten_Covid19_Vaccine.xlsx")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        downLoadId1=downloadManager.enqueue(request);

/*
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        int week = calendar.get(Calendar.WEEK_OF_YEAR) - 1;
        Log.i("ExcelDownloader", "Week = " + week);
        String covid_leveranser = "https://www.folkhalsomyndigheten.se/contentassets/ad481fe4487f4e6a8d1bcd95a370bc1a/v" + Integer.toString(week-2) +"-leveranser-av-covid-vaccin-till-och-med-vecka-" + Integer.toString(week) + ".xlsx";
        Log.i("ExcelDownloader", covid_leveranser);
         */
        String covid_leveranser = "https://www.folkhalsomyndigheten.se/contentassets/ad481fe4487f4e6a8d1bcd95a370bc1a/v38-leveranser-av-covid-vaccin-till-och-med-vecka-40.xlsx";

        request=new DownloadManager.Request(Uri.parse(covid_leveranser));
        request.setTitle("Voyager2")
                .setDescription("File is downloading...")
                .setDestinationInExternalFilesDir(activity, "Download", "v38-leveranser-av-covid-vaccin-till-och-med-vecka-40.xlsx")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        downLoadId2=downloadManager.enqueue(request);
    }

    //******************************* Get cell from file ***************************************//
    public void cellExtraction(Sheet sheet, String [][] buffer){
        for(int i = 0; i < sheet.getLastRowNum()+1; i++){
            Row row = sheet.getRow(i);
            if(row == null)
                return;
            int j = 0;
            if(row.getRowNum()>0){
                Iterator<Cell> cellIterator = row.cellIterator();
                while(cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            //Log.i("IIII", String.valueOf(cell.getStringCellValue()));
                            buffer[i][j] = String.valueOf(cell.getStringCellValue());
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            //Log.i("IIII", String.valueOf(cell.getNumericCellValue()));
                            buffer[i][j] = String.valueOf(cell.getNumericCellValue());
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            //addCell(String.valueOf(cell.getBooleanCellValue()), buffer, i, j);
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

        cellExtraction(sheet, buffer);
    }
    /* Total doses administered in Counties and Sweden
        - filter: counties, age groups, 1 vs 2 */
    //TODO saknas filter för produkt
    public void dosesAdministrated(Sheet sheet, String [][] buffer){

        cellExtraction(sheet, buffer);
    }

    /* Total cases and total deaths
    - filter: counties */
    public void casesPerRegion(Sheet sheet, String [][] buffer){

        cellExtraction(sheet, buffer);
    }
    /* Total cases and total deaths
    - filter: age groups */
    public void casesPerAge(Sheet sheet, String [][] buffer){

        cellExtraction(sheet, buffer);
    }

    public void dosesDistributed(Sheet sheet, String [][] buffer){

        cellExtraction(sheet, buffer);
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

    public void stopDownloads(FragmentActivity activity){
        downloadManager.remove(downLoadId1);
        downloadManager.remove(downLoadId2);
        Log.i("ExcelDownloader", "Removing downloads");
        remove_flag=1;

        File file1 = new File(activity.getExternalFilesDir("Download"), "Folkhalsomyndigheten_Covid19_Vaccine.xlsx");
        File file2 = new File(activity.getExternalFilesDir("Download"), "v37-leveranser-av-covid-vaccin-till-och-med-vecka-39.xlsx");
        if(file1.exists()){
            Log.i("info", "Deleting file " + file1);
            file1.delete();
        }if(file2.exists()){
            Log.i("info", "Deleting file " + file2);
            file2.delete();
        }

        activity.unregisterReceiver(onComplete);
    }


}
