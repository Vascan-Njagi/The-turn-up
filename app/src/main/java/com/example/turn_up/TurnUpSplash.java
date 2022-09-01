package com.example.turn_up;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class TurnUpSplash extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turn_up_splash);
    }


    public void showAccountDetails(View view) {
        PopupMenu popupMenu=new PopupMenu(this,view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.account_dropdown_menu);
        popupMenu.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.accountDetails:
            case R.id.accountSettings:
                Toast.makeText(this, "i will do after presentation", Toast.LENGTH_LONG).show();
                return true;
            case R.id.logout:
                //Log out the User
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(TurnUpSplash.this,MainActivity.class));
                return true;
            default:
                return false;
        }
    }
}