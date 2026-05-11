// THEME TOGGLE 
document.addEventListener('DOMContentLoaded', function () {

    const themeToggle = document.getElementById('theme-toggle');
    const body = document.body;

    // Apply saved or system preference on load
    const saved = localStorage.getItem('theme');
    const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;

    if (saved === 'dark' || (!saved && prefersDark)) {
        body.classList.add('dark-mode');
        setToggleLabel(true);
    } else {
        setToggleLabel(false);
    }

    if (themeToggle) {
        themeToggle.addEventListener('click', () => {
            body.classList.toggle('dark-mode');
            const isDark = body.classList.contains('dark-mode');
            localStorage.setItem('theme', isDark ? 'dark' : 'light');
            setToggleLabel(isDark);
            showToast(isDark ? 'Dark mode enabled' : 'Light mode enabled');
        });
    }

    // Sync across tabs
    window.addEventListener('storage', (e) => {
        if (e.key === 'theme') {
            const isDark = e.newValue === 'dark';
            body.classList.toggle('dark-mode', isDark);
            setToggleLabel(isDark);
        }
    });

    // System preference change (only if no manual override saved)
    window.matchMedia('(prefers-color-scheme: dark)').addEventListener('change', (e) => {
        if (!localStorage.getItem('theme')) {
            body.classList.toggle('dark-mode', e.matches);
            setToggleLabel(e.matches);
        }
    });
});

function setToggleLabel(isDark) {
    const btn = document.getElementById('theme-toggle');
    if (!btn) return;
    btn.innerHTML = isDark
        ? '<i class="fas fa-sun"></i>Light Mode'
        : '<i class="fas fa-moon"></i> Dark Mode';
}

function showToast(message) {
    // Remove existing toast
    const existing = document.querySelector('.toast');
    if (existing) existing.remove();

    const toast = document.createElement('div');
    toast.className = 'toast';
    toast.textContent = message;
    document.body.appendChild(toast);

    setTimeout(() => {
        toast.style.opacity = '0';
        toast.style.transform = 'translateX(110%)';
        setTimeout(() => toast.remove(), 300);
    }, 2200);
}