import { createContext, useContext, useEffect, useState } from "react";
import axios from "axios";

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);     // user object
  const [loading, setLoading] = useState(true);

  // Run on app load
  useEffect(() => {
    const token = localStorage.getItem("token");

    if (!token) {
      setLoading(false);
      return;
    }

    const loadUser = async () => {
      try {
        const res = await axios.get("http://localhost:8080/user/me", {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });

        setUser(res.data);   // 🔥 real backend data
      } catch (err) {
        console.error("Session expired / invalid token");
        localStorage.removeItem("token");
        setUser(null);
      } finally {
        setLoading(false);
      }
    };

    loadUser();
  }, []);

  const login = async (token) => {
    localStorage.setItem("token", token);
    axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
    try {
      const res = await axios.get("http://localhost:8080/user/me", {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });

      setUser(res.data);   // 🔥 backend is source of truth
    } catch (err) {
      console.error("Failed to fetch user");
      localStorage.removeItem("token");
      setUser(null);
      throw err;
    }
  };

  const logout = () => {
    localStorage.removeItem("token");
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, setUser, login, logout, loading }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
