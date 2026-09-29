const API_BASE_URL = '';

async function apiRequest(url, options = {}) {
  const response = await fetch(`${API_BASE_URL}${url}`, options);

  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(errorText || 'Request failed');
  }

  const contentType = response.headers.get('content-type') || '';
  if (contentType.includes('application/json')) {
    return await response.json();
  }

  return await response.text();
}

function getCurrentStudent() {
  const studentId = localStorage.getItem('studentId');
  const studentName = localStorage.getItem('studentName');
  const studentEmail = localStorage.getItem('studentEmail');
  const studentPhone = localStorage.getItem('studentPhone');

  return {
    studentId,
    name: studentName || '',
    email: studentEmail || '',
    phone: studentPhone || ''
  };
}

function getCurrentAdmin() {
  const adminId = localStorage.getItem('adminId');
  const adminName = localStorage.getItem('adminName');
  const adminEmail = localStorage.getItem('adminEmail');

  return {
    adminId,
    name: adminName || '',
    email: adminEmail || ''
  };
}

function requireAuth() {
  const studentId = localStorage.getItem('studentId');
  if (!studentId) {
    window.location.href = 'student-login.html';
  }
}

function requireAdminAuth() {
  const adminId = localStorage.getItem('adminId');
  if (!adminId) {
    window.location.href = 'admin-login.html';
  }
}

function showMessage(element, text, type) {
  if (!element) return;
  element.className = 'status-box';
  if (type === 'success') {
    element.classList.add('success');
  } else if (type === 'error') {
    element.classList.add('error');
  } else if (type === 'loading') {
    element.classList.add('info');
  }
  element.innerHTML = text;
  element.classList.remove('hidden');
}

function escapeHtml(value) {
  if (value === null || value === undefined) return '';
  return String(value)
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;')
    .replace(/'/g, '&#039;');
}

function formatNumber(value) {
  if (value === null || value === undefined || value === '') return '0';
  const num = Number(value);
  if (Number.isNaN(num)) return value;
  return new Intl.NumberFormat('en-IN').format(num);
}

async function fetchStudentById(studentId) {
  if (!studentId) {
    return {};
  }

  const students = await apiRequest('/students');
  const student = (students || []).find((item) => String(item.id) === String(studentId));
  return student || {};
}

async function fetchVerificationForApplication(applicationId) {
  try {
    const list = await apiRequest('/verifications');
    if (!Array.isArray(list)) return null;
    return list.find((item) => String(item.application?.id) === String(applicationId)) || null;
  } catch (error) {
    return null;
  }
}

function attachLogout() {
  const button = document.getElementById('logoutButton');
  if (!button) return;

  button.addEventListener('click', () => {
    const verificationCard = document.getElementById('verificationResult');
    const hasActiveVerification = verificationCard && verificationCard.style.display !== 'none';

    if (hasActiveVerification) {
      const shouldContinue = window.confirm('A verification or disbursement action is in progress. Logging out now may interrupt the process. Continue?');
      if (!shouldContinue) {
        return;
      }
    }

    localStorage.removeItem('studentId');
    localStorage.removeItem('studentName');
    localStorage.removeItem('studentEmail');
    localStorage.removeItem('studentPhone');
    localStorage.removeItem('adminId');
    localStorage.removeItem('adminName');
    localStorage.removeItem('adminEmail');
    window.location.href = 'index.html';
  });
}

document.addEventListener('DOMContentLoaded', () => {
  attachLogout();
});



