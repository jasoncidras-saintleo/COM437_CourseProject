package edu.saintleo.com437.inventorymanager;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.DialogFragment;

import edu.saintleo.com437.inventorymanager.constants.Strings;
import edu.saintleo.com437.inventorymanager.databinding.ActivityMainBinding;
import edu.saintleo.com437.inventorymanager.dialog.AddItemDialog;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_bar_main);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.menu_home);

        binding.fab1.setOnClickListener(view -> {
            DialogFragment addItemDialogFragment = new AddItemDialog();
            addItemDialogFragment.show(getSupportFragmentManager(), AddItemDialog.TAG);
        });
        // grab all buttons
        Button btnFilterCold = findViewById(R.id.btn_filter_cold);
        btnFilterCold.setOnClickListener(this::filterOnCold);

        Button btnFilterPantry = findViewById(R.id.btn_filter_pantry);
        btnFilterPantry.setOnClickListener(this::filterOnPantry);

        Button btnFilterHousehold = findViewById(R.id.btn_filter_household);
        btnFilterHousehold.setOnClickListener(this::filterOnHouseHold);

        Button btnViewInventory = findViewById(R.id.btn_view_inventory);
        btnViewInventory.setOnClickListener(this::viewInventory);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_inventory_list:
                startActivity(new Intent(this, InventoryActivity.class));
                return true;
            case R.id.menu_shopping_list:
                startActivity(new Intent(this, ShoppingListActivity.class));
                return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * Navigation to Inventory View
     */
    private void filterOnCold(View view) {
        Intent intent = new Intent(this, InventoryActivity.class);
        intent.putExtra(Strings.CATEGORY_ID, 0);
        startActivity(intent);
    }

    private void filterOnPantry(View view) {
        Intent intent = new Intent(this, InventoryActivity.class);
        intent.putExtra(Strings.CATEGORY_ID, 1);
        startActivity(intent);
    }

    private void filterOnHouseHold(View view) {
        Intent intent = new Intent(this, InventoryActivity.class);
        intent.putExtra(Strings.CATEGORY_ID, 2);
        startActivity(intent);
    }

    private void viewInventory(View view) {
        Intent intent = new Intent(this, InventoryActivity.class);
        startActivity(intent);
    }
}