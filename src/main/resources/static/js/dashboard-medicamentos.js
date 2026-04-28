const state = { itens: [] };

function sanitizeDecimalInput(raw, maxFrac) {
  const s = String(raw).replace(/[^\d.,]/g, "");
  if (!s) return "";
  const idxComma = s.indexOf(",");
  const idxDot = s.indexOf(".");
  if (idxComma < 0 && idxDot < 0) return s;
  const sepPos = idxComma >= 0 && idxDot >= 0 ? Math.min(idxComma, idxDot) : idxComma >= 0 ? idxComma : idxDot;
  const sep = s[sepPos];
  let intPart = s.slice(0, sepPos).replace(/\D/g, "");
  const fracDigits = s.slice(sepPos + 1).replace(/\D/g, "").slice(0, maxFrac);
  if (intPart === "" && sepPos === 0) intPart = "0";
  const endsWithSepOnly = /[.,]$/.test(s) && fracDigits.length === 0;
  if (endsWithSepOnly) return (intPart || "0") + sep;
  return intPart + sep + fracDigits;
}

function sanitizeLabIdInput(raw) {
  return String(raw).replace(/\D/g, "");
}

function sanitizeEanInput(raw) {
  return String(raw).replace(/\D/g, "").slice(0, 14);
}

function parseMoneyToNumber(str) {
  const t = String(str).trim().replace(/\s/g, "").replace(",", ".");
  if (t === "") return NaN;
  return Number(t);
}

function formatDecimalForForm(v) {
  if (v == null || v === "") return "";
  const s0 = String(v).trim();
  if (s0 === "") return "";
  const n = Number(s0.replace(",", "."));
  if (!Number.isFinite(n)) return s0;
  if (Number.isInteger(n) && !/[.,]/.test(s0)) return String(Math.trunc(n));
  const t = s0.replace(",", ".");
  const parts = t.split(".");
  const a = parts[0] || "0";
  const b = (parts[1] || "").replace(/\D/g, "").slice(0, 2);
  if (parts.length === 1) return a;
  return a + "," + b;
}

function isDecimalInputComplete(s) {
  const t = String(s).trim();
  if (!t) return false;
  if (/[.,]$/.test(t)) return false;
  return /^\d+(?:[.,]\d{1,2})?$/.test(t);
}

function showMsg(text, ok) {
  const el = document.getElementById("msg");
  el.textContent = text;
  el.className = ok ? "ok" : "err";
}
function clearMsg() {
  const el = document.getElementById("msg");
  el.textContent = "";
  el.className = "";
}
function toStatusText(value) {
  if (value === true) return "Ativo";
  if (value === false) return "Inativo";
  return "-";
}
function toBoolText(value) {
  if (value === true) return "Sim";
  if (value === false) return "Não";
  return "-";
}

function montarPayloadCadastro() {
  const dVal = document.getElementById("dosagemValor").value.trim();
  const precoN = parseMoneyToNumber(document.getElementById("precoVenda").value);
  const body = {
    nome: document.getElementById("nome").value.trim(),
    ean: document.getElementById("ean").value.trim(),
    dosagemValor: dVal,
    dosagemUM: document.getElementById("dosagemUM").value.trim(),
    categoria: document.getElementById("categoria").value,
    classeTerapeutica: document.getElementById("classeTerapeutica").value.trim(),
    formaFarmaceutica: document.getElementById("formaFarmaceutica").value,
    prescricao: document.getElementById("prescricao").checked,
    tarja: document.getElementById("tarja").value,
    anvisaRegular: document.getElementById("anvisaRegular").checked,
    pfp: document.getElementById("pfp").checked,
    precoVenda: precoN,
    status: document.getElementById("status").value === "true",
  };
  const lab = document.getElementById("laboratorioId").value.trim();
  const obs = document.getElementById("observacoes").value.trim();
  if (lab !== "") body.laboratorioId = Number(lab);
  if (obs) body.observacoes = obs;
  return body;
}
function extrairErroApi(texto) {
  if (!texto) return "";
  try {
    const data = JSON.parse(texto);
    if (data && typeof data === "object") {
      return data.mensagem || data.message || data.erro || texto;
    }
  } catch (_) {}
  return texto;
}
function montarPayloadEdicao() {
  const dVal = document.getElementById("editDosagemValor").value.trim();
  const precoN = parseMoneyToNumber(document.getElementById("editPrecoVenda").value);
  const body = {
    nome: document.getElementById("editNome").value.trim(),
    ean: document.getElementById("editEan").value.trim(),
    dosagemValor: dVal,
    dosagemUM: document.getElementById("editDosagemUM").value.trim(),
    categoria: document.getElementById("editCategoria").value,
    classeTerapeutica: document.getElementById("editClasseTerapeutica").value.trim(),
    formaFarmaceutica: document.getElementById("editFormaFarmaceutica").value,
    prescricao: document.getElementById("editPrescricao").checked,
    tarja: document.getElementById("editTarja").value,
    anvisaRegular: document.getElementById("editAnvisaRegular").checked,
    pfp: document.getElementById("editPfp").checked,
    precoVenda: precoN,
    status: document.getElementById("editStatus").value === "true",
  };
  const lab = document.getElementById("editLaboratorioId").value.trim();
  const obs = document.getElementById("editObservacoes").value.trim();
  if (lab !== "") body.laboratorioId = Number(lab);
  if (obs) body.observacoes = obs;
  return body;
}

