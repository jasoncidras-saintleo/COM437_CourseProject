package edu.saintleo.com437.inventorymanager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import edu.saintleo.com437.inventorymanager.dao.AppDatabase;
import edu.saintleo.com437.inventorymanager.dao.ShoppingListDao;
import edu.saintleo.com437.inventorymanager.dao.adapters.HeaderAdapter;
import edu.saintleo.com437.inventorymanager.dao.adapters.ShoppingListAdapter;
import edu.saintleo.com437.inventorymanager.dao.entities.ShoppingList;
import edu.saintleo.com437.inventorymanager.databinding.ActivityShoppinglistsBinding;

public class ShoppingListActivity extends AppCompatActivity implements DialogInterface.OnDismissListener, NavigationBarView.OnItemSelectedListener {

    private Context context;
    private RecyclerView recyclerView;
    private ShoppingListAdapter adapter;
    private HeaderAdapter headerAdapter;
    private ShoppingListDao dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityShoppinglistsBinding binding = ActivityShoppinglistsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_bar_shopping);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.menu_shopping_list);

        this.context = getApplicationContext();
        // get recycler view
        this.recyclerView = findViewById(R.id.shopping_list_recycler);
        // get db instance
        AppDatabase db = AppDatabase.getInstance(this.context);
        // assign the dao
        this.dao = db.shoppingListDao();
        this.initHeaderAdapter();
        this.initItemAdapter();
        // init recycler
        initRecycler();
    }

    /**
     * Initializes the header to be seen above the recycler
     */
    private void initHeaderAdapter() {
        this.headerAdapter = new HeaderAdapter("Your Shopping Lists", this.context, false, null);
    }

    /**
     * Initializes the items to be seen in the recycler
     */
    private void initItemAdapter() {
        // get list of items
        List<ShoppingList> shoppingLists = this.dao.getAll();
        // initialize adapter
        this.adapter = new ShoppingListAdapter(shoppingLists, this.context, this);
    }

    /**
     * Initializes the recycler
     */
    private void initRecycler() {
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL);
        this.recyclerView.addItemDecoration(dividerItemDecoration);
        this.setRecycler();
    }

    /**
     * Sets the recycler with items
     */
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
            case R.id.menu_inventory_list:
                startActivity(new Intent(this, InventoryActivity.class));
                return true;
        }
        return false;
    }

    @Override
    public void onDismiss(@NonNull final DialogInterface dialog) {
        // re-fetch from the database
        this.initItemAdapter();
        // set the recycler view
        this.setRecycler();
    }
}
