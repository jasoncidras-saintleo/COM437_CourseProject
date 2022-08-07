package edu.saintleo.com437.inventorymanager;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.saintleo.com437.inventorymanager.dao.AppDatabase;
import edu.saintleo.com437.inventorymanager.dao.ItemDao;
import edu.saintleo.com437.inventorymanager.dao.adapters.ItemAdapter;
import edu.saintleo.com437.inventorymanager.dao.entities.Item;
import edu.saintleo.com437.inventorymanager.databinding.ActivityInventoryBinding;
import edu.saintleo.com437.inventorymanager.dialog.AddItemDialog;

public class InventoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ItemDao dao;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO: Extract bindings to fragment to be shared across activities
        ActivityInventoryBinding binding = ActivityInventoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar2);
        // binding for adding an item
        binding.fab2.setOnClickListener(view -> {
            DialogFragment addItemDialogFragment = new AddItemDialog();
            addItemDialogFragment.show(getSupportFragmentManager(), "Add Item");
            // add positive button clicks here as we need to refresh the list

        });
        // get the context
        this.context = getApplicationContext();
        // get recycler view
        this.recyclerView = findViewById(R.id.recycler);
        // get db instance
        AppDatabase db = AppDatabase.getInstance(this.context);
        // assign the dao
        this.dao = db.itemDao();
        // init recycler
        initRecycler();

    }

    private void initRecycler() {
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL);
        this.recyclerView.addItemDecoration(dividerItemDecoration);
        // get list of items
        List<Item> items = this.dao.getAll();
        // initialize adapter
        ItemAdapter itemAdapter = new ItemAdapter(items, this.context);
        this.recyclerView.setAdapter(itemAdapter);
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
}
