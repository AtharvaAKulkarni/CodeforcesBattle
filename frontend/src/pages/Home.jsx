import { useAuth } from "@/context/authContext";
import { setOnChallenge, createRoom } from "@/webscoketConfig/duelSocket";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";

export default function Home() {
  const { user, loading } = useAuth();
  const navigate = useNavigate();
  const [challenge, setChallenge] = useState(null);
  useEffect(() => {
    setOnChallenge(({ roomId, challenger }) => {
      setChallenge({ roomId, challenger });
    });
  }, []);

  const handleChallenge = (friendName) => {
    createRoom(user.username, friendName);
    setChallenge({
      challenger: user.username,
      opponent: friendName,
      roomId: null // will be filled when WS responds
    });
    navigate("/duel"); // 🔥 move to duel page
  };
  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center text-white">
        Loading session...
      </div>
    );
  }

  if (!user) {
    return (
      <div className="min-h-screen flex items-center justify-center text-white">
        Not logged in
      </div>
    );
  }
  return (
    <div className="min-h-screen bg-zinc-950 text-white p-6">
      <h1 className="mb-4 text-xl font-semibold">
        Welcome {user.username}
      </h1>

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-5">
        {user.friends.map((name) => (
          <div
            key={name}
            className="bg-zinc-900 border border-zinc-800 rounded-xl p-4 flex justify-between"
          >
            <span className="font-semibold">{name}</span>

            <button
              onClick={() => handleChallenge(name)}
              className="px-4 py-2 rounded-lg bg-indigo-600 hover:bg-indigo-700"
            >
              Challenge
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}
