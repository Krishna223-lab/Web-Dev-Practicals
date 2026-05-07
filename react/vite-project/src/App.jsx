import { useState } from "react";



function App () {

  let [cnt, setCnt] = useState (0) ;
  function increment () {
    cnt ++ ;
    setCnt (cnt) ;
  }
  function decrement () {
    cnt --; 
    setCnt (cnt) ;
  }

  return (
    <>
      <div style={{backgroundColor : 'black', display : 'flex', flexDirection : 'column',alignItems : 'center', justifyContent : 'center', height : '100vh'}}>
        <p style={{color : 'white'}}>Counter : {cnt}</p>
        <button onClick={increment}>Increment</button>
        <button onClick={decrement}>Decrement</button>
      </div>
    </>
  )
}

export default App ; 