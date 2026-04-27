
// ================================================
// A. CONFIG & DOM REFERENCES
// ================================================

const API = 'http://localhost:8082/api';

// --- Auth ---
const token        = localStorage.getItem('authToken');
const userEmail    = localStorage.getItem('userEmail') || 'Organizer';
const userName    = localStorage.getItem('userName') || 'Organizer';

// --- Navbar ---
const organizerName = document.getElementById('organizerName');
const userAvatar    = document.getElementById('userAvatar');
const navLogoutBtn  = document.getElementById('navLogoutBtn');

// --- Sidebar buttons ---
const sidebarItems      = document.querySelectorAll('.sidebar-item[data-target]');
const navCreateEvent    = document.getElementById('navCreateEvent');
const sidebarLogoutBtn  = document.getElementById('sidebarLogoutBtn');

// --- Section containers ---
const allSections = document.querySelectorAll('.dash-section');

// --- Stat counters ---
const statTotal     = document.getElementById('statTotal');
const statActive    = document.getElementById('statActive');
const statCancelled = document.getElementById('statCancelled');
const statBookings  = document.getElementById('statBookings');

// --- Event grids ---
const recentEventsGrid = document.getElementById('recentEventsGrid');
const allEventsGrid    = document.getElementById('allEventsGrid');
const emptyState       = document.getElementById('emptyState');

// --- Filter buttons ---
const filterBtns = document.querySelectorAll('.filter-btn');

// --- Create buttons (multiple entry points) ---
const overviewCreateBtn = document.getElementById('overviewCreateBtn');
const eventsCreateBtn   = document.getElementById('eventsCreateBtn');
const emptyCreateBtn    = document.getElementById('emptyCreateBtn');
const viewAllBtn        = document.getElementById('viewAllBtn');
const backToEventsBtn   = document.getElementById('backToEventsBtn');

// --- Alerts ---
const eventsAlert   = document.getElementById('eventsAlert');
const bookingsAlert = document.getElementById('bookingsAlert');

// --- Bookings section ---
const bookingsEventName  = document.getElementById('bookingsEventName');
const bookingsTableBody  = document.getElementById('bookingsTableBody');
const noBookings         = document.getElementById('noBookings');
const bookingsTable      = document.getElementById('bookingsTable');

// --- Create Modal ---
const createEventModal    = document.getElementById('createEventModal');
const closeCreateModal    = document.getElementById('closeCreateModal');
const createEventForm     = document.getElementById('createEventForm');
const createModalAlert    = document.getElementById('createModalAlert');
const createEventSubmitBtn = document.getElementById('createEventSubmitBtn');
const createName  = document.getElementById('createName');
const createDesc  = document.getElementById('createDesc');
const createDate  = document.getElementById('createDate');
const createVenue = document.getElementById('createVenue');
const createSeats = document.getElementById('createSeats');
const createPrice = document.getElementById('createPrice');

// --- Edit Modal ---
const editEventModal     = document.getElementById('editEventModal');
const closeEditModal     = document.getElementById('closeEditModal');
const editEventForm      = document.getElementById('editEventForm');
const editModalAlert     = document.getElementById('editModalAlert');
const editEventSubmitBtn = document.getElementById('editEventSubmitBtn');
const editEventId = document.getElementById('editEventId');
const editName    = document.getElementById('editName');
const editDesc    = document.getElementById('editDesc');
const editDate    = document.getElementById('editDate');
const editVenue   = document.getElementById('editVenue');
const editSeats   = document.getElementById('editSeats');
const editPrice   = document.getElementById('editPrice');

// --- Cancel Confirm Modal ---
const cancelConfirmModal     = document.getElementById('cancelConfirmModal');
const closeCancelModal       = document.getElementById('closeCancelModal');
const cancelConfirmEventName = document.getElementById('cancelConfirmEventName');
const cancelEventId          = document.getElementById('cancelEventId');
const cancelConfirmYes       = document.getElementById('cancelConfirmYes');
const cancelConfirmNo        = document.getElementById('cancelConfirmNo');

// State: which filter is active
let currentFilter = 'ALL';
// State: all events fetched from backend
let allEvents = [];


