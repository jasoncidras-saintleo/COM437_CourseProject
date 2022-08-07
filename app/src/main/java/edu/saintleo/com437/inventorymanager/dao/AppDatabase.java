package edu.saintleo.com437.inventorymanager.dao;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import edu.saintleo.com437.inventorymanager.dao.entities.Item;
import edu.saintleo.com437.inventorymanager.dao.entities.ShoppingList;
import edu.saintleo.com437.inventorymanager.dao.entities.ShoppingListItem;

@Database(entities = {Item.class, ShoppingList.class, ShoppingListItem.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ItemDao itemDao();
    public abstract ShoppingListDao shoppingListDao();
    public abstract ShoppingListItemDao shoppingListItemDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context ctx) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(ctx.getApplicationContext(), AppDatabase.class, "inventory-db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
