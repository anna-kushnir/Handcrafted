const body = document.getElementById("body");
const removeButtons = document.getElementsByClassName("remove-btn");
const transferToFavButtons = document.getElementsByClassName("transfer-to-fav-btn");
const quantityInputs = document.getElementsByClassName("quantity-input");

const url = "/cart/products/";

body.addEventListener("click", (event) => {
    console.log("click")
    for (let i = 0; i < removeButtons.length; i++) {
        if (removeButtons[i].contains(event.target)) {
            const product_id = parseInt(removeButtons[i].id.replace(/\D/g, ''), 10)
            fetch(url + product_id + "/deleteFromCart", {
                method: "DELETE"
            })
                .then(() => {
                    location.replace("/cart")
                })
                .catch(error => {
                    console.error('An error occurred:', error);
                });
        }
    }

    for (let i = 0; i < transferToFavButtons.length; i++) {
        if (transferToFavButtons[i].contains(event.target)) {
            const product_id = parseInt(transferToFavButtons[i].id.replace(/\D/g, ''), 10)
            fetch(url + product_id + "/transferToFavorite", {
                method: "PUT"
            })
                .then(() => {
                    location.replace("/cart")
                })
                .catch(error => {
                    console.error('An error occurred:', error);
                });
        }
    }
})

body.addEventListener("change", (event) => {
    console.log("change")
    for (let i = 0; i < quantityInputs.length; i++) {
        if (quantityInputs[i].contains(event.target)) {
            const product_id = parseInt(quantityInputs[i].id.replace(/\D/g, ''), 10)
            fetch(url + product_id + "/updateQuantity/" + quantityInputs[i].value, {
                method: "PUT"
            })
                .then(() => {
                    location.replace("/cart")
                })
                .catch(error => {
                    console.error('An error occurred:', error);
                });
        }
    }
})