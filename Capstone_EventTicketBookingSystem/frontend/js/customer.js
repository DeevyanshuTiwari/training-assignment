/* =============================================
   customer.js — Shared JS for all Customer pages
   Pages: customer-dashboard, event-details, payment, my-bookings
   Backend base URL: http://localhost:8083/api
============================================= */

// ── CONFIG ──────────────────────────────────────────────────────────────────
const API_BASE = 'http://localhost:8082/api';

// ── HELPERS ─────────────────────────────────────────────────────────────────

/** Get JWT token from localStorage (saved by index.html on login) */
function getToken() {
  return localStorage.getItem('token');
}

/** Get logged-in user's name from localStorage */
function getUserName() {
  return localStorage.getItem('userName') || localStorage.getItem('userEmail') || 'Customer';
}

/**
 * Build fetch options with Authorization header.
 * @param {string} method  - HTTP method: GET, POST, DELETE, etc.
 * @param {object} [body]  - Optional request body (will be JSON-stringified)
 */
function authFetch(url, method = 'GET', body = null) {
  const token = getToken();
  const opts = {
    method,
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    }
  };
  if (body) opts.body = JSON.stringify(body);
  return fetch(url, opts);
}

/**
 * Format a LocalDateTime string from the backend (e.g. "2026-05-10T18:30:00")
 * into a human-readable string like "10 May 2026, 6:30 PM"
 */
function formatDateTime(dtStr) {
  if (!dtStr) return '—';
  const d = new Date(dtStr);
  return d.toLocaleString('en-IN', {
    day: 'numeric', month: 'short', year: 'numeric',
    hour: '2-digit', minute: '2-digit', hour12: true
  });
}

/** Format price as ₹1,234 */
function formatPrice(price) {
  if (price == null || price === 0) return 'Free';
  return '₹' + Number(price).toLocaleString('en-IN');
}

/** Read a URL query parameter by name */
function getParam(name) {
  return new URLSearchParams(window.location.search).get(name);
}

/**
 * Show an alert banner.
 * @param {string} id       - element id of the .c-alert div
 * @param {string} type     - 'success' | 'error' | 'info'
 * @param {string} message  - text to display
 */
function showAlert(id, type, message) {
  const el = document.getElementById(id);
  if (!el) return;
  el.className = `c-alert ${type} show`;
  el.textContent = message;
  // Auto-hide after 5 seconds
  setTimeout(() => { el.classList.remove('show'); }, 5000);
}

/** Redirect to login if no token */
function requireAuth() {
  if (!getToken()) {
    window.location.href = 'index.html';
  }
}

/** Fill in the navbar user name and avatar initial */
function initNavbar() {
  const name = getUserName();
  const nameEl = document.getElementById('navUserName');
  const avatarEl = document.getElementById('navAvatar');
  if (nameEl) nameEl.textContent = name;
  if (avatarEl) avatarEl.textContent = name.charAt(0).toUpperCase();

  const logoutBtn = document.getElementById('logoutBtn');
  if (logoutBtn) {
    logoutBtn.addEventListener('click', () => {
      localStorage.clear();
      window.location.href = 'index.html';
    });
  }
}

// ── DETERMINE WHICH PAGE WE ARE ON & RUN APPROPRIATE INIT ──────────────────
document.addEventListener('DOMContentLoaded', () => {
  requireAuth();
  initNavbar();

  const page = window.location.pathname.split('/').pop();

  if (page === 'customer-dashboard.html' || page === '') {
    initDashboard();
  } else if (page === 'event-details.html') {
    initEventDetails();
  } else if (page === 'payment.html') {
    initPayment();
  } else if (page === 'my-bookings.html') {
    initMyBookings();
  }
});


// ══════════════════════════════════════════════════════════════════════════════
// PAGE 1 — CUSTOMER DASHBOARD (event listing + filters)
// ══════════════════════════════════════════════════════════════════════════════

/** Stores the full list of events so we can filter without extra API calls */
let allEvents = [];

async function initDashboard() {
  await fetchEvents();
  setupFilters();
}

