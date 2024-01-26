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


function submitEditProduct(event) {
    event.preventDefault()
    const saveButton = document.getElementsByClassName("save-product-btn")[0]
    const product_id = parseInt(saveButton.id.replace(/\D/g, ''), 10)
    // const product_id = parseInt(deleteButton.id.replace(/\D/g, ''), 10)

    const name = document.getElementById("name").value;
    const description = document.getElementById("description").value;
    const colors = document.getElementById("colors").value;
    const key_words = document.getElementById("key_words").value;
    const price = document.getElementById("price").value;
    const with_discount = document.getElementById("with_discount_checkbox").checked;
    const discounted_price = document.getElementById("discounted_price").value;
    const in_stock = document.getElementById("in_stock_checkbox").checked;
    const quantity = document.getElementById("quantity").value;
    const category_id = document.getElementById("category").value;

    const photoInput = document.getElementById("photo");
    const photoFile = photoInput.files[0];

    if (!photoFile) {
        const productDto = {
            id: product_id,
            name: name,
            description: description,
            colors: colors,
            keyWords: key_words,
            price: price,
            withDiscount: with_discount,
            discountedPrice: discounted_price,
            inStock: in_stock,
            quantity: quantity,
            categoryId: category_id
        };

        fetch("/admin/products/" + product_id, {
            method: "PUT",
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(productDto)
        })
            .then(() => {
                location.replace("/admin/products");
            })
            .catch(error => {
                console.error('An error occurred:', error);
            });
    } else {
        const reader = new FileReader();
        reader.onload = function (e) {
            const productDto = {
                id: product_id,
                name: name,
                description: description,
                colors: colors,
                keyWords: key_words,
                price: price,
                withDiscount: with_discount,
                discountedPrice: discounted_price,
                inStock: in_stock,
                quantity: quantity,
                categoryId: category_id,
                photo: Array.from(new Uint8Array(e.target.result))
            };

            fetch("/admin/products/" + product_id, {
                method: "PUT",
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(productDto)
            })
                .then(() => {
                    location.replace("/admin/products");
                })
                .catch(error => {
                    console.error('An error occurred:', error);
                });
        };
        reader.readAsArrayBuffer(photoFile);
    }
}