/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conflux;

import confluxsdk.web3j.CfxUnit;
import confluxsdk.web3j.RpcException;
import confluxsdk.web3j.request.Epoch;
import confluxsdk.web3j.response.AccountInfo;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.*;
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
public class ConfluxServlet extends HttpServlet
{
	@Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException 
    {
		String sAction = req.getParameter( "Action" );
		if ( sAction.equals( "Epoch" ) )
		{
			Conflux conflux = new Conflux();
		}
		else if ( sAction.equals( "getWalletData" ) )
		{
			
			String sAddress = (String) req.getSession().getAttribute( "Wallet" );
			System.out.println( "wallet " + sAddress );
			resp.setContentType( "application/json" );
			String sJson = "";
			try
			{
				Conflux conflux				= new Conflux();
				BigInteger	epoch			= conflux.getCfx().getEpochNumber( Epoch.latestState() ).sendAndGet();
				AccountInfo info			= conflux.getCfx().getAccount( sAddress, Epoch.numberOf( epoch ) ).sendAndGet();
				BigInteger	balance			= info.getBalance();
				BigInteger	stakingBalance	= info.getStakingBalance();
				BigInteger	stakingInterest	= conflux.getCfx().getInterestRate( Epoch.numberOf( epoch ) ).sendAndGet();
				BigInteger	gas				= conflux.getCfx().getGasPrice().sendAndGet();
				
				BigDecimal bdStaking				= new BigDecimal( stakingBalance );
				BigDecimal bdBalance				= new BigDecimal( balance );
				DecimalFormat format				= new DecimalFormat( "0.000" );
				
				sJson = "{\"balance\":\"" + format.format( CfxUnit.drip2Cfx( balance ) ) + "\",\"stakingbalance\":\"" + CfxUnit.drip2Cfx( stakingBalance ).toString() + "\",\"rate\":\"4%\"" + 
						",\"gas\":\"" + gas  + "\" }";
		
			}
			catch ( RpcException e )
			{
				Logger.getLogger( ConfluxServlet.class.getName() ).log( Level.WARNING, e.getMessage() );
				sJson = "[]";
			}
			PrintWriter out = resp.getWriter();
			out.print( sJson );
			out.flush();
		}
	}
}
