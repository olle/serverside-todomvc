package com.studiomediatech.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

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
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Filter other = (Filter) obj;
    return Objects.equal(this.status, other.status);
  }

  @Override
  public String toString() {
    return Joiner.on('-').join(Iterables.transform(this.status, new Function<Status, String>() {
      public String apply(Status status) {
        return status.name();
      }
    })).toString();
  }

}
