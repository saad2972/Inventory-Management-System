# Inventory Management System (Java CLI)

A simple command-line Inventory Management System built using Java. It demonstrates core DSA and file handling concepts.

## Features

- Add, delete, update, and display products from a file  
- Search products by category  
- Sort inventory by category or pricing  
- File-based storage using `inventory.txt`  
- Admin-only access through `admin.txt`  
- Exception handling for invalid inputs  

## Project Files

- `Main.java` - Entry point, handles admin login  
- `Product.java` - Defines product attributes  
- `Database.java` - Handles file I/O operations  
- `Warehouse.java` - Sorting and searching logic  
- `admin.txt` - Stores admin credentials  
- `inventory.txt` - Stores inventory data  

## How It Works

1. Program starts by asking for admin name and password (from `admin.txt`)
2. On successful login, admin can manage the inventory
3. All product data is saved and updated in `inventory.txt`
