package com.application.subsystemsql;

import com.application.connection.JDBCPostgreSQLConnection;
import com.application.dao.ItemSiteDao;
import com.application.entity.ItemSite;
import com.application.helper.CalculateDater;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemSiteSubsystem implements ItemSiteDao {

    static Connection con
            = JDBCPostgreSQLConnection.getConnection();

    @Override
    public List<ItemSite> findItemSiteByItemId(String itemId) throws SQLException {
        String query = "select * from itemSite join site using (siteId) where itemId = ?";
        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setString(1, itemId);
        ResultSet rs = ps.executeQuery();
        List<ItemSite> listItem = new ArrayList<>();

        while (rs.next()) {
            String siteId = rs.getString("siteId");
            String siteName = rs.getString("siteName");
            String quantity = rs.getString("quantity");
            List<String> d = calculateDesiredDeliveryDate(siteId);
            ItemSite itemSite = new ItemSite(siteId, siteName, Integer.parseInt(quantity), d.get(0), d.get(1));
            listItem.add(itemSite);
        }

        return listItem;
    }

    public List<String> calculateDesiredDeliveryDate(String siteId) throws SQLException {
        CalculateDater cl = new CalculateDater();
        List<String> d = new ArrayList<>();

        String query = "select * from siteDelivery where siteId = ?";
        PreparedStatement ps
                = con.prepareStatement(query);
        ps.setString(1, siteId);
        ResultSet rs = ps.executeQuery();

        String desiredDeliveryByShipDate = "";
        String desiredDeliveryByAirDate = "";

        while(rs.next()) {
               int numberOfDaysDeliveryByShip = Integer.parseInt(rs.getString("numberOfDaysDeliveryByShip"));
               desiredDeliveryByShipDate = cl.calculateDesiredDeliveryDate(numberOfDaysDeliveryByShip);
               d.add(desiredDeliveryByShipDate);
               int numberOfDaysDeliveryByAir = Integer.parseInt(rs.getString("numberOfDaysDeliveryByAir"));
               desiredDeliveryByAirDate = cl.calculateDesiredDeliveryDate(numberOfDaysDeliveryByAir);
               d.add(desiredDeliveryByAirDate);
        }

        return d;
    }

}
