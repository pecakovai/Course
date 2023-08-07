// This is your test publishable API key.
const stripe = Stripe(publicKey);

// The items the customer wants to buy
const request = {
    amount: amount,
    email: email,
    productName: Array.isArray(productName) ? productName : [productName]
}

let elements;

initialize();
checkStatus();

document
    .querySelector("#payment-form")
    .addEventListener("submit", handleSubmit);

let emailAddress = '';
// Fetches a payment intent and captures the client secret

let paymentIntentID = '';
console.log(request.email);
async function initialize() {

    const response = await fetch("http://localhost:8080/create-payment", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(request),
    });
    console.log(response);
    const { intentID, clientSecret } = await response.json();
    console.log(clientSecret);
    paymentIntentID = intentID;

    const appearance = {
        theme: 'stripe',
    };
    const elementsOptions = {
        clientSecret: clientSecret, // Replace with the actual client secret
        appearance: appearance,
    };
    elements = stripe.elements(elementsOptions);

    const linkAuthenticationElement = elements.create("linkAuthentication");
    linkAuthenticationElement.mount("#link-authentication-element");

    linkAuthenticationElement.on('change', (event) => {
        emailAddress = event.value.email;
    });

    const paymentElementOptions = {
        layout: "tabs",
        defaultValues: {
            billingDetails:{
                email: request.email
            }
        }
    };

    const paymentElement = elements.create("payment", paymentElementOptions);
    paymentElement.mount("#payment-element");
}

console.log(paymentIntentID);

console.log(userId);
console.log(productId)

function enrollStudentsToSubjects(students, subjects) {
    var url = 'http://localhost:8080/api/enroll/student';
    var data = {
        students: students,
        subjects: subjects
    };

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(function (response) {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(function (data) {
            console.log('Enrollment successful:', data);
            // Handle the response data if needed
            // For example, you can display a success message to the user
            alert('Enrollment successful');
            // Optionally redirect to another page
            window.location.href = '/index';
        })
        .catch(function (error) {
            console.error('Error enrolling students:', error);
        });
}


async function handleSubmit(e) {
    e.preventDefault();
    setLoading(true);

    const { error } = await stripe.confirmPayment({
        elements,
        confirmParams: {
            // Make sure to change this to your payment completion page
            return_url: "http://localhost:8080/index"
        },
    });

    // This point will only be reached if there is an immediate error when
    // confirming the payment. Otherwise, your customer will be redirected to
    // your `return_url`. For some payment methods like iDEAL, your customer will
    // be redirected to an intermediate site first to authorize the payment, then
    // redirected to the `return_url`.
    if (error.type === "card_error" || error.type === "validation_error") {
        showMessage(error.message);
    } else {
        showMessage("An unexpected error occurred.");
    }

    setLoading(false);
}

// Fetches the payment intent status after payment submission
async function checkStatus() {
    const clientSecret = new URLSearchParams(window.location.search).get(
        "payment_intent_client_secret"
    );

    if (!clientSecret) {
        return;
    }

    const { paymentIntent } = await stripe.retrievePaymentIntent(clientSecret);

    switch (paymentIntent.status) {
        case "succeeded":
            showMessage("Payment succeeded!");
            break;
        case "processing":
            showMessage("Your payment is processing.");
            break;
        case "requires_payment_method":
            showMessage("Your payment was not successful, please try again.");
            break;
        default:
            showMessage("Something went wrong.");
            break;
    }
}

// ------- UI helpers -------

function showMessage(messageText) {
    const messageContainer = document.querySelector("#payment-message");

    messageContainer.classList.remove("hidden");
    messageContainer.textContent = messageText;

    setTimeout(function () {
        messageContainer.classList.add("hidden");
        messageContainer.textContent = "";
    }, 4000);
}

// Show a spinner on payment submission
function setLoading(isLoading) {
    let subjects = Array.isArray(productId) ? productId : [productId];
    console.log("BEFORE ENROLL");
    enrollStudentsToSubjects([userId],subjects)
    if (isLoading) {
        // Disable the button and show a spinner
        document.querySelector("#submit").disabled = true;
        document.querySelector("#spinner").classList.remove("hidden");
        document.querySelector("#button-text").classList.add("hidden");
    } else {

        document.querySelector("#submit").disabled = false;
        document.querySelector("#spinner").classList.add("hidden");
        document.querySelector("#button-text").classList.remove("hidden");
    }
}