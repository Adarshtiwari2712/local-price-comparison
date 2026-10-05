// =========================
// GET BUTTONS
// =========================

const getStartedBtn = document.getElementById("getStartedBtn");
const navGetStarted = document.getElementById("navGetStarted");
const learnMoreBtn = document.getElementById("learnMoreBtn");


// =========================
// GET STARTED
// =========================

function openRoleSelection() {
    window.location.href = "pages/role.html";
}


// =========================
// GET STARTED BUTTONS
// =========================

getStartedBtn.addEventListener("click", openRoleSelection);

navGetStarted.addEventListener("click", openRoleSelection);


// =========================
// LEARN MORE
// =========================

learnMoreBtn.addEventListener("click", function () {

    alert( "Local Price Compare helps users compare prices from nearby local shops.");

});