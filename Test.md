# Test 1: User Login
## Steps:
1.User launches application. //登陆系统
2.User selects the `username` textbox. //选择用户名输入框
3.User enters username via the keyboard. //输入用户名
4.User selects the `password` textbox. //选择密码输入框
5.User enter password via the keyboard. //输入密码
6.User selects the "Log in" button. //选择登陆

## Expected result:
- Application verifies the user's username and password and loads their homepage automatically.
- Don't show any Exception.

## Test Status:
- No Passed


# Test 2: User create an account
## Steps:
1.User launches application.
2.User select the register textbox.
3.User selects the `full name` textbox.
4.User enters full name via the keyboard.
5.User selects the `username` textbox.
6.User enter username via the keyboard.
7.User selects the `Password` textbox.
8.User enter password via the keyboard.
9.User selects the `Phone Number` textbox.
10.User enter Phone Number via the keyboard.
11.User selects the `Address` textbox.
12.User enter Address via the keyboard.
13.User selects the `Account Type` textbox.
14.User enter Account Type via the keyboard.
15.User selects the `Create an Account` button.
16.User select the `search` button.

## Expected result:
- Application create new User's information and loads their homepage automatically.
- Don't show any Exception.
- Don't show any exception when enter `1234` in Phone Number.
- Don't show any exception when enter `ab,cd.ac.ad!` in address
- Don't show any exception when enter `seller` and  `customer` in Account type.

## Test Status:
- Not Passed

/////////////// SELLER //////////////
# Test 3: Seller Function 1 -- Message Customer for search
## Steps:
1.User launches application.
2.User selects the `username` textbox.
3.User enters username via the keyboard.
4.User selects the `password` textbox.
5.User enter password via the keyboard.
6.User selects the "Log in" button.
7.User selects the `action` textbox.
8.User enter `1` action via the keyboard.
9.User select the `name of the customer` textbox.
10.User enter name of the customer via the keyboard.
11.User select the `search` button.
12.if result exist ---
- finished
  11.if result don't exist ---
- User selects the `Enter 1 to search again, or 2 to quit to main menu:` textbox.
- User enter choice via the keyboard.

## Expected result:
- Application loads their search results
- Don't show any exception when the result does not exist
- Don't show any exception when the choice is ( 3 or No or Yes )

## Test Status:
- Not Passed


# Test 4: Seller function 2- messageCustomerList
## Steps:
1.User launches application.
2.User selects the `username` textbox.
3.User enters username via the keyboard.
4.User selects the `password` textbox.
5.User enter password via the keyboard.
6.User selects the `Log in` button.
7.User selects the `action` textbox.
8.User enter `2` action via the keyboard.
9.User views the `The following is a list of customers that you can message:` button.
10.User selects `Please enter the number of a customer:` textbox
11.User enter choice via the keyboard.

## Expected result:
- The application displays the Customer they have chosen.
- Don't show any exception when choice over the customers' size or choice < 1

## Test Status:
- Not Passed


# Test 5: Seller function - create a store
## Steps:
1.User launches application.
2.User selects the `username` textbox.
3.User enters username via the keyboard.
4.User selects the `password` textbox.
5.User enter password via the keyboard.
6.User selects the `Log in` button.
7.User selects the `action` textbox.
8.User enter `3` action via the keyboard.
9.User selects the `store name` textbox.
10.User enter store name via the keyboard.
11.User selects the `store type` textbox.
12.User enter store type via the keyboard.
13.User selects the `store address` textbox.
14.User enter store address via the keyboard.
15.User selects the `submit` button

## Expected result:
- The `stores.txt` is existed.
- The `stores.txt` has this store's information(store name, store type, store address)
- Don't show any exception when enter `ab,ac.ad!ae%` in store address
- Don't show any exception

## Test Status:
- Not Passed


# Test 6: Seller function - Block User
## Steps:
1.User launches application.
2.User selects the `username` textbox.
3.User enters username via the keyboard.
4.User selects the `password` textbox.
5.User enter password via the keyboard.
6.User selects the `Log in` button.
7.User selects the `action` textbox.
8.User enter `4` action via the keyboard.
9.User selects the `name of the user you want to block` textbox.
10.User enter name via the keyboard.

## Expected result:
- The `blocked.txt` is existed and has this User's block name.
- It should not show this name for current user when he wants to search blocker again.
- Don't show any exception.
- Don't show any exception when name of block does not exist in the user list.
## Test Status:
- Not Passed


# Test 7: Seller function - Ghost User (set Invisible)
## Steps:
1.User launches application.
2.User selects the `username` textbox.
3.User enters username via the keyboard.
4.User selects the `password` textbox.
5.User enter password via the keyboard.
6.User selects the `Log in` button.
7.User selects the `action` textbox.
8.User enter `5` action via the keyboard.
9.User selects the `name of the user you want to set yourself invisible` textbox.
10.User enter name via the keyboard.

## Expected result:
- The `invisible.txt` is existed and has this User's block name.
- It should not show this name for current user when he wants to search name of invisible again.
- Don't show any exception.
- Don't show any exception when name of invisible does not exist in the user list.

