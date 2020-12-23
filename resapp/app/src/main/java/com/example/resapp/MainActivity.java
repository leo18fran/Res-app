package com.example.resapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView tvresultados;
    EditText etnombres, etapellidos, etcodigo, etpa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvresultados =findViewById(R.id.tvresultados);
        etnombres = findViewById(R.id.etnombres);
        etapellidos = findViewById(R.id.etapellidos);
        etcodigo = findViewById(R.id.etcodigo);
        etpa = findViewById(R.id.etpa);
    }

    public void TraerDatos(View View){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://192.168.0.69/servicioweb/servidor.php?hash=4512348";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            tvresultados.setText("");
                            for (int i=0; i<response.length(); i++){
                                JSONObject item = response.getJSONObject(i);
                                tvresultados.append(
                                        item.getInt("codigo")+" "+
                                        item.getString("nombres")+" "+
                                        item.getString("apellidos")+" \n"
                                );
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                       }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("RestApp", error.getMessage());
                    }
                }
        );

        requestQueue.add(request);

    }

    public void guardardatos(View view) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://192.168.0.69/servicioweb/servidor.php?hash=4512348";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("RestApp", error.getMessage());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("nombres", etnombres.getText().toString());
                parametros.put("apellidos", etapellidos.getText().toString());
                parametros.put("codigo", etcodigo.getText().toString());
                parametros.put("pa", etpa.getText().toString());
                return parametros ;
            }
        };
        requestQueue.add(request);
    }
}