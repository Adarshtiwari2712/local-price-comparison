const API_BASE_URL = "http://localhost:8082";

let myStore = null;
let myProducts = [];
let myPrices = [];


// ======================================================
// COMMON HELPERS
// ======================================================

function getToken() {
    return localStorage.getItem("token");
}


function getAuthHeaders() {

    return {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + getToken()
    };
}


async function getErrorMessage(response) {

    try {

        const data = await response.json();

        if (data.message) {
            return data.message;
        }

        if (data.error) {
            return data.error;
        }

        return "Something went wrong";

    } catch (error) {

        return "Something went wrong";
    }
}


// ======================================================
// PAGE INITIALIZATION
// ======================================================

document.addEventListener("DOMContentLoaded", function () {

    checkLogin();

    loadDashboard();

});


// ======================================================
// CHECK LOGIN
// ======================================================

function checkLogin() {

    const token = getToken();

    if (!token) {

        window.location.href =
            "../shopkeeper/login.html";

        return;
    }
}


// ======================================================
// LOAD COMPLETE DASHBOARD
// ======================================================

async function loadDashboard() {

    try {

        await loadMyStore();

        await loadMyProducts();

        await loadMyPrices();

        updateDashboardStats();

    } catch (error) {

        console.error(
            "Dashboard loading error:",
            error
        );

        alert(
            "Unable to load dashboard data"
        );
    }
}


// ======================================================
// LOAD MY STORE
// ======================================================

async function loadMyStore() {

    const response = await fetch(
        API_BASE_URL + "/stores/my-store",
        {
            method: "GET",
            headers: {
                "Authorization":
                    "Bearer " + getToken()
            }
        }
    );


    if (!response.ok) {

        if (response.status === 401 ||
            response.status === 403) {

            logout();
            return;
        }

        throw new Error(
            await getErrorMessage(response)
        );
    }


    myStore = await response.json();


    // Store name
    const storeNameElement =
        document.getElementById("headerStoreName");

    if (storeNameElement) {

        storeNameElement.textContent =
            myStore.name;
    }


    // Store address
    const storeAddressElement =
        document.getElementById("storeAddress");

    if (storeAddressElement) {

        storeAddressElement.textContent =
            myStore.address;
    }


    // Store phone
    const storePhoneElement =
        document.getElementById("storePhone");

    if (storePhoneElement) {

        storePhoneElement.textContent =
            myStore.phone || "Not provided";
    }


    // Store edit inputs
    const editStoreName =
        document.getElementById("editStoreName");

    if (editStoreName) {

        editStoreName.value =
            myStore.name || "";
    }


    const editStoreAddress =
        document.getElementById("editStoreAddress");

    if (editStoreAddress) {

        editStoreAddress.value =
            myStore.address || "";
    }


    const editStorePhone =
        document.getElementById("editStorePhone");

    if (editStorePhone) {

        editStorePhone.value =
            myStore.phone || "";
    }
}


// ======================================================
// LOAD MY PRODUCTS
// ======================================================

async function loadMyProducts() {

    const response = await fetch(
        API_BASE_URL + "/products/my-store",
        {
            method: "GET",
            headers: {
                "Authorization":
                    "Bearer " + getToken()
            }
        }
    );


    if (!response.ok) {

        throw new Error(
            await getErrorMessage(response)
        );
    }


    myProducts =
        await response.json();


    renderMyProducts();

    updateDashboardStats();
}


// ======================================================
// DISPLAY MY PRODUCTS
// ======================================================

function renderMyProducts() {

    const container =
        document.getElementById("productsList");

    if (!container) {
        return;
    }


    if (myProducts.length === 0) {

        container.innerHTML = `
            <div class="empty-state">
                <p>No products added yet.</p>
            </div>
        `;

        return;
    }


    let html = "";


    myProducts.forEach(product => {

        html += `
            <div class="product-card">

                <div class="product-info">

                    <h4>
                        ${escapeHtml(product.productName)}
                    </h4>

                    <p>
                        Price:
                        ₹${product.price}
                    </p>

                    <p>
                        Availability:
                        ${
            product.available
                ? "Available"
                : "Not Available"
        }
                    </p>

                </div>


                <div class="product-actions">

                    <button
                        onclick="editProduct(${product.productId})"
                    >
                        Edit
                    </button>

                    <button
                        class="delete-btn"
                        onclick="deleteProduct(${product.productId})"
                    >
                        Delete
                    </button>

                </div>

            </div>
        `;
    });


    container.innerHTML = html;
}