/** Fetch all upcoming events from GET /api/events/upcoming */
async function fetchEvents() {
  try {
    const res = await authFetch(`${API_BASE}/events/upcoming`);

    if (res.status === 401 || res.status === 403) {
      localStorage.clear();
      window.location.href = 'index.html';
      return;
    }

    if (!res.ok) throw new Error('Failed to load events');

    allEvents = await res.json();
    renderEventCards(allEvents);

  } catch (err) {
    showAlert('dashAlert', 'error', '⚠️ Could not load events. Please try again.');
    document.getElementById('eventsGrid').innerHTML = `
      <div class="c-empty">
        <div class="c-empty-icon">😕</div>
        <p class="c-empty-title">Failed to load events</p>
        <p class="c-empty-sub">${err.message}</p>
      </div>`;
  }
}

/**
 * Render an array of events as cards into #eventsGrid.
 * @param {Array} events
 */
function renderEventCards(events) {
  const grid = document.getElementById('eventsGrid');
  if (!grid) return;

  if (events.length === 0) {
    grid.innerHTML = `
      <div class="c-empty">
        <div class="c-empty-icon">📭</div>
        <p class="c-empty-title">No Events Found</p>
        <p class="c-empty-sub">Try adjusting your filters or check back later.</p>
      </div>`;
    return;
  }

  grid.innerHTML = events.map(event => {
    const isCancelled = event.cancelled === true;
    const seats = event.availableSeats || 0;
    const seatsClass = seats === 0 ? 'full' : seats <= 10 ? 'low' : 'available';
    const seatsLabel = seats === 0 ? '🔴 Sold Out' : seats <= 10 ? `⚠️ ${seats} left` : `✅ ${seats} seats`;

    return `
      <div class="c-event-card ${isCancelled ? 'cancelled' : ''}">
        <div class="c-card-banner ${isCancelled ? 'cancelled' : ''}"></div>
        <div class="c-card-body">
          <h2 class="c-card-title">${escHtml(event.title)}</h2>
          <div class="c-card-meta">
            <div class="c-card-meta-row">
              <span class="c-card-meta-icon">📅</span>
              <span>${formatDateTime(event.eventDateTime)}</span>
            </div>
            <div class="c-card-meta-row">
              <span class="c-card-meta-icon">📍</span>
              <span>${escHtml(event.venue)}</span>
            </div>
            <div class="c-card-meta-row">
              <span class="c-card-meta-icon">💰</span>
              <span>${formatPrice(event.price)}</span>
            </div>
          </div>
        </div>
        <div class="c-card-footer">
          ${isCancelled
            ? `<span class="c-cancelled-badge">🚫 Cancelled</span>`
            : `<span class="c-seats-badge ${seatsClass}">${seatsLabel}</span>
               <button class="btn btn-primary btn-sm"
                 onclick="goToDetails(${event.id})">
                 View Details →
               </button>`
          }
        </div>
      </div>`;
  }).join('');
}

/** Navigate to event-details page with eventId as query param */
function goToDetails(eventId) {
  window.location.href = `event-details.html?eventId=${eventId}`;
}

/** Wire up search, date, venue inputs and clear button */
function setupFilters() {
  const searchInput = document.getElementById('searchInput');
  const dateFilter  = document.getElementById('dateFilter');
  const venueFilter = document.getElementById('venueFilter');
  const clearBtn    = document.getElementById('clearFilters');

  function applyFilters() {
    const searchVal = (searchInput.value || '').toLowerCase().trim();
    const dateVal   = dateFilter.value;   // 'YYYY-MM-DD' or ''
    const venueVal  = (venueFilter.value || '').toLowerCase().trim();

    const filtered = allEvents.filter(ev => {
      // Name filter
      if (searchVal && !ev.title.toLowerCase().includes(searchVal)) return false;

      // Date filter: compare the date portion of eventDateTime
      if (dateVal) {
        const evDate = ev.eventDateTime ? ev.eventDateTime.split('T')[0] : '';
        if (evDate !== dateVal) return false;
      }

      // Venue filter
      if (venueVal && !ev.venue.toLowerCase().includes(venueVal)) return false;

      return true;
    });

    renderEventCards(filtered);
  }

  searchInput.addEventListener('input', applyFilters);
  dateFilter.addEventListener('change', applyFilters);
  venueFilter.addEventListener('input', applyFilters);

  clearBtn.addEventListener('click', () => {
    searchInput.value = '';
    dateFilter.value  = '';
    venueFilter.value = '';
    renderEventCards(allEvents);
  });
}

/** Simple HTML escape to prevent XSS */
function escHtml(str) {
  if (!str) return '';
  return str.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g,'&quot;');
}


