<%@ taglib uri="http://dooper.com/tags/custom" prefix="Dooper" %>
<!doctype html>
<html lang="en">
    <head>
        <title>Conflux: Generic Dashboard</title>
          <link rel="stylesheet" href="/conflux/css/main.css">
          <link rel="stylesheet" href="/css/custom-modal.css">
           <meta name="robots" content="index, follow" />
    </head>

    <body class="dark calendar" >
        <div class="outer" >
            <div class="left-col">
                
                <div><span class="big"> CFX</span><span class="small" >Balance</span><br/><br/>
                <span class="big" id="balance">0</span>

                
                </div>
            <div id="send"></div>
            </div>
            <div class="middle">
                <table id="results">
                    <tr>
                        <td>
                               <p>Staked:</p>  <h2 id="staking">0</h2>                       
                        </td>
                        <td>
                               <p>APR:</p>  <h2 id="rate">0</h2>                       
                        </td>
                        <td style="width:130px;">
                        <div  id="stake"></div>
                        </td>
                        <td style="width:130px;">
                        <div id="unstake"></div>
                        </td>
                        <td>
                         <input id="spend-amount" type="text" placeholder="0" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                        </td>
                        <td>
                        </td>
                        <td>
                        </td>
                        <td>
                        </td>
                        <td>
                        </td>
                    </tr>
                </table> 
                
            </div>
            <div class="right-col">
                <div id="connect"></div>
         
            </div>
        </div>

        </body>
<script src="/theme/plugins/jquery/jquery.min.js"></script>
<script src="/conflux/js/js-conflux-sdk.umd.min.js" ></script>
 <script src="/conflux/js/react/bundle.js"></script><script src="/conflux/js/react/0.chunk.js"></script><script src="/conflux/js/react/main.chunk.js"></script>    
<!-- <script src="/conflux/js/main.js"></script> -->


        <script>
            
            $(function()
            {
                $.ajax({
                    url : '/Address?Action=Addresses' ,
                    type : 'GET',
                    dataType : 'json',
                    success : function(data) {$.each(  data, function( index, element )
                    {
                        $('#results').append( '<tr><td class=\"address-card\" colspan=\"5\"><h3>' + element.name + '</h3><a href="#" onclick="fillAddress(\'' + element.address + '\');" ><img width=\"150px;\" src=\"' + element.image + '\" /></a></td></tr>' );
   
                    }
                     
              
            )}})});
                

            
            function fillAddress(address)
            {
                $('#spend-address').val(address);
            }
            
            var gotwallet = false;
            function getData()
            {
                    if ( ! gotwallet )
                    {
                        $.ajax({
                        url : '/Conflux?Action=getWalletData' ,
                        type : 'GET',
                        dataType : 'json',
                        success : function(data) 
                        {
                            $('#balance'   ).text( data.balance          );
                            $('#staking'   ).text( data.stakingbalance   );
                            $('#rate'      ).text( data.rate             );
                            $('#gas'       ).text( data.gas             );
                        },
                        error : function() 
                        {
                            console.log('error');
                        } });
                        gotwallet = true;
                    }
                
            }
            
            function getTX()
            {
                 $.ajax({
                    url : '/Transaction?Action=NewTransaction' ,
                    type : 'GET',
                    dataType : 'json',
                    success : function(data) 
                    {
                        if ( data.tx != '' )
                        {
                            $('#results').append( "<tr><td colspan=\"5\"><a target=\"_blank\" href=\"https://testnet.confluxscan.io/transaction/" + data.tx + "\">" + data.tx + "</a></td></tr>" )
                    
                        }                       
                    },
                    error : function() 
                    {
                        console.log('error');
                    } });
                           
               
            }
                    
           $( function timeout() 
            {
                setTimeout(function () { getData(); getTX(); timeout();}, 2000);
            });

        </script>
            
</html>