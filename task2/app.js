
let itemsList = document.getElementById ('itemsList') ; 

let items = ["Milk", "Bread", "Banana", "Apple", "Mangoes", "Eggs"] ;
let shoppingList = [] ;

for (const item of items) {
  const li = document.createElement ('li') ;
  li.textContent = item ;
  itemsList.append (li) ;
}

let btn = document.getElementById ('btn') ;


btn.addEventListener ('click', () => {

  let itemAdded = document.getElementById ('inp').value.trim () ;
  let err = document.getElementById ('error') ;
  let out = document.getElementById ('output') ;

  if (!items.includes (itemAdded)) {
    err.textContent = `Not in List` ;
    out.textContent = `` ;
  }
  else if (shoppingList.includes (itemAdded)) {
    err.textContent = `Cannot add added element to shopping list again!!` ;
    out.textContent = `` ;
  }
  else {
    err.textContent = `` ;
    out.textContent = `Added to shopping list` ;
    shoppingList.push (itemAdded) ;
  }

}) ;