// ======================================================
// ADD PRODUCT
// ======================================================

async function addProduct() {

    const nameElement =
        document.getElementById(
            "addProductName"
        );

    const priceElement =
        document.getElementById(
            "addProductPrice"
        );

    const availableElement =
        document.getElementById(
            "addProductAvailable"
        );


    const name =
        nameElement.value.trim();

    const amount =
        Number(priceElement.value);

    const available =
        availableElement.value === "true";


    // -------------------------------
    // Validation
    // -------------------------------

    if (!name) {

        alert(
            "Please enter product name"
        );

        return;
    }


    if (!amount || amount <= 0) {

        alert(
            "Please enter a valid price"
        );

        return;
    }


    if (!myStore) {

        alert(
            "Store information is not loaded"
        );

        return;
    }


    try {

        // ==================================================
        // STEP 1
        // CREATE PRODUCT FOR THIS SHOPKEEPER
        // ==================================================

        const productResponse =
            await fetch(
                API_BASE_URL + "/products",
                {
                    method: "POST",

                    headers:
                        getAuthHeaders(),

                    body: JSON.stringify({

                        name: name

                    })
                }
            );


        if (!productResponse.ok) {

            const message =
                await getErrorMessage(
                    productResponse
                );

            throw new Error(message);
        }


        const product =
            await productResponse.json();


        console.log(
            "Product created:",
            product
        );


        // ==================================================
        // STEP 2
        // ADD PRICE FOR THIS STORE
        // ==================================================

        const priceResponse =
            await fetch(
                API_BASE_URL + "/prices",
                {
                    method: "POST",

                    headers:
                        getAuthHeaders(),

                    body: JSON.stringify({

                        amount: amount,

                        available: available,

                        productId: product.id,

                        storeId: myStore.id

                    })
                }
            );


        if (!priceResponse.ok) {

            const message =
                await getErrorMessage(
                    priceResponse
                );

            throw new Error(message);
        }


        const price =
            await priceResponse.json();


        console.log(
            "Price created:",
            price
        );


        // ==================================================
        // SUCCESS
        // ==================================================

        alert(
            "Product added successfully!"
        );


        // Refresh dashboard
        await loadMyProducts();

        await loadMyPrices();

        updateDashboardStats();


        // Clear form
        nameElement.value = "";

        priceElement.value = "";

        availableElement.value =
            "true";


    } catch (error) {

        console.error(
            "Add product error:",
            error
        );


        alert(
            error.message ||
            "Unable to add product"
        );
    }
}


// ======================================================
// EDIT PRODUCT
// ======================================================

async function editProduct(productId) {

    const product =
        myProducts.find(
            p =>
                p.productId === productId
        );


    if (!product) {

        alert(
            "Product not found"
        );

        return;
    }


    const newName =
        prompt(
            "Enter new product name:",
            product.productName
        );


    if (newName === null) {
        return;
    }


    const name =
        newName.trim();


    if (!name) {

        alert(
            "Product name cannot be empty"
        );

        return;
    }


    try {

        const response =
            await fetch(
                API_BASE_URL +
                "/products/" +
                productId,
                {
                    method: "PUT",

                    headers:
                        getAuthHeaders(),

                    body: JSON.stringify({

                        name: name

                    })
                }
            );


        if (!response.ok) {

            const message =
                await getErrorMessage(
                    response
                );

            throw new Error(message);
        }


        alert(
            "Product updated successfully!"
        );


        await loadMyProducts();

        await loadMyPrices();


    } catch (error) {

        console.error(
            "Update product error:",
            error
        );


        alert(
            error.message ||
            "Unable to update product"
        );
    }
}


