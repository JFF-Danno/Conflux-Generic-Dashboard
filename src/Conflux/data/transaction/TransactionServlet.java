/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conflux.data.transaction;

import confluxsdk.web3j.CfxUnit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author danielo
 */
public class TransactionServlet extends HttpServlet
{
	@Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException 
    {
		String sAction = req.getParameter( "Action" );
		if ( sAction.equals( "Tx" ) )
		{
			String sTx = req.getParameter( "TX" );
			System.out.println( sTx );
			TransactionRecord tr		= new TransactionRecord( 0, sTx, (String) req.getSession().getAttribute( "Wallet" ), 0 );
			tr.insert();
			req.getSession().setAttribute( "Transaction", sTx );
		}
		else if ( sAction.equals( "Wallet" ) )
		{
			String sTx = req.getParameter( "Address" );
			System.out.println( sTx );
			req.getSession().setAttribute( "Wallet", sTx );
		}
		else if ( sAction.equals( "NewTransaction" ) )
		{
			resp.setContentType( "application/json" );
			String sJson;
			if( req.getSession().getAttribute( "Transaction" ) != null )
			{
				sJson = "{\"tx\":\"" + (String) req.getSession().getAttribute( "Transaction" ) + "\" }";
				req.getSession().removeAttribute( "Transaction" );
			}
			else
			{
				sJson = "{\"tx\":\"\" }";
			}
			PrintWriter out = resp.getWriter();
			out.print( sJson );
			out.flush();
		
		}
			
			
		
		
	}
	
	@Override
    protected void doPost( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException 
    {
		String sTarget = req.getParameter( "Target" );
		if ( sTarget.equals( "Insert" ) )
		{
			InsertTransaction( req );
		}
	}
	
	public void InsertTransaction( HttpServletRequest req )
	{
		BufferedReader br = null;
		try 
		{
			br = req.getReader();
		}
		catch ( IOException ex )
		{
			Logger.getLogger( TransactionServlet.class.getName() ).log( Level.SEVERE, null, ex );
		}
		StringBuffer sBody = new StringBuffer();
		String sLine = "";
		try 
		{
			while ( ( sLine = br.readLine() ) != null)
			{
				sBody.append( sLine );
			}
		} 
		catch ( IOException ex )
		{
			Logger.getLogger( TransactionServlet.class.getName() ).log( Level.SEVERE, null, ex );
		}
		String sJson = sBody.toString();
					
		try
		{
			JSONObject jo = new JSONObject( sJson );

			String	sTransaction		=	(String)	jo.get( "transaction"		); 
			String	sTransactionOwner	=	(String)	jo.get( "transactionowner"	);
			String	sIdAddress			=	(String)	jo.get( "idaddress"			);
			TransactionRecord tr		= new TransactionRecord( 0, sTransaction, sTransactionOwner, Integer.parseInt( sIdAddress ) );
			tr.insert();
		}
		catch( Exception e )
		{
			System.out.println( "Transaction insert error " + e.getMessage() );
		}
		
	}
}
