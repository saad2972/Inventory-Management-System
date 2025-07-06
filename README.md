📦 Inventory Management System (Java, CLI)
A basic command-line inventory management system built in Java using core DSA and file handling concepts.

🔧 Features
Add, delete, update, and display products from file

Search products by category

Sort inventory by category or pricing

File-based storage using .txt files

Admin-only access (reads admin.txt for authentication)

Robust exception handling for user input

🗂 Structure
Main.java: Program entry point with admin login

Product.java: Product class with attributes and methods

Database.java: Handles file operations (add, update, delete, etc.)

Warehouse.java: Logic for sorting/searching

admin.txt: Stores admin credentials

inventory.txt: Stores inventory data

🔐 How It Works
First, prompts admin username & password from admin.txt

Once authenticated, allows access to manage inventory

Simple terminal interface for interaction