// ======================================================
// DELETE PRODUCT
// ======================================================

async function deleteProduct(productId) {

    const product =
        myProducts.find(
            p =>
                p.productId === productId
        );


    if (!product) {

        alert(
            "Product not found"
        );

        return;
    }


    const confirmed =
        confirm(
            `Are you sure you want to delete "${product.productName}"?`
        );


    if (!confirmed) {
        return;
    }


    try {

        const response =
            await fetch(
                API_BASE_URL +
                "/products/" +
                productId,
                {
                    method: "DELETE",

                    headers: {
                        "Authorization":
                            "Bearer " + getToken()
                    }
                }
            );


        if (!response.ok) {

            const message =
                await getErrorMessage(
                    response
                );

            throw new Error(message);
        }


        alert(
            "Product deleted successfully!"
        );


        await loadMyProducts();

        await loadMyPrices();


    } catch (error) {

        console.error(
            "Delete product error:",
            error
        );


        alert(
            error.message ||
            "Unable to delete product"
        );
    }
}


// ======================================================
// LOAD MY PRICES
// ======================================================

async function loadMyPrices() {

    const response =
        await fetch(
            API_BASE_URL +
            "/prices/my-store",
            {
                method: "GET",

                headers: {
                    "Authorization":
                        "Bearer " + getToken()
                }
            }
        );


    if (!response.ok) {

        throw new Error(
            await getErrorMessage(response)
        );
    }


    myPrices =
        await response.json();


    renderMyPrices();

    updateDashboardStats();
}


// ======================================================
// DISPLAY MY PRICES
// ======================================================

function renderMyPrices() {

    const container =
        document.getElementById(
            "pricesList"
        );


    if (!container) {
        return;
    }


    if (myPrices.length === 0) {

        container.innerHTML = `
            <div class="empty-state">
                <p>No prices added yet.</p>
            </div>
        `;

        return;
    }


    let html = "";


    myPrices.forEach(price => {

        html += `
            <div class="price-card">

                <div class="price-info">

                    <h4>
                        ${escapeHtml(
            price.productName
        )}
                    </h4>

                    <p>
                        Price:
                        ₹${price.amount}
                    </p>

                    <p>
                        ${
            price.available
                ? "Available"
                : "Not Available"
        }
                    </p>

                </div>


                <div class="price-actions">

                    <button
                        onclick="editPrice(${price.id})"
                    >
                        Edit
                    </button>

                    <button
                        class="delete-btn"
                        onclick="deletePrice(${price.id})"
                    >
                        Delete
                    </button>

                </div>

            </div>
        `;
    });


    container.innerHTML = html;
}


// ======================================================
// EDIT PRICE
// ======================================================

async function editPrice(priceId) {

    const price =
        myPrices.find(
            p =>
                p.id === priceId
        );


    if (!price) {

        alert(
            "Price not found"
        );

        return;
    }


    const newAmount =
        prompt(
            "Enter new price:",
            price.amount
        );


    if (newAmount === null) {
        return;
    }


    const amount =
        Number(newAmount);


    if (!amount || amount <= 0) {

        alert(
            "Please enter a valid price"
        );

        return;
    }


    const newAvailability =
        confirm(
            "Is this product currently available?"
        );


    try {

        const response =
            await fetch(
                API_BASE_URL +
                "/prices/" +
                priceId,
                {
                    method: "PUT",

                    headers:
                        getAuthHeaders(),

                    body: JSON.stringify({

                        amount: amount,

                        available:
                        newAvailability

                    })
                }
            );


        if (!response.ok) {

            const message =
                await getErrorMessage(
                    response
                );

            throw new Error(message);
        }


        alert(
            "Price updated successfully!"
        );


        await loadMyPrices();

        await loadMyProducts();


    } catch (error) {

        console.error(
            "Update price error:",
            error
        );


        alert(
            error.message ||
            "Unable to update price"
        );
    }
}


// ======================================================
// DELETE PRICE
// ======================================================

