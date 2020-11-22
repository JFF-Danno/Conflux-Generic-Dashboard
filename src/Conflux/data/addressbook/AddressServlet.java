/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conflux.data.addressbook;

import confluxsdk.web3j.CfxUnit;
import confluxsdk.web3j.RpcException;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.String.format;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author danielo
 */
public class AddressServlet extends HttpServlet
{
	@Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException 
    {
		String sAction = req.getParameter( "Action" );

		System.out.println( "address servlet"  );
		 if ( sAction.equals( "Addresses" ) )
		{
			System.out.println( "addressed"  );
			resp.setContentType( "application/json" );
			String sJson = "[";
			try
			{
				LinkedList<AddressRecord> list = AddressRecord.getAddresses();
				for ( AddressRecord ar : list )
				{
					sJson += "{\"address\":\"" + ar.sAddress + "\",\"image\":\"" + ar.sImage + "\",\"name\":\"" + ar.sName + "\" },";			
				}
				sJson=sJson.substring( 0, sJson.length() - 1 );
				sJson += "]";
				System.out.println( "address servlet " + sJson );
			}
			catch ( RpcException e )
			{
				Logger.getLogger( AddressServlet.class.getName() ).log( Level.WARNING, e.getMessage() );
				sJson = "[]";
			}
			PrintWriter out = resp.getWriter();
			out.print( sJson );
			out.flush();
		}
	}
}
