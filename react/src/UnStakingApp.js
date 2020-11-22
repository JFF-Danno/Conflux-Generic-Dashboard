import React from "react";
import "./App.css";
const { Conflux, Drip } = require("js-conflux-sdk");
const staking = require("./staking.json");


const status = {
    INITSTAKE: "InitStake",
    STAKE: "Stake",
    WITHDRAW: "Withdraw",
};

//account initialization
  

function UnStakingApp() {
  const [state, setState] = React.useState(status.Stake);
  const [disabled, setDisabled] = React.useState(false);
  const [txReceipt, setTxReceipt] = React.useState("");

  //check if injected provider exists
  React.useEffect(() => {
    if (window.conflux) {
      setState(status.WITHDRAW);
    }
  }, []);

  const onClick = async () => {
    try {
      setDisabled(true);
      switch (state) {
        case status.WITHDRAW: //ready to stake
           const cfx = await new Conflux({url: "http://test.confluxrpc.org",defaultGasPrice: 100,defaultGas: 1000000,logger: console});

           setDisabled(false);
            const PRIVATE_KEY = '';//process.env.PRIVATE_KEY;
            const account = cfx.wallet.addPrivateKey(PRIVATE_KEY); // create account instance
             const contract = cfx.Contract({abi: staking.abi,address: "0x0888000000000000000000000000000000000002"});
           const tx0 = await contract.withdraw(Drip.fromCFX(document.getElementById('spend-amount').value)).sendTransaction({ from: account }).executed();
           console.log(tx0);
            const txResult = await cfx.sendTransaction(tx0);

            const response = await fetch( '/Transaction?Action=Tx&TX=' + txResult );
            
           break;
        default: 
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
        <button className="App-button-sm" onClick={onClick} disabled={disabled}>
          Withdraw
        </button>
       
      </header>
    </div>
  );


}
export default UnStakingApp;
