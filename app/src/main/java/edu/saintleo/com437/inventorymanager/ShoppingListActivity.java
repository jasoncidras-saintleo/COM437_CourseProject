package edu.saintleo.com437.inventorymanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import edu.saintleo.com437.inventorymanager.databinding.ActivityShoppinglistsBinding;

public class ShoppingListActivity extends AppCompatActivity implements DialogInterface.OnDismissListener, NavigationBarView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityShoppinglistsBinding binding = ActivityShoppinglistsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_bar_shopping);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.menu_shopping_list);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.menu_inventory_list:
                startActivity(new Intent(this, InventoryActivity.class));
                return true;
        }
        return false;
    }

    @Override
    public void onDismiss(@NonNull final DialogInterface dialog) {

    }
}
