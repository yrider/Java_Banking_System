## Java Banking System

Simple and robust banking system built in java utilising test-driven development and a MySQL database. 

**Functionality includes:**
1. New account creation.
2. Login user authentication.
3. Withdrawals.
4. Deposits.
5. Display of personal, account and transaction information through SQL database queries.

Please note the program will not run as the database variables have been set to null before pushing to Github. To run it, please create a MySQL database and change the url, username and password variables in src/Database.java. 

### Example Screenshots

**Creating an account:**

![creating an account](readmephotos/create_acc.jpeg)

**Login in authentication:**

ID does not exist: 

![invalid id](readmephotos/invalid_id.jpeg)

ID exists but incorrect password entered:

![invalid password](readmephotos/pass_attempts.jpeg)

**Deposit funds:**

![deposit funds](readmephotos/deposit.jpeg)

**Withdraw funds:**

![withdraw funds](readmephotos/withdrawal.jpeg)

**Check balance:**

![check balance](readmephotos/acc_bal.jpeg)

**Previous transactions:**

![previous transactions](readmephotos/prev_trans.jpeg)

**Personal information:**

![get personal information](readmephotos/personal_info.jpeg)