function abrirEdicao(m) {
  document.getElementById("editId").value = m.id ?? "";
  document.getElementById("editNome").value = m.nome ?? "";
  document.getElementById("editEan").value = sanitizeEanInput(m.ean ?? "");
  document.getElementById("editDosagemValor").value = formatDecimalForForm(m.dosagemValor);
  document.getElementById("editDosagemUM").value = m.dosagemUM ?? "";
  document.getElementById("editCategoria").value = m.categoria ?? "GENERICO";
  document.getElementById("editClasseTerapeutica").value = m.classeTerapeutica ?? "";
  document.getElementById("editFormaFarmaceutica").value = m.formaFarmaceutica ?? "COMPRIMIDO";
  document.getElementById("editTarja").value = m.tarja ?? "SEM_TARJA";
  document.getElementById("editPrescricao").checked = m.prescricao === true;
  document.getElementById("editStatus").value = String(m.status === true);
  document.getElementById("editPrecoVenda").value = formatDecimalForForm(m.precoVenda);
  document.getElementById("editLaboratorioId").value = m.laboratorioId != null ? String(m.laboratorioId) : "";
  document.getElementById("editObservacoes").value = m.observacoes ?? "";
  document.getElementById("editAnvisaRegular").checked = m.anvisaRegular === true;
  document.getElementById("editPfp").checked = m.pfp === true;
  document.getElementById("editDialog").showModal();
}

function validarAntesDeMed(op) {
  const precoEl = op === "cad" ? "precoVenda" : "editPrecoVenda";
  const dosEl = op === "cad" ? "dosagemValor" : "editDosagemValor";
  const labEl = op === "cad" ? "laboratorioId" : "editLaboratorioId";
  const preco = parseMoneyToNumber(document.getElementById(precoEl).value);
  if (!Number.isFinite(preco) || preco < 0) {
    showMsg("Preço de venda inválido. Use apenas números, com vírgula ou ponto e até 2 casas decimais.", false);
    return false;
  }
  const dos = document.getElementById(dosEl).value.trim();
  if (!isDecimalInputComplete(dos)) {
    showMsg("Dosagem (valor) inválida. Use apenas números, com vírgula ou ponto e até 2 casas decimais. Evite deixar só a vírgula/ponto no final.", false);
    return false;
  }
  const lab = document.getElementById(labEl).value.trim();
  if (lab !== "" && (lab === "0" || !/^\d+$/.test(lab))) {
    showMsg("ID do laboratório deve ser um número inteiro (apenas dígitos) ou vazio.", false);
    return false;
  }
  return true;
}