async function deletePrice(priceId) {

    const price =
        myPrices.find(
            p =>
                p.id === priceId
        );


    if (!price) {

        alert(
            "Price not found"
        );

        return;
    }


    const confirmed =
        confirm(
            `Delete price for "${price.productName}"?`
        );


    if (!confirmed) {
        return;
    }


    try {

        const response =
            await fetch(
                API_BASE_URL +
                "/prices/" +
                priceId,
                {
                    method: "DELETE",

                    headers: {
                        "Authorization":
                            "Bearer " + getToken()
                    }
                }
            );


        if (!response.ok) {

            const message =
                await getErrorMessage(
                    response
                );

            throw new Error(message);
        }


        alert(
            "Price deleted successfully!"
        );


        await loadMyPrices();

        await loadMyProducts();


    } catch (error) {

        console.error(
            "Delete price error:",
            error
        );


        alert(
            error.message ||
            "Unable to delete price"
        );
    }
}


// ======================================================
// UPDATE STORE
// ======================================================

async function updateStore() {

    if (!myStore) {

        alert(
            "Store information is not loaded"
        );

        return;
    }


    const name =
        document.getElementById(
            "editStoreName"
        ).value.trim();


    const address =
        document.getElementById(
            "editStoreAddress"
        ).value.trim();


    const phone =
        document.getElementById(
            "editStorePhone"
        ).value.trim();


    if (!name) {

        alert(
            "Store name is required"
        );

        return;
    }


    if (!address) {

        alert(
            "Store address is required"
        );

        return;
    }


    // ==============================================
    // CHECK STORE LOCATION
    // ==============================================

    if (
        myStore.latitude === undefined ||
        myStore.latitude === null ||
        myStore.longitude === undefined ||
        myStore.longitude === null
    ) {

        alert(
            "Store location is missing. Please set your store location first."
        );

        return;
    }


    try {

        const response =
            await fetch(
                API_BASE_URL +
                "/stores/" +
                myStore.id,
                {
                    method: "PUT",

                    headers:
                        getAuthHeaders(),

                    body: JSON.stringify({

                        name: name,

                        address: address,

                        phone: phone,

                        // Keep existing store location
                        latitude:
                        myStore.latitude,

                        longitude:
                        myStore.longitude

                    })
                }
            );


        if (!response.ok) {

            const message =
                await getErrorMessage(
                    response
                );

            throw new Error(message);
        }


        myStore =
            await response.json();


        alert(
            "Store updated successfully!"
        );


        await loadMyStore();


    } catch (error) {

        console.error(
            "Update store error:",
            error
        );


        alert(
            error.message ||
            "Unable to update store"
        );
    }
}


// ======================================================
// DASHBOARD STATS
// ======================================================

function updateDashboardStats() {

    const productCount =
        document.getElementById(
            "productCount"
        );


    if (productCount) {

        productCount.textContent =
            myProducts.length;
    }


    const priceCount =
        document.getElementById(
            "priceCount"
        );


    if (priceCount) {

        priceCount.textContent =
            myPrices.length;
    }


    const storeCount =
        document.getElementById(
            "storeCount"
        );


    if (storeCount) {

        storeCount.textContent =
            myStore ? "1" : "0";
    }
}


// ======================================================
// PANEL NAVIGATION
// ======================================================

function showPanel(panelId) {

    const panels =
        document.querySelectorAll(
            ".dashboard-panel"
        );


    panels.forEach(
        panel => {

            panel.style.display =
                "none";

        }
    );


    const selectedPanel =
        document.getElementById(
            panelId
        );


    if (selectedPanel) {

        selectedPanel.style.display =
            "block";
    }
}


// ======================================================
// LOGOUT
// ======================================================

function logout() {

    localStorage.removeItem(
        "token"
    );

    localStorage.removeItem(
        "user"
    );


    window.location.href =
        "../shopkeeper/login.html";
}


// ======================================================
// HTML ESCAPE
// ======================================================

function escapeHtml(value) {

    if (value === null ||
        value === undefined) {

        return "";
    }


    return String(value)
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}