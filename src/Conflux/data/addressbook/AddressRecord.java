/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conflux.data.addressbook;

import Conflux.data.db.Database;
import Dooper.user.UserRecord;
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
public class AddressRecord 
{
	public	int		id;
	public	String	sAddress;	
	public	String	sAddressOwner;
	public	String	sName;
	public	String	sImage;

	
	public AddressRecord( int id, String sAddress, String sAddressOwner, String sName, String sImage )
	{
		this.id					= id;
		this.sAddress			= sAddress;
		this.sAddressOwner		= sAddressOwner;
		this.sName				= sName;
		this.sImage				= sImage;
	}

	public static LinkedList<AddressRecord> getAddresses()
	{
		PostRecord pr = null;
		LinkedList<AddressRecord> llPosts = new LinkedList<AddressRecord>();
        try 
        {
            Database database = new Database();
			database.getConnection().setAutoCommit( false );
            ResultSet rs;
			String sql = "SELECT * FROM `address` order by id DESC";
			PreparedStatement ps = database.getConnection().prepareStatement( sql );
			rs = ps.executeQuery();
            while( rs.next() )
			{
				AddressRecord ar = new AddressRecord( rs.getInt( 1 ), rs.getString( 2 ), rs.getString( 3 ),rs.getString( 4 ), rs.getString( 5 ) );
				llPosts.add( ar );
			}
			database.closeConnection();
        }
        catch ( SQLException ex ) 
        {
            Logger.getLogger( AddressRecord.class.getName() ).log( Level.SEVERE, null, ex );
        }
        return llPosts;
	}
	
	public static AddressRecord load( int id )
    {
        AddressRecord cr = null;
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
				cr = new AddressRecord( rs.getInt( 1 ), rs.getString( 2 ), rs.getString( 3 ), rs.getString( 4 ), rs.getString( 5 ) );
					
			}
			database.closeConnection();
        }
        catch ( SQLException ex ) 
        {
            Logger.getLogger( AddressRecord.class.getName() ).log( Level.SEVERE, null, ex );
        }
        return cr;
    }
	
	public AddressRecord insert()
	{
		try 
		{		
			Database database = new Database();
			database.getConnection().setAutoCommit( false );
			String sql = "Insert into address ( id,address,addressowner,name,image ) values (?,?,?,?,?)";
			
			PreparedStatement ps = database.getConnection().prepareStatement( sql );
			ps.setInt(		1,	0			);
			ps.setString(	2,	sAddress			);
			ps.setString(	3,	sAddressOwner		);
			ps.setString(	4,	sName				);
			ps.setString(	5,	sImage				);
			ps.execute();
			database.getConnection().commit();
			return this;
		}
        catch ( SQLException ex )
        {
			Logger.getLogger( AddressRecord.class.getName() ).log( Level.SEVERE, null, ex );

        }
		return this;
	}
	
	public AddressRecord update()
	{
		try {

				String sql = "Update address set ";
				sql += "address = ?,";
				sql += "addressowner = ?,";
				sql += "name = ?,";
				sql += "image = ?";
				sql += " where id = ?";
				
				Database database = new Database();
				database.getConnection().setAutoCommit( false );
				PreparedStatement ps = database.getConnection().prepareStatement( sql );
				ps.setString(	1, sAddress			);
				ps.setString(	2, sAddressOwner	);
				ps.setString(	3, sName			);
				ps.setString(	4, sImage			);
				ps.setInt(		5, id				);
				try 
				{
					ps.executeUpdate();
					database.getConnection().commit();
				}
				catch ( Exception e )
				{
					Logger.getLogger( AddressRecord.class.getName() ).log( Level.SEVERE, null, e );
				}
				
		}
        catch ( SQLException ex )
        {
			Logger.getLogger(UserRecord.class.getName()).log(Level.SEVERE, null, ex);

        }
		return this;
	}
}
