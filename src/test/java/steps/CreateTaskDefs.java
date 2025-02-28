package steps;

import PageObjects.CreateTaskPage;
import PageObjects.TasksListPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import tests.TestBase;

import java.net.MalformedURLException;

public class CreateTaskDefs extends TestBase {

    CreateTaskPage createTaskPage;
    TasksListPage tasksListPage;

    @Given("Click Add new task")
    public void addNewTask() throws MalformedURLException {
        Android_setUp();
        tasksListPage = new TasksListPage(driver);
        createTaskPage = new CreateTaskPage(driver);
        tasksListPage.clickBtn();
    }

    @When("Enter task name {string}")
    public void enterTaskName(String taskName) {
        createTaskPage.enterTaskName(taskName);
    }

    @When("Enter task description {string}")
    public void enterTaskDesc(String taskDesc) {
        createTaskPage.enterTaskDesc(taskDesc);
    }
    @When("Click save")
    public void clickSave() {
        createTaskPage.clickSaveBtn();
    }

    @Then("Task added successfully")
    public void taskAdded() {
        driver.hideKeyboard();
        tearDown();
    }
}
