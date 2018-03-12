# 380 Project
Sales and Inventory Tool

## Names
Jondn Tryniski,
Thomas Hanley,
Josh Meritt.

### Project Description
The system created will be an inventory and sales tool. The tool will have users which allow us to login and track individual sales. The tool will also allow for us to track inventory. It will allow us to input what inventory we have through commands built into the tool. We will be able to input a command to check and see general inventory as well as what is currently low on inventory. We want to be able to look at the inventory by the department type. The tool will also go through and give suggestions on what to order based on what is selling well, and current stock levels. The tool will be able to give daily and weekly reports of what is sold, overall sales, as well as individual users sale totals. If the system is shut down, the tool should have saved the information and be able to load if after turning the system on again. The tool will also allow for general editing of price of inventory so that changes may be made over time. The tool should allow different employees to have different levels of access in the inventory.

### System Requirements
#### Requirement Scale Priority:1-5 1-Highest, 5-Lowest
Requirement 1: P1 The system will track sales totals.

Requirement 2: P3 The system will allow for creation of users.

Requirement 3: P3 The user will be able to authenticate into the system via a password.

Requirement 4: P4 The system will track individuals sales totals.

Requirement 5: P4 The system will alert when running low on certain products once a threshold is met.

Requirement 6: P5 The system will take into account current sales to help predict when to order products.

Requirement 7: P2 The system will have a command for showing current stock levels.

Requirement 8: P2 The system will allow for adding prices to items in inventory.

Requirement 9: P2 The system will allow for editing of item's prices in inventory.

Requirement 10: P5 The system will have daily and weekly reports of sales.

Requirement 11: P3 The system will be organized so that one can see inventory in different departments.

Requirement 12: P3 The system will be able to be shut down and loaded again with having the same information as before it was shut down.

Requirement 13: P4 The system will be able to calculate the net profit of the inventory daily and weekly. 

Requirement 14: P4 The system will allow different levels of access depending on your position in the company.

Requirement 15: P2 The system will allow users to add and remove items manually.


### User Stories

User Story 1: As a manager of a store, I want to be able to load in my inventory after shutting the system down. 
Time: 8

User Story 2: As a manager of a store, I want to be able to add new users so that I can have more cashiers.
Time: 5

User Story 3: As a manager of a store, I want to be able to see what items are popular so that I can restock accordingly.
Time: 4

User Story 4: As a manager of a store, I want to be able to see what is sold daily and weekly in order to see what is selling.
Time: 5

User Story 5: As a manager of a store, I want to be able to count the inventory so that I can see what needs to be restocked. 
Time: 4

User Story 6: As a manager of a store, I want to be able to see how much money was made each day and each week.
Time: 5

User Story 7: As a manager of a store, I need to see how much my employees are selling in order to see how productive they are.
Time: 6

User Story 8: As a manager of a store, I want to have restrictions on who has access to certain parts of the inventory.
Time: 4

User Story 9: As a cashier, I want to be able to log into my personal profile so that I can keep track of what I sell.
Time: 5

User Story 10: As a cashier, I want to be able to cash people out so that I can make sales and keep my job. 
Time: 4

Use Cases
Case 1: Load in Inventory
Case 2: Add Users
Case 3: Save Inventory
Case 4: See Inventory
Case 5: See the Distribution of Items sold
Case 6: Have a rating of how much employees are selling
Case 7: Sell items
Case 8: Return items
Case 9: Log in
Case 10: Restrictions
Case 11: See Profits

User Story|UC1|UC2|UC3|UC4|UC5|UC6|UC7|UC8|UC9|UC10|UC11|
	1	  | X |   |   |   |   |   |   |   |   |    |    |
	2	  |   | X |   |   |   |   | X |   |   |    |    |
	3	  | X |   |   |   | X |   |   |   |   |    |    |
	4	  | X |   |   | X | X |   | X |   |   |    |    |
	5	  | X |   | X | X |   |   | X | X |   |    |    |
	6	  |   |   |   |   |   |   |   |   |   |    | X  |
	7	  |   | X |   |   |   | X | X |   | X | X  |    |
	8	  |   |   |   |   |   |   |   |   |   | X  |    |
	9	  |   | X |   |   |   |   |   |   | X | X  |    |
	10	  |   | X |   |   |   |   | X |   | X |    |    |

	Use Case 1: