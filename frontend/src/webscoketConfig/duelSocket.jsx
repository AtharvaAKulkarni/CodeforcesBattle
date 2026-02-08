import SockJS from "sockjs-client";
import Stomp from "stompjs";

let stompClient = null;
let currentUser = null;
let challengeHandler = null;
let connected = false;
export function setOnChallenge(handler) {
  challengeHandler = handler;
}
export function connectWS(username) {
  if (stompClient && connected) return;

  const socket = new SockJS("http://localhost:8080/ws");
  stompClient = Stomp.over(socket);
  stompClient.debug = () => {};

  stompClient.connect({}, () => {
    connected = true;
    console.log("✅ WS connected as", username);

    stompClient.subscribe("/queue/duel-room/" + username, (msg) => {
      const data = JSON.parse(msg.body);

      if (challengeHandler) {
        challengeHandler(data); // ✅ safe
      }
    });
  });
}

export function createRoom(user1, user2) {
  console.log("createRoom called", { connected, user1, user2 });
  stompClient.send(
    "/app/duel/create-room",
    {},
    JSON.stringify({ user1, user2 })
  );
}

// 🔥 THIS is the important change
export function joinRoom(
  roomId,
  joiningUsername,
  opponent1,
  opponent2,
  onDuelStart
) {
  // 1️⃣ Subscribe FIRST (backend response)
  stompClient.subscribe("/topic/duel/" + roomId, (msg) => {
    const duel = JSON.parse(msg.body);

    console.log("🔥 BACKEND DUEL RESPONSE:", duel);
    console.log("🔥 QUESTION:", duel.question);

    onDuelStart(duel); // exact backend object
  });

  // 2️⃣ THEN send join
  stompClient.send(
    "/app/duel/join",
    {},
    JSON.stringify({
      roomId,
      joiningUsername,
      opponent1,
      opponent2,
      duelDurationSeconds: 120,
      rating: 900,
      result: null,
      question: null,
      id: null
    })
  );
}
