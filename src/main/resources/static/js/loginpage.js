document.getElementById('loginform').addEventListener('submit', async function (event) {
  event.preventDefault();

  const user_id = document.getElementById("user_id").value;
  const userpassword = document.getElementById('userpassword').value;

  const data = {
    "id": user_id,
    "password": userpassword
  }

  const options = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
    },
    body: new URLSearchParams(data)
  };
  const response = await fetch('api/login', options);

  if (response.redirected) {
    window.location.href = response.url;
  } else if (!response.ok) {
    const error = await response.text()
    console.error('Error:', error);
  }
});
