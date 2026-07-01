/**
 * Dashboard Logic
 * Protected route logic, profile fetching, and data population.
 */

import { isAuthenticated, logout, getProfile } from './api.js';

// Route Protection: Redirect if not logged in
if (!isAuthenticated()) {
    window.location.href = '../index.html';
}

document.addEventListener('DOMContentLoaded', async () => {
    // DOM Elements
    const btnLogout = document.getElementById('btn-logout');
    const welcomeMessage = document.getElementById('welcome-message');
    const userAvatarMini = document.getElementById('user-avatar-mini');

    // Auth actions
    if (btnLogout) {
        btnLogout.addEventListener('click', () => {
            logout();
        });
    }

    // Load Profile Data
    try {
        const profile = await getProfile();

        if (profile) {
            // Populate Welcome Message
            const name = profile.full_name || profile.email.split('@')[0];
            welcomeMessage.textContent = `Welcome back, ${name}!`;

            // Populate Avatar initials
            if (profile.full_name) {
                const initials = profile.full_name.split(' ').map(n => n[0]).join('').toUpperCase().substring(0,2);
                userAvatarMini.textContent = initials;
            } else {
                userAvatarMini.textContent = name.substring(0,2).toUpperCase();
            }
        }
    } catch (error) {
        console.error("Failed to fetch profile for dashboard:", error);
        // If this was a 401 token expiration, api.js automatically handles the redirect.
    }
});
