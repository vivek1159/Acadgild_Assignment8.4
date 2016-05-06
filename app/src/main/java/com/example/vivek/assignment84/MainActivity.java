package com.example.vivek.assignment84;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    Button AddButton,DeleteButton;
    EditText InputText;
    TextView OutputText;

    String Filename = "MyFile.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AddButton = (Button)findViewById(R.id.AddButton);
        DeleteButton = (Button)findViewById(R.id.DeleteButton);
        InputText = (EditText)findViewById(R.id.InputText);
        OutputText = (TextView)findViewById(R.id.OutputText);
    }

    public void onClickAdd (View v)
    {
        //Write the EditText content to the file
        String content = InputText.getText().toString() + "\n";
        try {
            OutputStreamWriter out = new OutputStreamWriter(openFileOutput(Filename, Context.MODE_APPEND));
            out.write(content);
            out.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        //Displaying the content of the file in the Text View
        OutputText.setText(readFromFile());
    }

    private String readFromFile() {

        String data = "";
        try {
            InputStream input = openFileInput(Filename);

            if (input != null) {
                InputStreamReader in = new InputStreamReader(input);
                BufferedReader bf = new BufferedReader(in);
                StringBuilder sb = new StringBuilder();
                String text = "";

                while ((text = bf.readLine()) != null) {
                    sb.append(text).append("\n");
                }
                InputText.setText("");
                in.close();
                data = sb.toString();
                return data;
            }
            else {
                return "File Empty";
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return "Some error occured while reading file.";
        }
    }

    public void onClickDelete (View v)
    {
        File file = new File(getFilesDir().getAbsolutePath(),Filename);
        Boolean deleted = file.delete();

        if (deleted){
            OutputText.setText("File deleted");
        }
        else{
            Toast.makeText(MainActivity.this,"File not deleted.",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
