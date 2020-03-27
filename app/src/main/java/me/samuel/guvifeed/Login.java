package me.samuel.guvifeed;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private EditText user;
    private EditText key;

    private Button login;

    private static final String REGISTER_URL = "https://pico.games/tnp/login.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = (EditText) findViewById(R.id.email);
        key = (EditText) findViewById(R.id.password);


        login = (Button) findViewById(R.id.ah_login);

        login.setOnClickListener(this);
    }







    @Override
    public void onClick(View v) {
        if(v == login){
            String name = user.getText().toString().trim().toLowerCase();
            String password = key.getText().toString().trim().toLowerCase();

            String urlSuffix = "?user="+name+"&key="+password;
            class RegisterUser extends AsyncTask<String, Void, String> {

                ProgressDialog loading;


                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    loading = ProgressDialog.show(Login.this, "Please Wait","Connecting to Server", true, true);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    loading.dismiss();
                    if (s.equals("error01")){
                        startActivity(new Intent(Login.this,Home.class));
                    }else if (s.equals("error02")){
                        startActivity(new Intent(Login.this,Home.class));
                    }else{
                        Toast.makeText(Login.this,"Login Failed",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                protected String doInBackground(String... params) {
                    String s = params[0];
                    BufferedReader bufferedReader = null;
                    try {
                        URL url = new URL(REGISTER_URL+s);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                        String result;

                        result = bufferedReader.readLine();

                        return result;
                    }catch(Exception e){
                        return null;
                    }
                }
            }

            RegisterUser ru = new RegisterUser();
            ru.execute(urlSuffix).toString();

        }
    }

}
