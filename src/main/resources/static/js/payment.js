document.getElementById('paymentform').addEventListener('submit', async function (event) {
  event.preventDefault();

  const receiver_id = document.getElementById("user_id").value;
  const amount = document.getElementById('amount').value;

  const data = {
    "receiver_id": receiver_id,
    "amount": amount
  }

  const options = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
    },
    body: new URLSearchParams(data)
  };
  const response = await fetch('api/payment', options);

  if (response.ok) {
    window.location.href = '/user';
  } else if (!response.ok) {
    const error = await response.text()
    console.error('Error:', error);
  }
});