## Test Status:
- Not Passed


# Test 8: Seller function - Modify User
## Steps:
1.User launches application.
2.User selects the `username` textbox.
3.User enters username via the keyboard.
4.User selects the `password` textbox.
5.User enter password via the keyboard.
6.User selects the `Log in` button.
7.User selects the `action` textbox.
8.User enter `6` action via the keyboard.
7.User selects the `modify user` button
8.User selects the `name` textbox.
9.User enter name via the keyboard.
10.User selects the `phone number` textbox.
11.User enter phone number via the keyboard.
12.User selects the `address` textbox.
13.User enter address via the keyboard.

## Expected result:
- The application shows that `User information updated.`
- Don't show any exception
- Don't show any exception when enter `1234` in Phone Number.
- Don't show any exception when enter `ab,cd.ac.ad!` in address.
- Ask user to enter again when enter `1234` in Phone Number or enter `ab,cd.ac.ad!` in address.

## Test Status:
- Not Passed


# Test 9: Seller function - Delete Account
## Steps:
1.User launches application.
2.User selects the `username` textbox.
3.User enters username via the keyboard.
4.User selects the `password` textbox.
5.User enter password via the keyboard.
6.User selects the `Log in` button.
7.User selects the `action` textbox.
8.User enter `7` via the keyboard.
9.User selects the `choice` textbox.
10.User enter choice via the keyboard.

## Expected result:
1.if choice is y
- The `user.txt` is not existed current user's information.
  2.if choice is n
- The application loads the menu
  3.if choice is not y and is not n
- Don't show any exception and ask user to enter again.

## Test Status:
- Not Passed


# Test 10: Seller function - Logout
## Steps:
1.User launches application.
2.User selects the `username` textbox.
3.User enters username via the keyboard.
4.User selects the `password` textbox.
5.User enter password via the keyboard.
6.User selects the `Log in` button.
7.User selects the `action` textbox.
8.User enter `8` action via the keyboard.

## Expected result:
- The application print the `logging out` and close the application
- Do not show any exception.

## Test Status:
- Not Passed

/////////////// MESSAGE //////////////
# Test 11: Message 1 - Send new message
## Steps:
1.User launches application.
2.User selects the `username` textbox.
3.User enters username via the keyboard.
4.User selects the `password` textbox.
5.User enter password via the keyboard.
6.User selects the `Log in` button.
7.User selects the `action` textbox.
8.User selects the person to send the message to
9.User select `choice` textbox.
10.User enter `1` via the keyboard.
11.User select the `message` textbox
12.User enter the message via the keyboard.

## Expected result:
- File should exist. ((current user's name + receiver's name).txt)
- This file should have message information, current user, receiver, current date.
- Do not show any exception.

## Test Status:
- Not Passed


# Test 12:  Message 2 - view message history
## Steps:
1.User launches application.
2.User selects the `username` textbox.
3.User enters username via the keyboard.
4.User selects the `password` textbox.
5.User enter password via the keyboard.
6.User selects the `Log in` button.
7.User selects the `action` textbox.
8.User selects the person to send the message to
9.User select `choice` textbox.
10.User enter `2` via the keyboard.

## Expected result:
- The message history should be load.
- Do not show any exception

## Test Status:
- Not Passed


# Test 13:  Message 3 - edit a message
## Steps:
1.User launches application.
2.User selects the `username` textbox.
3.User enters username via the keyboard.
4.User selects the `password` textbox.
5.User enter password via the keyboard.
6.User selects the `Log in` button.
7.User selects the `action` textbox.
8.User selects the person to send the message to
9.User select `choice` textbox.
10.User enter `3` via the keyboard.
11.User enters the new message via the keyboard.
12.User selects the Save button.

## Expected result:
- The application successfully edits the selected message.
- The message.txt is updated with the new content.
- The user is shown a message confirming the successful update.
- Do not show any exception.

## Test Status:
- Not Passed


# Test 14:  Message 4 - delete message
## Steps:
1.User launches application.
2.User selects the `username` textbox.
3.User enters username via the keyboard.
4.User selects the `password` textbox.
5.User enter password via the keyboard.
6.User selects the `Log in` button.
7.User selects the `action` textbox.
8.User selects the person to send the message to
9.User select `choice` textbox.
10.User enter `4` via the keyboard.
11.User confirms the deletion by selecting the Yes button.

## Expected result:
- The application successfully deletes the selected message.
- The message.txt file is updated with the deleted message removed.
- The user is shown a message confirming the successful deletion.
- Do not show any exception.
-
## Test Status:
- Not Passed


# Test 15: Message 5 -  main menu
## Steps:
1.User launches application.
2.User selects the `username` textbox.
3.User enters username via the keyboard.
4.User selects the `password` textbox.
5.User enter password via the keyboard.
6.User selects the `Log in` button.
7.User selects the `action` textbox.
8.User selects the person to send the message to
9.User select `choice` textbox.
10.User enter `0` via the keyboard.
11.Return to main menu