// ══════════════════════════════════════════════════════════════════════════════
// PAGE 2 — EVENT DETAILS
// ══════════════════════════════════════════════════════════════════════════════

let currentEvent = null; // stores the event object for this page

async function initEventDetails() {
  const eventId = getParam('eventId');
  if (!eventId) {
    window.location.href = 'customer-dashboard.html';
    return;
  }

  // Back button
  document.getElementById('backBtn').addEventListener('click', () => {
    window.history.back();
  });

  await fetchEventDetail(eventId);
  setupSeatsSelector();
}

/** Fetch events list and find the one matching eventId */
async function fetchEventDetail(eventId) {
  try {
    const res = await authFetch(`${API_BASE}/events/${eventId}`);
    if (res.status === 401 || res.status === 403) {
      localStorage.clear();
      window.location.href = 'index.html';
      return;
    }

    if (!res.ok) throw new Error('Event not found');

    const event = await res.json();

    if (!event) {
      showAlert('detailAlert', 'error', '⚠️ Event not found.');
      document.getElementById('detailSkeleton').style.display = 'none';
      return;
    }

    currentEvent = event;
    renderEventDetail(event);

  } catch (err) {
    showAlert('detailAlert', 'error', `⚠️ ${err.message}`);
  }
}

/** Fill in all the detail card fields */
function renderEventDetail(event) {
  document.getElementById('detailSkeleton').style.display = 'none';
  document.getElementById('detailCard').style.display = 'block';

  document.getElementById('detailTitle').textContent       = event.title;
  document.getElementById('detailDescription').textContent = event.description || 'No description provided.';
  document.getElementById('detailDate').textContent        = formatDateTime(event.eventDateTime);
  document.getElementById('detailVenue').textContent       = event.venue;
  document.getElementById('detailTotal').textContent       = event.totalSeats;
  document.getElementById('detailAvailable').textContent   = event.availableSeats;
  document.getElementById('detailPrice').textContent       = formatPrice(event.price);

  // Cancelled state
  if (event.cancelled) {
    document.getElementById('cancelledBadge').style.display = 'block';
    document.getElementById('detailBanner').classList.add('cancelled');
    document.getElementById('bookBtn').style.display = 'none';
  } else if (event.availableSeats === 0) {
    document.getElementById('bookBtn').textContent = '🔴 Sold Out';
    document.getElementById('bookBtn').disabled = true;
  }
}

/** Show/hide seats input when "Book Ticket" is clicked */
function setupSeatsSelector() {
  const bookBtn    = document.getElementById('bookBtn');
  const selector   = document.getElementById('seatsSelector');
  const seatsInput = document.getElementById('seatsInput');
  const totalDisp  = document.getElementById('totalPriceDisplay');
  const proceedBtn = document.getElementById('proceedBtn');
  const seatsErr   = document.getElementById('seatsError');

  if (!bookBtn) return;

  // Toggle seats selector
  bookBtn.addEventListener('click', () => {
    selector.classList.toggle('show');
    if (selector.classList.contains('show')) {
      seatsInput.value = 1;
      updateTotal();
      seatsInput.focus();
    }
  });

  // Update total price display as user changes quantity
  seatsInput.addEventListener('input', updateTotal);

  function updateTotal() {
    const qty = parseInt(seatsInput.value) || 0;
    const price = currentEvent ? (currentEvent.price || 0) : 0;
    totalDisp.textContent = `₹${(qty * price).toLocaleString('en-IN')}`;
  }

  // Proceed to payment
  proceedBtn.addEventListener('click', () => {
    const qty = parseInt(seatsInput.value);
    const max = currentEvent ? currentEvent.availableSeats : 0;

    seatsErr.style.display = 'none';

    if (!qty || qty < 1) {
      seatsErr.textContent = 'Please enter at least 1 ticket.';
      seatsErr.style.display = 'block';
      return;
    }
    if (qty > max) {
      seatsErr.textContent = `Only ${max} seats available.`;
      seatsErr.style.display = 'block';
      return;
    }

    // Navigate to payment page with eventId + seats in URL
    window.location.href = `payment.html?eventId=${currentEvent.id}&seats=${qty}`;
  });
}


// ══════════════════════════════════════════════════════════════════════════════
// PAGE 3 — PAYMENT
// ══════════════════════════════════════════════════════════════════════════════

