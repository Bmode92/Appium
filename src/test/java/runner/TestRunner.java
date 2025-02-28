package runner;

import io.cucumber.testng.CucumberOptions;
import tests.TestBase;

@CucumberOptions(features = "src/test/resources/features/CreateTask",
glue = {"steps"},
plugin = {"pretty", "html:target/cucumber/cucumber-html-report.html"})
public class TestRunner extends TestBase {
}
