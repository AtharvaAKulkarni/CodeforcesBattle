import { useState } from "react";
import { Card, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useAuth } from "@/context/authContext";
export default function AuthPages() {
  const navigate=useNavigate();
  const {login}=useAuth();
  const [mode, setMode] = useState("login");
  const [username, setUsername]=useState("");
  const [password, setPassword]=useState("");
    const handleLogin = async ()=>{
        try{
            const response=await axios.post("http://localhost:8080/public/login", {username:username, password:password});
            await login(response.data);
            navigate("/")
        }
        catch(e){
          console.error(e);
        }
    }
  return (
    <div className="min-h-screen flex items-center justify-center bg-black text-green-400 font-mono">
      <div className="absolute inset-0 bg-[radial-gradient(circle_at_top,_#0f172a,_black)] opacity-90" />

      <Card className="relative z-10 w-[380px] bg-zinc-900/90 border border-green-500 shadow-xl shadow-green-500/20 rounded-2xl">
        <CardContent className="p-6">
          <div className="text-center mb-6">
            <h1 className="text-2xl font-bold tracking-wider">
              CF<span className="text-green-500">1V1</span>
            </h1>
            <p className="text-sm text-green-300 mt-1">
              {mode === "login" ? "Login to duel ⚔️" : "Create your coding identity 🚀"}
            </p>
          </div>

          {mode === "signup" && (
            <input
              type="text"
              placeholder="Handle / Username"
              className="w-full mb-3 p-2 rounded bg-black border text-green-300 border-green-600 focus:outline-none focus:ring-2 focus:ring-green-500"
            />
          )}

          <input
            type="Username"
            placeholder="Username"
            className="w-full mb-3 p-2 rounded bg-black border text-green-300 border-green-600 focus:outline-none focus:ring-2 focus:ring-green-500"
            onChange={(e)=>setUsername(e.target.value)}
          />

          <input
            type="password"
            placeholder="Password"
            onChange={(e)=>setPassword(e.target.value)}
            className="w-full mb-4 p-2 rounded bg-black border text-green-300 border-green-600 focus:outline-none focus:ring-2 focus:ring-green-500"
          />

          <Button className="w-full bg-green-500 hover:bg-green-600 text-black font-bold rounded-xl" onClick={handleLogin}>
            {mode === "login" ? "LOGIN" : "SIGN UP"}
          </Button>

          <div className="text-center mt-4 text-sm text-green-300">
            {mode === "login" ? (
              <>
                No account?{" "}
                <button
                  onClick={() => setMode("signup")}
                  className="text-green-500 hover:underline"
                >
                  Create one
                </button>
              </>
            ) : (
              <>
                Already a coder?{" "}
                <button
                  onClick={() => setMode("login")}
                  className="text-green-500 hover:underline"
                >
                  Login
                </button>
              </>
            )}
          </div>

          <div className="mt-6 text-xs text-center text-green-700">
            <p>⚡ Competitive Programming Mode</p>
            <p>💻 Real-time 1v1 Coding Battles</p>
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
