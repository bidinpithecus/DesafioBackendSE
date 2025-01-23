document.addEventListener("DOMContentLoaded", function () {
  let participantCount = 1;

  document
    .getElementById("add-participant")
    .addEventListener("click", function () {
      participantCount++;

      const participantsDiv = document.getElementById("participants");

      const newParticipantDiv = document.createElement("div");
      newParticipantDiv.classList.add("participant");
      newParticipantDiv.innerHTML = `
      <label for="participant-${participantCount}">Nome do Participante:</label>
      <input
        type="text"
        class="participant-name"
        name="participant-${participantCount}-name"
        placeholder="Nome do Participante"
      />
      <label for="items-${participantCount}">Itens:</label>
      <input
        type="text"
        class="items-input"
        name="participant-${participantCount}-items"
        placeholder="Exemplo: Hamburguer-40.00,Sobremesa-2.00"
      />
      <button type="button" class="remove-participant">Remover Participante</button>
      <div class="error-messages"></div>
    `;

      participantsDiv.appendChild(newParticipantDiv);

      newParticipantDiv
        .querySelector(".remove-participant")
        .addEventListener("click", function () {
          participantsDiv.removeChild(newParticipantDiv);
        });
    });

  document
    .getElementById("payment-form")
    .addEventListener("submit", async function (event) {
      event.preventDefault();

      document.querySelectorAll(".error-messages").forEach((div) => {
        div.innerHTML = "";
      });
      document.getElementById("response-output").textContent = "";

      const items = {};
      const participants = document.querySelectorAll(".participant");

      participants.forEach((participantDiv) => {
        const participantName =
          participantDiv.querySelector(".participant-name").value;
        const itemsInput = participantDiv.querySelector(".items-input").value;

        const itemsList = itemsInput.split(",").map((item) => {
          const [name, price] = item.split("-");
          return { name, price: parseFloat(price) };
        });

        items[participantName] = itemsList;
      });

      const discountsInput = document.getElementById("discounts").value;
      const additionsInput = document.getElementById("additions").value;
      const ownerName = document.getElementById("ownerName").value;
      const ownerPixKey = document.getElementById("ownerPixKey").value;

      const discounts = discountsInput.split(",").map((discount) => {
        const [amount, type] = discount.split("-");
        return { amount: parseFloat(amount), type };
      });

      const additions = additionsInput.split(",").map((addition) => {
        const [amount, type] = addition.split("-");
        return { amount: parseFloat(amount), type };
      });

      const requestData = {
        items: items,
        discounts: discounts,
        additions: additions,
        ownerName: ownerName,
        ownerPixKey: ownerPixKey,
      };

      try {
        const response = await fetch(
          "http://localhost:8080/api/split-and-generate-links",
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(requestData),
          },
        );

        if (response.ok) {
          const result = await response.json();
          renderParticipantDetails(result);
        } else if (response.status === 400) {
          const errors = await response.json();
          displayValidationErrors(errors);
        } else {
          document.getElementById("response-output").textContent =
            `Erro: ${response.status}`;
        }
      } catch (error) {
        document.getElementById("response-output").textContent =
          `Erro: ${error.message}`;
      }
    });

  function displayValidationErrors(errors) {
    for (const [field, message] of Object.entries(errors)) {
      const match = field.match(/items\[(.*?)\]\[(\d+)\]\.(.+)/);
      if (match) {
        const participantName = match[1];
        const itemIndex = parseInt(match[2], 10);
        const fieldName = match[3];

        document.querySelectorAll(".participant").forEach((div) => {
          const nameInput = div.querySelector(".participant-name");
          if (nameInput && nameInput.value === participantName) {
            const errorMessagesDiv = div.querySelector(".error-messages");
            errorMessagesDiv.innerHTML += `<p>${fieldName}: ${message}</p>`;
          }
        });
      } else {
        document.getElementById("response-output").textContent +=
          `${field}: ${message}\n`;
      }
    }
  }

  function renderParticipantDetails(participants) {
    const responseOutput = document.getElementById("response-output");
    responseOutput.innerHTML = "";

    participants.forEach((participant) => {
      const participantDiv = document.createElement("div");
      participantDiv.classList.add("participant-response");

      const participantDetails = `
        <h3>${participant.name}</h3>
        <p>Valor: ${participant.amount.toFixed(2)}</p>
        ${
          participant.pixLink
            ? `<p>Pix Link: <a href="${participant.pixLink}" target="_blank">${participant.pixLink}</a></p>`
            : ""
        }
        ${
          participant.qrCodeBase64
            ? `<p>QR Code:</p><img src="data:image/png;base64,${participant.qrCodeBase64}" alt="QR Code" />`
            : ""
        }
      `;

      participantDiv.innerHTML = participantDetails;
      responseOutput.appendChild(participantDiv);
    });
  }
});
