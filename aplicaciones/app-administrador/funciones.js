let administrador = null;
let areaSeleccionada = null;

function setConectado(conectado) {
  document.getElementById('btnConectar').disabled = conectado;
  document.getElementById('areaSeleccionada').disabled = conectado;
  document.getElementById('btnDesconectar').disabled = !conectado;
}

function conectar() {
  areaSeleccionada = document.getElementById('areaSeleccionada').value;

  if (!areaSeleccionada) {
    alert("Debes seleccionar un área.");
    return;
  }

  const socket = new SockJS('http://localhost:8083/ws');
  administrador = Stomp.over(socket);

  administrador.connect(
    { area: areaSeleccionada },
    suscripcionAChat
  );
}

function desconectar() {
  if (administrador !== null) {
    administrador.disconnect(() => {
      setConectado(false);
    });
    administrador = null;
  }
}

function suscripcionAChat(frame) {
  setConectado(true);

  // Suscripción a mensajes grupales
  administrador.subscribe('/Administrador/salaChatPublica', recibirMensajeGrupal);

  // Suscripción a canal privado del área seleccionada
  if (areaSeleccionada) {
    administrador.subscribe(`/Administrador/${areaSeleccionada}`, recibirMensajePrivado);
  }

  // Suscripción a notificaciones opcionales (usuarios conectados, etc.)
  administrador.subscribe('/Administrador/notificaciones', recibirNotificacion);
}

function recibirMensajeGrupal(message) {
  const data = JSON.parse(message.body);
  const referenciaDiv = document.getElementById('mensajesGrupales');
  const nuevoParrafo = document.createElement('p');
  nuevoParrafo.textContent = `[${data.fechaGeneracion}] ${data.area}: ${data.mensaje}`;
  referenciaDiv.appendChild(nuevoParrafo);
}

function recibirMensajePrivadoV1(message) {
const data = JSON.parse(message.body);
  let texto = `\nEstudiante: ${data.nombreEstudiante} (Código: ${data.codigoEstudiante})\nDeudas:\n`;
  data.deudas.forEach(d => texto += "- " + JSON.stringify(d) + "\n");
  const referenciaDiv = document.getElementById('notificacionesPrivadas');
  const nuevoParrafo = document.createElement('p');
  nuevoParrafo.textContent = texto;
  referenciaDiv.appendChild(nuevoParrafo);
}

function recibirNotificacion(message) {
  const data = message.body;
  const referenciaDiv = document.getElementById('notificaciones');
  const nuevoParrafo = document.createElement('p');
  nuevoParrafo.textContent = data;
  referenciaDiv.appendChild(nuevoParrafo);
}
function recibirMensajePrivado(message) {
  const data = JSON.parse(message.body);

  const referenciaDiv = document.getElementById('notificacionesPrivadas');
  const nuevoParrafo = document.createElement('p');

  if (data.deudas && data.deudas.length > 0) {
    // Si hay deudas, mostrar mensaje de NO paz y salvo
    let texto = `El estudiante ${data.nombreEstudiante} (Código: ${data.codigoEstudiante}) NO está a paz y salvo en el área de ${areaSeleccionada}.\nDeudas:\n`;
    data.deudas.forEach(d => texto += "- " + JSON.stringify(d) + "\n");
    nuevoParrafo.style.color = "red"; // Para resaltar el mensaje
    nuevoParrafo.textContent = texto;
  } else {
    // Si no hay deudas, indicar que está a paz y salvo
    nuevoParrafo.textContent = `El estudiante ${data.nombreEstudiante} (Código: ${data.codigoEstudiante}) SÍ está a paz y salvo en el área de ${areaSeleccionada}.`;
  }
  referenciaDiv.appendChild(nuevoParrafo);
}
