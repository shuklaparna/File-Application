package com.example.fileapplication;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends Activity {
    EditText inputText,Fname;
    TextView response;
    Button saveButton,readButton,s1;
    private String filename = "";
    private final String filepath = "MyFileStorage";
    File myExternalFile,data;
    String myData = "";
    String fn="";
    MyDB d;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        d=new MyDB(this);
        inputText = (EditText) findViewById(R.id.myInputText);
        response = (TextView) findViewById(R.id.response);
        s1=(Button)findViewById(R.id.saveExternalStorage);
        s1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                fname();
                if (filename.length() <= 4) {
                    Toast.makeText(MainActivity.this, "enter the file name", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (d.check(fn))
                    {
                        Toast.makeText(MainActivity.this, "First create a file", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            FileOutputStream fos = new FileOutputStream(myExternalFile);
                            fos.write(inputText.getText().toString().getBytes());
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        inputText.setText("");
                        response.setText("Text saved at " + filename + "at External Storage...");
                    }
                }
            }
        });
        saveButton = (Button) findViewById(R.id.createExternalStorage);
        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                fname();

                create();
                if (filename.length() <= 4) {
                    Toast.makeText(MainActivity.this, "Enter the file name", Toast.LENGTH_SHORT).show();
                } else
                {
                    if (!d.check(fn))
                    {
                        Toast.makeText(MainActivity.this, "File already Exists ", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            FileOutputStream fos = new FileOutputStream(myExternalFile);
                            fos.write(inputText.getText().toString().getBytes());
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        inputText.setText("");
                        d.set(fn);
                        response.setText(filename + " saved to External Storage...");
                    }
                }
            }
        });
        readButton = (Button) findViewById(R.id.getExternalStorage);
        readButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                fname();
                create();
                if (filename.length() <= 4) {
                    Toast.makeText(MainActivity.this, "enter the file name", Toast.LENGTH_SHORT).show();
                } else {
                    if (d.check(fn)) {
                        Toast.makeText(MainActivity.this, "create a file", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            FileInputStream fis = new FileInputStream(myExternalFile);
                            DataInputStream in = new DataInputStream(fis);
                            BufferedReader br = new BufferedReader(new InputStreamReader(in));
                            String strLine;
                            while ((strLine = br.readLine()) != null) {
                                myData = myData + strLine + "\n";
                            }
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        inputText.setText(myData);
                        myData =  "";
                        response.setText(filename + " data retrieved from External Storage...");
                    }
                }
            }
        });
    }
    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState);
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(extStorageState);
    }
    public  void fname()
    {

        Fname = (EditText) findViewById(R.id.Fname);
        filename = Fname.getText().toString() + ".txt";
        fn=Fname.getText().toString();
    }
    public void create()
    {
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            saveButton.setEnabled(false);
        }
        else {
            myExternalFile = new File(getExternalFilesDir(filepath), filename);
        }
    }
}
