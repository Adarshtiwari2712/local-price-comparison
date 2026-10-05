const API_BASE_URL = "http://localhost:8082";


// =====================================================
// REGISTER
// =====================================================

const registerForm =
    document.getElementById("registerForm");

if (registerForm) {

    const registerMessage =
        document.getElementById("registerMessage");

    const registerButton =
        document.getElementById("registerButton");

    registerForm.addEventListener(
        "submit",
        async function (event) {

            event.preventDefault();


            // =========================
            // GET FORM VALUES
            // =========================

            const name =
                document.getElementById("name").value.trim();

            const email =
                document.getElementById("email").value.trim();

            const password =
                document.getElementById("password").value;

            const storeName =
                document.getElementById("storeName").value.trim();

            const address =
                document.getElementById("address").value.trim();

            const phone =
                document.getElementById("phone").value.trim();


            // =========================
            // GET STORE LOCATION
            // =========================

            const latitude =
                document.getElementById("latitude").value;

            const longitude =
                document.getElementById("longitude").value;


            // =========================
            // REQUIRED FIELD VALIDATION
            // =========================

            if (
                name === "" ||
                email === "" ||
                password === "" ||
                storeName === "" ||
                address === ""
            ) {

                showMessage(
                    registerMessage,
                    "Please fill in all required fields."
                );

                return;
            }


            // =========================
            // PASSWORD VALIDATION
            // =========================

            if (password.length < 6) {

                showMessage(
                    registerMessage,
                    "Password must contain at least 6 characters."
                );

                return;
            }


            // =========================
            // PHONE VALIDATION
            // =========================

            if (
                phone !== "" &&
                !/^[0-9]{10}$/.test(phone)
            ) {

                showMessage(
                    registerMessage,
                    "Phone number must contain exactly 10 digits."
                );

                return;
            }


            // =========================
            // LOCATION VALIDATION
            // =========================

            if (
                latitude === "" ||
                longitude === ""
            ) {

                showMessage(
                    registerMessage,
                    "Please set your store location before creating the account."
                );

                return;
            }


            // =========================
            // CONVERT LOCATION TO NUMBER
            // =========================

            const latitudeNumber =
                Number(latitude);

            const longitudeNumber =
                Number(longitude);


            if (
                !Number.isFinite(latitudeNumber) ||
                !Number.isFinite(longitudeNumber)
            ) {

                showMessage(
                    registerMessage,
                    "Invalid store location. Please capture your location again."
                );

                return;
            }


            // =========================
            // DISABLE BUTTON
            // =========================

            registerButton.disabled = true;

            registerButton.textContent =
                "Creating Account...";


            try {

                // =========================
                // REGISTRATION REQUEST
                // =========================

                const requestData = {

                    name: name,
                    email: email,
                    password: password,

                    storeName: storeName,
                    address: address,
                    phone: phone,

                    latitude: latitudeNumber,
                    longitude: longitudeNumber

                };


                console.log(
                    "Registration request:",
                    requestData
                );


                const response = await fetch(
                    API_BASE_URL + "/auth/register",
                    {
                        method: "POST",

                        headers: {
                            "Content-Type": "application/json"
                        },

                        body: JSON.stringify(requestData)
                    }
                );


                const text =
                    await response.text();


                console.log(
                    "Registration status:",
                    response.status
                );

                console.log(
                    "Registration response:",
                    text
                );


                // =========================
                // HANDLE ERROR
                // =========================

                if (!response.ok) {

                    let message =
                        "Registration failed.";

                    try {

                        const errorData =
                            JSON.parse(text);

                        message =
                            errorData.message ||
                            errorData.error ||
                            message;

                    } catch (error) {

                        if (text) {

                            message =
                                text;

                        }

                    }


                    showMessage(
                        registerMessage,
                        message
                    );

                    return;
                }


                // =========================
                // SUCCESS
                // =========================

                showMessage(
                    registerMessage,
                    "Shopkeeper registered successfully!"
                );


                registerForm.reset();


                setTimeout(function () {

                    window.location.href =
                        "login.html";

                }, 1500);


            } catch (error) {

                console.error(
                    "Registration error:",
                    error
                );


                showMessage(
                    registerMessage,
                    "Unable to connect to the server."
                );


            } finally {

                registerButton.disabled = false;

                registerButton.textContent =
                    "Create Account →";

            }

        }
    );
}


// =====================================================
// LOGIN
// =====================================================

const loginForm =
    document.getElementById("loginForm");

if (loginForm) {

    const loginMessage =
        document.getElementById("loginMessage");

    const loginButton =
        document.getElementById("loginButton");


    loginForm.addEventListener(
        "submit",
        async function (event) {

            event.preventDefault();


            // =========================
            // GET LOGIN VALUES
            // =========================

            const email =
                document.getElementById("email").value.trim();

            const password =
                document.getElementById("password").value;


            // =========================
            // VALIDATION
            // =========================

            if (
                email === "" ||
                password === ""
            ) {

                showMessage(
                    loginMessage,
                    "Please enter email and password."
                );

                return;
            }


            // =========================
            // DISABLE BUTTON
            // =========================

            loginButton.disabled = true;

            loginButton.textContent =
                "Logging in...";


            try {

                const requestData = {

                    email: email,
                    password: password

                };


                const response = await fetch(
                    API_BASE_URL + "/auth/login",
                    {
                        method: "POST",

                        headers: {
                            "Content-Type": "application/json"
                        },

                        body: JSON.stringify(requestData)
                    }
                );


                const text =
                    await response.text();


                console.log(
                    "Login status:",
                    response.status
                );

                console.log(
                    "Login response:",
                    text
                );


                // =========================
                // HANDLE LOGIN ERROR
                // =========================

                if (!response.ok) {

                    let message =
                        "Invalid email or password.";

                    try {

                        const errorData =
                            JSON.parse(text);

                        message =
                            errorData.message ||
                            errorData.error ||
                            message;

                    } catch (error) {

                        if (text) {

                            message =
                                text;

                        }

                    }


                    showMessage(
                        loginMessage,
                        message
                    );

                    return;
                }


                // =========================
                // LOGIN SUCCESS
                // =========================

                const loginData =
                    JSON.parse(text);


                // =========================
                // STORE JWT
                // =========================

                localStorage.setItem(
                    "token",
                    loginData.token
                );


                // =========================
                // STORE USER INFORMATION
                // =========================

                localStorage.setItem(
                    "user",
                    JSON.stringify({

                        id: loginData.id,

                        name: loginData.name,

                        email: loginData.email,

                        role: loginData.role

                    })
                );


                showMessage(
                    loginMessage,
                    "Login successful!"
                );


                // =========================
                // OPEN DASHBOARD
                // =========================

                setTimeout(function () {

                    window.location.href =
                        "dashboard.html";

                }, 800);


            } catch (error) {

                console.error(
                    "Login error:",
                    error
                );


                showMessage(
                    loginMessage,
                    "Unable to connect to the server."
                );


            } finally {

                loginButton.disabled = false;

                loginButton.textContent =
                    "Login →";

            }

        }
    );
}


// =====================================================
// BACK BUTTON
// =====================================================

const backButton =
    document.getElementById("backButton");

if (backButton) {

    backButton.addEventListener(
        "click",
        function () {

            window.location.href =
                "../role.html";

        }
    );

}


// =====================================================
// MESSAGE FUNCTION
// =====================================================

function showMessage(
    element,
    message
) {

    element.textContent =
        message;

}