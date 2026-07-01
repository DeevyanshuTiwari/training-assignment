/**
 * Centralized API Service for CircleUp
 * Handles all fetch calls, JWT injection, and global error handling.
 */

const BASE_URL = "http://127.0.0.1:8000";

/**
 * Core fetch wrapper
 * Automatically handles headers, JWT injection, and standardizes errors.
 */
async function apiFetch(endpoint, options = {}) {
    const url = `${BASE_URL}${endpoint}`;

    // Default headers
    const headers = {
        'Content-Type': 'application/json',
        ...options.headers
    };

    // Inject JWT Token if available
    const token = localStorage.getItem('circleup_jwt');
    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }

    const fetchOptions = {
        ...options,
        headers
    };

    try {
        const response = await fetch(url, fetchOptions);

        // Handle 401 Unauthorized globally (Token expired or missing)
        if (response.status === 401) {
            localStorage.removeItem('circleup_jwt');
            // If we are not already on the landing page, redirect to it
            const currentPath = window.location.pathname;
            if (!currentPath.endsWith('index.html') && currentPath !== '/' && currentPath !== '') {
                window.location.href = '../index.html';
            }
        }

        // Try to parse JSON response
        let data;
        try {
            data = await response.json();
        } catch (e) {
            data = null;
        }

        if (!response.ok) {
            // Throw standardized error using FastAPI's default 'detail' key
            throw new Error(data?.detail || `Network Error: ${response.status}`);
        }

        return data;

    } catch (error) {
        console.error(`API Error (${endpoint}):`, error);
        throw error;
    }
}

// ==========================================
// AUTHENTICATION APIs
// ==========================================

export async function login(email, password) {
    // FastAPI's OAuth2PasswordRequestForm expects x-www-form-urlencoded
    const formData = new URLSearchParams();
    formData.append('username', email); // OAuth2 expects 'username'
    formData.append('password', password);

    return await apiFetch('/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: formData.toString()
    });
}

export async function register(userData) {
    return await apiFetch('/auth/register', {
        method: 'POST',
        body: JSON.stringify(userData)
    });
}

export function logout() {
    localStorage.removeItem('circleup_jwt');
    window.location.href = '../index.html';
}

// ==========================================
// USER APIs
// ==========================================

export async function getProfile() {
    return await apiFetch('/users/me', {
        method: 'GET'
    });
}

export async function updateProfile(profileData) {
    return await apiFetch('/users/me', {
        method: 'PUT',
        body: JSON.stringify(profileData)
    });
}