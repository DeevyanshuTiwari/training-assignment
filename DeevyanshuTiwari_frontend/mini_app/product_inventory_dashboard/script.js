// Data
let products = JSON.parse(localStorage.getItem("products")) || [
    { id: 1, name: "Laptop", price: 50000, stock: 5, category: "electronics" },
    { id: 2, name: "Mouse", price: 1000, stock: 10, category: "electronics" },
    { id: 3, name: "T-Shirt", price: 800, stock: 2, category: "clothing" },
    { id: 4, name: "Book", price: 500, stock: 5, category: "books" },
    { id: 5, name: "Notebook", price: 200, stock: 2, category: "books" },
    { id: 10, name: "Watch", price: 1500, stock: 5, category: "accessories" },
    { id: 11, name: "Sunglasses", price: 1000, stock: 6, category: "accessories" },
    { id: 12, name: "Bag", price: 1500, stock: 3, category: "accessories", }
];

// Saving data
function saveData() {
    localStorage.setItem("products", JSON.stringify(products));
}
let currentPage = 1;
let pageSize = 8;

// Show products 
function showProducts(list) {
    let container = document.getElementById("productGrid");
    container.innerHTML = "";

    let start = (currentPage - 1) * pageSize;
    let end = start + pageSize;
    let pageItems = list.slice(start, end);

    pageItems.forEach(p => {
        let div = document.createElement("div");
        div.className = "product-card";

        div.innerHTML = `
      <h3>${p.name}</h3>
      <p>Price: ₹${p.price}</p>
      <p>Stock: ${p.stock}</p>
      <p>Category: ${p.category}</p>
      <button onclick="deleteProduct(${p.id})">Delete</button>
    `;

        container.appendChild(div);
    });
    showPagination(list.length);
    if (list.length === 0) {
        container.innerHTML = "No Item"
    }
}

// Filters
function applyFilters() {
    let search = document.getElementById("searchInput").value.toLowerCase();
    let category = document.getElementById("categoryFilter").value;
    let lowStock = document.getElementById("lowStockFilter").checked;
    let sort = document.getElementById("sortBy").value;

    let result = products.filter(p => {
        return (
            p.name.toLowerCase().includes(search) &&
            (category === "all" || p.category === category) &&
            (!lowStock || p.stock < 5)
        );
    });

    // SORT
    if (sort === "priceLowToHigh") {
        result.sort((a, b) => a.price - b.price);
    } else if (sort === "priceHighToLow") {
        result.sort((a, b) => b.price - a.price);
    } else if (sort === "nameAZ") {
        result.sort((a, b) => a.name.localeCompare(b.name));
    } else if (sort === "nameZA") {
        result.sort((a, b) => b.name.localeCompare(a.name));
    }

    return result;
}

// Pagination
function showPagination(total) {
    let div = document.getElementById("pagination");
    div.innerHTML = "";

    let totalPages = Math.ceil(total / pageSize);

    let prev = document.createElement("button");
    prev.innerText = "Prev";
    prev.onclick = () => {
        currentPage--;
        updateUI();
    };
    prev.disabled = currentPage === 1;

    let next = document.createElement("button");
    next.innerText = "Next";
    next.onclick = () => {
        currentPage++;
        updateUI();
    };
    next.disabled = currentPage === totalPages;

    div.append(prev, next);
}

// Analytics
function showAnalytics() {
    document.getElementById("totalProductsValue").innerText = products.length;

    let totalValue = 0;
    let outOfStock = 0;

    products.forEach(p => {
        totalValue += p.price * p.stock;
        if (p.stock === 0) outOfStock++;
    });

    document.getElementById("totalInventoryValue").innerText = "₹" + totalValue;
    document.getElementById("outOfStockValue").innerText = outOfStock;
}

// Update UI
function updateUI() {
    let data = applyFilters();
    showProducts(data);
    showAnalytics();
}
// Add product
function addProduct() {
    let name = document.getElementById("productName").value;
    let price = Number(document.getElementById("productPrice").value);
    let stock = Number(document.getElementById("productStock").value);
    let category = document.getElementById("productCategory").value;

    if (!name || price <= 0 || stock < 0 || !category) {
        alert("Invalid Input");
        return;
    }

    let newProduct = {
        id: Date.now(),
        name,
        price,
        stock,
        category
    };

    products.push(newProduct);
    saveData();
    updateUI();
    location.reload();
}

//Delete Product
function deleteProduct(id) {
    products = products.filter(p => p.id !== id);
    saveData();
    updateUI();
}

//calling update UI
updateUI();