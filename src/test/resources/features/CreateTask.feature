Feature: Create New Task
  Scenario: The user can add new task
    Given Click Add new task
    And Enter task name "test1"
    And Enter task description "this is test1"
    When Click save
    Then Task added successfully