# My Inventory app
Saint Leo University Course Project - COM-437

# Background
Inventory management can be a daunting task for anyone. Keeping track of what food you have in your pantry or cleaning supplies can be exhausting. It is tough to remember what items you need to put on a shopping list.

# Purpose
My Inventory is a mobile application that can help manage with the issues above. Whether you are keeping track of refrigerated items, nonperishables in the pantry or other household supplies, My Inventory will allow you to keep track of what you are running low on. With a click of a button, you can add them to a Shopping List for future use. Each Shopping List will act as a to-do list so you never forget an item.

# Platform
Android OS: 4.4+

# Front/Back End Support
Frontend: Android XML

Backend: Java

# Functionality
* Creating a new Inventory
  * Adding a new Item
    * Assigning a category to item
    * Assigning a name to item
* Viewing an Inventory
  * Filter by category or name
  * Add a new Item
* Creating a new Shopping List
  * On the Inventory View, click on "Add to List" on the item
    * Select a Shopping List to add the item to or create a new one
* Manage Shopping Lists
  * While shopping, check off an item by clicking the checkbox next to the item
  * When all items are checked, asks the user to delete the list or uncheck all items (to re-use the list)
* All data will be stored on the device

# Change Log
* July 10, 2022 - Version 1 - Initial Commit
* July 16, 2022 - Version 1 - First commit: added main activity, filter, inventory fragment, and add item dialog fragment
* July 20, 2022 - Version 1 - Checked in prototype/mockups
* July 31, 2022 - Version 1 - Updated main activity to reflect mockups. Updated add item dialog to include a category
* Aug 7, 2022 - Version 1 - Removed navigation fragments. Updated Inventory to be an activity. Now using room database.
* Aug 19, 2022 - Version 1 - Fixed bug where clicking on filter button didn't filter inventory. Fixed bug where adding an item didn't add to inventory. Added remove item dialog.
* Aug 20, 2022 - Version 1 - Added Shopping List activity. Added Remove Shopping List dialog and Add item to list dialog. Added bottom navigation to switch between activities.
* Aug 21, 2022 - Version 1 - Added Shopping Items activity. Added Remove Item form Shopping List dialog. Added new icons for adding/removing.
* Aug 27, 2022 - Version 1 - Added filter on Inventory activity.

# Upcoming changes
* Features
  * Shopping Lists
    * [x] Add Shopping List activity
    * [x] Add ability to mark item as 'done'
    * [x] Add ability to remove an item from a list
  * Navigation
    * [x] Add bottom navigation to switch between inventory and shopping lists
  * Inventory
    * [x] Add ability to add item to a shopping list
    * [x] Add ability to view all items when a filter is applied
    * [x] Add ability to filter when viewing all items
* Bugs
  * Start page
    * ~~Clicking on a filter button doesn't actually filter on the inventory list~~
    * ~~Adding a new item doesn't update the inventory list~~
    * Using the bottom navigation doesn't correctly show the selected screen.

