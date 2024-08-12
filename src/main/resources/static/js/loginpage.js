document.getElementById('loginform').addEventListener('submit', async function(event) {
  event.preventDefault();

  const user_id = document.getElementById("user_id").value;
  const userpassword = document.getElementById('userpassword').value;

  const options = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'User-Agent': 'insomnia/8.6.1'
    },
    body: JSON.stringify({ user_id: user_id, userpassword: userpassword })
  };

  try {
    const response = await fetch('http://localhost:8080/api/login', options);

    if (response.ok) {
      console.log('Login successful');
      window.location.href = 'http://localhost:8080/api/balance'; // URL de redirecionamento ajustada
    } else {
      const errorText = await response.text(); // Pegando o texto de erro do servidor
      console.error('Login failed:', errorText);
    }
  } catch (err) {
    console.error('Network error:', err);
  }
});
