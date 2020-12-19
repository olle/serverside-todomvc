package com.studiomediatech.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.wicket.util.lang.Objects;

public class Filter implements Serializable {

  private static final long serialVersionUID = -1894070442891160966L;

  private final List<Status> status;

  public Filter(Status... status) {
    this.status = Arrays.asList(status);
  }

  public List<Status> getStatus() {
    return this.status;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this.status);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Filter other = (Filter) obj;
    return Objects.equal(this.status, other.status);
  }

  @Override
  public String toString() {
    return this.status.stream().map(Status::name).collect(Collectors.joining("-"));
  }

  public boolean apply(Todo todo) {
    return this.status.contains(todo.getStatus());
  }

}
