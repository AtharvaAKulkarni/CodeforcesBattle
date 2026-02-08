import { useEffect, useState } from "react";
import { useAuth } from "@/context/authContext";
import { joinRoom } from "@/webscoketConfig/duelSocket";
import { useNavigate } from "react-router-dom";

export default function DuelLobby() {
    const { user, loading } = useAuth();
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
    const navigate = useNavigate();

    const [challenge, setChallenge] = useState(null);
    const [activeDuel, setActiveDuel] = useState(null);
    const [waitingForOpponent, setWaitingForOpponent] = useState(false);
    const [loadingMessage, setLoadingMessage] = useState("");

    

    const acceptChallenge = () => {
        setLoadingMessage("Waiting for opponent...");
        setWaitingForOpponent(true);

        joinRoom(
            challenge.roomId,
            user.username,
            challenge.challenger,
            user.username,
            (duel) => {
                setActiveDuel(duel);
                setWaitingForOpponent(false);
            }
        );

        setChallenge(null);
    };

    const joinAsChallenger = () => {
        setLoadingMessage("Waiting for opponent...");
        setWaitingForOpponent(true);

        joinRoom(
            challenge.roomId,
            user.username,
            user.username,
            challenge.opponent,
            (duel) => {
                setActiveDuel(duel);
                setWaitingForOpponent(false);
            }
        );

        setChallenge(null);
    };

    return (
        <div className="min-h-screen bg-zinc-950 text-white p-6">

            {/* Challenge Popup */}
            {challenge && !activeDuel && (
                <div className="fixed inset-0 bg-black/70 flex items-center justify-center z-50">
                    <div className="bg-zinc-900 p-6 rounded-xl w-[320px] text-center border border-zinc-700">
                        <h2 className="text-lg font-semibold mb-2">⚔️ Duel Challenge</h2>

                        {challenge.challenger === user.username ? (
                            <>
                                <p className="text-sm text-zinc-400 mb-5">
                                    Waiting for opponent to accept...
                                </p>
                                <button
                                    onClick={joinAsChallenger}
                                    className="px-4 py-2 rounded-lg bg-indigo-600 hover:bg-indigo-700"
                                >
                                    Join Room
                                </button>
                            </>
                        ) : (
                            <>
                                <p className="text-sm text-zinc-400 mb-5">
                                    {challenge.challenger} challenged you
                                </p>
                                <button
                                    onClick={acceptChallenge}
                                    className="px-4 py-2 rounded-lg bg-green-600 hover:bg-green-700"
                                >
                                    Accept
                                </button>
                            </>
                        )}
                    </div>
                </div>
            )}

            {/* Loader */}
            {waitingForOpponent && !activeDuel && (
                <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/70 backdrop-blur-sm">
                    <div className="bg-zinc-900 border border-zinc-700 rounded-xl p-6 w-[300px] text-center">
                        <div className="flex justify-center mb-4">
                            <div className="w-10 h-10 border-4 border-indigo-500/30
                              border-t-indigo-500 rounded-full animate-spin" />
                        </div>
                        <p className="text-sm text-zinc-400">{loadingMessage}</p>
                    </div>
                </div>
            )}

            {/* Question */}
            {activeDuel?.question && (
                <div className="mt-6 p-4 rounded-xl bg-zinc-900 border border-zinc-700">
                    <h3 className="text-lg font-semibold mb-2">⚔️ Duel Question</h3>
                    <p className="text-sm text-zinc-400 mb-3">
                        {activeDuel.question.name}
                    </p>

                    <button
                        onClick={() =>
                            window.open(
                                `https://codeforces.com/problemset/problem/${activeDuel.question.contestId}/${activeDuel.question.index}`,
                                "_blank"
                            )
                        }
                        className="px-4 py-2 rounded-lg bg-indigo-600 hover:bg-indigo-700"
                    >
                        Go to Question
                    </button>
                </div>
            )}
        </div>
    );
}
