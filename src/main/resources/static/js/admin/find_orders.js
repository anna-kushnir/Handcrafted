const findByPhoneButton = document.getElementById("find_by_phone_btn")
findByPhoneButton.addEventListener("click", findByPhone)

function findByPhone() {
    const userPhone = document.getElementById('user_phone').value;
    const url = "/admin/orders/all/find"
    if (userPhone.length !== 9 || userPhone < 0) {
        window.location.replace(url);
    } else {
        window.location.replace(url + `?userPhone=${userPhone}`);
    }
}

const findByOrderIdButton = document.getElementById("find_by_order_id_btn")
findByOrderIdButton.addEventListener("click", findByOrderId)

function findByOrderId() {
    const orderId = document.getElementById('order_id').value;
    const url = "/admin/orders/all/find"
    if (orderId <= 0) {
        window.location.replace(url);
    } else {
        window.location.replace(url + `?orderId=${orderId}`);
    }
}