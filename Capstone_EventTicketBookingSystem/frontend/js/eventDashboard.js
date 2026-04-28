<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Organizer Dashboard — Eventify</title>
  <!-- Step 2: Dashboard CSS -->
  <link rel="stylesheet" href="css/eventDashboard.css" />
</head>

<body>

  <!-- Atmospheric background -->
  <div class="noise-overlay" style="position:fixed;inset:0;z-index:0;pointer-events:none;opacity:.015;background-image:url('data:image/svg+xml,%3Csvg viewBox=%270 0 256 256%27 xmlns=%27http://www.w3.org/2000/svg%27%3E%3Cfilter id=%27n%27%3E%3CfeTurbulence type=%27fractalNoise%27 baseFrequency=%270.9%27 numOctaves=%274%27 stitchTiles=%27stitch%27/%3E%3C/filter%3E%3Crect width=%27100%25%27 height=%27100%25%27 filter=%27url(%23n)%27/%3E%3C/svg%3E');background-repeat:repeat;background-size:128px 128px"></div>

  <!-- ================================================
       NAVBAR — Fixed top bar
       Logo | Organizer name | Logout button
  ================================================ -->
  <header class="dash-navbar">
    <div class="dash-navbar-inner">

      <!-- Logo (left) -->
      <div class="dash-logo">
        <span class="dash-logo-icon">✦</span>
        <span class="dash-logo-text">Event<span class="dash-logo-accent">ify</span></span>
      </div>

      <!-- Right side: organizer name + logout -->
      <div class="dash-navbar-right">
        <!-- Organizer name filled by JS from localStorage -->
        <div class="dash-user-info">
          <span class="dash-user-avatar" id="userAvatar">O</span>
          <span class="dash-user-name" id="organizerName">Organizer</span>
        </div>
        <button id="navLogoutBtn" class="btn btn-danger-outline">
          🚪 Logout
        </button>
      </div>

    </div>
  </header>


  <!-- ================================================
       PAGE WRAPPER
       Sidebar (left) + Main content (right) side by side
  ================================================ -->
  <div class="dash-wrapper">


    <!-- ============================================
         SIDEBAR — Left navigation panel
         Menu items: Dashboard, My Events,
                     Create Event, Logout
    ============================================ -->
    <aside class="dash-sidebar">
      <nav class="sidebar-nav">

        <!-- Each nav item has data-target to tell JS which section to show -->
        <button class="sidebar-item active" data-target="section-overview" id="navDashboard">
          <span class="sidebar-icon">🏠</span>
          <span class="sidebar-label">Dashboard</span>
        </button>

        <button class="sidebar-item" data-target="section-events" id="navEvents">
          <span class="sidebar-icon">📅</span>
          <span class="sidebar-label">My Events</span>
        </button>

        <button class="sidebar-item" id="navCreateEvent">
          <span class="sidebar-icon">➕</span>
          <span class="sidebar-label">Create Event</span>
        </button>

        <!-- Divider line -->
        <div class="sidebar-divider"></div>

        <button class="sidebar-item sidebar-item-danger" id="sidebarLogoutBtn">
          <span class="sidebar-icon">🚪</span>
          <span class="sidebar-label">Logout</span>
        </button>

      </nav>
    </aside>


    <!-- ============================================
         MAIN CONTENT AREA
         All sections live here. JS shows/hides them.
    ============================================ -->
    <main class="dash-main">


      <!-- ==========================================
           SECTION 1: OVERVIEW / DASHBOARD HOME
           Summary cards + recent events preview
      ========================================== -->
      <section id="section-overview" class="dash-section active-section">

        <!-- Page heading -->
        <div class="section-header">
          <div>
            <h1 class="section-title">Dashboard Overview</h1>
            <p class="section-subtitle">Welcome back! Here's what's happening with your events.</p>
          </div>
          <!-- Quick action button -->
          <button class="btn btn-primary" id="overviewCreateBtn">
            ＋ Create Event
          </button>
        </div>

        <!-- ------------------------------------------
             SUMMARY CARDS — 4 stat boxes
             Numbers filled dynamically by JS
        ------------------------------------------ -->
        <div class="stats-grid">

          <!-- Card 1: Total Events -->
          <div class="stat-card stat-card-blue">
            <div class="stat-icon">📋</div>
            <div class="stat-info">
              <p class="stat-label">Total Events</p>
              <!-- JS writes the number here -->
              <p class="stat-number" id="statTotal">—</p>
            </div>
          </div>

          <!-- Card 2: Active Events -->
          <div class="stat-card stat-card-green">
            <div class="stat-icon">✅</div>
            <div class="stat-info">
              <p class="stat-label">Active Events</p>
              <p class="stat-number" id="statActive">—</p>
            </div>
          </div>

          <!-- Card 3: Cancelled Events -->
          <div class="stat-card stat-card-red">
            <div class="stat-icon">❌</div>
            <div class="stat-info">
              <p class="stat-label">Cancelled Events</p>
              <p class="stat-number" id="statCancelled">—</p>
            </div>
          </div>

          <!-- Card 4: Total Bookings -->
          <div class="stat-card stat-card-purple">
            <div class="stat-icon">🎟️</div>
            <div class="stat-info">
              <p class="stat-label">Total Bookings</p>
              <p class="stat-number" id="statBookings">—</p>
            </div>
          </div>

        </div><!-- /stats-grid -->

        <!-- Recent events preview (first 3 events) -->
        <div class="recent-events-header">
          <h2 class="subsection-title">Recent Events</h2>
          <button class="link-btn" data-target="section-events" id="viewAllBtn">
            View All →
          </button>
        </div>
        <!-- JS renders recent event cards here -->
        <div class="events-grid" id="recentEventsGrid">
          <!-- Skeleton loader shown while fetching -->
          <div class="skeleton-card"></div>
          <div class="skeleton-card"></div>
          <div class="skeleton-card"></div>
        </div>

      </section><!-- /section-overview -->


      <!-- ==========================================
           SECTION 2: MY EVENTS
           Full list of all events as cards
      ========================================== -->
      <section id="section-events" class="dash-section">

        <div class="section-header">
          <div>
            <h1 class="section-title">My Events</h1>
            <p class="section-subtitle">Manage all your created events here.</p>
          </div>
          <button class="btn btn-primary" id="eventsCreateBtn">
            ＋ Create Event
          </button>
        </div>

        <!-- Filter bar: All | Active | Cancelled -->
        <div class="filter-bar">
          <button class="filter-btn active" data-filter="ALL">All</button>
          <button class="filter-btn" data-filter="ACTIVE">Active</button>
          <button class="filter-btn" data-filter="CANCELLED">Cancelled</button>
        </div>

        <!-- Alert box for success/error messages -->
        <div id="eventsAlert" class="alert" style="display:none;"></div>

        <!-- JS renders all event cards here -->
        <div class="events-grid" id="allEventsGrid">
          <!-- Skeleton loader -->
          <div class="skeleton-card"></div>
          <div class="skeleton-card"></div>
        </div>

        <!-- Empty state — shown by JS when no events exist -->
        <div id="emptyState" class="empty-state" style="display:none;">
          <div class="empty-icon">📭</div>
          <h3 class="empty-title">No Events Yet</h3>
          <p class="empty-subtitle">Create your first event to get started!</p>
          <button class="btn btn-primary" id="emptyCreateBtn">＋ Create Your First Event</button>
        </div>

      </section><!-- /section-events -->


      <!-- ==========================================
           SECTION 3: BOOKINGS
           Shown when "View Bookings" is clicked on an event card.
           JS populates the table rows.
      ========================================== -->
      <section id="section-bookings" class="dash-section">

        <div class="section-header">
          <div>
            <!-- Event name filled by JS -->
            <h1 class="section-title">Bookings</h1>
            <p class="section-subtitle" id="bookingsEventName">
              Loading bookings...
            </p>
          </div>
          <!-- Buttons container -->
          <div style="display: flex; gap: 12px; align-items: center;">
            <button class="btn btn-primary" id="downloadCsvBtn" style="display: none;">
              📥 Download CSV
            </button>
            <button class="btn btn-outline" id="backToEventsBtn">
              ← Back to Events
            </button>
          </div>
        </div>

        <!-- Alert for errors -->
        <div id="bookingsAlert" class="alert" style="display:none;"></div>

        <!-- Bookings table wrapper -->
        <div class="table-wrapper">
          <table class="bookings-table" id="bookingsTable">
            <thead>
              <tr>
                <th>#</th>
                <th>User Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Tickets Booked</th>
                <th>Booked At</th>
                <th>Status</th>
              </tr>
            </thead>
            <!-- JS fills these rows dynamically -->
            <tbody id="bookingsTableBody">
              <!-- rows added by JS -->
            </tbody>
          </table>
        </div>

        <!-- Empty bookings state -->
        <div id="noBookings" class="empty-state" style="display:none;">
          <div class="empty-icon">🎟️</div>
          <h3 class="empty-title">No Bookings Yet</h3>
          <p class="empty-subtitle">No one has booked this event yet.</p>
        </div>

      </section><!-- /section-bookings -->


    </main><!-- /dash-main -->
  </div><!-- /dash-wrapper -->


  <!-- ================================================
       CREATE EVENT MODAL
       Overlay → Box → Form
       Fields: name, description, date, venue, seats
  ================================================ -->
  <div id="createEventModal" class="modal-overlay" role="dialog" aria-modal="true" aria-labelledby="createModalTitle">
    <div class="modal-box">

      <div class="modal-header">
        <div>
          <h2 id="createModalTitle" class="modal-title">Create New Event</h2>
          <p class="modal-subtitle">Fill in the details to list your event</p>
        </div>
        <button id="closeCreateModal" class="modal-close-btn" type="button" aria-label="Close">✕</button>
      </div>

      <!-- Success/Error alert inside modal -->
      <div id="createModalAlert" class="alert" style="display:none;"></div>

      <form id="createEventForm" class="modal-form" novalidate>

        <!-- Event Name -->
        <div class="form-group">
          <label for="createName" class="form-label">Event Name <span class="required">*</span></label>
          <input type="text" id="createName" class="form-input" placeholder="e.g. YOASOBI Asia Tour 2026" />
          <span class="field-error" id="createNameError"></span>
        </div>

        <!-- Description -->
        <div class="form-group">
          <label for="createDesc" class="form-label">Description <span class="required">*</span></label>
          <textarea id="createDesc" class="form-input form-textarea" placeholder="Describe your event..." rows="3"></textarea>
          <span class="field-error" id="createDescError"></span>
        </div>

        <!-- Date & Time -->
        <div class="form-group">
          <label for="createDate" class="form-label">Date &amp; Time <span class="required">*</span></label>
          <input type="datetime-local" id="createDate" class="form-input" />
          <span class="field-error" id="createDateError"></span>
        </div>

        <!-- Venue -->
        <div class="form-group">
          <label for="createVenue" class="form-label">Venue <span class="required">*</span></label>
          <input type="text" id="createVenue" class="form-input" placeholder="e.g. Jakarta Convention Center" />
          <span class="field-error" id="createVenueError"></span>
        </div>

        <!-- Total Seats -->
        <div class="form-group">
          <label for="createSeats" class="form-label">Total Seats <span class="required">*</span></label>
          <input type="number" id="createSeats" class="form-input" placeholder="e.g. 500" min="1" />
          <span class="field-error" id="createSeatsError"></span>
        </div>

        <!-- Ticket Price -->
        <div class="form-group">
          <label for="createPrice" class="form-label">Ticket Price (₹) <span class="required">*</span></label>
          <input type="number" id="createPrice" class="form-input" placeholder="e.g. 999" min="0" />
          <span class="field-error" id="createPriceError"></span>
        </div>

        <button type="submit" id="createEventSubmitBtn" class="btn btn-primary btn-full">
          ＋ Create Event
        </button>

      </form>

    </div>
  </div><!-- /createEventModal -->


  <!-- ================================================
       EDIT EVENT MODAL
       Same fields as create, but pre-filled by JS.
       A hidden input stores the event ID being edited.
  ================================================ -->
  <div id="editEventModal" class="modal-overlay" role="dialog" aria-modal="true" aria-labelledby="editModalTitle">
    <div class="modal-box">

      <div class="modal-header">
        <div>
          <h2 id="editModalTitle" class="modal-title">Edit Event</h2>
          <p class="modal-subtitle">Update your event details below</p>
        </div>
        <button id="closeEditModal" class="modal-close-btn" type="button" aria-label="Close">✕</button>
      </div>

      <!-- Alert inside edit modal -->
      <div id="editModalAlert" class="alert" style="display:none;"></div>

      <form id="editEventForm" class="modal-form" novalidate>

        <!-- Hidden field to store the event's ID (needed for PUT /events/{id}) -->
        <input type="hidden" id="editEventId" />

        <!-- Event Name -->
        <div class="form-group">
          <label for="editName" class="form-label">Event Name <span class="required">*</span></label>
          <input type="text" id="editName" class="form-input" />
          <span class="field-error" id="editNameError"></span>
        </div>

        <!-- Description -->
        <div class="form-group">
          <label for="editDesc" class="form-label">Description <span class="required">*</span></label>
          <textarea id="editDesc" class="form-input form-textarea" rows="3"></textarea>
          <span class="field-error" id="editDescError"></span>
        </div>

        <!-- Date & Time -->
        <div class="form-group">
          <label for="editDate" class="form-label">Date &amp; Time <span class="required">*</span></label>
          <input type="datetime-local" id="editDate" class="form-input" />
          <span class="field-error" id="editDateError"></span>
        </div>

        <!-- Venue -->
        <div class="form-group">
          <label for="editVenue" class="form-label">Venue <span class="required">*</span></label>
          <input type="text" id="editVenue" class="form-input" />
          <span class="field-error" id="editVenueError"></span>
        </div>

        <!-- Total Seats -->
        <div class="form-group">
          <label for="editSeats" class="form-label">Total Seats <span class="required">*</span></label>
          <input type="number" id="editSeats" class="form-input" min="1" />
          <span class="field-error" id="editSeatsError"></span>
        </div>

        <!-- Ticket Price -->
        <div class="form-group">
          <label for="editPrice" class="form-label">Ticket Price (₹) <span class="required">*</span></label>
          <input type="number" id="editPrice" class="form-input" min="0" />
          <span class="field-error" id="editPriceError"></span>
        </div>

        <button type="submit" id="editEventSubmitBtn" class="btn btn-primary btn-full">
          💾 Save Changes
        </button>

      </form>

    </div>
  </div><!-- /editEventModal -->


  <!-- ================================================
       CANCEL CONFIRM MODAL
       Simple "Are you sure?" before deleting.
       WHY: Prevents accidental cancellation.
  ================================================ -->
  <div id="cancelConfirmModal" class="modal-overlay" role="dialog" aria-modal="true">
    <div class="modal-box modal-box-sm">

      <div class="modal-header">
        <div>
          <h2 class="modal-title">Cancel Event?</h2>
          <p class="modal-subtitle" id="cancelConfirmEventName">This action cannot be undone.</p>
        </div>
        <button id="closeCancelModal" class="modal-close-btn" type="button">✕</button>
      </div>

      <p class="confirm-message">
        Are you sure you want to <strong>cancel</strong> this event?
        All existing bookings will be affected.
      </p>

      <!-- Hidden field stores event ID to cancel -->
      <input type="hidden" id="cancelEventId" />

      <div class="confirm-actions">
        <button id="cancelConfirmYes" class="btn btn-danger">Yes, Cancel Event</button>
        <button id="cancelConfirmNo"  class="btn btn-outline">No, Keep It</button>
      </div>

    </div>
  </div><!-- /cancelConfirmModal -->


  <!-- Step 4: Dashboard JavaScript -->
  <script src="js/eventDashboard.js"></script>

</body>
</html>