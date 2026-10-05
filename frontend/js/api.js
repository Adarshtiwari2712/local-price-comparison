// =========================
// BACKEND BASE URL
// =========================

const API_BASE_URL = "http://localhost:8082";


// =========================
// GET REQUEST
// =========================

async function getRequest(endpoint) {

    const response = await fetch(
        API_BASE_URL + endpoint
    );


    // Read response as text first

    const text = await response.text();


    // Show backend response in console

    console.log("Backend status:", response.status);
    console.log("Backend response:", text);


    // If backend returned an error

    if (!response.ok) {

        throw new Error(
            "Backend error " +
            response.status +
            ": " +
            text
        );

    }


    // Convert successful response to JSON

    return JSON.parse(text);
}