const cancelOrderButton = document.getElementsByClassName("cancel-order-btn")[0];
cancelOrderButton.addEventListener("click", cancelOrder)

function cancelOrder() {
    const product_id = parseInt(cancelOrderButton.id.replace(/\D/g, ''), 10)
    fetch("/orders/" + product_id + "/delete", {
        method: "DELETE"
    })
        .then(() => {
            window.location.replace("/orders")
        })
        .catch(error => {
            console.error('An error occurred:', error);
        });
}