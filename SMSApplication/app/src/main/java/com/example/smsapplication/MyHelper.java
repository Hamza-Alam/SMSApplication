package com.example.smsapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MyHelper extends SQLiteOpenHelper {


    private static final String dbname = "Test";
    private static final int version = 1;
    boolean checkReturn;

    public MyHelper(Context context) {
        super(context, dbname, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE if not exists DESTINATION_NUMBER (id INTEGER PRIMARY KEY AUTOINCREMENT,PHONE_NUMBER TEXT,STATUS BOOL)");
        db.execSQL("CREATE TABLE if not exists Short_Code (id INTEGER PRIMARY KEY AUTOINCREMENT,Short_Code_Name TEXT,Short_Code_String TEXT,Category INTEGER)");
        db.execSQL("CREATE TABLE if not exists Pre_Built_Message (id INTEGER PRIMARY KEY AUTOINCREMENT,Phone_Number TEXT,Ammount TEXT,Short_Code_String TEXT)");
        db.execSQL("CREATE TABLE if not exists Message (id INTEGER PRIMARY KEY AUTOINCREMENT,Destination_Number TEXT,Message TEXT,Status TEXT)");
        db.execSQL("CREATE TABLE if not exists CodeCategory (id INTEGER PRIMARY KEY AUTOINCREMENT,Category_Name TEXT)");
        db.execSQL("CREATE TABLE if not exists Short_Code_Forward_String (id INTEGER PRIMARY KEY AUTOINCREMENT,Code_Name TEXT,Code_String TEXT,Forward_String TEXT,Category INTEGER)");
        db.execSQL("CREATE TABLE if not exists Recipients_Number (id INTEGER PRIMARY KEY AUTOINCREMENT,Number_Name TEXT,Number TEXT)");
        db.execSQL("CREATE TABLE if not exists Auto_Short_Code (id INTEGER PRIMARY KEY AUTOINCREMENT,Code_Name TEXT,Sample_String TEXT,Build_String TEXT,Number TEXT,Selected_Functionality TEXT)");
    }


    public ArrayList<Auto_Short_Code_List_Model> getAllAutoShortCode() {
        ArrayList<Auto_Short_Code_List_Model> returnResult=new ArrayList<>();
        String selectQuery = "SELECT * FROM Auto_Short_Code";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {

                returnResult.add(new Auto_Short_Code_List_Model(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5)));

            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();
        return returnResult;
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists DESTINATION_NUMBER");
        sqLiteDatabase.execSQL("drop table if exists Short_Code");
        sqLiteDatabase.execSQL("drop table if exists Pre_Built_Message");
        sqLiteDatabase.execSQL("drop table if exists Message");
        sqLiteDatabase.execSQL("drop table if exists CodeCategory");
        sqLiteDatabase.execSQL("drop table if exists Short_Code_Forward_String");
        sqLiteDatabase.execSQL("drop table if exists Recipients_Number");
        onCreate(sqLiteDatabase);
    }

    public boolean addNumber(String numberName, String number) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Number_Name", numberName);
        values.put("Number", number);
        long result = database.insert("Recipients_Number", null, values);
        database.close();
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<Add_Number_Model> getAllNumber() {
        List<Add_Number_Model> numberlist = new ArrayList<>();
        String selectQuery = "SELECT  * FROM Recipients_Number order by id desc";
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Add_Number_Model codes = new Add_Number_Model();
                    codes.setNumberID(String.valueOf(cursor.getInt(0)));
                    codes.setNumberName(cursor.getString(1));
                    codes.setNumber(cursor.getString(2));
//                    // Adding contact to list
                    numberlist.add(codes);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            if (e.getMessage().contains("no such table")) {
                onCreate(db);
            }
        }
        // return contact list
        return numberlist;
    }

    public boolean insertData(String number, boolean status) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("PHONE_NUMBER", number);
        values.put("STATUS", status);
        long result = database.insert("DESTINATION_NUMBER", null, values);
        database.close();
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertCode(String name, String codeString,Integer catID) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Short_Code_Name", name);
        values.put("Short_Code_String", codeString);
        values.put("Category",catID);
        long result = database.insert("Short_Code", null, values);
        database.close();
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<DestinationClas> getAllContacts() {
        List<DestinationClas> contactList = new ArrayList<DestinationClas>();
        String selectQuery = "SELECT  * FROM DESTINATION_NUMBER order by id desc";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                DestinationClas contact = new DestinationClas();
                contact.setNumberID(cursor.getString(0));
                contact.setDestination_number(cursor.getString(1));
                contact.setStatus(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list
        return contactList;
    }

    public List<ShortCode_Model> getAllCodes() {
        List<ShortCode_Model> codeList = new ArrayList<ShortCode_Model>();
        String selectQuery = "SELECT  * FROM Short_Code order by id desc";
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    ShortCode_Model codes = new ShortCode_Model();
                    codes.setId(cursor.getString(0));
                    codes.setCodeName(cursor.getString(1));
                    codes.setCodeString(cursor.getString(2));
                    // Adding contact to list
                    codeList.add(codes);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            if (e.getMessage().contains("no such table")) {
                onCreate(db);
            }
        }
        // return contact list
        return codeList;
    }

    public ArrayList<SpinnerAdp> spinnerAllCodes(int catID) {
        ArrayList<SpinnerAdp> codeList = new ArrayList<SpinnerAdp>();
        // Select All Query
        String selectQuery = "SELECT  * FROM Short_Code where Category="+catID+" order by id desc";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                codeList.add(new SpinnerAdp(cursor.getInt(0), cursor.getString(1)));

            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return codeList;
    }

    public boolean updateNumber(String numberId, String number, boolean status) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("PHONE_NUMBER", number);
        cv.put("STATUS", status);
        long result = database.update("DESTINATION_NUMBER", cv, "id=" + numberId, null);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateCode(String codeId, String codeName, String codeString) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Short_Code_Name", codeName);
        values.put("Short_Code_String", codeString);
        long result = database.update("Short_Code", values, "id=" + codeId, null);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean saveMessage(String activeNumber, String messageBody) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Destination_Number", activeNumber);
        values.put("Message", messageBody);
        values.put("Status","Send");
        long result1 = database.insert("Message", null, values);
        database.close();
        if (result1 == -1) {
            return false;
        } else {
            return true;
        }
    }

    private boolean checkTableExist() {
        List<MessageModel> codeList = new ArrayList<MessageModel>();
        String selectQuery = "SELECT  * FROM Message";
        boolean result=false;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                result=true;
            }
        } catch (SQLiteException e) {
            if (e.getMessage().contains("no such table")) {
                onCreate(db);
                result=true;
            }
        }
        return result;

    }

    public String getStringCode(int selected_code) {
        String selectQuery = "SELECT  Short_Code_String FROM Short_Code where id='"+selected_code+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String result1="";
        if(cursor.moveToFirst())
        {
            result1=cursor.getString(0);
        }
        return result1;
    }

    public boolean addCategory(String name) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Category_Name", name);
        long result1 = database.insert("CodeCategory", null, values);
        database.close();
        if (result1 == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<CategoryModel> spinnerAllCategory() {
        ArrayList<CategoryModel> catList = new ArrayList<CategoryModel>();
        String selectQuery = "SELECT  * FROM CodeCategory order by id desc";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {

                catList.add(new CategoryModel(cursor.getInt(0), cursor.getString(1)));

            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return catList;
    }

    public boolean deleteCategory(CharSequence text) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete("CodeCategory", "id" + "=" + text, null) > 0;

    }

    public boolean updateCategory(String name, String id) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Category_Name", name);
        long result = database.update("CodeCategory", cv, "id=" + id, null);
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    public boolean deleteNumber(CharSequence text) {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete("DESTINATION_NUMBER", "id" + "=" + text, null) > 0;

    }

    public boolean deleteCode(CharSequence text) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete("Short_Code", "id" + "=" + text, null) > 0;
    }

    public void setAllDatabase() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("drop table if exists DESTINATION_NUMBER");
        sqLiteDatabase.execSQL("drop table if exists Short_Code");
        sqLiteDatabase.execSQL("drop table if exists Pre_Built_Message");
        sqLiteDatabase.execSQL("drop table if exists Message");
        sqLiteDatabase.execSQL("drop table if exists CodeCategory");
        sqLiteDatabase.execSQL("drop table if exists Short_Code_Forward_String");

        onCreate(sqLiteDatabase);
    }

    public boolean addForwarShortcode(String codeName, String codeString, String codeForward, int catID) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Code_Name", codeName);
        values.put("Code_String", codeString);
        values.put("Forward_String", codeForward);
        values.put("Category", catID);
        long result = database.insert("Short_Code_Forward_String", null, values);
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    public List<Forward_Short_code_Model> getAllForwardCode() {
        List<Forward_Short_code_Model> codeList = new ArrayList<Forward_Short_code_Model>();
        String selectQuery = "SELECT  * FROM Short_Code_Forward_String order by id desc";
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Forward_Short_code_Model codes = new Forward_Short_code_Model();
                    codes.setId(cursor.getString(0));
                    codes.setCodeName(cursor.getString(1));
                    codes.setCodeString(cursor.getString(2));
                    codes.setForwardString(cursor.getString(3));
                    // Adding contact to list
                    codeList.add(codes);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            if (e.getMessage().contains("no such table")) {
                onCreate(db);
            }
        }
        // return contact list
        return codeList;
    }

    public boolean checkString(String indexCode) {

        String selectQuery = "SELECT Code_String FROM Short_Code_Forward_String where Code_String Like '"+indexCode+"%'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                return false;
            } while (cursor.moveToNext());
        }
        else {
            return true;
        }
    }

    public boolean deleteForwardCode(CharSequence text) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete("Short_Code_Forward_String", "id" + "=" + text, null) > 0;
    }

    public boolean updateForwardCode(String codeName, String codeString, String codeForward, int catID) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Code_Name", codeName);
        cv.put("Code_String", codeString);
        cv.put("Forward_String", codeForward);
        cv.put("Category", catID);
        long result = database.update("Short_Code_Forward_String", cv, "id=" + catID, null);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<ShortCode_Model> getAllCodeIfExist(String s) {
        ArrayList<ShortCode_Model> returnResult=new ArrayList<>();
        String selectQuery = "SELECT id,Code_String FROM Short_Code_Forward_String where Code_String Like '"+s+"%'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {

                returnResult.add(new ShortCode_Model(cursor.getString(0),cursor.getString(1)));

            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return returnResult;
    }

    public ArrayList<String> getForwardString(String finalId) {
        ArrayList<String> returnResult=new ArrayList<>();
        String selectQuery = "SELECT Forward_String FROM Short_Code_Forward_String where id Like '"+finalId+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {

                returnResult.add(cursor.getString(0));

            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return returnResult;

    }

    public ArrayList<Recipients_Number_Model> AllRecipientsNumber() {
        ArrayList<Recipients_Number_Model> numList = new ArrayList<Recipients_Number_Model>();
        String selectQuery = "SELECT  * FROM Recipients_Number order by id desc";
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Recipients_Number_Model codes = new Recipients_Number_Model();
                    codes.setId(cursor.getInt(0));
                    codes.setRecipNumber(cursor.getString(2));
//                    // Adding contact to list
                    numList.add(codes);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            if (e.getMessage().contains("no such table")) {
                onCreate(db);
            }
        }
        // returning lables
        return numList;
    }

    public String getRecipientNumber(int numID) {
        String selectQuery = "SELECT  Number FROM Recipients_Number where id='"+numID+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String result1="";
        if(cursor.moveToFirst())
        {
            result1=cursor.getString(0);
        }
        return result1;
    }

    public boolean addAutoShortCodes(String name, String query1, String query2, String number, String selectedRadioButtonValue) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Code_Name", name);
        values.put("Sample_String", query1);
        values.put("Build_String", query2);
        values.put("Number", number);
        values.put("Selected_Functionality", selectedRadioButtonValue);
        long result = database.insert("Auto_Short_Code", null, values);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkSampleStringInDB(String sampleQuery){
        String query="SELECT Sample_String FROM Auto_Short_Code where Sample_String LIKE  '"+sampleQuery+"%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                return true;
            } while (cursor.moveToNext());
        }
        else {
            return false;
        }
    }

    public boolean numberExist(String number) {
        String query="SELECT Number FROM Recipients_Number where Number = '"+number+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do {
                return true;
            } while (cursor.moveToNext());
        }
        else {
            return false;
        }
    }

    public boolean updateRecipientNumber(String numid, String name, String number) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Number_Name", name);
        values.put("Number", number);
        long result = database.update("Recipients_Number", values, "id=" + numid, null);
        database.close();
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteRecipientNumber(CharSequence text) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete("Recipients_Number", "id" + "=" + text, null) > 0;
    }

    public boolean updateAutoShortCode(String id, String name, String query1, String query2, String number, String selectedRadioButtonValue) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Code_Name", name);
        values.put("Sample_String", query1);
        values.put("Build_String", query2);
        values.put("Number", number);
        values.put("Selected_Functionality", selectedRadioButtonValue);
        long result = database.update("Auto_Short_Code",values, "id=" + id, null);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteAutoShortCode(CharSequence id){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete("Auto_Short_Code", "id" + "=" + id, null) > 0;
    }

    public boolean checkUpdateSampleStringDB(String id, String sampleString) {
        String query="SELECT * FROM Auto_Short_Code where Sample_String LIKE  '"+sampleString+"%' AND id!='"+id+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                return true;
            } while (cursor.moveToNext());
        }
        else {
            return false;
        }
    }

    public boolean checkUpdateNumberDB(String numid, String number) {
        String query="SELECT Number FROM Recipients_Number where Number = '"+number+"' AND id!='"+numid+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do {
                return true;
            } while (cursor.moveToNext());
        }
        else {
            return false;
        }
    }
}