async function initPayment() {
  const eventId = getParam('eventId');
  const seats   = parseInt(getParam('seats')) || 1;

  if (!eventId) {
    window.location.href = 'customer-dashboard.html';
    return;
  }

  // Back button
  const backBtn = document.getElementById('payBackBtn');
  if (backBtn) {
    backBtn.addEventListener('click', () => {
      window.location.href = `event-details.html?eventId=${eventId}`;
    });
  }

  // Fetch event to fill summary
  await loadPaymentSummary(eventId, seats);

  // Complete booking button
  document.getElementById('completeBtn').addEventListener('click', () => {
    confirmBooking(eventId, seats);
  });
}

/** Fill in the order summary card */
async function loadPaymentSummary(eventId, seats) {
  try {
    const res = await authFetch(`${API_BASE}/events/${eventId}`);
    if (!res.ok) throw new Error('Could not load event');
    const event = await res.json();

    document.getElementById('sumEventTitle').textContent = event.title;
    document.getElementById('sumDate').textContent        = formatDateTime(event.eventDateTime);
    document.getElementById('sumVenue').textContent       = event.venue;
    document.getElementById('sumSeats').textContent       = `${seats} ticket${seats > 1 ? 's' : ''}`;
    document.getElementById('sumPrice').textContent       = formatPrice(event.price);
    document.getElementById('sumTotal').textContent       = `₹${((event.price || 0) * seats).toLocaleString('en-IN')}`;

  } catch (err) {
    console.log(err);
    showAlert('paymentAlert', 'error', `⚠️ ${err.message}`);
  }
}

/** Call POST /api/bookings to confirm the booking */
async function confirmBooking(eventId, seats) {
  // Show overlay with loading spinner
  const overlay = document.getElementById('bookingOverlay');
  const loadingEl = document.getElementById('overlayLoading');
  const successEl = document.getElementById('overlaySuccess');

  overlay.classList.add('show');
  loadingEl.style.display = 'block';
  successEl.style.display = 'none';

  try {
    const res = await authFetch(`${API_BASE}/bookings`, 'POST', {
      eventId: parseInt(eventId),
      seatsRequested: seats,
      userName: localStorage.getItem('userName') || 'Anonymous'
    });

    if (res.status === 401 || res.status === 403) {
      overlay.classList.remove('show');
      localStorage.clear();
      window.location.href = 'index.html';
      return;
    }

    if (!res.ok) {
      const errText = await res.text();
      throw new Error(errText || 'Booking failed');
    }

    // Switch to success state
    loadingEl.style.display = 'none';
    successEl.style.display = 'block';

  } catch (err) {
    console.log(err);
    overlay.classList.remove('show');
    showAlert('paymentAlert', 'error', `⚠️ ${err.message}`);
  }
}


// ══════════════════════════════════════════════════════════════════════════════
// PAGE 4 — MY BOOKINGS
// ══════════════════════════════════════════════════════════════════════════════

let allBookings   = []; // full list from API
let activeFilter  = 'ALL';

async function initMyBookings() {
  await fetchBookings();
  setupBookingTabs();
  setupCancelModal();
}

/** Fetch GET /api/bookings/history */
async function fetchBookings() {
  try {
    const res = await authFetch(`${API_BASE}/bookings/history`);

    if (res.status === 401 || res.status === 403) {
      localStorage.clear();
      window.location.href = 'index.html';
      return;
    }

    if (!res.ok) throw new Error('Failed to load bookings');

    allBookings = await res.json();
    renderBookings(allBookings);

  } catch (err) {
    showAlert('bookingsAlert', 'error', `⚠️ ${err.message}`);
    document.getElementById('bookingsGrid').innerHTML = `
      <div class="c-empty">
        <div class="c-empty-icon">😕</div>
        <p class="c-empty-title">Could not load bookings</p>
        <p class="c-empty-sub">${err.message}</p>
      </div>`;
  }
}

