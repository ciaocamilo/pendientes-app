package com.misiontic.testpendientesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToTaskList (View view) {
        Intent intentTaskList = new Intent(this, TaskListActivity.class);
        startActivity(intentTaskList);
    }

    public void showHelp (View view) {
        Toast.makeText(this, getString(R.string.help_text), Toast.LENGTH_LONG).show();
        Toast.makeText(this, getString(R.string.developed_by), Toast.LENGTH_SHORT).show();
    }

}