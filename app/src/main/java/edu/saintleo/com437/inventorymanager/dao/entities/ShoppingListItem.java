package edu.saintleo.com437.inventorymanager.dao.entities;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(foreignKeys = {
    @ForeignKey(entity = ShoppingList.class, parentColumns = "id", childColumns = "shoppingListId", onDelete = ForeignKey.CASCADE),
    @ForeignKey(entity = Item.class, parentColumns = "id", childColumns = "itemId", onDelete = ForeignKey.CASCADE)
})
public class ShoppingListItem implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "shoppingListId")
    public int shoppingListId;

    @ColumnInfo(name = "itemId")
    public int itemId;

    @ColumnInfo(name = "isDone")
    public boolean isDone;

    public String itemName;
}