// ================================================
// B. AUTH GUARD
// WHY: Dashboard is protected. If no JWT token
//      exists in localStorage, send user to login.
// ================================================
if (!token) {
  window.location.href = 'index.html';
}

// Set organizer name in navbar
// Show first letter as avatar, rest as name
const displayName = userName;
if (organizerName) organizerName.textContent = displayName;
if (userAvatar)    userAvatar.textContent    = displayName.charAt(0).toUpperCase();


// ================================================
// C. SIDEBAR NAVIGATION
// WHY: Instead of separate pages, we show/hide
//      sections using JS. Faster and simpler.
// ================================================

/**
 * Shows one section, hides all others.
 * @param {string} targetId - The id of the section to show.
 */
function showSection(targetId) {
  allSections.forEach(sec => sec.classList.remove('active-section'));
  const target = document.getElementById(targetId);
  if (target) target.classList.add('active-section');

  // Update active sidebar item highlight
  sidebarItems.forEach(item => {
    item.classList.toggle('active', item.dataset.target === targetId);
  });
}

// Wire up sidebar nav buttons
sidebarItems.forEach(item => {
  item.addEventListener('click', () => showSection(item.dataset.target));
});

// "View All" link on overview → go to My Events
viewAllBtn.addEventListener('click', () => showSection('section-events'));

// "Back to Events" in bookings section
backToEventsBtn.addEventListener('click', () => showSection('section-events'));

// Sidebar "Create Event" → open modal
navCreateEvent.addEventListener('click', () => openModal(createEventModal));

// All "+ Create Event" buttons
overviewCreateBtn.addEventListener('click', () => openModal(createEventModal));
eventsCreateBtn.addEventListener('click',   () => openModal(createEventModal));
emptyCreateBtn.addEventListener('click',    () => openModal(createEventModal));

// Filter bar
filterBtns.forEach(btn => {
  btn.addEventListener('click', () => {
    filterBtns.forEach(b => b.classList.remove('active'));
    btn.classList.add('active');
    currentFilter = btn.dataset.filter;
    renderEventCards(allEventsGrid, filterEvents(allEvents, currentFilter));
  });
});


// ================================================
// D. MODAL HELPERS
// ================================================
function openModal(modal) {
  modal.classList.add('active');
  document.body.classList.add('modal-open');
}

function closeModal(modal) {
  modal.classList.remove('active');
  document.body.classList.remove('modal-open');
  // Reset form inside
  const form = modal.querySelector('form');
  if (form) form.reset();
  // Clear errors
  modal.querySelectorAll('.field-error').forEach(el => el.textContent = '');
  modal.querySelectorAll('.form-input').forEach(el => el.classList.remove('input-error'));
  // Hide alert
  const alertEl = modal.querySelector('.alert');
  if (alertEl) { alertEl.style.display = 'none'; alertEl.textContent = ''; }
}

// Close buttons
closeCreateModal.addEventListener('click',  () => closeModal(createEventModal));
closeEditModal.addEventListener('click',    () => closeModal(editEventModal));
closeCancelModal.addEventListener('click',  () => closeModal(cancelConfirmModal));
cancelConfirmNo.addEventListener('click',   () => closeModal(cancelConfirmModal));

// Close on overlay click
[createEventModal, editEventModal, cancelConfirmModal].forEach(modal => {
  modal.addEventListener('click', e => { if (e.target === modal) closeModal(modal); });
});

// Close on Escape key
document.addEventListener('keydown', e => {
  if (e.key === 'Escape') {
    [createEventModal, editEventModal, cancelConfirmModal].forEach(m => {
      if (m.classList.contains('active')) closeModal(m);
    });
  }
});


// ================================================
// E. ALERT HELPERS
// ================================================
function showAlert(el, type, message) {
  el.className = 'alert alert-' + type;
  el.textContent = (type === 'success' ? '✅ ' : '⚠️ ') + message;
  el.style.display = 'block';
  // Auto-hide success after 4s
  if (type === 'success') setTimeout(() => { el.style.display = 'none'; }, 4000);
}

function showFieldError(inputEl, errorElId, message) {
  inputEl.classList.add('input-error');
  document.getElementById(errorElId).textContent = message;
}

function clearFieldError(inputEl, errorElId) {
  inputEl.classList.remove('input-error');
  document.getElementById(errorElId).textContent = '';
}

