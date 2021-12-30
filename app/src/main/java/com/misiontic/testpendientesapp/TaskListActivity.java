package com.misiontic.testpendientesapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES | InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE | InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);
        builder.setIcon(R.drawable.imagen_grande);
        builder.setView(input);
        input.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

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
                tareas.add(" ► " + resultados.getString(1));
            } while (resultados.moveToNext());
        }

        // ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tareas);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_view_format, tareas);
        lvTask.setAdapter(adapter);
        resultados.close();
    }

    /***** Adicional eliminar *******/
    public void eliminarTarea(String tarea) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Se eliminará la tarea de la lista. ¿Está seguro?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String tabla = "tareas";
                String whereClause = "descripcion = ?";
                String[] params = new String[]{tarea};
                conexion_bd.deleteData(tabla, whereClause, params);
                cargarTareas();
                    Toast.makeText(TaskListActivity.this, "Tarea eliminada", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do nothing
            }
        });
        builder.create();
        builder.show();
    }
    /********************************/

}