function renderRows() {
  const filtro = document.getElementById("filtro").value.trim().toLowerCase();
  const tbody = document.getElementById("tbody");
  const empty = document.getElementById("empty");
  tbody.innerHTML = "";
  const visiveis = state.itens.filter((m) => {
    if (!filtro) return true;
    const lab = m.laboratorioNomeFantasia || m.laboratorioRazaoSocial || "";
    const texto = [m.id, m.nome, m.ean, m.categoria, m.tarja, m.formaFarmaceutica, lab].join(" ").toLowerCase();
    return texto.includes(filtro);
  });
  if (!visiveis.length) {
    empty.style.display = "block";
    return;
  }
  empty.style.display = "none";
  for (const m of visiveis) {
    const tr = document.createElement("tr");
    const lab = m.laboratorioNomeFantasia || m.laboratorioRazaoSocial || "-";
    tr.innerHTML = `<td>${m.id ?? "-"}</td><td>${m.nome ?? "-"}</td><td>${m.ean ?? "-"}</td><td>${(m.dosagemValor || m.dosagemUM) ? `${m.dosagemValor ?? ""} ${m.dosagemUM ?? ""}`.trim() : "-"}</td><td>${m.categoria ?? "-"}</td><td>${m.classeTerapeutica ?? "-"}</td><td>${m.formaFarmaceutica ?? "-"}</td><td>${m.tarja ?? "-"}</td><td>${toBoolText(m.prescricao)}</td><td>${toBoolText(m.anvisaRegular)}</td><td>${m.precoVenda != null ? Number(m.precoVenda).toFixed(2) : "-"}</td><td>${toStatusText(m.status)}</td><td>${toBoolText(m.pfp)}</td><td>${lab}</td><td class="actions-col"><button type="button" class="btn-small btn-edit" data-action="editar" data-id="${m.id}">Editar</button><button type="button" class="btn-small btn-delete" data-action="deletar" data-id="${m.id}">Excluir</button></td>`;
    tbody.appendChild(tr);
  }
}
async function carregar() {
  clearMsg();
  try {
    const res = await fetch("/api/medicamentos", { headers: { Accept: "application/json" } });
    const text = await res.text();
    const data = text ? JSON.parse(text) : [];
    if (!res.ok) {
      showMsg("Erro ao carregar medicamentos (HTTP " + res.status + ").", false);
      return;
    }
    state.itens = Array.isArray(data) ? data : [];
    renderRows();
    showMsg("Lista atualizada com " + state.itens.length + " registro(s).", true);
  } catch (err) {
    showMsg("Falha ao carregar listagem: " + err.message, false);
  }
}
async function deletarMedicamento(id) {
  if (!window.confirm("Deseja excluir o medicamento de ID " + id + "?")) return;
  clearMsg();
  try {
    const res = await fetch("/api/medicamentos/" + id, { method: "DELETE" });
    if (!res.ok) {
      showMsg("Erro ao excluir medicamento (HTTP " + res.status + ").", false);
      return;
    }
    state.itens = state.itens.filter((m) => m.id !== id);
    renderRows();
    showMsg("Medicamento removido com sucesso.", true);
  } catch (err) {
    showMsg("Falha na exclusão: " + err.message, false);
  }
}
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
document.getElementById("formMed").addEventListener("submit", async (e) => {
  e.preventDefault();
  clearMsg();
  if (!validarAntesDeMed("cad")) return;
  try {
    const res = await fetch("/api/medicamentos", {
      method: "POST",
      headers: { "Content-Type": "application/json", Accept: "application/json" },
      body: JSON.stringify(montarPayloadCadastro()),
    });
    const text = await res.text();
    const erroApi = extrairErroApi(text);
    let data = null;
    try {
      data = text ? JSON.parse(text) : null;
    } catch (_) {
      data = null;
    }
    if (!res.ok || !data) {
      const msgErro = erroApi.toLowerCase();
      if (res.status === 404 && msgErro.includes("laborat")) {
        showMsg("Não há laboratório cadastrado com esse ID. Cadastre um laboratório primeiro e tente novamente.", false);
        return;
      }
      showMsg("Erro ao cadastrar medicamento (HTTP " + res.status + ")." + (erroApi ? "\n" + erroApi : ""), false);
      return;
    }
    state.itens.unshift(data);
    renderRows();
    document.getElementById("formMed").reset();
    showMsg("Medicamento cadastrado com sucesso.", true);
  } catch (err) {
    showMsg("Falha no cadastro: " + err.message, false);
  }
});
document.getElementById("btnLimpar").addEventListener("click", () => {
  document.getElementById("formMed").reset();
  clearMsg();
});
document.getElementById("btnAtualizar").addEventListener("click", carregar);
document.getElementById("filtro").addEventListener("input", renderRows);
document.getElementById("tbody").addEventListener("click", (e) => {
  const btn = e.target.closest("button[data-action]");
  if (!btn) return;
  const id = Number(btn.dataset.id);
  if (!Number.isFinite(id)) return;
  const med = state.itens.find((m) => m.id === id);
  if (!med) return;
  if (btn.dataset.action === "editar") abrirEdicao(med);
  if (btn.dataset.action === "deletar") deletarMedicamento(id);
});
document.getElementById("btnCancelarEdicao").addEventListener("click", () => {
  document.getElementById("editDialog").close();
});
document.getElementById("editForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  clearMsg();
  if (!validarAntesDeMed("edit")) return;
  const id = Number(document.getElementById("editId").value);
  if (!Number.isFinite(id)) return;
  try {
    const res = await fetch("/api/medicamentos/" + id, {
      method: "PUT",
      headers: { "Content-Type": "application/json", Accept: "application/json" },
      body: JSON.stringify(montarPayloadEdicao()),
    });
    const text = await res.text();
    const erroApi = extrairErroApi(text);
    let data = null;
    try {
      data = text ? JSON.parse(text) : null;
    } catch (_) {
      data = null;
    }
    if (!res.ok || !data) {
      const msgErro = erroApi.toLowerCase();
      if (res.status === 404 && msgErro.includes("laborat")) {
        showMsg("Não há laboratório cadastrado com esse ID. Cadastre um laboratório primeiro e tente novamente.", false);
        return;
      }
      showMsg("Erro ao atualizar medicamento (HTTP " + res.status + ")." + (erroApi ? "\n" + erroApi : ""), false);
      return;
    }
    const idx = state.itens.findIndex((m) => m.id === id);
    if (idx >= 0) state.itens[idx] = data;
    renderRows();
    document.getElementById("editDialog").close();
    showMsg("Medicamento atualizado com sucesso.", true);
  } catch (err) {
    showMsg("Falha na atualização: " + err.message, false);
  }
});

["precoVenda", "dosagemValor", "editPrecoVenda", "editDosagemValor"].forEach((id) => {
  const el = document.getElementById(id);
  if (el) el.addEventListener("input", () => (el.value = sanitizeDecimalInput(el.value, 2)));
});
["laboratorioId", "editLaboratorioId"].forEach((id) => {
  const el = document.getElementById(id);
  if (el) el.addEventListener("input", () => (el.value = sanitizeLabIdInput(el.value)));
});
["ean", "editEan"].forEach((id) => {
  const el = document.getElementById(id);
  if (el) el.addEventListener("input", () => (el.value = sanitizeEanInput(el.value)));
});

carregar();
