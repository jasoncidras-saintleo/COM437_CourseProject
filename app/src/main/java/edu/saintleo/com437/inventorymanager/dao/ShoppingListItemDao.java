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

    @Query("SELECT a.id, a.shoppingListId, a.itemId, a.isDone, b.name as 'itemName' FROM shoppingListItem AS a " +
            "INNER JOIN item AS b ON a.itemId = b.id " +
            "WHERE a.shoppingListId = :shoppingListId")
    List<ShoppingListItem> getAll(int shoppingListId);

    @Insert
    void insert(ShoppingListItem entity);

    @Update
    void update(ShoppingListItem entity);

    @Delete
    void delete(ShoppingListItem entity);
}
