import React from "react";
import "./App.css";
import { Drip } from "js-conflux-sdk";


const status = {
  INSTALL: "Install Conflux Portal",
  CONNECT: "Connect to Conflux Portal",
  SEND: "Send Transaction",
};

function App() {
  const [state, setState] = React.useState("");
  const [disabled, setDisabled] = React.useState(false);
  const [txReceipt, setTxReceipt] = React.useState("");

  //check if injected provider exists
  React.useEffect(() => {
    if (window.conflux) {
      setState(status.SEND);
    }
  }, []);

  const onClick = async () => {
    try {
      
      switch (state) {

        case status.SEND: //send transaction to self on button click through conflux portal
            if ( document.getElementById('spend-amount').value === '' || document.getElementById('spend-address').value === '' )
            {
                setDisabled(true);
            }
            else
            {
                  const tx = {
                    // gas: util.format.hex(2000000),
                    // gasPrice: util.unit.fromGDripToDrip(0.0000001),
                    from: window.conflux.selectedAddress,
                    to: document.getElementById('spend-address').value,
                    value: Drip.fromCFX(document.getElementById('spend-amount').value)
                  };
                    
                  const txResult = await window.confluxJS.sendTransaction(tx);
                  const response = await fetch( '/Transaction?Action=Tx&TX=' + txResult );
                  document.getElementById('spend-amount').value = '';
                  document.getElementById('spend-address').value = '';
            }
              break;
        default: //if conflux portal is not installed, button opens link for installation
          window.open("https://portal.conflux-chain.org/");
      }
    } catch (e) {
      console.log(e);
    } finally {
      setDisabled(false);
    }
  };


  return (
    <div className="App">
        <input id="spend-address" type="text"  placeholder="address" /><br />
       <span id="spend-label">CFX</span> <input id="spend-amount" type="text" placeholder="0" />
      <header className="App-header">
        <button className="App-button" onClick={onClick} disabled={disabled}>
          {state}
        </button>
        <br/><br/>
      </header>
    </div>
  );


}

export default App;
