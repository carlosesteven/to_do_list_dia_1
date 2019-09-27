package csc.app.todolist.interfaz.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import csc.app.todolist.R;
import csc.app.todolist.interfaz.adaptador.RV_item;
import csc.app.todolist.room.base_datos.BD_tareas;
import csc.app.todolist.room.objetos.Tarea;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ListaTareas extends AppCompatActivity {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RecyclerView RvTareas;

    private ImageView user_foto;
    private TextView user_nombre;
    private MaterialButton user_btn;
    private MaterialButton user_btn_cerrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // INICIA LA BASE DE DATOS
        BD_tareas baseDatos = BD_tareas.getDatabase(this);

        // INICIA LAS VARIABLES LOCALES Y LOS ELEMENTOS DE LA VISTA
        TextView no_tareas = findViewById( R.id.error_no_tareas );
        user_foto = findViewById( R.id.user_foto );
        user_nombre = findViewById( R.id.user_nombre );
        user_btn = findViewById( R.id.user_btn_inicio);
        user_btn_cerrar = findViewById(R.id.user_btn_cerrar);
        RvTareas = findViewById( R.id.listaTareas );
        FloatingActionButton btnAgregar = findViewById( R.id.btnAgregar);

        /*
        *
        * CONFIGURA EL EVENTO ONCLICK EL CUAL VA A DETECTAR CADA VES QU7E EL USUARIO
        * TOCA EL BOTON
        *
        * EN ESTE CASO SON EL BOTON DE INICIAR SESION Y CERRAR SESION
        *
        */
        user_btn.setOnClickListener(
                view -> startActivity( new Intent( getBaseContext(), Autenticacion.class ) )
        );
        user_btn_cerrar.setOnClickListener(
                view -> cerrarSesion()
        );
        btnAgregar.setOnClickListener(view -> startActivity( new Intent(getBaseContext(), AgregarTarea.class) ));

        /*
        *
        * CONFIGURA EL RV
        * SE ESTABLECE QUE VA SER UN RV LINEAL
        *
         */
        RvTareas.setLayoutManager( new LinearLayoutManager( this ) );
        RvTareas.setHasFixedSize(true);

        /*
            * METODO ENCARGADO DE INTERACTUAR CON LA BASE DE SATOS
            *
            * SI LA LISTA DE TAREA ES MAYOR A 0 SE VA A MOSTRAR EL RV Y SE LE ENVIARA LA LISTA
            * DE TAREAS ENCONTRADA
            *
            * EN CASO DE NO EXISTIR TAREA SE VA OCULTAR EL RV Y SE MOSTRARA LA ALERTA DE "SIN TAREAS"
            *
            * EN CASO QUE PASE ALGUN ERROR DURANTE LA CONSULTA A LA BASE DE DATOS, NOS MOSTRARA LA
            * CAUSA Y LA MOSTRAR EN LA CONSOLA ( LOGCAST )
            *
         */
        Disposable disposable = baseDatos
                .tareasDao()
                .getListaTareas()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lista ->
                        {
                            Log.d("csc_debug", "Cantidad Tareas -> " + lista.size());
                            if ( lista.size() > 0 ) {
                                RvTareas.setVisibility(View.VISIBLE);
                                no_tareas.setVisibility( View.GONE );
                                RV_tareas(lista);
                            }else {
                                RvTareas.setVisibility(View.INVISIBLE);
                                no_tareas.setVisibility( View.VISIBLE );
                            }
                        }, e -> {
                            if ( e != null && e.getMessage() != null )
                                Log.d("csc_debug", e.getMessage());
                            else
                                Log.d("csc_debug", "Se produjo un error al verificar la lista de tareas");
                        }
                );
        compositeDisposable.add(disposable);

    }

    /*
     *
     * METODO ENCARGADO DE BORRAR LOS DATOS DEL ARCHIVOS DE PERSISTENCIA LOCAL
     *
     */
    private void cerrarSesion() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("user_foto", null);
        editor.putString("user_nombre", null);
        editor.apply();
        validarAutenticacion();
    }

    /*
    *
    * METODO ENCARGADO DE VERIFICAR QUE EN EL ARCHIVO DE PERSISTENCIA LOCAL EXISTA
    * LOS DATOS DE ALGUN USUARIO AUTENTICADO, EN EL CASO DE ENCONTRARLOS LOS VA A MOSTRAR
    * EN PANTALLA.
    *
     */
    private void validarAutenticacion()
    {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String userNombre = sharedPrefs.getString("user_nombre", null);
        String userFoto = sharedPrefs.getString("user_foto", null);

        if ( userNombre != null && userFoto != null )
        {
            userNombre = "Hola " + userNombre + "!";
            user_nombre.setText( userNombre );
            Picasso.get()
                    .load(userFoto)
                    .error( R.drawable.ic_user )
                    .into(user_foto);
            user_btn.setVisibility( View.GONE );
            user_btn_cerrar.setVisibility( View.VISIBLE );
        }else{
            user_nombre.setText( getString(R.string.alert_no_auth) );
            user_btn.setVisibility( View.VISIBLE );
            user_btn_cerrar.setVisibility( View.GONE );
            user_foto.setImageResource( R.drawable.ic_user );
        }
    }

    /*
    *
    * ESTE METODO SE ENCARGA DE CONFIGURAR EL ADAPTADOR
    * PASA LA LISTA AL ADAPTADOR Y LO IMPRIME EN PANTALLA
    *
     */
    private void RV_tareas(List<Tarea> lista)
    {
        RV_item adaptador = new RV_item(
                lista,
                getBaseContext(),
                (v, position) -> {
                    Intent editar = new Intent( getBaseContext(), MostrarTarea.class );
                    editar.putExtra( "idTarea", lista.get( position ).getIdTarea() );
                    startActivity( editar );
                }
        );
        RvTareas.setAdapter(adaptador);
    }

    /*
    *
    * compositeDisposable.dispose();
    *
    * ESTA FUNCION SE ENCARGA DE ELIMINAR EL OBSERVADOR DE LA BASE DE DATOS CUANDO
    * EL USUARIO DEJE LA APLICACIÓN Y ASI NO SIGA CONSUMIENDO RECURSOS EN SEGUNDO PLANO
    *
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    /*
    *
    * ESTE METODO SE ENCARGA QUE CADA VES QUE EL USUARIO INGRESE A LA APLICACIÓN SE VALIDE SI
    * EL USUARIO HA INICIADO SESION O NO.
    *
     */
    @Override
    protected void onResume() {
        super.onResume();
        validarAutenticacion();
    }
}