## Expected result:
- The application return to the main menu
- Do not show any exception

## Test Status:
- Not Passed


/////////////// CUSTOMER //////////////
# Test 16: Customer function 1 - Message Seller from list of Stores
## Steps:
1.User launches application.
2.User selects the `username` textbox.
3.User enters username via the keyboard.
4.User selects the `password` textbox.
5.User enter password via the keyboard.
6.User selects the "Log in" button.
7.User selects the `action` textbox.
8.User enter `1` action via the keyboard.
9.User select the `name of the customer` textbox.
10.User enter name of the customer via the keyboard.
11.User select the `search` button.
12.if result exist ---
- finished
  11.if result don't exist ---
- User selects the `Enter 1 to search again, or 2 to quit to main menu:` textbox.
- User enter choice via the keyboard.

## Expected result:
- Application loads their search results
- Don't show any exception when the result does not exist
- Don't show any exception when the choice is ( 3 or No or Yes )

## Test Status:
- Not passed

# Test 17: Customer function 2 - Message Seller from list of Stores
## Steps:
1.User launches application.
2.User selects the `username` textbox.
3.User enters username via the keyboard.
4.User selects the `password` textbox.
5.User enter password via the keyboard.
6.User selects the `Log in` button.
7.User selects the `action` textbox.
8.User enter `2` action via the keyboard.
9.User views the `The following is a list of customers that you can message:` button.
10.User selects `Please enter the number of a customer:` textbox
11.User enter choice via the keyboard.

## Expected result:
- The application displays the Customer they have chosen.
- Don't show any exception when choice over the customers' size or choice < 1

## Test Status:
- Not Passed


# Test 18: Customer function 3 - Block User
## Steps:
1.User launches application.
2.User selects the `username` textbox.
3.User enters username via the keyboard.
4.User selects the `password` textbox.
5.User enter password via the keyboard.
6.User selects the `Log in` button.
7.User selects the `action` textbox.
8.User enter `4` action via the keyboard.
9.User selects the `name of the user you want to block` textbox.
10.User enter name via the keyboard.

## Expected result:
- The `blocked.txt` is existed and has this User's block name.
- It should not show this name for current user when he wants to search blocker again.
- Don't show any exception.
- Don't show any exception when name of block does not exist in the user list.
## Test Status:
- Not Passed


# Test 19: Customer function 4 - Ghost User (set Invisible)
## Steps:
1.User launches application.
2.User selects the `username` textbox.
3.User enters username via the keyboard.
4.User selects the `password` textbox.
5.User enter password via the keyboard.
6.User selects the `Log in` button.
7.User selects the `action` textbox.
8.User enter `5` action via the keyboard.
9.User selects the `name of the user you want to set yourself invisible` textbox.
10.User enter name via the keyboard.

## Expected result:
- The `invisible.txt` is existed and has this User's block name.
- It should not show this name for current user when he wants to search name of invisible again.
- Don't show any exception.
- Don't show any exception when name of invisible does not exist in the user list.

## Test Status:
- Not Passed


# Test 20: Customer function 5 - Modify User
## Steps:
1.User launches application.
2.User selects the `username` textbox.
3.User enters username via the keyboard.
4.User selects the `password` textbox.
5.User enter password via the keyboard.
6.User selects the `Log in` button.
7.User selects the `action` textbox.
8.User enter `6` action via the keyboard.
7.User selects the `modify user` button
8.User selects the `name` textbox.
9.User enter name via the keyboard.
10.User selects the `phone number` textbox.
11.User enter phone number via the keyboard.
12.User selects the `address` textbox.
13.User enter address via the keyboard.

## Expected result:
- The application shows that `User information updated.`
- Don't show any exception
- Don't show any exception when enter `1234` in Phone Number.
- Don't show any exception when enter `ab,cd.ac.ad!` in address.
- Ask user to enter again when enter `1234` in Phone Number or enter `ab,cd.ac.ad!` in address.

## Test Status:
- Not Passed

# Test 21: Customer function 6 - Delete User
## Steps:
1.User launches application.
2.User selects the `username` textbox.
3.User enters username via the keyboard.
4.User selects the `password` textbox.
5.User enter password via the keyboard.
6.User selects the `Log in` button.
7.User selects the `action` textbox.
8.User enter `7` via the keyboard.
9.User selects the `choice` textbox.
10.User enter choice via the keyboard.

## Expected result:
1.if choice is y
- The `user.txt` is not existed current user's information.
  2.if choice is n
- The application loads the menu
  3.if choice is not y and is not n
- Don't show any exception and ask user to enter again.

## Test Status:
- Not Passed


# Test 22: Customer function 7 - Logout
## Steps:
1.User launches application.
2.User selects the `username` textbox.
3.User enters username via the keyboard.
4.User selects the `password` textbox.
5.User enter password via the keyboard.
6.User selects the `Log in` button.
7.User selects the `action` textbox.
8.User enter `8` action via the keyboard.

## Expected result:
- The application print the `logging out` and close the application
- Do not show any exception.

## Test Status:
- Not Passed