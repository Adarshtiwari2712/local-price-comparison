// ===============================
// ROLE PAGE NAVIGATION
// ===============================

const userButton = document.getElementById("userButton");
const shopkeeperButton = document.getElementById("shopkeeperButton");
const backButton = document.getElementById("backButton");


// ===============================
// USER
// ===============================

if (userButton) {
    userButton.addEventListener("click", function () {
        window.location.href = "user/user.html";
    });
}


// ===============================
// SHOPKEEPER
// ===============================

if (shopkeeperButton) {
    shopkeeperButton.addEventListener("click", function () {
        window.location.href = "shopkeeper/login.html";
    });
}


// ===============================
// BACK
// ===============================

if (backButton) {
    backButton.addEventListener("click", function () {
        window.location.href = "../index.html";
    });
}