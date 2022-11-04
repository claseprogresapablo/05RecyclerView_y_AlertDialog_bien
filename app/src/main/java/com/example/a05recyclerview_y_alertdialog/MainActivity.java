package com.example.a05recyclerview_y_alertdialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.a05recyclerview_y_alertdialog.adapters.TodoAdapter;
import com.example.a05recyclerview_y_alertdialog.modelos.ToDo;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a05recyclerview_y_alertdialog.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<ToDo> todosList;
    private TodoAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public static ActivityResultLauncher<Intent> launcherEditToDo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        launcherEditToDo = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

            }
        });

        todosList = new ArrayList<>();
        //crearTodos();


        adapter = new TodoAdapter(todosList, R.layout.todo_view_model,MainActivity.this);
        binding.contentMain.contenedor.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(MainActivity.this); //EN LINEA
        //layoutManager = new GridLayoutManager(MainActivity.this,2); // EN TABLA
        binding.contentMain.contenedor.setLayoutManager(layoutManager);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createToDo("NUEVA TAREA").show();
            }
        });





    }

    private AlertDialog createToDo(String titulo){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(titulo);

        View contenido = LayoutInflater.from(MainActivity.this).inflate(R.layout.add_todo_alert_dialog, null);
        TextView txtTitulo = contenido.findViewById(R.id.txtTituloAddToDo);
        TextView txtContenido = contenido.findViewById(R.id.txtContenidoAddToDo);

        builder.setView(contenido);

        builder.setNegativeButton("CANCELAR",null);
        builder.setPositiveButton("AGREGAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ToDo toDo = new ToDo(txtTitulo.getText().toString(), txtContenido.getText().toString());
                todosList.add(toDo);
                adapter.notifyDataSetChanged();
            }
        });
        return builder.create();
    }

    private void crearTodos() {
        for (int i = 0; i < 1000; i++) {
            todosList.add(new ToDo("Titulo "+i, "contenido "+i));
        }
    }


}