/** Render booking cards. Accepts a filtered or full array. */
function renderBookings(bookings) {
  const grid = document.getElementById('bookingsGrid');
  if (!grid) return;

  if (bookings.length === 0) {
    grid.innerHTML = `
      <div class="c-empty">
        <div class="c-empty-icon">🎟️</div>
        <p class="c-empty-title">No bookings here</p>
        <p class="c-empty-sub">You haven't made any bookings yet. <a href="customer-dashboard.html" style="color:var(--primary)">Browse events →</a></p>
      </div>`;
    return;
  }

  grid.innerHTML = bookings.map(b => {
    const isCancelled = b.bookingStatus === 'CANCELLED';
    const statusClass = isCancelled ? 'cancelled' : 'confirmed';
    const statusLabel = isCancelled ? '🚫 Cancelled' : '✅ Confirmed';

    return `
      <div class="c-booking-card">
        <div class="c-booking-banner ${isCancelled ? 'cancelled-banner' : ''}"></div>
        <div class="c-booking-body">
          <h2 class="c-booking-title">${escHtml(b.eventTitle)}</h2>
          <div class="c-booking-meta">
            <div class="c-booking-meta-row">
              <span>📅</span>
              <span>Booked on: ${formatDateTime(b.bookingTime)}</span>
            </div>
            <div class="c-booking-meta-row">
              <span>🎫</span>
              <span>${b.seatsBooked} ticket${b.seatsBooked > 1 ? 's' : ''}</span>
            </div>
            <div class="c-booking-meta-row">
              <span>📧</span>
              <span>${escHtml(b.userEmail)}</span>
            </div>
          </div>
        </div>
        <div class="c-booking-footer">
          <span class="status-badge ${statusClass}">${statusLabel}</span>
          ${!isCancelled
            ? `<button class="btn btn-danger btn-sm"
                 onclick="openCancelModal(${b.bookingId}, '${escHtml(b.eventTitle).replace(/'/g,"\\'")}')">
                 Cancel
               </button>`
            : ''
          }
        </div>
      </div>`;
  }).join('');
}

/** Wire up the tab buttons to filter bookings */
function setupBookingTabs() {
  document.querySelectorAll('.c-tab').forEach(tab => {
    tab.addEventListener('click', () => {
      document.querySelectorAll('.c-tab').forEach(t => t.classList.remove('active'));
      tab.classList.add('active');
      activeFilter = tab.dataset.filter;
      applyBookingFilter();
    });
  });
}

/** Filter allBookings based on activeFilter */
function applyBookingFilter() {
  const now = new Date();

  const filtered = allBookings.filter(b => {
    if (activeFilter === 'ALL') return true;
    if (activeFilter === 'CANCELLED') return b.bookingStatus === 'CANCELLED';
    // We check the bookingTime date since BookingResponse doesn't include eventDateTime
    // Upcoming = booking is CONFIRMED (not cancelled, not in the past by bookingTime)
    if (activeFilter === 'UPCOMING') return b.bookingStatus !== 'CANCELLED';
    if (activeFilter === 'PAST') {
      const bTime = new Date(b.bookingTime);
      return bTime < now && b.bookingStatus !== 'CANCELLED';
    }
    return true;
  });

  renderBookings(filtered);
}

/** Open cancel confirmation modal */
function openCancelModal(bookingId, eventTitle) {
  document.getElementById('cancelBookingId').value = bookingId;
  document.getElementById('cancelModalEventName').textContent =
    `Cancel your booking for "${eventTitle}"? This cannot be undone.`;
  document.getElementById('cancelModal').classList.add('show');
}

/** Wire up the cancel confirmation modal buttons */
function setupCancelModal() {
  const yesBtn = document.getElementById('confirmCancelYes');
  const noBtn  = document.getElementById('confirmCancelNo');

  if (noBtn) {
    noBtn.addEventListener('click', () => {
      document.getElementById('cancelModal').classList.remove('show');
    });
  }

  if (yesBtn) {
    yesBtn.addEventListener('click', async () => {
      const bookingId = document.getElementById('cancelBookingId').value;
      document.getElementById('cancelModal').classList.remove('show');
      await cancelBooking(bookingId);
    });
  }
}

/** Call DELETE /api/bookings/{bookingId} */
async function cancelBooking(bookingId) {
  try {
    const res = await authFetch(`${API_BASE}/bookings/${bookingId}`, 'DELETE');

    if (res.status === 401 || res.status === 403) {
      localStorage.clear();
      window.location.href = 'index.html';
      return;
    }

    if (!res.ok) {
      const errText = await res.text();
      throw new Error(errText || 'Could not cancel booking');
    }

    showAlert('bookingsAlert', 'success', '✅ Booking cancelled successfully.');

    // Refresh the bookings list
    await fetchBookings();

  } catch (err) {
    showAlert('bookingsAlert', 'error', `⚠️ ${err.message}`);
  }
}
