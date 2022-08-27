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

import edu.saintleo.com437.inventorymanager.constants.Strings;
import edu.saintleo.com437.inventorymanager.dao.AppDatabase;
import edu.saintleo.com437.inventorymanager.dao.ShoppingListDao;
import edu.saintleo.com437.inventorymanager.dao.ShoppingListItemDao;
import edu.saintleo.com437.inventorymanager.dao.adapters.HeaderAdapter;
import edu.saintleo.com437.inventorymanager.dao.adapters.ShoppingListItemAdapter;
import edu.saintleo.com437.inventorymanager.dao.entities.ShoppingList;
import edu.saintleo.com437.inventorymanager.dao.entities.ShoppingListItem;
import edu.saintleo.com437.inventorymanager.databinding.ActivityShoppinglistItemsBinding;

public class ShoppingListItemActivity extends AppCompatActivity implements DialogInterface.OnDismissListener, NavigationBarView.OnItemSelectedListener {

    private Context context;
    private RecyclerView recyclerView;
    private AppDatabase db;
    private ShoppingListItemDao dao;
    private ShoppingList shoppingList;
    private HeaderAdapter headerAdapter;
    private ShoppingListItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityShoppinglistItemsBinding binding =
                ActivityShoppinglistItemsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int shoppingListId = getIntent().getIntExtra(Strings.SHOPPING_LIST_ID, -1);
        // if the shopping list is -1, then no valid shopping list was passed
        // finish activity to return back to shopping lists
        if (shoppingListId == -1) {
            finish();
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_bar_shopping_items);
        bottomNavigationView.setOnItemSelectedListener(this);

        this.context = getApplicationContext();
        // get recycler view
        this.recyclerView = findViewById(R.id.shopping_list_item_recycler);
        // get db instance
        db = AppDatabase.getInstance(this.context);
        // assign the dao
        this.dao = db.shoppingListItemDao();
        // init shopping list from db
        initShoppingList(shoppingListId);
        // init header adapter
        initHeaderAdapter();
        // init recycler
        initRecycler();
    }

    private void initShoppingList(int shoppingListId) {
        ShoppingListDao shoppingListDao = db.shoppingListDao();
        // get the shopping list form db
        this.shoppingList = shoppingListDao.get(shoppingListId);
    }

    private void initHeaderAdapter() {
        // init header adapter
        this.headerAdapter = new HeaderAdapter(shoppingList.name, this.context, false, null);
    }

    private void initItemAdapter() {
        // get lists of shopping lists
        List<ShoppingListItem> shoppingLists = this.dao.getAll(this.shoppingList.id);
        // initialize adapter
        this.adapter = new ShoppingListItemAdapter(shoppingLists, this.context, this, this.dao);
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
        ConcatAdapter concatAdapter = new ConcatAdapter(this.headerAdapter, this.adapter);
        // concat header and adapter
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
            case R.id.menu_shopping_list:
                startActivity(new Intent(this, ShoppingListActivity.class));
                return true;
        }
        return false;
    }

    @Override
    public void onDismiss(@NonNull final DialogInterface dialog) {
        // set the recycler to adapter
        this.initItemAdapter();
        this.setRecycler();
    }
}
