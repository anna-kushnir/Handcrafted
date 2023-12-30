const body = document.getElementById("body");
const categoryButtons = document.getElementsByClassName("btn-category");

body.addEventListener("click", (event) => {
    console.log("click")
    for (let i = 0; i < categoryButtons.length; i++) {
        if (categoryButtons[i].contains(event.target)) {
            const category_id = parseInt(categoryButtons[i].id.replace(/\D/g, ''), 10)
            if (category_id === 0) {
                window.location.replace("/admin/products")
            }
            else {
                window.location.replace("/admin/products?categoryId=" + category_id)
            }
        }
    }
})