/* customer.css — Minimalist Dark — Customer Pages */
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&family=Space+Grotesk:wght@400;500;600;700&family=JetBrains+Mono:wght@400;500&display=swap');

:root {
  --bg:#0A0A0F;--bg-alt:#12121A;--surface:#1A1A24;--fg:#FAFAFA;--muted:#71717A;
  --accent:#F59E0B;--accent-dark:#D97706;--accent-muted:rgba(245,158,11,0.15);
  --border:rgba(255,255,255,0.08);--border-hover:rgba(255,255,255,0.15);
  --card:rgba(26,26,36,0.6);--card-solid:#1A1A24;
  --error:#ef4444;--error-bg:rgba(239,68,68,0.15);
  --success:#22c55e;--success-bg:rgba(34,197,94,0.15);
  --info-bg:rgba(99,102,241,0.15);--info:#818cf8;
  --font-d:'Space Grotesk',system-ui,sans-serif;
  --font-b:'Inter',system-ui,sans-serif;
  --font-m:'JetBrains Mono',monospace;
  --r-sm:6px;--r-md:8px;--r-lg:12px;--r-xl:16px;--r-full:9999px;
  --nav-h:68px;
  --tr-fast:200ms ease-out;--tr-norm:300ms ease-out;
  --glow-btn:0 0 20px rgba(245,158,11,0.4);
}

*,*::before,*::after{box-sizing:border-box;margin:0;padding:0}
html{font-family:var(--font-b);font-size:16px;scroll-behavior:smooth}
body{background:var(--bg);color:var(--fg);min-height:100vh;overflow-x:hidden}
::-webkit-scrollbar{width:6px}
::-webkit-scrollbar-track{background:var(--bg)}
::-webkit-scrollbar-thumb{background:rgba(255,255,255,0.1);border-radius:4px}

.noise-overlay{position:fixed;inset:0;z-index:0;pointer-events:none;opacity:.015;background-image:url("data:image/svg+xml,%3Csvg viewBox='0 0 256 256' xmlns='http://www.w3.org/2000/svg'%3E%3Cfilter id='n'%3E%3CfeTurbulence type='fractalNoise' baseFrequency='0.9' numOctaves='4' stitchTiles='stitch'/%3E%3C/filter%3E%3Crect width='100%25' height='100%25' filter='url(%23n)'/%3E%3C/svg%3E");background-repeat:repeat;background-size:128px 128px}
.ambient-orb{position:fixed;border-radius:50%;pointer-events:none;z-index:0;filter:blur(120px)}
.orb-1{width:600px;height:600px;background:rgba(245,158,11,0.03);top:-200px;left:50%;transform:translateX(-50%)}
.orb-2{width:400px;height:400px;background:rgba(245,158,11,0.02);bottom:-100px;right:-100px}

/* ── Navbar ── */
.c-navbar{position:fixed;top:0;left:0;right:0;z-index:100;height:var(--nav-h);background:rgba(10,10,15,0.85);backdrop-filter:blur(12px);border-bottom:1px solid var(--border);display:flex;align-items:center}
.c-navbar-inner{max-width:1200px;margin:0 auto;padding:0 24px;width:100%;display:flex;align-items:center;gap:24px}
.c-logo{display:flex;align-items:center;gap:8px;text-decoration:none;flex-shrink:0}
.c-logo-icon{font-size:1.4rem;color:var(--accent);filter:drop-shadow(0 0 8px rgba(245,158,11,0.5))}
.c-logo-text{font-family:var(--font-d);font-size:1.2rem;font-weight:700;color:var(--fg);letter-spacing:-.5px}
.c-logo-accent{color:var(--accent)}
.c-nav-links{display:flex;gap:4px;margin-left:auto}
.c-nav-link{padding:8px 16px;border-radius:var(--r-md);font-size:.9rem;font-weight:500;color:var(--muted);text-decoration:none;background:none;border:none;cursor:pointer;transition:all var(--tr-fast)}
.c-nav-link:hover,.c-nav-link.active{color:var(--accent);background:var(--accent-muted)}
.c-nav-right{display:flex;align-items:center;gap:12px;flex-shrink:0}
.c-user-chip{display:flex;align-items:center;gap:8px;background:rgba(255,255,255,0.05);padding:6px 12px;border-radius:var(--r-full);border:1px solid var(--border)}
.c-user-avatar{width:32px;height:32px;border-radius:50%;background:var(--accent);color:var(--bg);font-size:.8rem;font-weight:700;display:flex;align-items:center;justify-content:center}
.c-user-name{font-size:.85rem;font-weight:600;color:var(--fg)}

