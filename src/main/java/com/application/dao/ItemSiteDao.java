package com.application.dao;

import com.application.entity.ItemSite;

import java.sql.SQLException;
import java.util.List;

public interface ItemSiteDao {
    public List<ItemSite> findItemSiteByItemId(String itemId) throws SQLException;
}