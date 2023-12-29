const deleteButton = document.getElementsByClassName("delete-product-btn")[0];
deleteButton.addEventListener("click", deleteProduct)

function deleteProduct() {
    const product_id = parseInt(deleteButton.id.replace(/\D/g, ''), 10)
    fetch("/admin/products/" + product_id + "/delete", {
        method: "DELETE"
    })
    .then(() => {
        window.location.replace("/admin/products")
    })
    .catch(error => {
        console.error('An error occurred:', error);
    });
}