package model;

public class Todo {

  private String id;
  private String todo;
  private String status;

  public Todo() {
    this.status = "active";
  }

  public String getId() {

    return this.id;
  }

  public void setId(String id) {

    this.id = id;
  }

  public String getTodo() {

    return this.todo;
  }

  public void setTodo(String todo) {

    this.todo = todo;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getToggle() {
    if (this.status.equals("active")) {
      return "completed";
    }
    return "active";
  }

  public void toggleStatus() {
    this.status = getToggle();
  }
}
