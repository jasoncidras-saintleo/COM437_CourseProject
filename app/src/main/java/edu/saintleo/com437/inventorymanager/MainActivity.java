package edu.saintleo.com437.inventorymanager;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.DialogFragment;

import edu.saintleo.com437.inventorymanager.databinding.ActivityMainBinding;
import edu.saintleo.com437.inventorymanager.dialog.AddItemDialog;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnFilterCold, btnFilterPantry, btnFilterHousehold,
        btnViewInventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar1);

        binding.fab1.setOnClickListener(view -> {
            DialogFragment addItemDialogFragment = new AddItemDialog();
            addItemDialogFragment.show(getSupportFragmentManager(), "Add Item");
        });
        // grab all buttons
        this.btnFilterCold = findViewById(R.id.btn_filter_cold);
        this.btnFilterCold.setOnClickListener(this::filterOnCold);

        this.btnFilterPantry = findViewById(R.id.btn_filter_pantry);
        this.btnFilterPantry.setOnClickListener(this::filterOnPantry);

        this.btnFilterHousehold = findViewById(R.id.btn_filter_household);
        this.btnFilterHousehold.setOnClickListener(this::filterOnHouseHold);

        this.btnViewInventory = findViewById(R.id.btn_view_inventory);
        this.btnViewInventory.setOnClickListener(this::viewInventory);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
        intent.putExtra("categoryId", 0);
        startActivity(intent);
    }

    private void filterOnPantry(View view) {
        Intent intent = new Intent(this, InventoryActivity.class);
        intent.putExtra("categoryId", 1);
        startActivity(intent);
    }

    private void filterOnHouseHold(View view) {
        Intent intent = new Intent(this, InventoryActivity.class);
        intent.putExtra("categoryId", 2);
        startActivity(intent);
    }

    private void viewInventory(View view) {
        Intent intent = new Intent(this, InventoryActivity.class);
        startActivity(intent);
    }
}