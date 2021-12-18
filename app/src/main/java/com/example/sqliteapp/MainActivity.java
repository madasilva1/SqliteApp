package com.example.sqliteapp;

import static com.example.sqliteapp.DatabaseHelper.KEY_DATE;
import static com.example.sqliteapp.DatabaseHelper.KEY_ID;
import static com.example.sqliteapp.DatabaseHelper.KEY_ROOM;
import static com.example.sqliteapp.DatabaseHelper.KEY_STATUS;
import static com.example.sqliteapp.DatabaseHelper.TABLE_NAME;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.sqliteapp.Details;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editRoom,editStatus,editDate ,editTextId;
    Button btnAddData;
    Button btnviewAll;
    Button btnDelete,btnClear;
    Button btnviewUpdate;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editRoom = (EditText)findViewById(R.id.editText_name);
        editStatus = (EditText)findViewById(R.id.editText_surname);
        editDate = (EditText)findViewById(R.id.editText_Marks);
        editTextId = (EditText)findViewById(R.id.editText_id);
        btnAddData = (Button)findViewById(R.id.button_add);
        btnviewAll = (Button)findViewById(R.id.button_viewAll);
        btnviewUpdate= (Button)findViewById(R.id.button_update);
        btnDelete= (Button)findViewById(R.id.button_delete);
        btnClear =(Button)findViewById(R.id.clearbtn);
        AddData();
        viewAll();
        UpdateData();
        DeleteData();
        ClearData();

    }

    private void ClearData() {
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set the  EditText empty
               editRoom.setText("");
               editStatus.setText("");
               editDate.setText("");
               editTextId.setText("");

            }
        });
    }

    public void DeleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(editTextId.getText().toString());
                        if(deletedRows > 0)
                            Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Deleted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public void UpdateData() {
        btnviewUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData(editTextId.getText().toString(),
                                editRoom.getText().toString(),
                                editStatus.getText().toString(),editDate.getText().toString());
                        if(isUpdate == true)
                            Toast.makeText(MainActivity.this,"Data Update",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public  void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String room = editRoom.getText().toString();
                        String status = editStatus.getText().toString();
                        String date = editDate.getText().toString();
                        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                        databaseHelper.insertData(room,status,date);
                       // intent = new Intent(MainActivity.this,Details.class);
                      // startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Details Inserted Successfully",Toast.LENGTH_SHORT).show();
                       /* boolean isInserted = myDb.insertData(editRoom.getText().toString(),
                                editRoom.getText().toString(),
                                editDate.getText().toString() );
                        if(isInserted == true)
                            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();

                        */
                    }
                }
        );
    }

    public void viewAll() {
        btnviewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0) {
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                          /*  buffer.append("create table");
                            buffer.append(TABLE_NAME);
                            buffer.append("(");
                            buffer.append(KEY_ID);
                            buffer.append(KEY_ROOM);
                            buffer.append(KEY_STATUS);
                            buffer.append(KEY_DATE);
                          */
                            buffer.append("Id :"+ res.getString(0)+"\n");
                            buffer.append("Room :"+ res.getString(1)+"\n");
                            buffer.append("Status :"+ res.getString(2)+"\n");
                            buffer.append("Date :"+ res.getString(3)+"\n\n");

                        }

                        // Show all dataed
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }



    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
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

