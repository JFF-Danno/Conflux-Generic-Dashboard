/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conflux.data.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author danielo
 */
public class  Database 
{
    Connection conn = null;
    public Connection getConnection()
    {
		if( conn == null )
		{
			try 
			{
				Class.forName("com.mysql.jdbc.Driver");
			//	conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/dannoint_dooper", "dannoint_daniel", "r@cer999" );
				conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/dooper", "root", "" );

			}
			catch ( SQLException ex )
			{
				Logger.getLogger( Database.class.getName() ).log( Level.SEVERE, null, ex );
			} 
			catch (ClassNotFoundException ex) 
			{
				Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
        return conn;
    }
    
    public void closeConnection()
            throws SQLException
    {
        conn.close();
    }


    
}
