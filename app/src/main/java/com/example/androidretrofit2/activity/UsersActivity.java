package com.example.androidretrofit2.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androidretrofit2.R;
import com.example.androidretrofit2.adapter.UsersAdapter;
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

    UsersResource apiUserResourse;

    private String username;
    private String phone;
    private Integer id;

    EditText txtId;
    EditText txtUserName;
    EditText txtPhone;

    ListView listViewUsers;
    ArrayList<Users> listUsers = new ArrayList<Users>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        apiUserResourse = APIClient.getClient().create(UsersResource.class);

        Call<List<Users>> get = apiUserResourse.get();

        get.enqueue(new Callback<List<Users>>() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                listViewUsers = findViewById(R.id.listViewUser);
                listUsers = (ArrayList<Users>) response.body();
                listViewUsers.setAdapter(new UsersAdapter(getApplicationContext(), listUsers));
                registerForContextMenu(listViewUsers);
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private boolean existeId(ArrayList<Users> users, int id) {
        for (Users user : users) {
            if (user.getId() == id)
                return true;
        }
        return false;
    }

    public void userAdd(View view) {

        txtId = findViewById(R.id.txtId);
        txtUserName = findViewById(R.id.txtUserName);
        txtPhone = findViewById(R.id.txtPhone);


        id = Integer.parseInt(txtId.getText().toString());
        username = txtUserName.getText().toString();
        phone = txtPhone.getText().toString();

        Users user = new Users();
        user.setId(id);
        user.setName(username);
        user.setPhone(phone);

        Call<Users> post = apiUserResourse.post(user);
        post.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                Users u = response.body();
                listViewUsers = findViewById(R.id.listViewUser);

                if (existeId(listUsers, id)) {
                    Toast.makeText(getApplicationContext(), "PESSOA JÁ EXISTE", Toast.LENGTH_LONG).show();
                    txtPhone.setText("");
                    txtId.setText("");
                    txtUserName.setText("");
                } else {

                    listUsers.add(user);

                    listViewUsers.setAdapter(new UsersAdapter(getApplicationContext(), listUsers));

                }

            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(getApplicationContext(),
                        t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }

        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deletar = menu.add("Delete");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Users user = (Users) listViewUsers.getItemAtPosition(info.position);
                Call<Void> delete = apiUserResourse.delete(user.getId());
                delete.enqueue(new Callback<Void>() {
                   @Override
                   public void onResponse(Call<Void> call, Response<Void> response) {
                       apiUserResourse.delete(user.getId());
                       Intent intent = new Intent(listViewUsers.getContext(),listUsers.getClass());
                       startActivity(intent);
                   }

                   @Override
                   public void onFailure(Call<Void> call, Throwable t) {

                   }
               });
                return false;
            }
        });
    }
}