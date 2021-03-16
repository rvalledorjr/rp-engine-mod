package com.develmode.rpenginemod;

import com.develmode.rpenginemod.player.RoleplayPlayer;
import com.develmode.rpenginemod.utils.Lang;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class MySQLManager {
  public Connection connection;

  private Engine plugin;

  private String dbType = "sqlite";

  private String myHost = null;

  private String myPort = null;

  private String myDB = null;

  private String myUser = null;

  private String myPassword = null;

  private String tablePrefix = "rpen_";

  public MySQLManager(Engine plugin) {
    this.plugin = plugin;
  }

  public void OnEnable() {
    if (this.plugin.getConfig().contains("databasetype")
        && this.plugin.getConfig().getString("databasetype").equalsIgnoreCase("mysql")) {
      boolean mysqlLegit = true;
      if (this.plugin.getConfig().contains("mysql.host")) {
        this.myHost = this.plugin.getConfig().getString("mysql.host");
      } else {
        mysqlLegit = false;
      }
      if (this.plugin.getConfig().contains("mysql.port")) {
        this.myPort = this.plugin.getConfig().getString("mysql.port");
      } else {
        mysqlLegit = false;
      }
      if (this.plugin.getConfig().contains("mysql.database")) {
        this.myDB = this.plugin.getConfig().getString("mysql.database");
      } else {
        mysqlLegit = false;
      }
      if (this.plugin.getConfig().contains("mysql.user")) {
        this.myUser = this.plugin.getConfig().getString("mysql.user");
      } else {
        mysqlLegit = false;
      }
      if (this.plugin.getConfig().contains("mysql.password")) {
        this.myPassword = this.plugin.getConfig().getString("mysql.password");
      } else {
        mysqlLegit = false;
      }
      if (mysqlLegit)
        this.dbType = "mysql";
    }
    if (this.plugin.getConfig().contains("table-prefix"))
      this.tablePrefix = this.plugin.getConfig().getString("table-prefix");
  }

  public void onDisable() {
    try {
      if (this.connection == null && !this.connection.isClosed())
        this.connection.close();
    } catch (Exception e) {
      if (Engine.utils.sendDebug())
        e.printStackTrace();
    }
  }

  private void openConnection() {
    try {
      if (this.dbType == "mysql") {
        this.connection = DriverManager.getConnection(
            "jdbc:mysql://" + this.myHost + ":" + this.myPort + "/" + this.myDB, this.myUser,

            this.myPassword);
      } else {
        Class.forName("org.sqlite.JDBC");
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.plugin.getDataFolder() + "/data.db");
      }
    } catch (Exception e) {
      this.plugin.getLogger().log(Level.SEVERE, Lang.TITLE.toString() + " Couldn't connect to database");
      this.plugin.getLogger().log(Level.SEVERE, Lang.TITLE.toString() + " This is a fatal error, disabling plugin");
      if (Engine.utils.sendDebug() && Engine.utils.sendDebug())
        e.printStackTrace();
      this.plugin.disablePlugin();
    }
  }

  public void closeConnection() {
    try {
      this.connection.close();
    } catch (Exception e) {
      if (Engine.utils.sendDebug())
        e.printStackTrace();
    }
  }

  public void initDatabase() {
    Bukkit.getScheduler().runTaskAsynchronously((Plugin) this.plugin, new Runnable() {
      public void run() {
        MySQLManager.this.openConnection();
        try {
          PreparedStatement sql = MySQLManager.this.connection.prepareStatement("CREATE TABLE IF NOT EXISTS `"
              + MySQLManager.this.tablePrefix
              + "Cards` (`UUID` varchar(100) NOT NULL UNIQUE, `username` varchar(100), `name` varchar(250), `race` varchar(100), `nation` varchar(100), `gender` varchar(20), `age` INT, `desc` TEXT, `channel` varchar(20), `ooc` tinyint(1), `OOCban` tinyint(1), `BannedTill` DATETIME) ;");
          sql.execute();
          sql.close();
        } catch (Exception e) {
          if (Engine.utils.sendDebug())
            e.printStackTrace();
          return;
        } finally {
          MySQLManager.this.closeConnection();
        }
        MySQLManager.this.closeConnection();
      }
    });
  }

  public void createRoleplayPlayer(Player p) {
    createRoleplayPlayer(p.getUniqueId(), p.getName());
  }

  public void createRoleplayPlayer(final UUID uuid, final String playerName) {
    Bukkit.getScheduler().runTaskAsynchronously((Plugin) this.plugin, new Runnable() {
      public void run() {
        MySQLManager.this.openConnection();
        try {
          boolean online = true;
          if (Bukkit.getPlayer(uuid) == null)
            online = false;
          PreparedStatement sql = MySQLManager.this.connection
              .prepareStatement("SELECT * FROM `" + MySQLManager.this.tablePrefix + "Cards` WHERE `UUID`=?;");
          sql.setString(1, uuid.toString());
          ResultSet rs = sql.executeQuery();
          if (rs.next()) {
            PreparedStatement sql3 = MySQLManager.this.connection.prepareStatement(
                "UPDATE `" + MySQLManager.this.tablePrefix + "Cards` SET `username`=? WHERE `UUID`=?;");
            sql3.setString(1, playerName);
            sql3.setString(2, uuid.toString());
            sql3.executeUpdate();
            sql3.close();
            Engine.manager.addPlayer(
                new RoleplayPlayer(uuid, playerName, rs.getString("name"), rs.getString("race"), rs.getString("nation"),
                    RoleplayPlayer.Gender.valueOf(rs.getString("gender").toUpperCase()), rs.getInt("age"),
                    rs.getString("desc"), RoleplayPlayer.Channel.RP, true, online, MySQLManager.this.plugin));
            if (rs.getInt("ooc") == 0)
              MySQLManager.this.setStringField(uuid, "ooc", "1");
          } else {
            PreparedStatement sql2 = MySQLManager.this.connection.prepareStatement("INSERT INTO `"
                + MySQLManager.this.tablePrefix
                + "Cards` (`UUID`, `username`, `name`, `race`, `nation`, `gender`, `age`, `desc`, `channel`, `ooc`) VALUES(?,?,?,?,?,?,?,?,?,?);");
            sql2.setString(1, uuid.toString());
            sql2.setString(2, playerName);
            sql2.setString(3, "NONE");
            sql2.setString(4, "NONE");
            sql2.setString(5, "NONE");
            sql2.setString(6, "NONE");
            sql2.setString(7, "0");
            sql2.setString(8, "NONE");
            sql2.setString(9, "RP");
            sql2.setString(10, "1");
            sql2.execute();
            sql2.close();
            Engine.manager
                .addPlayer(new RoleplayPlayer(uuid, playerName, playerName, "NONE", "NONE", RoleplayPlayer.Gender.NONE,
                    0, "NONE", RoleplayPlayer.Channel.RP, true, online, MySQLManager.this.plugin));
          }
          sql.close();
          rs.close();
        } catch (SQLException e) {
          if (Engine.utils.sendDebug())
            e.printStackTrace();
          return;
        } finally {
          MySQLManager.this.closeConnection();
        }
        MySQLManager.this.closeConnection();
        Engine.manager.getPlayer(uuid).setTag();
      }
    });
  }

  public void setStringField(final UUID u, final String field, final String data) {
    Bukkit.getScheduler().runTaskAsynchronously((Plugin) this.plugin, new Runnable() {
      public void run() {
        MySQLManager.this.openConnection();
        try {
          PreparedStatement sql1 = MySQLManager.this.connection.prepareStatement(
              "UPDATE `" + MySQLManager.this.tablePrefix + "Cards` SET `" + field + "`=? WHERE `UUID`=?;");
          sql1.setString(1, data);
          sql1.setString(2, u.toString());
          sql1.executeUpdate();
          sql1.close();
        } catch (Exception e) {
          if (Engine.utils.sendDebug())
            e.printStackTrace();
          return;
        } finally {
          MySQLManager.this.closeConnection();
        }
        MySQLManager.this.closeConnection();
      }
    });
  }

  public void setIntegerField(final UUID u, final String field, final int data) {
    Bukkit.getScheduler().runTaskAsynchronously((Plugin) this.plugin, new Runnable() {
      public void run() {
        MySQLManager.this.openConnection();
        try {
          PreparedStatement sql1 = MySQLManager.this.connection.prepareStatement(
              "UPDATE `" + MySQLManager.this.tablePrefix + "Cards` SET `" + field + "`=? WHERE `UUID`=?;");
          sql1.setInt(1, data);
          sql1.setString(2, u.toString());
          sql1.executeUpdate();
          sql1.close();
        } catch (Exception e) {
          if (Engine.utils.sendDebug())
            e.printStackTrace();
          return;
        } finally {
          MySQLManager.this.closeConnection();
        }
        MySQLManager.this.closeConnection();
      }
    });
  }

  public String getDefaultValue(String text, String compare) {
    return text.equalsIgnoreCase(compare) ? (ChatColor.GRAY + compare) : text;
  }
}

/*
 * Location:
 * C:\Users\develmode\dev\msp-basic-chat\server\plugins\RPEngine-4.5.3.jar!\com\
 * Alvaeron\MySQLManager.class Java compiler version: 8 (52.0) JD-Core Version:
 * 1.1.3
 */