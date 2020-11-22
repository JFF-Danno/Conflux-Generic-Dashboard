import React from "react";
import "./App.css";
import { Drip } from "js-conflux-sdk";


const status = {
  INSTALL: "Install Conflux Portal",
  CONNECT: "Connect to Conflux Portal",
  CONNECTED: "Connected",
};

function Connect() {
  const [state, setState] = React.useState(status.INSTALL);
  const [disabled, setDisabled] = React.useState(false);
  const [txReceipt, setTxReceipt] = React.useState("");

  //check if injected provider exists
  React.useEffect(() => {
    if (window.conflux) {
      setState(status.CONNECT);
    }
  }, []);

  const onClick = async () => {
    try {
      setDisabled(true);
      switch (state) {
        case status.CONNECT: //connect with conflux portal
          await window.conflux.enable();
          const response = await fetch( '/Transaction?Action=Wallet&Address=' + window.conflux.selectedAddress );
          setState(status.CONNECTED);
          break;
        case status.CONNECTED: //send transaction to self on button click through conflux portal
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
      <header className="App-header">
        <button className="App-button" onClick={onClick} disabled={disabled}>
          {state}
        </button>
        {txReceipt && <div className="App-details">Transaction ID: {txReceipt}</div>}
        <br/><br/>
      </header>
    </div>
  );


}

export default Connect;
