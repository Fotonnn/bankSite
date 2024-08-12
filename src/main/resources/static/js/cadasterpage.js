document.getElementById('cadasterform').addEventListener('submit', async function(event) {
  event.preventDefault();

  const username = document.getElementById("username").value;
  const useremail = document.getElementById("useremail").value;
  const userage = document.getElementById("userage").value;
  const usercpf = document.getElementById("usercpf").value;
  const userpassword = document.getElementById('userpassword').value;

  const options = {
    method: 'POST',
    headers: {'Content-Type': 'application/json', 'User-Agent': 'insomnia/8.6.1'},
    body: JSON.stringify({"username":username,"userage":userage,"userbalance":1000.5,"userpassword":userpassword,"usercpf":usercpf,"useremail":useremail})
  };
  try {
    const response = await fetch('http://localhost:8080/api/cadastro', options);

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
