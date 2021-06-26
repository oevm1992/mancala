import axios from 'axios';
import { useEffect, useState } from 'react';
import './App.css';

const baseurl = "http://localhost:8080";
const afterMovement = { board: [6,6,6,6,6,6,0,6,6,6,6,6,6,0], player1Turn: true}

function App() {

const [boardStatus, setBoardStatus] = useState(afterMovement)

  useEffect( ()=> {
    startGame();
  },[])

  const startGame = async() => {
    await axios.put(baseurl+"/mancala/start")
    .then(response => setBoardStatus(response.data))
  }

  const makeMove = async(pitChosen) => {
    await axios.put(baseurl+"/mancala/move", {
      pitChosen: pitChosen
    })
    .then(response => {
      setBoardStatus(response.data)
      console.log(response.data.board.slice(0,6).every(pit => pit === 0) && response.data.player1Turn)
      console.log(response.data.board.slice(7,13).every(pit => pit === 0) && !response.data.player1Turn)
      console.log(response.data.board)
      if((response.data.board.slice(0,6).every(pit => pit === 0) && response.data.player1Turn) 
      || (response.data.board.slice(7,13).every(pit => pit === 0) && !response.data.player1Turn)){
        getWinner();
      }

    }).catch((error) => {
      alert(error.response.data.errorMessage)
    })
  }

  const getWinner = async() => {
    await axios.get(baseurl+"/mancala/winner")
    .then(response =>{
      alert(response.data.gameFinishedStatus)
      startGame()
    })
  }

  const isPlayer1Turn = (player1Turn) => {
    return player1Turn ? "It's player one turn" : "It's player two turn"
  }

  const getInvertedBoard = () => {

    let array = []

    for(let i = boardStatus.board.length-2; i > 6; i--){
      array.push(<div onClick = {() => makeMove(i)} className="pit" key={i}><p>{boardStatus.board[i]}</p></div>)
    }

    return array;
  }
console.log(boardStatus.board)



  return (

    <div className="App">
    <h1>Mancala</h1>
    
    <div className="panel">
         <p className = "text">Player Two</p>
         <div id="pointer-two" className="second player">
             <p>{boardStatus.board[13]}</p>
         </div>
         
         <div className="rows">
         <div className="row-two player">
            {getInvertedBoard()}
         </div>
             
         <div className="row-one player">
            {boardStatus.board.slice(0, 6).map( (pit, index) => 
              <div onClick = {() => makeMove(index)} className="pit" key={index}><p>{pit}</p></div>
            )}
         </div>
     </div>
     
     <div id="pointer-one" className="first player">
         <p>{boardStatus.board[6]}</p>
     </div>
     <p className = "text">Player One</p>
 </div>
 
 <p className="shift"> {isPlayer1Turn(boardStatus.player1Turn)} </p>
 
 <button onClick = {() => startGame()}>Start Game</button>
    </div>

  );
}

export default App;
