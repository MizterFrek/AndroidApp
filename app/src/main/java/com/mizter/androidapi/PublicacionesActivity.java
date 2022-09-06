package com.mizter.androidapi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mizter.androidapi.Config.Config;
import com.mizter.androidapi.Interface.ApiService;
import com.mizter.androidapi.model.Publicaciones;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublicacionesActivity extends AppCompatActivity {

    private ListView lv;
    private AdapterPublicaciones adapter;
    private ApiService servicio= Config.getRetrofit().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicaciones);

        lv = findViewById(R.id.list);

        getPublicaciones();

    }
    private void getPublicaciones(){
        Call<List<Publicaciones>> listCall = servicio.getPublicaciones();
        listCall.enqueue(new Callback<List<Publicaciones>>() {
            @Override
            public void onResponse(Call<List<Publicaciones>> call, Response<List<Publicaciones>> response) {
                if(response.isSuccessful()){
                    //Log.d("ERROR",response.body().toString());
                    adapter = new AdapterPublicaciones(getApplicationContext(),response.body());
                    lv.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Publicaciones>> call, Throwable t) {
                Log.d("ERROR",t.getMessage());
            }
        });



    }


    class AdapterPublicaciones extends ArrayAdapter<Publicaciones>{
        List<Publicaciones> listapublicacion;
        public AdapterPublicaciones(Context context, List<Publicaciones> lista) {
            super(context, R.layout.lista,lista);
            listapublicacion = lista;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.lista,null);
            TextView n= view.findViewById(R.id.txtnombre);
            TextView d= view.findViewById(R.id.txtdescripcion);

            n.setText(listapublicacion.get(position).getNombre());
            n.setText(listapublicacion.get(position).getDescripcion());
            return view;

        }
    }
}