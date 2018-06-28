package com.example.thegerman.simpletodo;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ArrayList<String> todoItems;
    ArrayAdapter<String> aTodoAdapter;
    ListView Ls_item;
    EditText Et_item;
    Button bt_add;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        Ls_item = (ListView) findViewById(R.id.Ls_item);
        bt_add = (Button) findViewById(R.id.Bt_add);
        Ls_item.setAdapter(aTodoAdapter);
        Et_item = (EditText) findViewById(R.id.Et_item);
        fm = getSupportFragmentManager();

        Ls_item.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                todoItems.remove(position);
                aTodoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });


        Ls_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Edit e = Edit.newInstance(position, todoItems.get(position));
                e.show(fm, "EDIT ITEM");
            }
        });

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aTodoAdapter.add(Et_item.getText().toString());
                aTodoAdapter.notifyDataSetChanged();
                Et_item.setText("");
            }
        });
    }

    public void populateArrayItems(){
        readItems();
        todoItems = new ArrayList<String>();
        aTodoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
    }
    private void readItems(){
        File filesDir = getFilesDir();
        File file = new File (filesDir, "todo.txt");
        try{
            todoItems = new ArrayList<String>(FileUtils.readLines(file));

        }catch (IOException e){

        }
    }

    private void writeItems(){
        File filesDir = getFilesDir();
        File file = new File (filesDir, "todo.txt");
        try{
            FileUtils.writeLines(file, todoItems);

        }catch (IOException e){

        }
    }

    public void onAddItem(View view){
        aTodoAdapter.add(Et_item.getText().toString());
        Et_item.setText("");
        writeItems();
    }


    public void onEditItem(int p, String c){
        todoItems.set(p, c);
        aTodoAdapter.notifyDataSetChanged();
    }
}
