package com.example.a05recyclerview_y_alertdialog;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.a05recyclerview_y_alertdialog.databinding.ActivityEditBinding;
import com.example.a05recyclerview_y_alertdialog.modelos.ToDo;

public class EditActivity extends AppCompatActivity {


    private ActivityEditBinding binding;

    private ActivityResultLauncher<Intent> launcherEditToDo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //2-Construye el binding
        binding = ActivityEditBinding.inflate(getLayoutInflater());
        //3- Asocia el binding a la activity
        setContentView(binding.getRoot());

        //FASE 1-> CONSEGUIR LA INFORMACION
        //RECUOERAR EL INTENT
        Intent intent = getIntent();
        //RECUPERAR BUNDLE
        Bundle bundle = intent.getExtras();
        //SI NO ES NULL HAY UN STRING
        if (bundle != null){

            ToDo toDo = (ToDo) bundle.getSerializable("TODO");


            System.out.printf(toDo.toString());

            inicializarVistas(toDo);

        }

        binding.btnCancelarEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.btnEditarEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int posicion = bundle.getInt("POS");
                ToDo toDo = createToDo();

                if (toDo!=null){

                } else {
                    Toast.makeText(EditActivity.this, "Faltan Datos", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    private ToDo createToDo() {


        if (binding.txtTituloEditToDo.getText().toString().isEmpty() || binding.txtContenidoEditToDo.getText().toString().isEmpty()){
            return null;
        }


        return new ToDo(binding.txtTituloEditToDo.getText().toString(), binding.txtContenidoEditToDo.getText().toString());


    }

    private void inicializarVistas(ToDo toDo) {
        binding.txtTituloEditToDo.setText(toDo.getTitulo());
        binding.txtContenidoEditToDo.setText(toDo.getContenido());
    }
}