// Live clear on typing
[
  [createName,  'createNameError'],
  [createDesc,  'createDescError'],
  [createDate,  'createDateError'],
  [createVenue, 'createVenueError'],
  [createSeats, 'createSeatsError'],
  [createPrice, 'createPriceError'],
  [editName,    'editNameError'],
  [editDesc,    'editDescError'],
  [editDate,    'editDateError'],
  [editVenue,   'editVenueError'],
  [editSeats,   'editSeatsError'],
  [editPrice,   'editPriceError'],
].forEach(([el, errId]) => {
  el.addEventListener('input', () => clearFieldError(el, errId));
});


// ================================================
// F. API HELPER
// WHY: All API calls need the same JWT header.
//      One helper prevents repetition.
// ================================================

/**
 * Wrapper around fetch() that adds JWT auth header.
 * @param {string} endpoint  - API path e.g. '/events'
 * @param {object} options   - fetch options (method, body, etc.)
 * @returns {Promise<Response>}
 */
async function apiFetch(endpoint, options = {}) {
  const defaultHeaders = {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token}`
  };
  return fetch(`${API}${endpoint}`, {
    ...options,
    headers: { ...defaultHeaders, ...(options.headers || {}) }
  });
}


// ================================================
// G. LOAD & RENDER EVENTS
// ================================================

/** Filters events array by status. */
function filterEvents(events, filter) {
  if (filter === 'ALL') return events;
  if (filter === 'ACTIVE') return events.filter(e => !e.cancelled);
  if (filter === 'CANCELLED') return events.filter(e => e.cancelled);
  return events;
}

/**
 * Formats an ISO date string to a readable format.
 * e.g. "2026-05-25T19:00" → "25 May 2026 · 7:00 PM"
 */
function formatDate(dateStr) {
  if (!dateStr) return 'N/A';
  const d = new Date(dateStr);
  return d.toLocaleString('en-IN', {
    day: '2-digit', month: 'short', year: 'numeric',
    hour: '2-digit', minute: '2-digit', hour12: true
  });
}

/**
 * Builds a single event card HTML string.
 * @param {object} event - The event object from backend.
 * @returns {string} HTML for one event card.
 */
function buildEventCard(event) {
  const isActive    = event.cancelled === false;
  const badgeClass  = isActive ? 'status-active' : 'status-cancelled';
  const badgeText   = isActive ? 'Active' : 'Cancelled';
  const totalSeats  = event.totalSeats || 0;
  const availSeats  = event.availableSeats ?? totalSeats;
  const bookedSeats = totalSeats - availSeats;
  const fillPct     = totalSeats > 0 ? Math.round((bookedSeats / totalSeats) * 100) : 0;
  const price       = event.ticketPrice ?? event.price ?? 'N/A';

  return `
    <div class="event-card" data-id="${event.id}">
      <div class="event-card-top">
        <p class="event-name">${escapeHtml(event.title || event.eventName || 'Unnamed Event')}</p>
        <span class="status-badge ${badgeClass}">${badgeText}</span>
      </div>

      <div class="event-meta">
        <div class="event-meta-row">
          <span class="event-meta-icon">📅</span>
          <span>${formatDate(event.eventDateTime || event.date)}</span>
        </div>
        <div class="event-meta-row">
          <span class="event-meta-icon">📍</span>
          <span>${escapeHtml(event.venue || 'N/A')}</span>
        </div>
        <div class="event-meta-row">
          <span class="event-meta-icon">💰</span>
          <span>₹${event.price}</span>
        </div>
      </div>

      <div class="seats-info">
        <div class="seats-bar-bg">
          <div class="seats-bar-fill" style="width: ${fillPct}%"></div>
        </div>
        <p class="seats-label">
          ${availSeats} of ${totalSeats} seats available (${bookedSeats} booked)
        </p>
      </div>

      <div class="event-card-actions">
        <button
          class="btn btn-outline btn-sm"
          onclick="openEditModal(${event.id})">
          ✏️ Edit
        </button>
        ${isActive ? `
        <button
          class="btn btn-danger btn-sm"
          onclick="openCancelModal(${event.id}, '${escapeHtml(event.title || event.eventName || '')}')">
          ❌ Cancel
        </button>` : ''}
        <button
          class="btn btn-sm"
          style="border:2px solid #2563eb; color:#2563eb;"
          onclick="loadBookings(${event.id}, '${escapeHtml(event.title || event.eventName || '')}')">
          🎟️ View Bookings
        </button>
      </div>
    </div>
  `;
}

/** Renders array of events into a grid container. */
function renderEventCards(container, events) {
  // Remove skeleton loaders
  container.querySelectorAll('.skeleton-card').forEach(s => s.remove());

  if (!events || events.length === 0) {
    container.innerHTML = '';
    return;
  }
  container.innerHTML = events.map(buildEventCard).join('');
}

/** Updates the 4 summary stat counters. */
function updateStats(events) {
  const total     = events.length;
  const active    = events.filter(e => !e.cancelled).length;
  const cancelled = events.filter(e => e.cancelled).length;
  // Total bookings = sum of booked seats across all events
  const bookings  = events.reduce((sum, e) => {
    const booked = (e.totalSeats || 0) - (e.availableSeats ?? e.totalSeats ?? 0);
    return sum + Math.max(0, booked);
  }, 0);

  statTotal.textContent     = total;
  statActive.textContent    = active;
  statCancelled.textContent = cancelled;
  statBookings.textContent  = bookings;
}

/** Fetches all events from GET /api/events/upcoming and renders them. */
async function loadEvents() {
  try {
    // Your backend: GET /api/events (Organizer sees all, customer sees upcoming)
    const res = await apiFetch('/events');

    if (!res.ok) {
      if (res.status === 401) {
      console.log("Unauthorized access - token may be invalid or expired. Redirecting to login.");
       //logout(); return;
        }
      throw new Error('Failed to load events');
    }

    allEvents = await res.json();

    // Update stat cards
    updateStats(allEvents);

    // Render recent 3 on overview
    renderEventCards(recentEventsGrid, allEvents.slice(0, 3));

    // Render all events on My Events section
    renderEventCards(allEventsGrid, filterEvents(allEvents, currentFilter));

    // Show empty state if no events
    if (allEvents.length === 0) {
      emptyState.style.display = 'block';
    } else {
      emptyState.style.display = 'none';
    }

  } catch (err) {
    console.log(err);
    showAlert(eventsAlert, 'error', err.message || 'Could not load events.');
    recentEventsGrid.innerHTML = '';
    allEventsGrid.innerHTML    = '';
  }
}

/** Safely escape HTML to prevent XSS. */
function escapeHtml(str) {
  return String(str)
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;')
    .replace(/'/g, '&#39;');
}


// ================================================
// H. CREATE EVENT
// ================================================

/** Validates the Create Event form. Returns true if valid. */
function validateCreateForm() {
  let valid = true;
  if (!createName.value.trim()) {
    showFieldError(createName, 'createNameError', 'Event name is required'); valid = false;
  }
  if (!createDesc.value.trim()) {
    showFieldError(createDesc, 'createDescError', 'Description is required'); valid = false;
  }
  if (!createDate.value) {
    showFieldError(createDate, 'createDateError', 'Date & time is required'); valid = false;
  } else if (new Date(createDate.value) <= new Date()) {
    showFieldError(createDate, 'createDateError', 'Date must be in the future'); valid = false;
  }
  if (!createVenue.value.trim()) {
    showFieldError(createVenue, 'createVenueError', 'Venue is required'); valid = false;
  }
  if (!createSeats.value || createSeats.value < 1) {
    showFieldError(createSeats, 'createSeatsError', 'Enter a valid number of seats'); valid = false;
  }
  if (createPrice.value === '' || createPrice.value < 0) {
    showFieldError(createPrice, 'createPriceError', 'Enter a valid ticket price'); valid = false;
  }
  return valid;
}

createEventForm.addEventListener('submit', async (e) => {
  e.preventDefault();
  if (!validateCreateForm()) return;

  createEventSubmitBtn.disabled    = true;
  createEventSubmitBtn.textContent = 'Creating...';
  createModalAlert.style.display   = 'none';

  const body = {
    title:         createName.value.trim(),
    description:  createDesc.value.trim(),
    eventDateTime:     createDate.value,
    venue:        createVenue.value.trim(),
    totalSeats:   parseInt(createSeats.value),
    price:  parseFloat(createPrice.value)
  };

  try {
    // Your backend: POST /api/events  (API already ends with /events, so path is '')
    // const res = await apiFetch('', {
    //   method: 'POST',
    //   body: JSON.stringify(body)
    // });
    const res = await apiFetch('/events', {
      method: 'POST',
      body: JSON.stringify(body)
    });

    if (res.status === 201 || res.status === 200) {
      console.log("Created event successfully");
    } else {
      const errorText = await res.text(); // Read the error message from backend
      console.error("Backend Error (400):", errorText);
      console.log("Status Code:", res.status);
      throw new Error(errorText || 'Failed to create event');
    }

    // if (!res.ok) {
    //   const err = await res.json().catch(() => ({}));
    //   throw new Error(err.message || 'Failed to create event');
    // }

    // Success
    closeModal(createEventModal);
    showAlert(eventsAlert, 'success', 'Event created successfully!');
    await loadEvents(); // Refresh list

  } catch (err) {
    //console.log(err);
    showAlert(createModalAlert, 'error', err.message);
  } finally {
    createEventSubmitBtn.disabled    = false;
    createEventSubmitBtn.textContent = '＋ Create Event';
  }
});


// ================================================
// I. EDIT EVENT
// ================================================

/**
 * Opens the Edit modal and pre-fills fields with event data.
 * Called from event card button: onclick="openEditModal(id)"
 */
window.openEditModal = function(id) {
  const event = allEvents.find(e => e.id === id);
  if (!event) return;

  // Pre-fill all fields
  editEventId.value = event.id;
  editName.value    = event.title || event.eventName || '';
  editDesc.value    = event.description || '';
  editVenue.value   = event.venue || '';
  editSeats.value   = event.totalSeats || '';
  editPrice.value   = event.ticketPrice ?? event.price ?? '';

  // Format datetime for input (needs "YYYY-MM-DDTHH:MM" format)
  if (event.eventDateTime || event.date) {
    const raw = event.eventDateTime || event.date;
    // Slice to "YYYY-MM-DDTHH:MM" (16 chars)
    editDate.value = raw.slice(0, 16);
  }

  openModal(editEventModal);
};

/** Validates the Edit Event form. Returns true if valid. */
function validateEditForm() {
  let valid = true;
  if (!editName.value.trim())   { showFieldError(editName,  'editNameError',  'Event name is required'); valid = false; }
  if (!editDesc.value.trim())   { showFieldError(editDesc,  'editDescError',  'Description is required'); valid = false; }
  if (!editDate.value)          { showFieldError(editDate,  'editDateError',  'Date & time is required'); valid = false; }
  if (!editVenue.value.trim())  { showFieldError(editVenue, 'editVenueError', 'Venue is required'); valid = false; }
  if (!editSeats.value || editSeats.value < 1) { showFieldError(editSeats, 'editSeatsError', 'Enter valid seats'); valid = false; }
  if (editPrice.value === '' || editPrice.value < 0) { showFieldError(editPrice, 'editPriceError', 'Enter valid price'); valid = false; }
  return valid;
}

editEventForm.addEventListener('submit', async (e) => {
  e.preventDefault();
  if (!validateEditForm()) return;

  editEventSubmitBtn.disabled    = true;
  editEventSubmitBtn.textContent = 'Saving...';
  editModalAlert.style.display   = 'none';

  const id   = editEventId.value;
  const body = {
    title:        editName.value.trim(),
    description: editDesc.value.trim(),
    eventDateTime:    editDate.value,
    venue:       editVenue.value.trim(),
    totalSeats:  parseInt(editSeats.value),
    price: parseFloat(editPrice.value)
  };

  try {
    // Your backend: PUT /api/events/{eventId}
    const res = await apiFetch(`/events/${id}`, {
      method: 'PUT',
      body: JSON.stringify(body)
    });

    if (!res.ok) {
      const err = await res.json().catch(() => ({}));
      throw new Error(err.message || 'Failed to update event');
    }

    closeModal(editEventModal);
    showAlert(eventsAlert, 'success', 'Event updated successfully!');
    await loadEvents();

  } catch (err) {
    showAlert(editModalAlert, 'error', err.message);
  } finally {
    editEventSubmitBtn.disabled    = false;
    editEventSubmitBtn.textContent = '💾 Save Changes';
  }
});


// ================================================
// J. CANCEL EVENT
// ================================================

/**
 * Opens the Cancel Confirm modal.
 * Called from event card button: onclick="openCancelModal(id, name)"
 */
window.openCancelModal = function(id, name) {
  cancelEventId.value              = id;
  cancelConfirmEventName.textContent = `Event: "${name}"`;
  openModal(cancelConfirmModal);
};

cancelConfirmYes.addEventListener('click', async () => {
  const id = cancelEventId.value;
  cancelConfirmYes.disabled    = true;
  cancelConfirmYes.textContent = 'Cancelling...';

  try {
    // Your backend: PUT /api/events/{eventId}/cancel  (NOT DELETE!)
    const res = await apiFetch(`/events/${id}/cancel`, { method: 'PUT' });

    if (!res.ok) {
      const err = await res.json().catch(() => ({}));
      throw new Error(err.message || 'Failed to cancel event');
    }

    closeModal(cancelConfirmModal);
    showAlert(eventsAlert, 'success', 'Event cancelled successfully.');
    await loadEvents();

  } catch (err) {
    closeModal(cancelConfirmModal);
    showAlert(eventsAlert, 'error', err.message);
  } finally {
    cancelConfirmYes.disabled    = false;
    cancelConfirmYes.textContent = 'Yes, Cancel Event';
  }
});


// ================================================
// K. VIEW BOOKINGS
// ================================================

/**
 * Loads bookings for a specific event and shows the bookings section.
 * Called from event card: onclick="loadBookings(id, name)"
 */
window.loadBookings = async function(eventId, eventName) {
  // Switch to bookings section
  showSection('section-bookings');
  bookingsEventName.textContent = `Bookings for: ${eventName}`;
  bookingsTableBody.innerHTML   = '<tr><td colspan="6" style="text-align:center;padding:20px;">Loading...</td></tr>';
  bookingsTable.style.display   = 'table';
  noBookings.style.display      = 'none';
  bookingsAlert.style.display   = 'none';

  try {
    // NOTE: Share your Bookings controller so we can set the exact URL.
    // Placeholder — update this path to match your BookingController endpoint.
    const res = await apiFetch(`/bookings/event/${eventId}`);

    if (!res.ok) {
      const err = await res.json().catch(() => ({}));
      throw new Error(err.message || 'Failed to load bookings');
    }

    const bookings = await res.json();

    if (!bookings || bookings.length === 0) {
      bookingsTable.style.display  = 'none';
      noBookings.style.display     = 'block';
      return;
    }

    // Build table rows
    bookingsTableBody.innerHTML = bookings.map((b, i) => `
      <tr>
        <td>${i + 1}</td>
        <td>${escapeHtml(b.userName || 'N/A')}</td>
        <td>${escapeHtml(b.userEmail)}</td>
        <td style="text-align:center;">${b.seatsBooked}</td>
        <td>${formatDate(b.bookingTime)}</td>
        <td>
          <span class="status-badge ${b.bookingStatus === 'CONFIRMED' ? 'status-active' : 'status-cancelled'}">
            ${b.bookingStatus || 'CONFIRMED'}
          </span>
        </td>
      </tr>
    `).join('');

  } catch (err) {
    console.log(err);
    bookingsTableBody.innerHTML = '';
    bookingsTable.style.display = 'none';
    showAlert(bookingsAlert, 'error', err.message);
  }
};


// ================================================
// L. LOGOUT
// ================================================
function logout() {
  localStorage.removeItem('authToken');
  localStorage.removeItem('userEmail');
  localStorage.removeItem('token');
  localStorage.removeItem('userName');
  localStorage.removeItem('userRole');
  window.location.href = 'index.html';
}

navLogoutBtn.addEventListener('click',     logout);
sidebarLogoutBtn.addEventListener('click', logout);


// ================================================
// M. INIT — Runs when page loads
// ================================================
document.addEventListener('DOMContentLoaded', () => {
  // Show overview section by default
  showSection('section-overview');
  // Load all events from backend
  loadEvents();
});
