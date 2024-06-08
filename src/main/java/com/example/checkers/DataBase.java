package com.example.checkers;
import java.sql.*;
import java.util.Properties;

public class DataBase {

    String driver = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/";
    private final static String DBUSER = "root";
    private final static String DBPASS = "";
    private Connection con;
    private Statement st;
    private String query;
    public static boolean checkDriver(String driver){
        System.out.print("Sprawdzanie sterownika:");
        try{
            Class.forName(driver);
            System.out.println("OK");
            return true;
        } catch (ClassNotFoundException e) {
            System.out.println("Błąd");
            return false;
        }
    }

    public DataBase(){
        if(this.checkDriver("com.mysql.cj.jdbc.Driver")){
            System.out.println("Sterownik OK");
        }else{
            System.out.println("Brak sterownika");
        }

        con = this.getConnection("jdbc:mysql://", "localhost", 3306, "root", "");
        st = this.createStatement(con);
        String dbName = "warcaby";

        if(this.executeUpdate(st,"USE " + dbName + ";") == 0) {
            System.out.println("Użyto bazy " + dbName);
        }else{
            System.out.println("Baza nie istenieje! Tworzymy bazę: ");
            if(this.executeUpdate(st,"CREATE DATABASE " + dbName + ";") == 1) {
                System.out.println("Baza utworzona");
            }else{
                System.out.println("Błąd tworzenia bazy");
            }
            if(this.executeUpdate(st,"USE " + dbName + ";") == 0) {
                System.out.println("Użyto bazy " + dbName);
                if(this.executeUpdate(st,"CREATE TABLE game (id_rozgrywki int not null, login1 varchar(50) not null, login2 varchar(50) not null, wygrany varchar(50) not null);") == 0) {
                    System.out.println("Utworzono tabelę game");
                }
                if(this.executeUpdate(st,"CREATE TABLE user (login varchar(50) not null, haslo varchar(50) not null, ilosc_wygranych int, ilosc_przegranych int, ilosc_remisow int);") == 0) {
                    System.out.println("Utworzono tabelę user");
                }
            }else{
                System.out.println("Błąd używania bazy");
            }
        }
    }

    String getData(String query){
         ResultSet rs = this.executeQuery(st, query);
        try {
            if(rs.next()){
                return rs.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public static Connection connectToDatabase(String kindOfDatabase, String adress, String dataBaseName, String userName, String password){
        System.out.println("Łączenie z bazą danych:");
        String baza = kindOfDatabase + adress + "/" + dataBaseName;
        java.sql.Connection connection = null;
        try{
            connection = DriverManager.getConnection(baza, userName, password);
        } catch (SQLException e) {
            System.out.println("Błąd połączenia z bazą danych");
            System.exit(1);
        }
        return connection;
    }


    public static Connection getConnection(String kindOfDatabase, String adres, int port, String userName, String password){
        Connection conn = null;
        Properties connnectionProps = new Properties();
        connnectionProps.put("user", userName);
        connnectionProps.put("password", password);
        try{
            conn = DriverManager.getConnection(kindOfDatabase + adres + ":" + port + "/", connnectionProps);
        }catch (SQLException e){
            System.out.println("Błąd połączenia z bazą danych! " + e.getMessage() + ": " + e.getErrorCode());
            System.exit(2);
        }
        System.out.println("Połączenie z bazą danych: ... OK");
        return conn;
    }

    public static Statement createStatement(Connection conn){
        try{
            return conn.createStatement();
        }catch (SQLException e){
            System.out.println("Błąd createStatement! " + e.getMessage() + ": " + e.getErrorCode());
            System.exit(3);
        }
        return null;
    }

    private static void closeConnection(Connection conn, Statement s){
        System.out.println("Zamykanie połączenia z bazą danych:");
        try{
            s.close();
            conn.close();
        }catch (SQLException e){
            System.out.println("Błąd zamknięcia połączenia z bazą danych! " + e.getMessage() + ": " + e.getErrorCode());
            System.exit(4);
        }
        System.out.println("Zamknięcie połączenia z bazą danych: ... OK");
    }
    public static ResultSet executeQuery(Statement s, String sql){
        try{
            return s.executeQuery(sql);
        }catch (SQLException e){
            System.out.println("Zapytanie nie wykonane! " + e.getMessage() + ": " + e.getErrorCode());
        }
        return null;
    }

    public static int executeUpdate(Statement s, String sql){
        try{
            return s.executeUpdate(sql);
        }catch (SQLException e){
            System.out.println("Zapytanie nie wykonane! " + e.getMessage() + ": " + e.getErrorCode());

        }
        return -1;
    }


    private static void printDataFromQuery(ResultSet r){
        ResultSetMetaData rsmd;
        try{
            rsmd = r.getMetaData();
            int numcols = rsmd.getColumnCount();
            for(int i = 1 ;i <= numcols; i++){
                System.out.print("\t" + rsmd.getColumnLabel(i) + "\t|");
            }
            System.out.print("\n___________________________________________________________\n");
            while(r.next()){
                for(int i = 1; i<= numcols; i++){
                    Object obj = r.getObject(i);
                    if(obj != null)
                        System.out.print("\t" + obj.toString() + "\t|");
                    else
                        System.out.print("\t");
                }
                System.out.println();
            }
        }catch (SQLException e){
            System.out.println("Błąd odczytu z bazy! " + e.getMessage() + ": " + e.getErrorCode());

        }
    }

    public static void sqlGetDataByName(ResultSet r){
        try{
            ResultSetMetaData rsmd = r.getMetaData();
            int numcols = rsmd.getColumnCount();
            for(int i = 1; i <= numcols; i++){
                System.out.print(rsmd.getColumnLabel(i) + "\t|\t");
            }
            System.out.print("\n___________________________________________________________\n");
            while(r.next()){
                int size = r.getMetaData().getColumnCount();
                for(int i = 1; i <= size; i++){
                    switch (r.getMetaData().getColumnTypeName(i)){
                        case "INT":
                            System.out.print(r.getInt(r.getMetaData().getColumnName(i)) + "\t|\t");
                            break;
                        case "DATE":
                            System.out.print(r.getDate(r.getMetaData().getColumnName(i)) + "\t|\t");
                            break;
                        case "VARCHAR":
                            System.out.print(r.getString(r.getMetaData().getColumnName(i)) + "\t|\t");
                            break;
                        default:
                            System.out.print(r.getObject(r.getMetaData().getColumnName(i)));
                    }
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Błąd odczytu z bazy! " + e.getMessage() + ": " + e.getErrorCode());
        }
    }
}

