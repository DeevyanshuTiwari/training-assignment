// ================================================
// STEP 3 & 4 — MODAL + JAVASCRIPT FUNCTIONALITY
// File: frontend/js/app.js
//
// What this file does:
//  1. Opens / closes Login and Register modals
//  2. Switches between the two modals
//  3. Validates form fields (empty, email format, password rules)
//  4. Calls Spring Boot backend via fetch()
//  5. Stores JWT token in localStorage
//  6. Redirects to dashboard.html after login
// ================================================


// ------------------------------------------------
// SECTION A: CONFIGURATION
// WHY: Keep the API URL in ONE place.
//      If your backend URL changes, update only here.
// ------------------------------------------------
const API_BASE_URL = 'http://localhost:8080/api/auth';


// ------------------------------------------------
// SECTION B: GET REFERENCES TO HTML ELEMENTS
// WHY: We grab all the elements we need ONCE at
//      the top. This is faster than searching for
//      them every time inside event listeners.
// ------------------------------------------------

// --- Modal overlays ---
const loginModal    = document.getElementById('loginModal');
const registerModal = document.getElementById('registerModal');

// --- Navbar buttons ---
const openLoginBtn    = document.getElementById('openLoginBtn');
const openRegisterBtn = document.getElementById('openRegisterBtn');

// --- Hero CTA button ---
const heroRegisterBtn = document.getElementById('heroRegisterBtn');

// --- Close (✕) buttons ---
const closeLoginBtn    = document.getElementById('closeLoginBtn');
const closeRegisterBtn = document.getElementById('closeRegisterBtn');

// --- Switch links ---
const switchToRegister = document.getElementById('switchToRegister');
const switchToLogin    = document.getElementById('switchToLogin');

// --- Login form elements ---
const loginForm          = document.getElementById('loginForm');
const loginEmailInput    = document.getElementById('loginEmail');
const loginPasswordInput = document.getElementById('loginPassword');
const loginEmailError    = document.getElementById('loginEmailError');
const loginPasswordError = document.getElementById('loginPasswordError');
const loginError         = document.getElementById('loginError');
const loginSubmitBtn     = document.getElementById('loginSubmitBtn');
const toggleLoginPwd     = document.getElementById('toggleLoginPassword');

// --- Register form elements ---
const registerForm          = document.getElementById('registerForm');
const registerNameInput     = document.getElementById('registerName');
const registerEmailInput    = document.getElementById('registerEmail');
const registerPasswordInput = document.getElementById('registerPassword');
const registerPhoneInput    = document.getElementById('registerPhone');
const registerRoleInput     = document.getElementById('registerRole');
const registerNameError     = document.getElementById('registerNameError');
const registerEmailError    = document.getElementById('registerEmailError');
const registerPasswordError = document.getElementById('registerPasswordError');
const registerPhoneError    = document.getElementById('registerPhoneError');
const registerRoleError     = document.getElementById('registerRoleError');
const registerSuccess       = document.getElementById('registerSuccess');
const registerError         = document.getElementById('registerError');
const registerSubmitBtn     = document.getElementById('registerSubmitBtn');
const toggleRegisterPwd     = document.getElementById('toggleRegisterPassword');


// ------------------------------------------------
// SECTION C: MODAL OPEN / CLOSE HELPERS
// WHY: We reuse these functions many times.
//      Functions keep code DRY (Don't Repeat Yourself).
// ------------------------------------------------

/**
 * Opens a modal.
 * @param {HTMLElement} modal - The modal overlay element to open.
 */
function openModal(modal) {
  modal.classList.add('active');       // CSS transition shows the modal
  document.body.classList.add('modal-open'); // Locks background scroll
}

/**
 * Closes a modal and resets its form.
 * @param {HTMLElement} modal - The modal overlay element to close.
 */
function closeModal(modal) {
  modal.classList.remove('active');         // CSS transition hides modal
  document.body.classList.remove('modal-open'); // Re-enable scroll

  // Reset the form inside this modal (clear all field values)
  const form = modal.querySelector('form');
  if (form) form.reset();

  // Clear all field errors
  clearAllErrors(modal);

  // Hide any alert messages
  const alerts = modal.querySelectorAll('.alert');
  alerts.forEach(alert => {
    alert.style.display = 'none';
    alert.textContent = '';
  });
}

/**
 * Clears all field-level error messages inside a modal.
 * @param {HTMLElement} container - The modal or form element.
 */
function clearAllErrors(container) {
  // Remove red border from all inputs
  container.querySelectorAll('.form-input').forEach(input => {
    input.classList.remove('input-error');
  });
  // Clear all error text spans
  container.querySelectorAll('.field-error').forEach(span => {
    span.textContent = '';
  });
}


