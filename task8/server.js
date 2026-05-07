const express = require ('express') ;
const app = express () ;


app.use (express.json ()) ;
const PORT = 3000 ; 

const restaurants = [
  {
    id : 1, 
    name : "Pizza Hut", 
    menu : [
      {name : "Chicken Pizza", price : "399"}, 
      {name : "Paneer Pizza", price : "299"}, 
      {name : "Margherita Cheese Pizza", price : "499"}, 
      {name : "Pepperoni Cheese Pizza", price : "349"}, 
    ]

  } , 
  {
    id : 2, 
    name : "Mc Donalds", 
    menu : [
      {name : "Chicken Burger", price : "399"}, 
      {name : "Chicken Nuggets", price : "149"}, 
      {name : "Coke", price : "49"}, 
      {name : "Aloo Tikki Burger", price : "199"}, 
    ]
  } , 
  {
    id : 3, 
    name : "Subway", 
    menu : [
      {name : "Tuna Sandwitch", price : "399"}, 
      {name : "Paneer Tikka", price : "149"}, 
      {name : "Pepsi", price : "49"}, 
      {name : "Chicken Shawarma", price : "149"}, 
    ]
  }

]

// get restaurant of restaurantID menu
app.get ("/restaurant/:restaurantID/menu", (req, res) => {
  const restaurantId = req.params.restaurantID ; 
  const restaurant = restaurants.find (r => r.id == restaurantId) ; 
  if (!restaurant) res.status(404).send (`Did not find restaurant`) ;

  res.send (restaurant) ;
}) ;

app.post ("/order", (req, res) => {
  const {restaurantId, itemName} = req.body ; 

  const restaurant = restaurants.find (r => r.id == restaurantId) ; 
  if (!restaurant) res.status(404).send (`Did not find restaurant`) ;

  res.send(`<h2>Order Placed</h2><p>You ordered ${itemName} from ${restaurant.name}.</p>`);
})

app.listen (PORT, ()=> console.log (`Listening at port : ${PORT}`))