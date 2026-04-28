async function copiarEndpoint(texto, botao) {
  try {
    await navigator.clipboard.writeText(texto);
    const antigo = botao.querySelector(".copy-label").textContent;
    botao.querySelector(".copy-label").textContent = "Copiado";
    setTimeout(() => {
      botao.querySelector(".copy-label").textContent = antigo;
    }, 1200);
  } catch (err) {
    window.alert("Não foi possível copiar automaticamente. Endpoint: " + texto);
  }
}

document.querySelectorAll(".api-endpoint").forEach((btn) => {
  btn.addEventListener("click", () => copiarEndpoint(btn.dataset.endpoint, btn));
});