// ------------------------------------------------
// SECTION D: WIRE UP OPEN / CLOSE / SWITCH BUTTONS
// WHY: Connect all the button clicks to our helper functions.
// ------------------------------------------------

// Navbar: Login button → open login modal
openLoginBtn.addEventListener('click', () => openModal(loginModal));

// Navbar: Register button → open register modal
openRegisterBtn.addEventListener('click', () => openModal(registerModal));

// Hero: "Get Started Free" button → open register modal
heroRegisterBtn.addEventListener('click', () => openModal(registerModal));

// Login modal: ✕ close button
closeLoginBtn.addEventListener('click', () => closeModal(loginModal));

// Register modal: ✕ close button
closeRegisterBtn.addEventListener('click', () => closeModal(registerModal));

// Switch from login → register
switchToRegister.addEventListener('click', () => {
  closeModal(loginModal);
  openModal(registerModal);
});

// Switch from register → login
switchToLogin.addEventListener('click', () => {
  closeModal(registerModal);
  openModal(loginModal);
});

// Close modal when clicking the dark overlay OUTSIDE the box
// WHY: Click the background behind the popup → it closes
loginModal.addEventListener('click', (e) => {
  // e.target is what was clicked
  // If they clicked the overlay itself (not the box inside), close
  if (e.target === loginModal) closeModal(loginModal);
});

registerModal.addEventListener('click', (e) => {
  if (e.target === registerModal) closeModal(registerModal);
});

// Close modal when pressing Escape key
// WHY: Standard UX pattern — users expect Escape to close popups
document.addEventListener('keydown', (e) => {
  if (e.key === 'Escape') {
    if (loginModal.classList.contains('active'))    closeModal(loginModal);
    if (registerModal.classList.contains('active')) closeModal(registerModal);
  }
});


// ------------------------------------------------
// SECTION E: PASSWORD SHOW/HIDE TOGGLE
// WHY: Lets users verify what they typed.
//      We change input type between "password" and "text".
// ------------------------------------------------

/**
 * Toggles the type of a password input between "password" and "text".
 * @param {HTMLInputElement} inputEl - The password input.
 * @param {HTMLButtonElement} btnEl - The toggle button element.
 */
function setupPasswordToggle(inputEl, btnEl) {
  btnEl.addEventListener('click', () => {
    const isPassword = inputEl.type === 'password';
    inputEl.type = isPassword ? 'text' : 'password';
    btnEl.textContent = isPassword ? '🙈' : '👁️';  // Change icon
  });
}

setupPasswordToggle(loginPasswordInput, toggleLoginPwd);
setupPasswordToggle(registerPasswordInput, toggleRegisterPwd);


// ------------------------------------------------
// SECTION F: VALIDATION HELPERS
// WHY: We validate BEFORE sending to the server.
//      This gives instant feedback without a network call.
// ------------------------------------------------

/**
 * Shows an error message below a specific field.
 * @param {HTMLInputElement} inputEl   - The input that failed.
 * @param {HTMLElement}      errorEl   - The <span> where error shows.
 * @param {string}           message   - The error text to display.
 */
function showFieldError(inputEl, errorEl, message) {
  inputEl.classList.add('input-error');  // Red border
  errorEl.textContent = message;
}

/**
 * Clears error on a specific field (called when user starts typing).
 */
function clearFieldError(inputEl, errorEl) {
  inputEl.classList.remove('input-error');
  errorEl.textContent = '';
}

// Email regex — checks for basic email format
// Matches: anything@anything.anything
const EMAIL_REGEX = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

