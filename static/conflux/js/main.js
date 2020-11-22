const { Conflux } = require('js-conflux-sdk');
const conflux = new Conflux({url:'http://127.0.0.1'});


const account = null;// = cfx.wallet.addPrivateKey(PRIVATE_KEY); // create account instance
const staking_contract = null;// = cfx.InternalContract('Staking');
const PRIVATE_KEY = 'B2A8EBF93C38760FB70820B5F2F63B34F8E17382510BD5ED747CC56246426164';
const cfx = new Conflux({
  url: 'http://127.0.0.1',
  logger: console,
});
/*
$(function(){
    account = cfx.wallet.addPrivateKey(PRIVATE_KEY); // create account instance
    staking_contract = cfx.InternalContract('Staking');
})
*/

function deposit(amount)
{
    // deposit some amount of tokens
    staking_contract.deposit(amount).sendTransaction({
      from: account,
    }).confirmed();
}

function withdraw(amount)
{
    // withdraw some amount of tokens
    staking_contract.withdraw(amount).sendTransaction({
      from: account,
    }).confirmed();
}

function vote(amount,unblocknbr)
{
    // lock some tokens until some block number
    staking_contract.voteLock(amount,unblocknbr).sendTransaction({
      from: account,
    }).confirmed();
}
