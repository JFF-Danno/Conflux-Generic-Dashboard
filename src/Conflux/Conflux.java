/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conflux;

import confluxsdk.web3j.Cfx;
/**
 *
 * @author danielo
 */
public class Conflux 
{
	public Conflux()
	{
	}
	
	Cfx cfx = null;
	public Cfx getCfx()
	{
		if ( cfx == null )
		{
			cfx = getNewCFX();
		}
		return cfx;
	}

	public Cfx getNewCFX()
	{
		try
		{
			cfx = Cfx.create( "http://testnet-jsonrpc.conflux-chain.org:12537", 3, 1000 );
		}
		catch ( Exception e )
		{
			System.out.println( "error getting cfx: " + e.getMessage() );		
		}
		return cfx;
	}

}