// Password rules matching backend:
// 8–12 chars, at least one uppercase, at least one special char
const PASSWORD_REGEX = /^(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{8,12}$/;

// Name: only letters and spaces, min 2 chars
const NAME_REGEX = /^[A-Za-z ]{2,}$/;

/**
 * Validates the LOGIN form.
 * Returns true if valid, false if any field fails.
 */
function validateLoginForm() {
  let isValid = true;

  const email    = loginEmailInput.value.trim();
  const password = loginPasswordInput.value;

  // Clear previous errors first
  clearAllErrors(loginModal);

  // Validate email
  if (!email) {
    showFieldError(loginEmailInput, loginEmailError, 'Email is required');
    isValid = false;
  } else if (!EMAIL_REGEX.test(email)) {
    showFieldError(loginEmailInput, loginEmailError, 'Please enter a valid email address');
    isValid = false;
  }

  // Validate password
  if (!password) {
    showFieldError(loginPasswordInput, loginPasswordError, 'Password is required');
    isValid = false;
  }

  return isValid;
}

/**
 * Validates the REGISTER form.
 * Returns true if all fields are valid.
 */
function validateRegisterForm() {
  let isValid = true;

  const name     = registerNameInput.value.trim();
  const email    = registerEmailInput.value.trim();
  const password = registerPasswordInput.value;
  const phone    = registerPhoneInput.value.trim().replace(/\D/g, ''); // digits only
  const role     = registerRoleInput.value;

  // Clear previous errors first
  clearAllErrors(registerModal);

  // Validate name
  if (!name) {
    showFieldError(registerNameInput, registerNameError, 'Full name is required');
    isValid = false;
  } else if (!NAME_REGEX.test(name)) {
    showFieldError(registerNameInput, registerNameError, 'Name must be at least 2 letters, no numbers');
    isValid = false;
  }

  // Validate email
  if (!email) {
    showFieldError(registerEmailInput, registerEmailError, 'Email is required');
    isValid = false;
  } else if (!EMAIL_REGEX.test(email)) {
    showFieldError(registerEmailInput, registerEmailError, 'Please enter a valid email address');
    isValid = false;
  }

  // Validate password
  if (!password) {
    showFieldError(registerPasswordInput, registerPasswordError, 'Password is required');
    isValid = false;
  } else if (!PASSWORD_REGEX.test(password)) {
    showFieldError(
      registerPasswordInput,
      registerPasswordError,
      'Password must be 8–12 chars with at least one uppercase and one special character'
    );
    isValid = false;
  }

  // Validate phone — must be exactly 10 digits
  if (!phone) {
    showFieldError(registerPhoneInput, registerPhoneError, 'Phone number is required');
    isValid = false;
  } else if (phone.length !== 10) {
    showFieldError(registerPhoneInput, registerPhoneError, 'Phone must be exactly 10 digits');
    isValid = false;
  }

  // Validate role
  if (!role) {
    showFieldError(registerRoleInput, registerRoleError, 'Please select a role');
    isValid = false;
  }

  return isValid;
}


// ------------------------------------------------
// SECTION G: LOGIN FORM — API CALL
// WHY: Sends email + password to POST /login.
//      On success → saves JWT → redirects.
//      On failure → shows error message.
// ------------------------------------------------
loginForm.addEventListener('submit', async (e) => {
  // Prevent the browser from reloading the page
  e.preventDefault();

  // Run validation — stop if any field fails
  if (!validateLoginForm()) return;

  // Get values from inputs
  const email    = loginEmailInput.value.trim();
  const password = loginPasswordInput.value;

  // Disable button and show loading text
  // WHY: Prevent double-submit while waiting for server
  loginSubmitBtn.disabled    = true;
  loginSubmitBtn.textContent = 'Logging in...';

  // Hide any previous error message
  loginError.style.display = 'none';
  loginError.textContent   = '';

  try {
    // Make API call to Spring Boot backend
    const response = await fetch(`${API_BASE_URL}/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'   // Tell server we're sending JSON
      },
      body: JSON.stringify({ email, password })  // Convert JS object to JSON string
    });

    // Parse the JSON response from server
    const data = await response.json();
    console.log("Login Response Data:", data); // Debug log to see token field name

    // If server returned an error (status 4xx or 5xx)
    if (!response.ok) {
      // Use server's message if available, otherwise show generic error
      throw new Error(data.message || 'Login failed. Please check your credentials.');
    }

    // Extract JWT token — backend may return it in different fields
    const token = data.token || data.accessToken || data.jwt;

    if (!token) {
      throw new Error('Login succeeded but no token was returned. Contact support.');
    }

    // Save token, email, role, and name to localStorage.
    // customer.js reads 'token'; eventDashboard.js reads 'authToken'.
    // We save both keys so both dashboards work without changes.
    localStorage.setItem('token',     token);       // used by customer.js
    localStorage.setItem('authToken', token);       // used by eventDashboard.js
    localStorage.setItem('userEmail', data.email || email);
    localStorage.setItem('userRole',  data.role  || '');
    localStorage.setItem('userName',  data.fullName || data.email || email);

    // Show success feedback briefly before redirect
    loginSubmitBtn.textContent = '✓ Success! Redirecting...';

    // Role-based redirect:
    //   CUSTOMER  → customer-dashboard.html
    //   ORGANIZER → eventDashboard.html
    const role = (data.role || '').toUpperCase();
    setTimeout(() => {
      if (role === 'CUSTOMER') {
        window.location.href = 'customer-dashboard.html';
      } else {
        window.location.href = 'eventDashboard.html';
      }
    }, 900);

  } catch (error) {
    // Show the error message inside the modal
    loginError.textContent   = '⚠️ ' + error.message;
    loginError.style.display = 'block';

    // Re-enable submit button so user can try again
    loginSubmitBtn.disabled    = false;
    loginSubmitBtn.textContent = 'Login to My Account';
  }
});


// ------------------------------------------------
// SECTION H: REGISTER FORM — API CALL
// WHY: Sends all fields to POST /register.
//      On success → shows success message → switches to login.
//      On failure → shows error message.
// ------------------------------------------------
registerForm.addEventListener('submit', async (e) => {
  e.preventDefault();

  // Run validation — stop if any field fails
  if (!validateRegisterForm()) return;

  // Get values
  const name     = registerNameInput.value.trim();
  const email    = registerEmailInput.value.trim().toLowerCase();
  const password = registerPasswordInput.value;
  const phone    = registerPhoneInput.value.trim().replace(/\D/g, ''); // digits only
  const role     = registerRoleInput.value;

  // Disable button, show loading state
  registerSubmitBtn.disabled    = true;
  registerSubmitBtn.textContent = 'Creating account...';

  // Hide any previous messages
  registerError.style.display   = 'none';
  registerError.textContent     = '';
  registerSuccess.style.display = 'none';
  registerSuccess.textContent   = '';

  try {
    // Build the request body — must match what Spring Boot @RequestBody expects
    const requestBody = {
      fullName: name,   // matches your backend field name
      email:    email,
      password: password,
      phone:    phone,
      role:     role    // "CUSTOMER" or "ORGANIZER"
    };

    const response = await fetch(`${API_BASE_URL}/register`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(requestBody)
    });

    // 201 Created = registration successful
    if (response.status === 201 || response.ok) {
      // Show success message inside modal
      registerSuccess.textContent   = '🎉 Account created successfully! Please login.';
      registerSuccess.style.display = 'block';

      // Reset button text
      registerSubmitBtn.textContent = '✓ Account Created!';

      // After 2 seconds → switch to login modal automatically
      setTimeout(() => {
        closeModal(registerModal);
        openModal(loginModal);
        // Pre-fill email in login form for convenience
        loginEmailInput.value = email;
      }, 2000);

    } else {
      // Server returned an error — parse message
      let errorMessage = 'Registration failed. Please try again.';
      try {
        const errorData = await response.json();
        errorMessage = errorData.message || errorMessage;
      } catch (_) {
        // Response body wasn't JSON — use default message
      }
      throw new Error(errorMessage);
    }

  } catch (error) {
    // Show error message
    registerError.textContent   = '⚠️ ' + error.message;
    registerError.style.display = 'block';

    // Re-enable submit button
    registerSubmitBtn.disabled    = false;
    registerSubmitBtn.textContent = 'Create My Account';
  }
});


// ------------------------------------------------
// SECTION I: AUTO-REDIRECT IF ALREADY LOGGED IN
// WHY: If the user already has a JWT in localStorage,
//      there's no need to see the landing page —
//      send them straight to the dashboard.
// ------------------------------------------------
document.addEventListener('DOMContentLoaded', () => {
  // If a token already exists, skip the landing page and go straight to the
  // correct dashboard based on saved role.
  const existingToken = localStorage.getItem('token') || localStorage.getItem('authToken');
  if (existingToken) {
    const role = (localStorage.getItem('userRole') || '').toUpperCase();
    if (role === 'CUSTOMER') {
      window.location.href = 'customer-dashboard.html';
    } else {
      window.location.href = 'eventDashboard.html';
    }
  }
});


// ------------------------------------------------
// SECTION J: CLEAR FIELD ERRORS ON USER INPUT
// WHY: As soon as the user starts correcting a field,
//      remove the red border — better UX.
// ------------------------------------------------
loginEmailInput.addEventListener('input', () =>
  clearFieldError(loginEmailInput, loginEmailError));

loginPasswordInput.addEventListener('input', () =>
  clearFieldError(loginPasswordInput, loginPasswordError));

registerNameInput.addEventListener('input', () =>
  clearFieldError(registerNameInput, registerNameError));

registerEmailInput.addEventListener('input', () =>
  clearFieldError(registerEmailInput, registerEmailError));

registerPasswordInput.addEventListener('input', () =>
  clearFieldError(registerPasswordInput, registerPasswordError));

registerPhoneInput.addEventListener('input', () =>
  clearFieldError(registerPhoneInput, registerPhoneError));

registerRoleInput.addEventListener('change', () =>
  clearFieldError(registerRoleInput, registerRoleError));