const body = document.getElementById("body");
const categoryButtons = document.getElementsByClassName("btn-category");

body.addEventListener("click", (event) => {
    console.log("click")
    for (let i = 0; i < categoryButtons.length; i++) {
        if (categoryButtons[i].contains(event.target)) {
            const category_id = parseInt(categoryButtons[i].id.replace(/\D/g, ''), 10)
            if (category_id === 0) {
                window.location.replace("/products")
            }
            else {
                window.location.replace("/products?categoryId=" + category_id)
            }
        }
    }
})


const applyFiltersButton = document.getElementById("apply_filters_btn")
applyFiltersButton.addEventListener("click", applyFilters)

function applyFilters() {
    const sortByCost = document.getElementById('checkbox_cost').checked;
    const sortByCostAsc = document.getElementById('cost_sort_direction_asc').checked;
    const sortByNewness = document.getElementById('checkbox_newness').checked;
    const sortByNewnessAsc = document.getElementById('newness_sort_direction_asc').checked;
    const priceLimitFrom = document.getElementById('price_limit_from').value;
    const priceLimitTo = document.getElementById('price_limit_to').value;

    const requestURL = `/products?sortByCost=${sortByCost}&sortByCostAsc=${sortByCostAsc}&sortByNewness=${sortByNewness}&sortByNewnessAsc=${sortByNewnessAsc}&priceLimitFrom=${priceLimitFrom}&priceLimitTo=${priceLimitTo}`;
    window.location.replace(requestURL);
}

const findButton = document.getElementById("find_btn")
findButton.addEventListener("click", findProducts)

function findProducts() {
    const searchLine = document.getElementById('search_line').value;
    const url = "/products"
    if (searchLine.trim() === "") {
        window.location.replace(url);
    } else {
        window.location.replace(url + `?search=${searchLine}`);
    }
}