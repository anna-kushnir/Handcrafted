const body = document.getElementById("body");
const categoryButtons = document.getElementsByClassName("btn-category");

const url = "/admin/products";

body.addEventListener("click", (event) => {
    console.log("click")
    for (let i = 0; i < categoryButtons.length; i++) {
        if (categoryButtons[i].contains(event.target)) {
            const category_id = parseInt(categoryButtons[i].id.replace(/\D/g, ''), 10)
            if (category_id === 0) {
                window.location.replace(url)
            }
            else {
                window.location.replace(url + "?categoryId=" + category_id)
            }
        }
    }
})