/* ── Buttons ── */
.btn{display:inline-flex;align-items:center;justify-content:center;gap:6px;padding:10px 20px;border-radius:var(--r-lg);font-size:.9rem;font-weight:600;border:1px solid transparent;cursor:pointer;transition:all var(--tr-fast);text-decoration:none;white-space:nowrap}
.btn:active{transform:scale(.98)}
.btn:disabled{opacity:.5;cursor:not-allowed;transform:none!important}
.btn:focus-visible{outline:none;box-shadow:0 0 0 2px var(--bg),0 0 0 4px var(--accent)}
.btn-primary{background:var(--accent);color:var(--bg);border:none}
.btn-primary:hover{filter:brightness(1.1);box-shadow:var(--glow-btn)}
.btn-outline{background:transparent;color:var(--fg);border:1px solid var(--border-hover)}
.btn-outline:hover{background:rgba(255,255,255,0.05);border-color:rgba(255,255,255,0.25)}
.btn-danger{background:var(--error);color:#fff;border:none}
.btn-danger:hover{filter:brightness(1.1);box-shadow:0 0 20px rgba(239,68,68,0.3)}
.btn-ghost{background:transparent;color:var(--fg);border:1px solid var(--border)}
.btn-ghost:hover{background:rgba(255,255,255,0.05)}
.btn-sm{padding:7px 14px;font-size:.82rem}
.btn-full{width:100%}

/* ── Page Layout ── */
.c-page{padding-top:calc(var(--nav-h) + 40px);padding-bottom:80px;position:relative;z-index:1}
.c-container{max-width:1600px;margin:0 auto;padding:0 40px}
.c-container-sm{max-width:900px;margin:0 auto;padding:0 40px}
.c-section-header{margin-bottom:28px}
.c-section-title{font-family:var(--font-d);font-size:2.5rem;font-weight:700;color:var(--fg);letter-spacing:-.5px}
.c-section-sub{font-size:1.15rem;color:var(--muted);margin-top:6px}

/* ── Filter Bar ── */
.c-filter-bar{background:var(--card);backdrop-filter:blur(8px);border-radius:var(--r-lg);padding:18px 22px;margin-bottom:28px;border:1px solid var(--border);display:flex;flex-wrap:wrap;gap:12px;align-items:center}
.c-search-wrap{position:relative;flex:1;min-width:200px}
.c-search-icon{position:absolute;left:14px;top:50%;transform:translateY(-50%);font-size:1.3rem;pointer-events:none}
.c-search-input{width:100%;padding:14px 18px 14px 44px;border:1px solid var(--border);border-radius:var(--r-md);font-size:.9rem;font-family:var(--font-b);outline:none;transition:all var(--tr-fast);background:rgba(255,255,255,0.03);color:var(--fg)}
.c-search-input:focus{border-color:rgba(245,158,11,0.5);background:rgba(255,255,255,0.05);box-shadow:0 0 0 3px rgba(245,158,11,0.1)}
.c-search-input::placeholder{color:var(--muted)}
.c-filter-input{padding:14px 18px;border:1px solid var(--border);border-radius:var(--r-md);font-size:.9rem;font-family:var(--font-b);outline:none;background:rgba(255,255,255,0.03);color:var(--fg);transition:all var(--tr-fast);min-width:160px}
.c-filter-input:focus{border-color:rgba(245,158,11,0.5);background:rgba(255,255,255,0.05);box-shadow:0 0 0 3px rgba(245,158,11,0.1)}
.c-clear-btn{padding:10px 16px;border-radius:var(--r-md);background:none;border:1px solid var(--border);color:var(--muted);font-size:.85rem;cursor:pointer;transition:all var(--tr-fast)}
.c-clear-btn:hover{border-color:rgba(239,68,68,0.4);color:var(--error)}

/* ── Event Cards Grid ── */
.c-events-grid{display:grid;grid-template-columns:repeat(auto-fill,minmax(340px,1fr));gap:28px}

.c-event-card{background:var(--card);backdrop-filter:blur(8px);border-radius:var(--r-lg);border:1px solid var(--border);overflow:hidden;transition:all var(--tr-norm);display:flex;flex-direction:column}
.c-event-card:hover{transform:translateY(-4px);border-color:var(--border-hover);background:rgba(26,26,36,0.8);box-shadow:0 10px 25px rgba(0,0,0,0.4)}
.c-event-card.cancelled{opacity:.55}

.c-card-banner{height:4px;background:linear-gradient(90deg,var(--accent),var(--accent-dark))}
.c-card-banner.cancelled{background:linear-gradient(90deg,#3f3f46,#52525b)}

.c-card-body{padding:24px;flex:1;display:flex;flex-direction:column;gap:14px}
.c-card-title{font-family:var(--font-d);font-size:1.25rem;font-weight:700;color:var(--fg);line-height:1.3}
.c-card-meta{display:flex;flex-direction:column;gap:6px}
.c-card-meta-row{display:flex;align-items:center;gap:10px;font-size:1rem;color:var(--muted)}
.c-card-meta-icon{font-size:1.4rem;flex-shrink:0}

.c-card-footer{padding:16px 24px;border-top:1px solid var(--border);display:flex;align-items:center;justify-content:space-between;gap:10px}
.c-seats-badge{display:inline-flex;align-items:center;gap:6px;padding:6px 14px;border-radius:var(--r-full);font-family:var(--font-m);font-size:.88rem;font-weight:600}
.c-seats-badge.available{background:var(--success-bg);color:var(--success)}
.c-seats-badge.low{background:rgba(245,158,11,0.15);color:var(--accent)}
.c-seats-badge.full{background:var(--error-bg);color:var(--error)}
.c-cancelled-badge{display:inline-flex;align-items:center;gap:5px;padding:5px 10px;border-radius:var(--r-full);font-size:.78rem;font-weight:600;background:rgba(255,255,255,0.05);color:var(--muted)}

.status-badge{display:inline-flex;align-items:center;gap:5px;padding:4px 10px;border-radius:var(--r-full);font-family:var(--font-m);font-size:.75rem;font-weight:600}
.status-badge.confirmed{background:var(--success-bg);color:var(--success)}
.status-badge.cancelled{background:var(--error-bg);color:var(--error)}

/* ── Skeleton ── */
.c-skeleton-card{background:var(--card-solid);border-radius:var(--r-lg);border:1px solid var(--border);height:220px;overflow:hidden;position:relative}
.c-skeleton-card::after{content:'';position:absolute;inset:0;background:linear-gradient(90deg,transparent 0%,rgba(255,255,255,0.03) 50%,transparent 100%);background-size:200% 100%;animation:shimmer 1.4s infinite}
@keyframes shimmer{0%{background-position:200% 0}100%{background-position:-200% 0}}
.c-skeleton-inner{padding:20px;display:flex;flex-direction:column;gap:12px}
.c-skel{background:rgba(255,255,255,0.06);border-radius:6px}
.c-skel-title{height:18px;width:70%}
.c-skel-line{height:13px}
.c-skel-line.w60{width:60%}
.c-skel-line.w40{width:40%}

/* ── Empty State ── */
.c-empty{grid-column:1/-1;text-align:center;padding:64px 24px}
.c-empty-icon{font-size:4.5rem;margin-bottom:20px}
.c-empty-title{font-family:var(--font-d);font-size:1.6rem;font-weight:700;color:var(--fg);margin-bottom:10px}
.c-empty-sub{font-size:1.1rem;color:var(--muted)}

/* ── Alert ── */
.c-alert{padding:12px 16px;border-radius:var(--r-md);font-size:.9rem;font-weight:500;display:none;margin-bottom:18px;align-items:center;gap:8px;border:1px solid transparent}
.c-alert.show{display:flex}
.c-alert.success{background:var(--success-bg);color:var(--success);border-color:rgba(34,197,94,0.3)}
.c-alert.error{background:var(--error-bg);color:var(--error);border-color:rgba(239,68,68,0.3)}
.c-alert.info{background:var(--info-bg);color:var(--info);border-color:rgba(99,102,241,0.3)}

/* ── Detail Card ── */
.c-detail-card{background:var(--card);backdrop-filter:blur(8px);border-radius:var(--r-xl);border:1px solid var(--border);overflow:hidden}
.c-detail-banner{height:4px;background:linear-gradient(90deg,var(--accent),var(--accent-dark))}
.c-detail-body{padding:36px}
.c-detail-title{font-family:var(--font-d);font-size:2.2rem;font-weight:700;color:var(--fg);margin-bottom:16px;letter-spacing:-.5px}
.c-detail-description{font-size:1.1rem;color:var(--muted);line-height:1.7;margin-bottom:32px}
.c-detail-grid{display:grid;grid-template-columns:1fr 1fr;gap:16px;margin-bottom:28px}
.c-detail-item{display:flex;flex-direction:column;gap:4px}
.c-detail-label{font-family:var(--font-m);font-size:.85rem;font-weight:500;color:var(--muted);text-transform:uppercase;letter-spacing:.05em}
.c-detail-value{font-size:1.15rem;font-weight:600;color:var(--fg)}
.c-detail-actions{display:flex;gap:12px;flex-wrap:wrap}

/* ── Seats Selector ── */
.c-seats-selector{background:rgba(255,255,255,0.03);border:1px solid var(--border);border-radius:var(--r-lg);padding:22px;margin-top:24px;display:none}
.c-seats-selector.show{display:block}
.c-seats-selector h3{font-family:var(--font-d);font-size:1rem;font-weight:700;color:var(--fg);margin-bottom:14px}
.c-seats-row{display:flex;align-items:center;gap:12px;flex-wrap:wrap}
.c-seats-input{width:100px;padding:10px 14px;border:1px solid var(--border);border-radius:var(--r-md);font-size:1rem;font-family:var(--font-b);outline:none;text-align:center;background:rgba(255,255,255,0.03);color:var(--fg);transition:all var(--tr-fast)}
.c-seats-input:focus{border-color:rgba(245,158,11,0.5);box-shadow:0 0 0 3px rgba(245,158,11,0.1)}
.c-seats-total{font-size:.95rem;color:var(--muted)}
.c-seats-total strong{color:var(--accent)}

/* ── Payment ── */
.c-payment-wrap{display:grid;grid-template-columns:1fr 1fr;gap:24px;align-items:start}
.c-order-card{background:var(--card);backdrop-filter:blur(8px);border-radius:var(--r-lg);border:1px solid var(--border);padding:28px}
.c-order-title{font-family:var(--font-d);font-size:1.1rem;font-weight:700;color:var(--fg);margin-bottom:20px}
.c-order-rows{display:flex;flex-direction:column;gap:12px;margin-bottom:20px}
.c-order-row{display:flex;justify-content:space-between;align-items:center;font-size:.95rem}
.c-order-row .label{color:var(--muted)}
.c-order-row .val{font-weight:600;color:var(--fg)}
.c-order-divider{border:none;border-top:1px solid var(--border);margin:16px 0}
.c-order-total-row{display:flex;justify-content:space-between;font-size:1.1rem;font-weight:800;color:var(--fg)}

.c-mock-payment-card{background:var(--card);backdrop-filter:blur(8px);border-radius:var(--r-lg);border:1px solid var(--border);padding:28px}
.c-mock-title{font-family:var(--font-d);font-size:1.1rem;font-weight:700;color:var(--fg);margin-bottom:6px}
.c-mock-sub{font-size:.85rem;color:var(--muted);margin-bottom:24px}
.c-mock-icons{display:flex;gap:10px;margin-bottom:24px;flex-wrap:wrap}
.c-mock-icon{padding:8px 14px;background:rgba(255,255,255,0.05);border-radius:var(--r-md);font-size:.85rem;font-weight:600;color:var(--muted);border:1px solid var(--border)}

/* ── Overlay (Loading/Success) ── */
.c-overlay{display:none;position:fixed;inset:0;z-index:200;background:rgba(0,0,0,0.7);backdrop-filter:blur(4px);align-items:center;justify-content:center}
.c-overlay.show{display:flex}
.c-overlay-box{background:var(--bg-alt);border:1px solid var(--border);border-radius:var(--r-xl);padding:48px;text-align:center;max-width:360px;width:90%;animation:popIn .35s cubic-bezier(.34,1.56,.64,1)}
@keyframes popIn{from{opacity:0;transform:scale(.8)}to{opacity:1;transform:scale(1)}}
.c-spinner{width:56px;height:56px;border:3px solid rgba(255,255,255,0.1);border-top-color:var(--accent);border-radius:50%;animation:spin .8s linear infinite;margin:0 auto 18px}
@keyframes spin{to{transform:rotate(360deg)}}
.c-success-icon{width:64px;height:64px;background:var(--success-bg);border-radius:50%;display:flex;align-items:center;justify-content:center;font-size:1.8rem;margin:0 auto 18px;animation:popIn .5s cubic-bezier(.34,1.56,.64,1)}
.c-overlay-title{font-family:var(--font-d);font-size:1.2rem;font-weight:700;color:var(--fg);margin-bottom:8px}
.c-overlay-sub{font-size:.9rem;color:var(--muted);margin-bottom:24px}

/* ── Tabs ── */
.c-tabs{display:flex;gap:6px;margin-bottom:24px}
.c-tab{padding:9px 20px;border-radius:var(--r-full);font-size:.88rem;font-weight:600;border:1px solid var(--border);background:transparent;color:var(--muted);cursor:pointer;transition:all var(--tr-fast)}
.c-tab:hover{border-color:rgba(245,158,11,0.3);color:var(--accent)}
.c-tab.active{background:var(--accent);color:var(--bg);border-color:var(--accent)}

/* ── Booking Cards ── */
.c-bookings-grid{display:grid;grid-template-columns:repeat(auto-fill,minmax(320px,1fr));gap:20px}
.c-booking-card{background:var(--card);backdrop-filter:blur(8px);border-radius:var(--r-lg);border:1px solid var(--border);overflow:hidden;transition:all var(--tr-norm)}
.c-booking-card:hover{transform:translateY(-3px);border-color:var(--border-hover);box-shadow:0 10px 25px rgba(0,0,0,0.4)}
.c-booking-banner{height:4px;background:linear-gradient(90deg,var(--accent),var(--accent-dark))}
.c-booking-banner.cancelled-banner{background:linear-gradient(90deg,#3f3f46,#52525b)}
.c-booking-body{padding:18px 20px;display:flex;flex-direction:column;gap:10px}
.c-booking-title{font-family:var(--font-d);font-size:1.2rem;font-weight:700;color:var(--fg)}
.c-booking-meta{display:flex;flex-direction:column;gap:5px}
.c-booking-meta-row{display:flex;align-items:center;gap:10px;font-size:1rem;color:var(--muted)}
.c-booking-footer{padding:12px 20px;border-top:1px solid var(--border);display:flex;align-items:center;justify-content:space-between}

/* ── Responsive ── */
@media(max-width:768px){
  .c-nav-links{display:none}
  .c-detail-grid{grid-template-columns:1fr}
  .c-payment-wrap{grid-template-columns:1fr}
  .c-detail-body{padding:22px}
  .c-section-title{font-size:1.4rem}
  .orb-1{width:400px;height:400px}
  .orb-2{width:250px;height:250px}
}
@media(max-width:480px){
  .c-filter-bar{flex-direction:column}
  .c-search-wrap{width:100%}
  .c-filter-input{width:100%}
  .c-detail-actions{flex-direction:column}
  .c-detail-actions .btn{width:100%;justify-content:center}
}

/* ── Profile Drawer ── */
.c-profile-overlay {
  position: fixed;
  inset: 0;
  z-index: 200; /* Above navbar */
  background: rgba(0, 0, 0, 0.7);
  backdrop-filter: blur(4px);
  opacity: 0;
  pointer-events: none;
  transition: opacity var(--tr-norm);
}
.c-profile-overlay.show {
  opacity: 1;
  pointer-events: auto;
}

.c-profile-drawer {
  position: fixed;
  top: 0;
  right: 0; /* Sliding from right */
  bottom: 0;
  width: 100%;
  max-width: 400px;
  background: var(--surface);
  border-left: 1px solid var(--border);
  box-shadow: -10px 0 30px rgba(0, 0, 0, 0.5);
  z-index: 201; /* Above overlay */
  transform: translateX(100%);
  transition: transform var(--tr-norm);
  display: flex;
  flex-direction: column;
}
.c-profile-drawer.show {
  transform: translateX(0);
}

.c-drawer-header {
  padding: 24px 28px;
  border-bottom: 1px solid var(--border);
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.c-drawer-title {
  font-family: var(--font-d);
  font-size: 1.4rem;
  font-weight: 700;
  color: var(--fg);
}
.c-close-btn {
  background: none;
  border: none;
  color: var(--muted);
  font-size: 1.2rem;
  cursor: pointer;
  padding: 8px;
  border-radius: var(--r-md);
  transition: all var(--tr-fast);
}
.c-close-btn:hover {
  background: rgba(255, 255, 255, 0.05);
  color: var(--fg);
}

.c-drawer-body {
  padding: 28px;
  flex: 1;
  overflow-y: auto;
}

.c-profile-avatar-large {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  background: var(--accent);
  color: var(--bg);
  font-size: 2.5rem;
  font-weight: 700;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 32px auto;
  box-shadow: 0 0 20px rgba(245, 158, 11, 0.2);
}

.c-profile-form {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.c-form-group {
  display: flex;
  flex-direction: column;
  gap: 8px;
}
.c-form-label {
  font-size: 0.9rem;
  font-weight: 600;
  color: var(--muted);
}
.w-full {
  width: 100%;
}

.c-drawer-actions {
  margin-top: 24px;
  display: flex;
  flex-direction: column;
  gap: 12px;
}
