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

function recibirMensajePrivado(message) {
const data = JSON.parse(message.body);
  let texto = `\nEstudiante: ${data.nombreEstudiante} (Código: ${data.codigoEstudiante})\nDeudas:\n`;
  data.deudas.forEach(d => texto += "- " + JSON.stringify(d) + "\n");
  const referenciaDiv = document.getElementById('mensajesPrivados');
  const nuevoParrafo = document.createElement('p');
  nuevoParrafo.textContent = texto;
  referenciaDiv.appendChild(nuevoParrafo);
}

function recibirNotificacion(message) {
  const data = message.body;
  const referenciaDiv = document.getElementById('usuariosConectados');
  const nuevoParrafo = document.createElement('p');
  nuevoParrafo.textContent = data;
  referenciaDiv.appendChild(nuevoParrafo);
}

function enviarMensajeGrupalServidor() {
  const input = document.getElementById('message');
  const nicknameInput = document.getElementById('nicknameOrigen');

  if (administrador && administrador.connected) {
    const mensaje = {
      area: areaSeleccionada,
      mensaje: input.value,
      nickname: nicknameInput.value,
      fechaGeneracion: new Date().toISOString().split("T")[0]
    };

    administrador.send("/apiChat/enviarMensajePublico", {}, JSON.stringify(mensaje));
    input.value = '';
  } else {
    alert("No estás conectado.");
  }
}

function enviarMensajePrivadoServidor() {
  const input = document.getElementById('messagePrivado');
  const nicknameOrigenInput = document.getElementById('nicknameOrigen');
  const nicknameDestinoInput = document.getElementById('nicknameDestino');

  if (administrador && administrador.connected) {
    const mensaje = {
      area: areaSeleccionada,
      mensaje: input.value,
      nicknameOrigen: nicknameOrigenInput.value,
      nicknameDestino: nicknameDestinoInput.value,
      fechaGeneracion: new Date().toISOString().split("T")[0]
    };

    administrador.send("/apiChat/enviarMensajePrivado", {}, JSON.stringify(mensaje));
    input.value = '';
  } else {
    alert("No estás conectado.");
  }
}
