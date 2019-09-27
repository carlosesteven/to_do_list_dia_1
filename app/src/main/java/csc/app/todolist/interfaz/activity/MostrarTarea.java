package csc.app.todolist.interfaz.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import org.jetbrains.annotations.NotNull;

import csc.app.todolist.R;
import csc.app.todolist.room.objetos.Tarea;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MostrarTarea extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrar_tarea);

        // INICIA LA BASE DE DATOS


        // ASINGA A LAS VARIABLES LOS COMPONENTES GRAFICOS


        // CONFIGURA EVENTO ONCLICKLISTENES PARA LOS EL BOTON EDITARTAREA


        // LEE EL PARAMETRO ENVIADO EN EL INTENT

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
                                   Toast.makeText( getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           }
                );
    }

    /*
     *
     *  METODO PARA MOSTRAR LA INFORMACION CONTENIDA DE LA TAREA SELECCIONADA EN PANTALLA
     *
     */
    private void mostrarInformacion( Tarea tarea )
    {

    }

    /*
    *
    * METODO ENCARGADO DE ELIMINAR LA TEREA SELECCIONADA
     */
    private void eliminarTarea()
    {
        Completable.fromAction(
                () -> {

                }
        ).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(
                                getBaseContext(),
                                "Tarea Eliminada",
                                Toast.LENGTH_SHORT
                        ).show();
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(
                                getBaseContext(),
                                e.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
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

        //btnAgregar.setBackgroundTintList( getResources().getColorStateList(color) );

        getWindow().setNavigationBarColor(ContextCompat.getColor(this, color));
        getWindow().setStatusBarColor(ContextCompat.getColor(this, color));

    }

    /*
    *
    * METODO DEL SISTEMA QUE ASIGNA A INTENT UN MENU PERSONALIZADO
    *
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mostrar, menu);
        return true;
    }

    /*
    *
    * CAPTURA LOS EVENTOS ONCLICK SOBRE EL MENU
    *
     */
    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.menu_borrar) {
            eliminarTarea();
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    *
    * METODO ENCARGADO DE HACER LA CONSULTA CADA VES QUE EL USUARIO QUIERE CONSULTAR LA INFORMACION
    * DE ALGUNA TAREA EN LA BASE DE DATOS
    *
     */
    @Override
    protected void onResume() {
        super.onResume();
        //informacionTarea( idTarea );
    }

}