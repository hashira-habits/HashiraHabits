function showToast(message) {
  let t = document.querySelector('.toast');
  if (!t) {
    t = document.createElement('div');
    t.className = 'toast';
    document.body.appendChild(t);
  }
  t.textContent = message;
  t.style.display = 'block';
  setTimeout(() => t.style.display = 'none', 2500);
}

function requireAuth() {
  const userId = localStorage.getItem('userId');
  if (!userId) window.location.href = '/login.html';
  return userId;
}

function setThemeFromSettings() {
  const theme = localStorage.getItem('theme') || 'light';
  document.body.classList.toggle('dark', theme === 'dark');
}

document.addEventListener('DOMContentLoaded', setThemeFromSettings);


