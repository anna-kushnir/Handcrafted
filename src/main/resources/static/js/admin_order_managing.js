function acceptOrderWithPickup() {
    const acceptButton = document.getElementsByClassName("accept-order-with-pickup")[0]
    const order_id = parseInt(acceptButton.id.replace(/\D/g, ''), 10)
    const receipt_date = document.getElementById("receipt_date")
    if (receipt_date.value === "") {
        receipt_date.style.outline = "2px solid red";
        receipt_date.style.borderColor = "red";
        receipt_date.focus();
        alert('Date & Time of receipt cannot be empty');
        return
    }
    if (new Date(receipt_date.value) < Date.now()) {
        receipt_date.style.outline = "2px solid red";
        receipt_date.style.borderColor = "red";
        receipt_date.focus();
        alert('Date & Time of receipt cannot be less than the current date & time');
        return
    }
    fetch("/admin/orders/" + order_id + "/acceptOrderWithPickup", {
        method: "PUT",
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(receipt_date.value)
    })
        .then(() => {
            location.replace("/admin/orders/accepted/" + order_id);
        })
        .catch(error => {
            console.error('An error occurred:', error);
        });
}

function acceptOrderWithDelivery() {
    const acceptButton = document.getElementsByClassName("accept-order-with-delivery")[0]
    const order_id = parseInt(acceptButton.id.replace(/\D/g, ''), 10)
    fetch("/admin/orders/" + order_id + "/acceptOrderWithDelivery", {
        method: "PUT"
    })
        .then(() => {
            location.replace("/admin/orders/accepted/" + order_id);
        })
        .catch(error => {
            console.error('An error occurred:', error);
        });
}

function declineOrder() {
    const declineButton = document.getElementsByClassName("decline-order")[0]
    const order_id = parseInt(declineButton.id.replace(/\D/g, ''), 10)
    fetch("/admin/orders/" + order_id + "/declineOrder", {
        method: "DELETE"
    })
        .then(() => {
            location.replace("/admin/orders/in_processing");
        })
        .catch(error => {
            console.error('An error occurred:', error);
        });
}

function markOrderAsForwardedForDelivery() {
    const markButton = document.getElementsByClassName("mark-order-as-forwarded-for-delivery")[0]
    const order_id = parseInt(markButton.id.replace(/\D/g, ''), 10)
    const invoice_number = document.getElementById("invoice_number")
    if (invoice_number.value.trim().length !== 16) {
        invoice_number.style.outline = "2px solid red";
        invoice_number.style.borderColor = "red";
        invoice_number.focus();
        alert('Invoice number must contain 16 digits');
        return
    }
    fetch("/admin/orders/" + order_id + "/markOrderAsForwardedForDelivery", {
        method: "PUT",
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(invoice_number.value)
    })
        .then(() => {
            location.replace("/admin/orders/forwarded_for_delivery/" + order_id);
        })
        .catch(error => {
            console.error('An error occurred:', error);
        });
}

function markOrderAsReceived() {
    const markButton = document.getElementsByClassName("mark-order-as-received")[0]
    const order_id = parseInt(markButton.id.replace(/\D/g, ''), 10)
    fetch("/admin/orders/" + order_id + "/markOrderAsReceived", {
        method: "PUT"
    })
        .then(() => {
            location.replace("/admin/orders/received/" + order_id);
        })
        .catch(error => {
            console.error('An error occurred:', error);
        });
}
