package csc.app.todolist.interfaz.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import csc.app.todolist.R;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AgregarTarea extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_tarea);

        // INICIA LA BASE DE DATOS


        // ASINGA A LAS VARIABLES LOS COMPONENTES GRAFICOS


        // CONFIGURA EVENTO ONCLICKLISTENES PARA LOS RadioButton


        // VALIDA QUE TODOS LOS CAMPOS ESTEN COMPLETOS ANTES DE ENVIAR LOS DATOS EN LA BASE DE DATOS
    }

    /*
    * METODO ENCARGADO DE AGREGAR LOS DATOS A LA BASE DE DATOS
     */
    private void agregarTarea(String titulo, String descripcion, int color)
    {
        Completable.fromAction(
                () -> {

                }
        )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(
                                getBaseContext(),
                                "Tarea Creada",
                                Toast.LENGTH_LONG
                        ).show();
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(
                                getBaseContext(),
                                e.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                        finish();
                    }
                });
    }

    /*
     *
     * METODO ENCARGADO DE CAMBIAR LOS COLOR DE LA INTERFAZ DE USUARIO
     * DEPENDIENDO DEL COLOR QUE USUARIO SELECCIONE
     *
     */
    private void cambiarColorUI(int color)
    {
        if ( getSupportActionBar() != null )
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(color)));

        getWindow().setNavigationBarColor(ContextCompat.getColor(this, color));
        getWindow().setStatusBarColor(ContextCompat.getColor(this, color));

        //btnAgregar.setBackgroundTintList( getResources().getColorStateList(color) );

    }

}
