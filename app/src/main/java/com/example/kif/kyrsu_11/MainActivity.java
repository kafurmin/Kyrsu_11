package com.example.kif.kyrsu_11;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OnClick (View view) {
        SharedPreferences preferences = null;
        SharedPreferences.Editor editor = null;

        switch (view.getId()) {
            case R.id.button:
                preferences = getPreferences(MODE_PRIVATE);
                editor = preferences.edit();
                editor.putString("Test", "I am here^)");
                editor.commit();


                break;
            case R.id.button2:
                preferences = getPreferences(MODE_PRIVATE);
                Toast.makeText(this, preferences.getString("Test", ""), Toast.LENGTH_SHORT).show();

                break;
            case R.id.button3:

                startActivity(new Intent(this, SettingsActivity.class));

                break;
            case R.id.button4:
                preferences = PreferenceManager.getDefaultSharedPreferences(this);
                Toast.makeText(this, preferences.getString("edit_text_preference_1", ""), Toast.LENGTH_SHORT).show();

                break;
            case R.id.button5:
                saveInternalFile("MyFile.txt","New data from me $)");

                break;
            case R.id.button6:
                String text= readInternalFile("MyFile.txt");
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

                break;
            case R.id.button7:
                if(hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                        File folder = Environment.getExternalStorageDirectory();// корневая папка

                        folder = new File(folder.getAbsolutePath()+"/MyFolder/Trash");

                        if(!folder.exists()){

                            folder.mkdirs();// также false если не дали пермишн
                        }
                        saveExternalFile(folder,"MyFile.txt", "I am tadam");

                    }
                }else {
                    if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
                        String[] permissions = {
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        };

                        requestPermissions(permissions, 0);
                    }
                    //Toast.makeText(this, "No permission !", Toast.LENGTH_SHORT).show();

                }

                break;

            case R.id.button8:
                if(hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)){
                    if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                        File folder = Environment.getExternalStorageDirectory();// корневая папка

                        folder = new File(folder.getAbsolutePath()+"/MyFolder/Trash");

                        if(folder.exists()){
                            readExternalFile(folder, "MyFile.txt");
                        }
                        Toast.makeText(this, readExternalFile(folder,"MyFile.txt"), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
                        String[] permissions = {
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        };

                        requestPermissions(permissions, 0);
                    }
                    //Toast.makeText(this, "No permission !", Toast.LENGTH_SHORT).show();

                }

                break;
            case R.id.button9:
                Student student = new Student("Ivan","Ivanov", 22);
                Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy HH:mm:ss").create();
                String json = gson.toJson(student);

                saveInternalFile("Student.txt", json);

                break;
            case R.id.button10:
                String json2 = readInternalFile("Student.txt");


                Gson gson2 = new GsonBuilder().setDateFormat("dd.MM.yyyy HH:mm:ss").create();

                Student student2 = gson2.fromJson(json2, Student.class);
                Toast.makeText(this, student2.FirstName+" "+student2.LastName+" "+student2.Age, Toast.LENGTH_SHORT).show();



                break;
            case R.id.button11:

                Serializer serializer = new Persister();
                Student student3 = new Student("Ivangogogog","Ivanovrrrrrrrr", 88);
                File result = new File(getFilesDir()+"/student.xml");

                try {
                    serializer.write(student3, result);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.button12:

                Serializer serializer2 = new Persister();
                File source = new File(getFilesDir()+"/student.xml");

                try {
                    Student student4 = serializer2.read(Student.class, source);
                    Toast.makeText(this, student4.FirstName+" "+student4.LastName+" "+student4.Age, Toast.LENGTH_SHORT).show();

                } catch (Exception e){
                    e.printStackTrace();
                }
                break;

        }
    }
    private boolean hasPermission(String permission){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private void saveInternalFile(String fileName, String data) {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(openFileOutput(fileName, Context.MODE_PRIVATE)));

            writer.write(data);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String readInternalFile(String fileName) {
        try {
            StringBuilder builder = new StringBuilder();

            BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput(fileName)));
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            reader.close();

            return builder.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void saveExternalFile(File folder, String fileName, String data) {
        File file = new File(folder, fileName);

        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),
                    "UTF8"));

            writer.write(data);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String readExternalFile(File folder, String fileName) {
        File file = new File(folder, fileName);

        try {
            if (file.exists()) {
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;

                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                reader.close();

                return builder.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
