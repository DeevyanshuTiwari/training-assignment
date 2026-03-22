// Data
let products = JSON.parse(localStorage.getItem("products")) || [
    { id: 1, name: "Laptop", price: 50000, stock: 5, category: "electronics" },
    { id: 2, name: "Mouse", price: 1000, stock: 10, category: "electronics" },
    { id: 3, name: "T-Shirt", price: 800, stock: 2, category: "clothing" },
    { id: 4, name: "Book", price: 500, stock: 5, category: "books" },
    { id: 5, name: "Notebook", price: 200, stock: 2, category: "books" },
    { id: 10, name: "Watch", price: 1500, stock: 5,category: "accessories" },
    { id: 11, name: "Sunglasses", price: 1000, stock: 6,category: "accessories" },
    { id: 12, name: "Bag", price: 1500,stock: 3,category: "accessories", }
];