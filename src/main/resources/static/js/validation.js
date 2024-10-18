/**
 * 
 */

 // validation.js
 
console.log("hello world");

document.getElementById('btn').addEventListener('click', function(event) {
    // Prevent form submission
    event.preventDefault();

    // Clear previous error messages
    clearErrors();

    // Get form elements
    const username = document.getElementById('username').value.trim();
    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value.trim();
    const confirmPassword = document.getElementById('confirmPassword').value.trim();

    // Validation flags
    let isValid = true;

    // Validate username
    if (username === "") {
        displayError('username', 'Username is required');
        isValid = false;
    }

    // Validate email
    const emailPattern = /^[^ ]+@[^ ]+\.[a-z]{2,3}$/;
    if (email === "" || !email.match(emailPattern)) {
        displayError('email', 'Please enter a valid email');
        isValid = false;
    }

    // Validate password
    if (password === "") {
        displayError('password', 'Password is required');
        isValid = false;
    }

    // Validate confirm password
    if (confirmPassword === "") {
        displayError('confirmPassword', 'Please confirm your password');
        isValid = false;
    } else if (password !== confirmPassword) {
        displayError('confirmPassword', 'Passwords do not match');
        isValid = false;
    }

    // If all validations pass, submit the form
    if (isValid) {
		let currentStep = 1;
        let selectedRole = null;
        
        function nextStep(step) {
            document.querySelector(`#step${currentStep}`).classList.remove('active');
            currentStep = step;
            document.querySelector(`#step${currentStep}`).classList.add('active');
            if (currentStep === 3 && selectedRole === 'user') {
                document.getElementById('adminDetails').style.display = 'none';
            } else if (currentStep === 3 && selectedRole === 'admin') {
                document.getElementById('adminDetails').style.display = 'block';
            }
        }

        function prevStep(step) {
            document.querySelector(`#step${currentStep}`).classList.remove('active');
            currentStep = step;
            document.querySelector(`#step${currentStep}`).classList.add('active');
        }

        function selectRole(role) {
            selectedRole = role;
        }
        //document.querySelector('form').submit();
    }
});

function displayError(inputId, message) {
    const inputElement = document.getElementById(inputId);
    const errorElement = document.createElement('p');
    errorElement.className = 'text-danger';
    errorElement.style.fontSize = '0.77rem';
    errorElement.innerText = message;
    inputElement.parentNode.appendChild(errorElement);
}

function clearErrors() {
    const errorMessages = document.querySelectorAll('.text-danger');
    errorMessages.forEach(function(message) {
        message.remove();
    });
}




 		


        // Preview Image for Restaurant
        /*const imageData = document.querySelector("#restaurantImage");
        const preview = document.querySelector("#imagePreview");
        imageData.onchange = () => {
            const [file] = imageData.files;
            if (file) preview.src = URL.createObjectURL(file);
        };
        */
