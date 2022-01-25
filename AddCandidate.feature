Feature: Add Candidate Functionality
As a Admin user I should be able add a candidate with only valid credentials
and should get error message when entered invalid credentials in order to validate add candidate feature

Background: open browser, application and go to the login page
Given A "chrome" browser initialized
And open application "https://opensource-demo.orangehrmlive.com/index.php/auth/login with creds - admin/admin123"
And I click on candidate submenu
And I click on the Add button

Scenario: As a Admin user I should be able to add a candidate with valid credentials
Given I am on add candidate page
When I enter firstName as "fname" and last name as "lname" & email as "test@test.com"
And  Click on save button
Then I validate that I am able to add the candidate