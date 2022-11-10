package ui;

import model.Account;
import model.SavingGoal;
import model.Transaction;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Money Manager Application
 * Code mostly referenced from ca.ubc.cpsc210.bank.ui.TellerApp
 * Json code implementations were referenced from
 * https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */

public class MoneyManagerApp {
    private static final String JSON_STORE = "./data/accountDetails.json";
    private Account user;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the money manager app application
    public MoneyManagerApp() throws FileNotFoundException {
        user = new Account(0);
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        input.useDelimiter("\n");
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runApp() {
        boolean keepGoing = true;
        String command = null;

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        switch (command) {
            case "w":
                updateWage();
                break;
            case "b":
                editBalance();
                break;
            case "e":
                editSavingGoals();
                break;
            case "h":
                viewTransactionHistory();
                break;
            case "s":
                saveAccountDetails();
                break;
            case "l":
                loadAccountDetails();
                break;
            default:
                System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nWelcome to the Money Manager App!");
        System.out.println("\nAccount details:");
        System.out.println("\tYour current balance is: $" + user.getBalance());
        System.out.println("\tYou currently have " + user.getSavingGoals().size() + " saving goals");
        System.out.println("\nWhat do you want to do today?");
        System.out.println("\tw -> update my wage");
        System.out.println("\tb -> deposit/withdraw money");
        System.out.println("\te -> edit saving goals");
        System.out.println("\th -> view transaction history");
        System.out.println("\ts -> save account details to file");
        System.out.println("\tl -> load account details saved in file");
        System.out.println("\tq -> quit");
    }

    //
    // MODIFIES: this
    // EFFECTS: deposit/withdraw money, record in transaction history
    private void editBalance() {
        System.out.println("\nType:");
        System.out.println("\td -> deposit money");
        System.out.println("\tw -> withdraw money");
        String command = input.next();
        System.out.print("\nEnter amount (to the nearest integer): $");
        int amount = input.nextInt();

        if (amount >= 0) {
            switch (command) {
                case "d":
                    // add balance to account
                    user.addBalance(amount);

                    // add new transaction to transaction history
                    Transaction deposit = new Transaction(true, amount, "Deposit");
                    user.addTransactions(deposit);

                    break;
                case "w":
                    withdrawBalance(amount);
                    break;
                default:
                    System.out.println("Selection not valid...");
            }
        } else {
            System.out.println("Cannot deposit negative amount...\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: create a new saving goal to the list of saving goals
    private void createSavingGoal() {
        System.out.print("Enter saving goal title (case sensitive): ");
        String goalTitle = input.next();
        System.out.print("\nEnter target (to the nearest integer): $");
        int goalTarget = input.nextInt();

        SavingGoal savingGoal = new SavingGoal(goalTitle, goalTarget);
        user.addSavingGoal(savingGoal);

        System.out.println("\nSuccessfully added a new saving goal!");
    }

    // MODIFIES: this
    // EFFECTS: increase or decrease money in saving goal, increase or decrease target,
    // or remove a saving goal
    private void editSavingGoals() {
        System.out.println("\nType:");
        System.out.println("\ts -> create a saving goal");
        System.out.println("\td -> deposit money");
        System.out.println("\tw -> withdraw money");
        System.out.println("\th -> increase target");
        System.out.println("\tl -> decrease target");
        System.out.println("\tr -> remove a saving goal");
        System.out.println("\tv -> view list of saving goals");
        String command = input.next();

        if (command.equals("s")) {
            createSavingGoal();
        } else if (command.equals("v")) {
            listSavingGoal();
        } else {
            System.out.print("\nEnter saving goal title (case sensitive): ");
            String goalTitle = input.next();

            SavingGoal goal = user.getSavingGoal(goalTitle);

            if (goal != null) {
                executeCommand(command, goal);
            } else {
                System.out.print("No saving goals matched...");
            }
        }

    }

    // MODIFIES: this
    // EFFECTS: lists out user's saving goal titles and status
    private void listSavingGoal() {
        if (!user.getSavingGoals().isEmpty()) {
            System.out.println("Your current saving Goals:");
            for (SavingGoal goal : user.getSavingGoals()) {
                System.out.println("Name: " + goal.getTitle());
                System.out.println("You have saved $" + goal.getMoneySaved() + " out of $" + goal.getTarget());
                System.out.println("----------------------------------------");
            }
        } else {
            System.out.println("You have no saving goals...");
        }
    }

    // MODIFIES: this
    // EFFECTS: execute edit saving goal command
    private void executeCommand(String command, SavingGoal goal) {
        switch (command) {
            case "d":
                depositSaving(goal);
                break;
            case "w":
                withdrawSaving(goal);
                break;
            case "h":
                System.out.print("Enter amount (to the nearest integer): $");
                int amount = input.nextInt();
                goal.increaseTarget(amount);
                break;
            case "l":
                System.out.print("Enter amount (to the nearest integer): $");
                amount = input.nextInt();
                goal.decreaseTarget(amount);
                break;
            case "r":
                user.deleteSavingGoal(goal.getTitle());
                break;
            default:
                System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: withdraw money from saving goal, add amount to balance
    private void withdrawSaving(SavingGoal goal) {
        System.out.print("Enter amount (to the nearest integer): $");
        int amount = input.nextInt();

        if (amount <= goal.getMoneySaved()) {
            goal.withdraw(amount);
            user.addBalance(amount);

            Transaction savingWithdrawal = new Transaction(true,
                    amount, "Transfer from saving goal");
            user.addTransactions(savingWithdrawal);
        } else {
            System.out.println("Insufficient balance in saving goal...");
        }
    }

    // MODIFIES: this
    // EFFECTS: deposit money to saving, subtract amount from balance, record transactions
    // in transaction history
    private void depositSaving(SavingGoal goal) {
        System.out.print("Enter amount (to the nearest integer): $");
        int amount = input.nextInt();

        if (amount <= user.getBalance()) {
            goal.save(amount);
            user.subtractBalance(amount);

            Transaction saving = new Transaction(false, amount, "Deposit to saving goal");
            user.addTransactions(saving);

            int workingHours = Math.round(amount / user.getWage());
            System.out.println("Congratulations, you have saved the equivalent of " + workingHours
                                + " working hours to " + goal.getTitle());
        } else {
            System.out.println("Insufficient balance in Account...");
        }
    }

    // MODIFIES: this
    // EFFECTS: withdraw money from balance, record transaction
    private void withdrawBalance(int amount) {
        if (amount > user.getBalance()) {
            System.out.println("Insufficient balance on account...");
        } else {
            System.out.print("Enter spending category (capitalize first letter): ");
            String category = input.next();

            user.subtractBalance(amount);

            Transaction spending = new Transaction(false, amount, category);
            user.addTransactions(spending);

            int workingHours = Math.round(amount / user.getWage());

            System.out.println("You have spent the equivalent of " + workingHours
                    + " working hours on " + category + ".");
        }
    }

    // MODIFIES: this
    // EFFECTS: update user's wage
    private void updateWage() {
        System.out.print("Enter your hourly wage (to the nearest integer): $");
        int wage = input.nextInt();

        if (wage >= Account.MINIMUM_WAGE_BC) {
            user.updateWage(wage);
            System.out.println("Your wage is now " + user.getWage() + " per hour.");
        } else {
            System.out.println("Wage must be higher than the minimum wage.");
        }
    }

    // MODIFIES: this
    // EFFECTS: prints out list of transactions
    private void viewTransactionHistory() {
        if (!user.getTransactions().isEmpty()) {
            System.out.println("Your Transactions:");
            for (Transaction trans : user.getTransactions()) {
                System.out.println("Transaction Category: " + trans.getCategory());
                if (trans.getIsIncome()) {
                    System.out.println("Amount: $" + trans.getAmount());
                } else {
                    System.out.println("Amount: -$" + trans.getAmount());
                }
                System.out.println("----------------------------------------");
            }
        } else {
            System.out.println("You have no transactions...");
        }
    }

    // EFFECTS: saves the account details to file
    private void saveAccountDetails() {
        try {
            jsonWriter.open();
            jsonWriter.write(user);
            jsonWriter.close();
            System.out.println("Saved account details " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads account details from file
    private void loadAccountDetails() {
        try {
            user = jsonReader.read();
            System.out.println("Loaded account details from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
