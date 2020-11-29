package service;

import org.hsqldb.jdbc.JDBCDataSource;
import java.io.*;
import java.sql.*;
import java.sql.Connection;

public class DBManager {
	
	//private final String DBSCRIPT_PATH = "src/main/resources/createDatabase.sql";
    private final String DB_NAME = "testtask3db";
    private final String DB_PATH = "src/main/resources/" + DB_NAME;
    private final String DB_URL = "jdbc:hsqldb:file:" + DB_PATH;
    
    private Connection connection = null;

    public Connection getConnection(){
        return this.connection;
    }

    public DBManager(){
        //Проверка наличия драйвера БД
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //Установка значений
        JDBCDataSource dataSource = new JDBCDataSource();
        dataSource.setDatabaseName(DB_NAME);
        dataSource.setURL(DB_URL);
        dataSource.setUser("SA");
        dataSource.setPassword("");
        //Соединение
        try {
            connection = DriverManager.getConnection("jdbc:hsqldb:file:testtask3db", "SA", "");
            executeSqlScript("src/main/resources/createDataBase.sql");
            executeSqlScript("src/main/resources/fillDataBase.sql");
            
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    //Считывает и выполняет SQL-скрипт
    public void executeSqlScript(String path){
        InputStream input;
        try {
            input = new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            String str = reader.readLine();
            StringBuilder script = new StringBuilder(str);
            while ((str = reader.readLine()) != null) {
                script.append(str);
            }
            String[] statements = script.toString().split(";");

            for (String s : statements) {
                PreparedStatement statement = connection.prepareStatement(s);
                statement.execute();
            }
            reader.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        
    }
}
