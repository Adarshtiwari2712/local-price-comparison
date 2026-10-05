// =========================
// GET ELEMENTS
// =========================

const backButton =
    document.getElementById("backButton");

const searchButton =
    document.getElementById("searchButton");

const productSearch =
    document.getElementById("productSearch");

const priceResults =
    document.getElementById("priceResults");

const resultCount =
    document.getElementById("resultCount");


// =========================
// USER LOCATION
// =========================

let userLatitude = null;
let userLongitude = null;


// =========================
// BACK BUTTON
// =========================

backButton.addEventListener(
    "click",
    function () {

        window.location.href =
            "../role.html";
    }
);


// =========================
// SEARCH BUTTON
// =========================

searchButton.addEventListener(
    "click",
    function () {

        searchProduct();
    }
);


// =========================
// ENTER KEY SEARCH
// =========================

productSearch.addEventListener(
    "keypress",
    function (event) {

        if (event.key === "Enter") {

            searchProduct();
        }
    }
);


// =========================
// GET USER LOCATION
// =========================

function getUserLocation() {

    return new Promise(
        function (resolve, reject) {

            if (!navigator.geolocation) {

                reject(
                    new Error(
                        "Location is not supported by your browser."
                    )
                );

                return;
            }

            navigator.geolocation.getCurrentPosition(

                function (position) {

                    userLatitude = position.coords.latitude;
                    userLongitude = position.coords.longitude;

                    console.log("USER LATITUDE:", userLatitude);
                    console.log("USER LONGITUDE:", userLongitude);

                    resolve();
                },

                function (error) {

                    switch (error.code) {

                        case error.PERMISSION_DENIED:

                            reject(
                                new Error(
                                    "Location permission is required to find shops within 5 km."
                                )
                            );

                            break;

                        case error.POSITION_UNAVAILABLE:

                            reject(
                                new Error(
                                    "Your location could not be determined."
                                )
                            );

                            break;

                        case error.TIMEOUT:

                            reject(
                                new Error(
                                    "Location request timed out."
                                )
                            );

                            break;

                        default:

                            reject(
                                new Error(
                                    "Unable to get your location."
                                )
                            );
                    }
                },

                {
                    enableHighAccuracy: true,
                    timeout: 10000,
                    maximumAge: 60000
                }
            );
        }
    );
}


// =========================
// SEARCH PRODUCT
// =========================

async function searchProduct() {

    const productName =
        productSearch.value.trim();

    if (productName === "") {

        showMessage(
            "Please enter a product name."
        );

        return;
    }


    priceResults.innerHTML = `
        <div class="empty-state">
            <div class="empty-icon">📍</div>
            <h3>Getting your location...</h3>
            <p>We are finding nearby shops.</p>
        </div>
    `;


    try {

        // Get location
        await getUserLocation();


        priceResults.innerHTML = `
            <div class="empty-state">
                <div class="empty-icon">🔎</div>
                <h3>Searching nearby shops...</h3>
                <p>Checking shops within 5 km.</p>
            </div>
        `;


        const encodedName =
            encodeURIComponent(productName);


        const response =
            await getRequest(
                "/prices/compare/nearby"
                + "?name="
                + encodedName
                + "&latitude="
                + userLatitude
                + "&longitude="
                + userLongitude
            );


        if (
            !response ||
            !response.prices
        ) {

            showMessage(
                "No nearby prices found."
            );

            return;
        }


        const prices =
            response.prices;


        if (prices.length === 0) {

            showMessage(
                "No shops found within 5 km."
            );

            return;
        }


        displayResults(
            prices,
            response.cheapestPrice
        );

    } catch (error) {

        console.error(
            "Search error:",
            error
        );

        showMessage(
            error.message ||
            "Unable to search nearby shops."
        );
    }
}


// =========================
// DISPLAY RESULTS
// =========================

function displayResults(
    prices,
    cheapestPrice
) {

    priceResults.innerHTML = "";


    resultCount.textContent =
        prices.length + " nearby shops";


    prices.forEach(
        function (price) {

            const card =
                document.createElement("div");

            card.classList.add(
                "price-card"
            );


            if (
                price.available &&
                price.amount === cheapestPrice
            ) {

                card.classList.add(
                    "best-price"
                );
            }


            const availabilityText =
                price.available
                    ? "✓ Available"
                    : "✕ Currently unavailable";


            let bestLabel = "";


            if (
                price.available &&
                price.amount === cheapestPrice
            ) {

                bestLabel = `
                    <span class="best-label">
                        BEST PRICE
                    </span>
                `;
            }


            const distance =
                Number(price.distanceKm)
                    .toFixed(2);


            card.innerHTML = `

                <div class="shop-info">

                    <div class="shop-icon">
                        🏪
                    </div>

                    <div>

                        <h3>
                            ${escapeHtml(
                price.storeName
            )}
                        </h3>

                        <p>
                            📍 ${distance} km away
                        </p>

                    </div>

                </div>


                <div class="shop-price">

                    ${bestLabel}

                    <div class="price">
                        ₹${price.amount}
                    </div>

                    <span class="availability">
                        ${availabilityText}
                    </span>

                </div>


                <div class="shop-contact">

                    <p>
                        📍 ${escapeHtml(
                price.storeAddress
            )}
                    </p>

                    ${
                price.storePhone
                    ? `
                                <p>
                                    📞 ${escapeHtml(
                        price.storePhone
                    )}
                                </p>
                              `
                    : ""
            }

                </div>

            `;


            priceResults.appendChild(
                card
            );
        }
    );
}


// =========================
// MESSAGE
// =========================

function showMessage(message) {

    priceResults.innerHTML = `

        <div class="empty-state">

            <div class="empty-icon">
                🔎
            </div>

            <h3>
                ${escapeHtml(message)}
            </h3>

            <p>
                Try searching for another product.
            </p>

        </div>

    `;


    resultCount.textContent =
        "0 shops";
}


// =========================
// ESCAPE HTML
// =========================

function escapeHtml(value) {

    if (
        value === null ||
        value === undefined
    ) {

        return "";
    }


    return String(value)
        .replace(
            /&/g,
            "&amp;"
        )
        .replace(
            /</g,
            "&lt;"
        )
        .replace(
            />/g,
            "&gt;"
        )
        .replace(
            /"/g,
            "&quot;"
        )
        .replace(
            /'/g,
            "&#039;"
        );
}