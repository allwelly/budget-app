# My Personal Project

## Proposal
**What will the application do?**

  The app records the user's income and expenses, tracks user's saving goals, and compare user's spending 
  against their budget. every time they enter their expenses, it will print the equivalent working hours
  they had spent. On the other hand, every time they enter an income, it will print the equivalent thing 
  that worth that amount. This would discourage users to spend and encourage them to save more.

  example use case:

  The user gets an hourly wage of $20 per hour. User spent $100 on clothes, after the user inputs 
  their spending, the app will record the spending and print out a message “You have spent 5 hours 
  worth of working on clothes!”

**Who will use it?**

  People who are financially intelligent, or at least financially conscious. In terms of the
  demographics, the main target demographic would be people middle-low class between the age of 20-60 
  years old. Though ultimately, anyone can use the app.

**Why is this project of interest to you?**

  Financial literacy is an important aspect of growing up. As someone who have started to manage their own 
  finances, I myself use a couple different apps to track my finances. I find this project to be a great 
  starting point for my interest in fintech, as there's a potential for 
  me to build on this project into a more advanced money management app, or even group money management app.

## User Stories
**Phase 1**
- As a user, I want to be able to add a saving goal to my list of saving goals
- As a user, I want to be able to add/subtract money to/from my saving goal
- As a user, I want to be able to increase/decrease my saving goal target
- As a user, I want to be able to delete a saving goal
- As a user, I want to be able to see my list of saving goals
- As a user, I want to be able to input my deposit/withdraw to/from my account

**Phase 2**  
- As a user, I want to be able to save my account details to file.
- As a user, I want to be able to load my account details from file.
- As a user, I want to be able to input my hourly wage to the nearest integer, if I choose not to input my hourly wage, the system will set it to the current minimum wage in BC round to the nearest integer.
- As a user, I want to be able to know the equivalent working hours I had spent to the nearest integer
- As a user, I want to be able to know the equivalent working hours I have saved to the nearest integer when I put money in my saving
- As a user, I want to be able to see my transaction history

## Phase 4
**Task 2**   
Wed Mar 30 18:44:51 PDT 2022  
goal1 was added to the list of saving goals

Wed Mar 30 18:44:56 PDT 2022  
goal2 was added to the list of saving goals

Wed Mar 30 18:45:02 PDT 2022  
Information about the saving goal goal1 was accessed by the user

Wed Mar 30 18:45:14 PDT 2022  
goal3 was added to the list of saving goals

**Task 3**
- I would change the name of the class "Account" into "User"
- I would add an abstract class called Account that implements addBalance and subtractBalance methods
- I would make SavingGoal and now class User to extend the abstract class Account, because both classes have addBalance/save and subtractBalance/withdraw methods that basically do the same things.
- I would make a reusable component Popup that takes in an image as a parameter to replace duplication in both SuccessPopup and ErrorPopup because the design of both are pretty much the same, the only difference is the image used for each popup.