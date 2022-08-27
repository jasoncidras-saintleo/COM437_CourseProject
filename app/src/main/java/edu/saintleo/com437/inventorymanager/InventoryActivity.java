package edu.saintleo.com437.inventorymanager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import edu.saintleo.com437.inventorymanager.constants.Strings;
import edu.saintleo.com437.inventorymanager.dao.AppDatabase;
import edu.saintleo.com437.inventorymanager.dao.ItemDao;
import edu.saintleo.com437.inventorymanager.dao.adapters.HeaderAdapter;
import edu.saintleo.com437.inventorymanager.dao.adapters.ItemAdapter;
import edu.saintleo.com437.inventorymanager.dao.entities.Item;
import edu.saintleo.com437.inventorymanager.databinding.ActivityInventoryBinding;
import edu.saintleo.com437.inventorymanager.dialog.AddItemDialog;

public class InventoryActivity extends AppCompatActivity implements
        DialogInterface.OnDismissListener,
        NavigationBarView.OnItemSelectedListener,
        AdapterView.OnItemSelectedListener {
    private RecyclerView recyclerView;
    private ItemDao dao;
    private Context context;
    private Integer selectedCategoryId;
    private ItemAdapter adapter;
    private HeaderAdapter headerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO: Extract bindings to fragment to be shared across activities
        ActivityInventoryBinding binding = ActivityInventoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // binding for adding an item
        binding.fab2.setOnClickListener(view -> {
            DialogFragment addItemDialogFragment = new AddItemDialog();
            addItemDialogFragment.show(getSupportFragmentManager(), AddItemDialog.TAG);
            // add positive button clicks here as we need to refresh the list
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_bar_inventory);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelected(true);
        bottomNavigationView.setSelectedItemId(R.id.menu_inventory_list);
        // get selected category ID from intent
        this.selectedCategoryId = getIntent().getIntExtra(Strings.CATEGORY_ID, -1);
        // if the category is -1, that's the default and set the selected category to null
        this.selectedCategoryId = this.selectedCategoryId == -1 ? null : this.selectedCategoryId;
        // get the context
        this.context = getApplicationContext();
        // get recycler view
        this.recyclerView = findViewById(R.id.recycler);
        // get db instance
        AppDatabase db = AppDatabase.getInstance(this.context);
        // assign the dao
        this.dao = db.itemDao();
        // init header adapter
        initHeaderAdapter();
        // init recycler
        initRecycler();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // if position is 0, then it's the placeholder
        if (pos == 0) {
            return;
        }
        //check to see if the user selected 1 (view all)
        // if user selected any other filter, then take the position - 2 to
        // correspond to the appropriate category id.
        pos = pos == 1 ? -1 : pos - 2;
        selectedCategoryId = pos == -1 ? null : pos;
        initItemAdapter();
        initRecycler();
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void initHeaderAdapter() {
        this.headerAdapter = new HeaderAdapter("Your Inventory", this.context, true, this);
    }
    private void initItemAdapter() {
        // get list of items
        List<Item> items = this.selectedCategoryId != null
                ? this.dao.getAll(this.selectedCategoryId)
                : this.dao.getAll();
        // initialize adapter
        this.adapter = new ItemAdapter(items, this.context, this);
    }

    private void initRecycler() {
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL);
        this.recyclerView.addItemDecoration(dividerItemDecoration);
        this.initItemAdapter();
        this.setRecycler();
    }

    private void setRecycler() {
        ConcatAdapter concatAdapter = new ConcatAdapter(
          this.headerAdapter,
          this.adapter
        );
        this.recyclerView.setAdapter(concatAdapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.menu_shopping_list:
                startActivity(new Intent(this, ShoppingListActivity.class));
                return true;
        }
        return false;
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        this.initItemAdapter();
        this.setRecycler();
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
}
