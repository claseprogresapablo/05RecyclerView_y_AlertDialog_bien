package com.example.a05recyclerview_y_alertdialog.adapters;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a05recyclerview_y_alertdialog.EditActivity;
import com.example.a05recyclerview_y_alertdialog.MainActivity;
import com.example.a05recyclerview_y_alertdialog.R;
import com.example.a05recyclerview_y_alertdialog.modelos.ToDo;

import java.io.Serializable;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoVH> {

    //elementos que necesita el adapter para hacer su funcion

    private List<ToDo> obejects;
    private int resource;
    private Context context;



    //crear constructor que recoja los datos
    public TodoAdapter(List<ToDo> obejects, int resource, Context context) {

        this.obejects = obejects;
        this.resource = resource;
        this.context = context;


    }


    /**
     * ALGO !!!!!! NO ME IMPORTA QUIEN. LLAMARA ESTE METODO PARA CREAR UNA NUEVA FILA
     * @param parent
     * @param viewType
     * @return un Objeto ViewHolder
     */
    @NonNull
    @Override
    public TodoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View toDoView = LayoutInflater.from(context).inflate(resource, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        toDoView.setLayoutParams(lp);


        return new TodoVH(toDoView);
    }




    /**
     * A partir de un ViewHolder -> Asignar Valores a los elementos
     * @param holder -> Fila a configurar
     * @param position -> Elemto de la lista a monstrar
     * Todo o relacionado con eventos va a qui
     */
    @Override
    public void onBindViewHolder(@NonNull TodoVH holder, int position) {

        ToDo toDo = obejects.get(position);
        holder.lblTitulo.setText(toDo.getTitulo());
        holder.lblContenido.setText(toDo.getContenido());
        holder.lblFecha.setText(toDo.getFecha().toString());
        if (toDo.isCompletado()){
            holder.btnCompletado.setImageResource(android.R.drawable.checkbox_on_background);
        } else{
            holder.btnCompletado.setImageResource(android.R.drawable.checkbox_off_background);
        }

        //para que se ponga check o se quite
        holder.btnCompletado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmaCambioEstado("Estas seguro de cambiar el estado?", toDo).show();
            }
        });


        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //le paso el objeto a eliminar porque el .remove del arraylist le puedes pasar
                // la posicion del objeto a elimar o el objeto tal cual
                confirmarDelete("Estas seguro de que quieres eliminar?", toDo).show();


            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Bundle bundle = new Bundle();
                bundle.putSerializable("TODO",toDo);
                Intent intent = new Intent(context, EditActivity.class);
                intent.putExtras(bundle);
                MainActivity.launcherEditToDo.launch(intent);
                //ontext.startActivity(intent);


            }
        });




    }




    private AlertDialog confirmarDelete(String mensaje, ToDo toDo){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(mensaje);
        builder.setCancelable(false);

        builder.setNegativeButton("NO",null);
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                obejects.remove(toDo);
                //notifyItemRemoved(to-Do); //para esto necesito pasarle la posicion queda mas bonico
                notifyDataSetChanged();
            }
        });

        return builder.create();
    }

    @Override
    public int getItemCount() {
        return obejects.size();
    }

    private AlertDialog confirmaCambioEstado(String mensaje, ToDo toDo){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(mensaje);
        builder.setCancelable(false);

        builder.setNegativeButton("NO",null);
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                toDo.setCompletado(!toDo.isCompletado());
                notifyDataSetChanged();
            }
        });

        return builder.create();
    }

    /**
     * Objeto se instanciara cada vez que tenga que monstrar un to-do en el Recycler
     * Pero solo se instancian los que caben en la pantalla + 1
     */
    public class TodoVH extends RecyclerView.ViewHolder {

        Button btnEliminar;
        TextView lblTitulo, lblContenido, lblFecha;
        ImageButton btnCompletado;


        public TodoVH(@NonNull View itemView) {
            super(itemView);

            lblTitulo = itemView.findViewById(R.id.lblTituloTodoModelView);
            lblContenido = itemView.findViewById(R.id.lblContenidoTodoModelView);
            lblFecha = itemView.findViewById(R.id.lblFechaTodoModelView);
            btnCompletado = itemView.findViewById(R.id.btnCompletadoTodoModelView);

            btnEliminar = itemView.findViewById(R.id.btnEliminarTodoModelView);
        }
    }
}
