    package com.example.smd_march_4;

    import android.content.ContentValues;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;
    import android.util.Log;

    import androidx.annotation.Nullable;

    public class DBClass extends SQLiteOpenHelper {
        private  static final String NAME="MYDATABASE";
        private static final int VERSION=1;
        private static  final String TABLE_NAME="studentInfo";

        private static final String KEY_ID="Id";
        private static final String KEY_NAME="Name";
        private static final String KEY_PHONE_NUMBER="phoneNumber";

        public DBClass(@Nullable Context context) {
            super(context, NAME, null, VERSION);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createTableQuery = "CREATE TABLE " + TABLE_NAME + " ( "
                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_NAME + " TEXT, "
                    + KEY_PHONE_NUMBER + " TEXT)";
            db.execSQL(createTableQuery);
        }

        public void addinfo(String name,String Phonenumber){
            SQLiteDatabase database=this.getWritableDatabase();
            ContentValues value=new ContentValues();
            value.put(KEY_NAME,name);
            value.put(KEY_PHONE_NUMBER,Phonenumber);
            database.insert(TABLE_NAME,null,value);
        }

        public Cursor getAllData() {
            SQLiteDatabase database = this.getReadableDatabase();
            return database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        }

        public boolean updateData(int id, String name, String phoneNumber) {
            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, name);
            values.put(KEY_PHONE_NUMBER, phoneNumber);
            int result = database.update(TABLE_NAME, values, KEY_ID + "=?", new String[]{String.valueOf(id)});
            return result > 0;
        }

        public boolean deleteData(int id) {
            SQLiteDatabase database = this.getWritableDatabase();
            int result = database.delete(TABLE_NAME, KEY_ID + "=?", new String[]{String.valueOf(id)});
            return result > 0;
        }

        // Method to view all database values in logcat
        public void viewAllData() {
            SQLiteDatabase database = this.getReadableDatabase();
            Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
            if (cursor.getCount() == 0) {
                Log.d("DB_LOG", "No data found in table.");
            } else {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String name = cursor.getString(1);
                    String phone = cursor.getString(2);
                    Log.d("DB_LOG", "ID: " + id + ", Name: " + name + ", Phone: " + phone);
                }
            }
            cursor.close();
        }

        // Method to delete all table data without deleting the table
        public void deleteAllData() {
            SQLiteDatabase database = this.getWritableDatabase();
            database.delete(TABLE_NAME, null, null);
            Log.d("DB_LOG", "All data deleted from table.");
        }
    }
