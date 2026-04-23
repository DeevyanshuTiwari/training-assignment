
// Backend base URL (update if your backend runs elsewhere)
const API_BASE_URL = 'http://localhost:8080/api/auth';

// Helper: show messages in the page's message box
function showMessage(type, text) {
  const box = document.getElementById('messageBox');
  if (!box) return;
  box.textContent = text;
  box.className = 'message-box show ' + (type === 'success' ? 'success' : 'error');
}

function clearMessage(){
  const box = document.getElementById('messageBox');
  if (!box) return;
  box.textContent = '';
  box.className = 'message-box';
}

/* ----------------------
   LOGIN FORM
   ---------------------- */
const loginForm = document.getElementById('loginForm');
if (loginForm) {
  // If user already has token, redirect to dashboard
  document.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('authToken');
    if (token) {
      window.location.href = 'dashboard.html';
    }
  });

  loginForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    clearMessage();

    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value;

    // Simple validation
    if (!email) { showMessage('error','Email is required'); return; }
    if (!password) { showMessage('error','Password is required'); return; }

    const submitBtn = loginForm.querySelector('.btn-primary');
    submitBtn.disabled = true; submitBtn.textContent = 'Logging in...';

    try {
      const res = await fetch(`${API_BASE_URL}/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password })
      });

      const data = await res.json();
      if (!res.ok) throw new Error(data.message || res.statusText || 'Login failed');

      // Expecting backend to return a token (e.g. { token: '...' })
      const token = data.token || data.accessToken || data.jwt;
      if (!token) throw new Error('No token returned from server');

      localStorage.setItem('authToken', token);
      localStorage.setItem('userEmail', email);

      showMessage('success','Login successful — redirecting...');
      setTimeout(()=> window.location.href = 'dashboard.html', 1000);

    } catch (err) {
      showMessage('error', err.message || 'Login error');
      submitBtn.disabled = false; submitBtn.textContent = 'Login';
    }
  });
}

/* ----------------------
   REGISTER FORM
   ---------------------- */
const registerForm = document.getElementById('registerForm');
if (registerForm) {
  registerForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    clearMessage();

    const name = document.getElementById('name').value.trim();
    const emailRaw = document.getElementById('email').value.trim();
    const email = emailRaw.toLowerCase(); // backend expects lowercase @gmail.com
    const phoneRaw = document.getElementById('phone').value.trim();
    const phone = phoneRaw.replace(/\D/g, ''); // digits only
    const password = document.getElementById('password').value;
    const role = document.getElementById('role').value;

    // Client-side validation matching backend rules
    const nameRegex = /^[A-Za-z ]{2,}$/;
    if (!name || !nameRegex.test(name)) {
      showMessage('error', 'Name must be at least 2 letters and contain only letters/spaces');
      return;
    }

    const emailRegex = /^[A-Za-z0-9._%+-]+@gmail\.com$/;
    if (!email || !emailRegex.test(email)) {
      showMessage('error', 'Email must be a valid @gmail.com address');
      return;
    }

    if (!phone || phone.length !== 10) {
      showMessage('error', 'Phone must be exactly 10 digits (numbers only)');
      return;
    }

    const pwdRegex = /^(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{8,12}$/;
    if (!password || !pwdRegex.test(password)) {
      showMessage('error', 'Password must be 8–12 chars, include at least one uppercase and one special character');
      return;
    }

    if (!role) { showMessage('error','Please select a role'); return; }

    const submitBtn = registerForm.querySelector('.btn-primary');
    submitBtn.disabled = true; submitBtn.textContent = 'Creating...';

    try {
      const res = await fetch("http://localhost:8080/api/auth/register", {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ fullName: name, email: email, phone: phone, password: password, role: role })
      });
      if (res.status === 201) {
        console.log("Created user successfully");
      }
      else {throw new Error('Registration failed');}


      showMessage('success','Registration successful — redirecting to Dashboard');
      setTimeout(()=> window.location.href = 'dashboard.html', 1200);

    } catch (err) {
      console.log(err);
      showMessage('error', err.message || 'Registration error');
      submitBtn.disabled = false; submitBtn.textContent = 'Sign Up';
    }
  });
}

/* ----------------------
   COMMON helpers
   ---------------------- */

// Expose for debugging (optional)
window.__authHelpers = { showMessage, clearMessage };
