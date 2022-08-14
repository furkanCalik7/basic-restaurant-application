package restaurant;

import restaurant.enums_interfaces.AccountStatus;

import java.io.Serializable;

public class Account implements Serializable {
  private String username;
  private String password;
  private AccountStatus status;

  public Account(String username, String password, AccountStatus status) {
    this.username = username;
    this.password = password;
    this.status = status;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String id) {
    this.username = id;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public AccountStatus getStatus() {
    return status;
  }

  public void setStatus(AccountStatus status) {
    this.status = status;
  }
}
