package edu.saintleo.com437.inventorymanager.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.saintleo.com437.inventorymanager.dao.entities.Item;

@Dao
public interface ItemDao {

    @Query("SELECT * FROM item")
    List<Item> getAll();

    @Query("SELECT * FROM item WHERE id = :id LIMIT 1")
    Item getItem(int id);

    @Insert
    void insert(Item entity);

    @Update
    void update(Item entity);

    @Delete
    void delete(Item entity);
}
