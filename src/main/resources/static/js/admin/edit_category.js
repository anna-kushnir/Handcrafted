const deleteButton = document.getElementsByClassName("delete-category-btn")[0];
deleteButton.addEventListener("click", deleteCategory)

function deleteCategory() {
    const category_id = parseInt(deleteButton.id.replace(/\D/g, ''), 10)
    fetch("/admin/categories/" + category_id + "/delete", {
        method: "DELETE"
    })
        .then(() => {
            window.location.replace("/admin/categories")
        })
        .catch(error => {
            console.error('An error occurred:', error);
        });
}