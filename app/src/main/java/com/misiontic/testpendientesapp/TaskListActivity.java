package com.misiontic.testpendientesapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class TaskListActivity extends AppCompatActivity {

    private ArrayList<String> tareas;
    private ListView lvTask;

    private MySQLiteHelper conexion_bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        /*tareas = new ArrayList<>();*/
        lvTask = findViewById(R.id.lvTask);
        cargarTareas();

        /***** Adicional eliminar *******/
        lvTask.setLongClickable(true);
        lvTask.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                eliminarTarea(tareas.get(position));
                return false;
            }
        });
        /*********************************/
    }

    public void agregarTarea(View view) {
        /*tareas.add("Ejemplo de nueva tarea");*/

        // input //
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Descripción de la tarea");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        builder.setView(input);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tarea = input.getText().toString();

                String sentence = "INSERT INTO tareas(descripcion) VALUES ('"+tarea+"')";
                conexion_bd.insertData(sentence);

                cargarTareas();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
        //////
    }

    public void cargarTareas() {
        tareas = new ArrayList<>();
        conexion_bd = new MySQLiteHelper(this);
        String sentence = "SELECT * FROM tareas";
        Cursor resultados = conexion_bd.getData(sentence, null);

        if (resultados.moveToFirst()) {
            do {
                tareas.add(resultados.getString(1));
            } while (resultados.moveToNext());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tareas);
        lvTask.setAdapter(adapter);
        resultados.close();
    }

    /***** Adicional eliminar *******/
    public void eliminarTarea(String tarea){
        String tabla = "tareas";
        String whereClause = "descripcion = ?";
        String[] params = new String[]{tarea};
        conexion_bd.deleteData(tabla, whereClause, params);
        cargarTareas();
    }
    /********************************/

}