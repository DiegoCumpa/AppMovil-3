package proyecto.appcampeonato.conexion;

import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import proyecto.appcampeonato.conexion.PlaceContract.PlaceEntry;

public class Conexion extends SQLiteOpenHelper {

    // The database name
    private static final String DATABASE_NAME = "prueba53";

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 1;


    public Conexion(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override

    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_PLACES_TABLE = "CREATE TABLE " + PlaceEntry.TABLE_NAME + " (" +
                PlaceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PlaceEntry.COLUMN_PLACE_ID + " TEXT NOT NULL, " +
                "UNIQUE (" + PlaceEntry.COLUMN_PLACE_ID + ") ON CONFLICT REPLACE" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_PLACES_TABLE);


        String USUARIO="create table usuario(email text,pwd text);";
        sqLiteDatabase.execSQL(USUARIO);

        sqLiteDatabase.execSQL(("insert into usuario values('diego.cl16@hotmail.com','12345')"));
        sqLiteDatabase.execSQL(("insert into usuario values('aragcar@gmail.com','12345')"));

        String SEXO="create table sexo(integer codigo,nombre text);";
        sqLiteDatabase.execSQL(SEXO);

        sqLiteDatabase.execSQL(("insert into sexo values('1','Masculino')"));
        sqLiteDatabase.execSQL(("insert into sexo values('2','Femenino')"));

        String TIPODOC="create table tipodoc(integer codigo,nombre text);";
        sqLiteDatabase.execSQL(TIPODOC);

        sqLiteDatabase.execSQL(("insert into tipodoc values('1','DNI')"));
        sqLiteDatabase.execSQL(("insert into tipodoc values('2','RUC')"));
        sqLiteDatabase.execSQL(("insert into tipodoc values('3','Carnet de Extranjeria')"));

        String ESTADO="create table estado(integer codigo,nombre text);";
        sqLiteDatabase.execSQL(ESTADO);

        sqLiteDatabase.execSQL(("insert into estado values('1','Activo')"));
        sqLiteDatabase.execSQL(("insert into estado values('2','Inactivo')"));

        String PERSONA="create table persona(idpersona INTEGER PRIMARY KEY AUTOINCREMENT, nombres VARCHAR, apaterno VARCHAR,amaterno varchar," +
                " sexo integer,tipdoc integer,numdoc varchar,fecnac varchar,email varchar,telefono varchar, estado integer,foto BLOB);";
        sqLiteDatabase.execSQL(PERSONA);


    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PlaceEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}
