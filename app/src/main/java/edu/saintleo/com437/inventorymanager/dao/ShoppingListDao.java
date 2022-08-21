package edu.saintleo.com437.inventorymanager.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.saintleo.com437.inventorymanager.dao.entities.ShoppingList;

@Dao
public interface ShoppingListDao {

    @Query("SELECT * FROM shoppingList")
    List<ShoppingList> getAll();

    @Query("SELECT * FROM shoppinglist WHERE id = :id LIMIT 1")
    ShoppingList get(int id);

    @Insert
    long insert(ShoppingList entity);

    @Update
    void update(ShoppingList entity);

    @Delete
    void delete(ShoppingList entity);
}
