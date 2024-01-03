function acceptOrderWithPickup() {
    const acceptButton = document.getElementsByClassName("accept-order-with-pickup")[0]
    const order_id = parseInt(acceptButton.id.replace(/\D/g, ''), 10)
    const receipt_date = document.getElementById("receipt_date")
    if (receipt_date.value === "") {
        receipt_date.style.outline = "2px solid red";
        receipt_date.style.borderColor = "red";
        return
    }
    fetch("/admin/orders/" + order_id + "/acceptOrderWithPickup", {
        method: "PUT",
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(receipt_date.value)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error();
            }
        })
        .then(() => {
            location.replace("/admin/orders/accepted/" + order_id);
        })
        .catch(_ => {
            location.replace("/admin/orders/in_processing/");
        });
}

function declineOrderWithPickup() {
    const declineButton = document.getElementsByClassName("decline-order-with-pickup")[0]
    const order_id = parseInt(declineButton.id.replace(/\D/g, ''), 10)
    fetch("/admin/orders/" + order_id + "/declineOrderWithPickup", {
        method: "DELETE"
    })
        .then(() => {
            location.replace("/admin/orders/in_processing");
        })
        .catch(error => {
            console.error('An error occurred:', error);
        });
}