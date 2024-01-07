function submitAddProduct(event) {
    event.preventDefault()
    const name = document.getElementById("name").value;
    const description = document.getElementById("description").value;
    const colors = document.getElementById("colors").value;
    const key_words = document.getElementById("key_words").value;
    const price = document.getElementById("price").value;
    const in_stock = document.getElementById("in_stock_checkbox").checked;
    const quantity = document.getElementById("quantity").value;
    const category_id = document.getElementById("category").value;

    const photoInput = document.getElementById("photo");
    const photoFile = photoInput.files[0];

    const reader = new FileReader();
    reader.onload = function (e) {
        const productDto = {
            name: name,
            description: description,
            colors: colors,
            keyWords: key_words,
            price: price,
            inStock: in_stock,
            quantity: quantity,
            categoryId: category_id,
            photo: Array.from(new Uint8Array(e.target.result))
        };

        fetch("/admin/products", {
            method: "POST",
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