package com.example.androidretrofit2.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.androidretrofit2.R;
import com.example.androidretrofit2.bootstrap.APIClient;
import com.example.androidretrofit2.model.Users;
import com.example.androidretrofit2.resource.UsersResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersActivity extends AppCompatActivity {

    UsersResource apiUserResouce;

    ListView listViewUser;
    List<Users> listUser;
    List<HashMap<String,String>> colecao =
            new ArrayList<HashMap<String,String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //tem o contexto da aplicação (application)
        //PASSO 4
        apiUserResouce = APIClient.getClient().create(UsersResource.class);

        Call<List<Users>> get = apiUserResouce.get();

        get.enqueue(new Callback<List<Users>>() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                listViewUser = findViewById(R.id.listViewUser);

                listUser = response.body();

                listUser.forEach(u ->{
                    //Criar dados para adapter
                    HashMap<String,String> mapUser = new HashMap<String,String>();
                    mapUser.put("id",String.valueOf(u.getId()));
                    mapUser.put("name",u.getName());

                    colecao.add(mapUser);
                });

                String[] from = {"id","name"};
                int[] to = {R.id.txtId,R.id.txtUserName};

                SimpleAdapter simpleAdapter =
                        new SimpleAdapter(
                                getApplicationContext(),
                                colecao,
                                R.layout.user,
                                from,
                                to);

                listViewUser.setAdapter(simpleAdapter);
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(),Toast.LENGTH_LONG).show();
            }
        });
//    }
//
//    public void addUser(View view) {
//
//        String username,data;
//        Integer id;
//        id = Integer.parseInt(txtId.getText().toString());
//        username = txtUserName.getText().toString();
//        data = txtData.getText().toString();
//
//
//        final User user = User.builder()
//                .id(id)
//                .userName(username)
//                .avatar(null)
//                .uuid(UUID.randomUUID().toString())
//                .data(data)
//                .build();
//
//        Call<User> post = apiUserResouce.post(user);
//        post.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                User u = response.body();
//                Toast.makeText(getApplicationContext(),
//                        u.toString(),
//                        Toast.LENGTH_LONG).show();
//
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                Toast.makeText(getApplicationContext(),
//                        t.getMessage(),
//                        Toast.LENGTH_LONG).show();
//            }
//        });
//
//        Call<User> put = apiUserResouce.put(user);
//        put.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//
//            }
//        });
//
//        Call<Void> delete = apiUserResouce.delete(user);
//        delete.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//
//            }
//        });
    }
}
