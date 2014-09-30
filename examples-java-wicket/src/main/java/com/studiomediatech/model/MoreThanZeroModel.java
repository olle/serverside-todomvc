package com.studiomediatech.model;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class MoreThanZeroModel extends Model<Boolean> {

  private static final long serialVersionUID = 6195047890159821545L;

  private final IModel<Integer> countModel;

  public MoreThanZeroModel(IModel<Integer> countModel) {
    this.countModel = countModel;
  }

  @Override
  public Boolean getObject() {
    return this.countModel.getObject() > 0;
  }
}