package com.application.subsystemsql;

import com.application.connection.JDBCPostgreSQLConnection;
import com.application.dao.ItemDao;
import com.application.entity.Item;

import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class ItemSubsystem implements ItemDao {

    static Connection con
            = JDBCPostgreSQLConnection.getConnection();

    @Override
    public List<Item> getListItem() throws SQLException {
        String query = "select * from item";
        PreparedStatement ps
                = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<Item> listItem = new ArrayList<>();

        while (rs.next()) {
            String itemId = rs.getString("itemId");
            String itemName = rs.getString("itemName");
            String unit = rs.getString("unit");
            Item item = new Item(itemId, itemName, unit);
            listItem.add(item);
        }

        return listItem;
    }

    @Override
    public List<Item> getItemByName(String value) throws SQLException {
        String query = "select * from item where itemName like ?";
        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setString(1, "%" + value + "%");
        ResultSet rs = ps.executeQuery();
        List<Item> listItem = new ArrayList<>();

        while (rs.next()) {
            String itemId = rs.getString("itemId");
            String itemName = rs.getString("itemName");
            String unit = rs.getString("unit");
            Item item = new Item(itemId, itemName, unit);
            listItem.add(item);
        }

        return listItem;
    }
}
