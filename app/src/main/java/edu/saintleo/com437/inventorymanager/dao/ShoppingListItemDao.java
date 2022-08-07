package edu.saintleo.com437.inventorymanager.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.saintleo.com437.inventorymanager.dao.entities.ShoppingListItem;

@Dao
public interface ShoppingListItemDao {

    @Query("SELECT * FROM shoppingListItem WHERE shoppingListId = :shoppingListId")
    List<ShoppingListItem> getAll(int shoppingListId);

    @Insert
    void insert(ShoppingListItem entity);

    @Update
    void update(ShoppingListItem entity);

    @Delete
    void delete(ShoppingListItem entity);
}
