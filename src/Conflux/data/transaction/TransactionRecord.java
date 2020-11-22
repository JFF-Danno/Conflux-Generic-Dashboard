/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conflux.data.transaction;

import Conflux.data.db.Database;
import diary.post.PostRecord;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author danielo
 */
public class TransactionRecord 
{
	public	int		id;
	public	String	sTransaction;	
	public	String	sTransactionOwner;
	public	int		idAddress;

	
	public TransactionRecord( int id, String sTransaction, String sTransactionOwner, int idAddress )
	{
		this.id					= id;
		this.sTransaction		= sTransaction;
		this.sTransactionOwner	= sTransactionOwner;
		this.idAddress			= idAddress;
	}

	public LinkedList<TransactionRecord> getTransaction()
	{
		PostRecord pr = null;
		LinkedList<TransactionRecord> llPosts = new LinkedList<TransactionRecord>();
        try 
        {
            Database database = new Database();
			database.getConnection().setAutoCommit( false );
            ResultSet rs;
			String sql = "SELECT * FROM `transaction` where order by id DESC";
			PreparedStatement ps = database.getConnection().prepareStatement( sql );
			rs = ps.executeQuery();
            while( rs.next() )
			{
				TransactionRecord ar = new TransactionRecord( rs.getInt( 1 ), rs.getString( 2 ), rs.getString( 3 ), rs.getInt( 4 ) );
				llPosts.add( ar );
			}
			database.closeConnection();
        }
        catch ( SQLException ex ) 
        {
            Logger.getLogger( TransactionRecord.class.getName() ).log( Level.SEVERE, null, ex );
        }
        return llPosts;
	}
	
	public static TransactionRecord load( int id )
    {
        TransactionRecord cr = null;
        try 
        {
            Database database = new Database();
			database.getConnection().setAutoCommit( false );
			String sql = "SELECT * FROM `address` where id = ?";
			PreparedStatement ps = database.getConnection().prepareStatement( sql );
			ps.setInt(	1, id		);
			ResultSet rs  = ps.executeQuery();
			database.getConnection().commit();
	
			while( rs.next() )
			{
				cr = new TransactionRecord( rs.getInt( 1 ), rs.getString( 2 ), rs.getString( 3 ), rs.getInt( 4 ) );
					
			}
			database.closeConnection();
        }
        catch ( SQLException ex ) 
        {
            Logger.getLogger( TransactionRecord.class.getName() ).log( Level.SEVERE, null, ex );
        }
        return cr;
    }
	
	public TransactionRecord insert()
	{
		try 
		{		
			Database database = new Database();
			database.getConnection().setAutoCommit( false );
			String sql = "Insert into transaction ( id,transaction,transactionowner,idaddress ) values (?,?,?,?)";
			
			PreparedStatement ps = database.getConnection().prepareStatement( sql );
			ps.setInt(		1,	0			);
			ps.setString(	2,	sTransaction			);
			ps.setString(	3,	sTransactionOwner		);
			ps.setInt(		4,	idAddress				);
			ps.execute();
			database.getConnection().commit();
			return this;
		}
        catch ( SQLException ex )
        {
			Logger.getLogger( TransactionRecord.class.getName() ).log( Level.SEVERE, null, ex );

        }
		return this;
	}
	
	public TransactionRecord update()
	{
		try 
		{
				String sql = "Update address set ";
				sql += "transaction = ?,";
				sql += "transactionowner = ?,";
				sql += "idaddress = ?";
				sql += " where id = ?";
				
				Database database = new Database();
				database.getConnection().setAutoCommit( false );
				PreparedStatement ps = database.getConnection().prepareStatement( sql );
				ps.setString(	1, sTransaction			);
				ps.setString(	2, sTransactionOwner	);
				ps.setInt(		3, idAddress			);
				ps.setInt(		5, id				);
				ps.executeUpdate();
				database.getConnection().commit();
		}
		catch ( SQLException ex )
        {
			Logger.getLogger(TransactionRecord.class.getName()).log(Level.SEVERE, null, ex);

        }
		return this;
	}
}
