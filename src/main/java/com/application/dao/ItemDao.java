package com.application.dao;

import com.application.entity.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemDao {
    public List<Item> getListItem() throws SQLException;
    public List<Item> getItemByName(String value) throws SQLException;
}
