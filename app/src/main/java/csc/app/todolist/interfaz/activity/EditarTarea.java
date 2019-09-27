package csc.app.todolist.interfaz.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import csc.app.todolist.R;
import csc.app.todolist.room.objetos.Tarea;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EditarTarea extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_tarea);

        // INICIA LA BASE DE DATOS


        // ASINGA A LAS VARIABLES LOS COMPONENTES GRAFICOS


        // CONFIGURA EVENTO ONCLICKLISTENES PARA LOS RadioButton


        // LEE EL PARAMETRO ENVIADO EN EL INTENT


        // ENVIA EL PARAMETRO ENVIADO AL METODO DE CONSULTA


        // VALIDA QUE TODOS LOS CAMPOS ESTEN COMPLETOS ANTES DE ACTUALIZAR LOS DATOS EN LA BASE DE DATOS

    }

    /*
     *
     *  METODO PARA MOSTRAR LA INFORMACION CONTENIDA DE LA TAREA SELECCIONADA EN PANTALLA
     *
     */
    private void mostrarInformacion( Tarea tarea )
    {
        // CONFIGURACION DE COMO SE VA A MOSTRAR LA TAREA
    }

    /*
     *
     *  METODO DE CONSULTA DE LA TAREA
     *
     */
    private void informacionTarea( int idTarea )
    {
        Single<Tarea> tareaSingle = null;
        tareaSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Tarea>()
                           {
                               @Override
                               public void onSubscribe(Disposable d)
                               {
                               }
                               @Override
                               public void onSuccess(Tarea actual)
                               {
                                   mostrarInformacion( actual );
                               }
                               @Override
                               public void onError(Throwable e) {
                                   Toast.makeText(
                                           getBaseContext(),
                                           e.getMessage(),
                                           Toast.LENGTH_SHORT
                                   ).show();
                                   finish();
                               }
                           }
                );
    }

    /*
    *
    * CONFIGURACION DE COMO SE ACTUALIZA UNA TAREA
    *
     */
    private void actualizarTarea(int idTarea, String titulo, String descripcion, int color)
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
                                "Tarea Actualizada",
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
