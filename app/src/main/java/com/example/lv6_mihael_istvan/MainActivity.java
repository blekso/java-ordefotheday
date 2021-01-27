package com.example.lv6_mihael_istvan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RemoveClickListener{

    private static final String FILE_NAME = "log.txt";
    private RecyclerView recycler;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupRecycler();
        setupRecyclerData();
        changeActivity();

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void changeActivity() {
        ImageView settingsImg = findViewById(R.id.imgSettings);
        settingsImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondScreen.class);
                startActivity(intent);

            }
        });
    }

    private void setupRecycler(){
        recycler = findViewById(R.id.recyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter(this);
        recycler.setAdapter(adapter);
    }

    private void setupRecyclerData(){
        List<String> data = new ArrayList<>();


        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;
            while ((text = br.readLine()) != null) {
                if(!text.equals("")){
                    data.add(text);
                    Log.i("dataIn", "loading: " + text);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        adapter.addData(data);
    }

    public void addCell(View view) {
        EditText editText = findViewById(R.id.newCellName);
        String cellName = editText.getText().toString();
        if (TextUtils.isEmpty(cellName)) {
            Toast.makeText(this, "Please write item name!", Toast.LENGTH_SHORT).show();
        } else {

            editText.getText().clear();
            adapter.addNewCell(cellName, adapter.getItemCount());
            File directory = getFilesDir();
            File file = new File(directory, FILE_NAME);
            FileOutputStream fos = null;

            try {
                fos = new FileOutputStream(file, true);
                fos.write(cellName.getBytes());
                fos.write(10);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onRemoveClick(int position) {
        Log.i("dataRemove", "removed item: " + adapter.getItemName(position));
        try {
            File directory = getFilesDir();
            File file = new File(directory, FILE_NAME);
            File temp = File.createTempFile("file", ".txt", file.getParentFile());
            String charset = "UTF-8";
            String delete = adapter.getItemName(position);
            adapter.removeCell(position);
            adapter.printList();
            Log.i("dataDelete", delete);
            Log.i("dataPosition", String.valueOf(position));
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(temp), charset));
            for (String line = reader.readLine(); (line != null);  line = reader.readLine()) {
                if(!line.equals(delete)){
                    writer.println(line);
                    Log.i("dataLine", line);
                }
            }
            reader.close();
            writer.close();
            file.delete();
            temp.renameTo(file);
        } catch (IOException e) {
            Log.i("dataError", e.getMessage());
        }
    }
}