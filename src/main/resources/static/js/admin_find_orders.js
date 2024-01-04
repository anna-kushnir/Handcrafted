const findByPhoneButton = document.getElementById("find_by_phone_btn")
findByPhoneButton.addEventListener("click", findByPhone)

function findByPhone() {
    const userPhone = document.getElementById('user_phone').value;
    const requestURL = `/admin/orders/all/find?userPhone=${userPhone}`;
    window.location.replace(requestURL);
}

const findByOrderIdButton = document.getElementById("find_by_order_id_btn")
findByOrderIdButton.addEventListener("click", findByOrderId)

function findByOrderId() {
    const orderId = document.getElementById('order_id').value;
    const requestURL = `/admin/orders/all/find?orderId=${orderId}`;
    window.location.replace(requestURL);
}