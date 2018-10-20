package com.example.michaelxuzhi.atry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by michaelxuzhi on 2018/10/20.
 */

public class DBDefinition {
    private static final String DB_NAME = "student.db";
    private static final String DB_TABLE = "studentinfo";
    private static final int DB_VERSION = 2;
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_SEX = "sex";
    public static final String KEY_TOTALCREDITS = "totalcredits";
    private SQLiteDatabase db;
    private Context context;
    private DBOpenHelper dbOpenHelper;



    public DBDefinition(Context _context){
        context=_context;}




    //打开数据库
    public void open(){
        dbOpenHelper = new DBOpenHelper(context,DB_NAME,null,DB_VERSION);
                try{
                    db = dbOpenHelper.getWritableDatabase();
                }catch (SQLiteException e){
                    db = dbOpenHelper.getReadableDatabase();
                }
    }

    //关闭数据库
    public void close(){ db.close();}


    //插入数据
    public long insert(Student student){
        ContentValues newValues=new ContentValues();
        newValues.put(KEY_NAME,student.Name);
        newValues.put(KEY_SEX,student.Sex);
        newValues.put(KEY_TOTALCREDITS,student.Totalcredits);
        System.out.println(db.insert(DB_TABLE,null,null)); //控制台输出
        return db.insert(DB_TABLE,null,newValues);
    }




    //删除数据
    public long deleteOneData(long id){
        return db.delete(DB_TABLE,KEY_ID +"="+id,null);
    }

    //查询全部数据
    public Student[] queryAllData(){
        Cursor results = db.query(DB_TABLE,
                new String[]{KEY_ID,KEY_NAME,KEY_SEX,KEY_TOTALCREDITS},
                null,null,null,null,null);
        return ConverToStudent(results);
    }

    //查询指定ID数据
    public Student[] queryData(long id){
        Cursor results=db.query(DB_TABLE,
                new String[]{KEY_ID,KEY_NAME,KEY_SEX,KEY_TOTALCREDITS},
                KEY_ID+"="+id,
                null,null,null,null);
        return ConverToStudent(results);
    }

    //更新指定的数据，将Name、Sex、Totalcredits属性的值写入到ContentValues对象
    public long updateData(long id,Student students){
        ContentValues newValues=new ContentValues();
        newValues.put(KEY_NAME,students.Name);
        newValues.put(KEY_SEX,students.Sex);
        newValues.put(KEY_TOTALCREDITS,students.Totalcredits);
        return db.update(DB_TABLE,newValues,KEY_ID+"="+id,null);
    }

    //显示查询数据
    public Student[] ConverToStudent(Cursor cursor){
        int resultCounts=cursor.getCount();//获取查询数据集的记录数
        if(resultCounts == 0||!cursor.moveToFirst()){
            return null;
        }
        Student[] students=new Student[resultCounts];
        for(int i=0;i<resultCounts;i++){
            students[i]=new Student();
            students[i].ID=cursor.getInt(0);
            students[i].Name=cursor.getString(cursor.getColumnIndex(KEY_NAME));
            students[i].Sex=cursor.getString(cursor.getColumnIndex(KEY_SEX));
            students[i].Totalcredits=cursor.getInt(cursor.getColumnIndex(KEY_TOTALCREDITS));
            cursor.moveToNext();
        }
        return students;
    }




    class DBOpenHelper extends SQLiteOpenHelper{
        public DBOpenHelper(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
            super(context,name,factory,version);
        }


        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL("create table " + DB_TABLE +"("
                    + KEY_ID + " integer primary key autoincrement, "
                    + KEY_NAME + " varchar,"
                    + KEY_SEX + " varchar,"
                    + KEY_TOTALCREDITS +" integer)");
        }
        @Override
        public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
            db.execSQL("ALTER TABLE studentinfo add totalcredits integer(20);");
            onCreate(db);
        }
    }



}
