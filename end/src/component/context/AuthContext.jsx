import { useState, useEffect, createContext } from "react";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  // session.setAttribute("KEY", value);

  const [auth, setAuth] = useState({
    memberId: null,
    memberNo: null,
    memberName: null,
    accessToken: null,
    refreshToken: null,
    isAuthenticated: false,
  });

  useEffect(() => {
    const accessToken = localStorage.getItem("accessToken");
    const refreshToken = localStorage.getItem("refreshToken");
    const memberName = localStorage.getItem("memberName");
    const memberId = localStorage.getItem("memberId");
    const memberNo = localStorage.getItem("memberNo");

    if(accessToken && refreshToken && memberId && memberNo && memberName) {
      setAuth({
        memberNo,
        memberId,
        memberName,
        accessToken,
        refreshToken,
        isAuthenticated: true,
      });
    }
  },[]);

  const login = (memberId, memberName, memberNo, accessToken, refreshToken) => {
    setAuth({
      memberNo,
      memberId,
      memberName,
      accessToken,
      refreshToken,
      isAuthenticated: true,
    });
    localStorage.setItem("memberId", memberId);
    localStorage.setItem("memberNo", memberNo);
    localStorage.setItem("memberName", memberName);
    localStorage.setItem("accessToken", accessToken);
    localStorage.setItem("refreshToken", refreshToken);
  };

  const logout = () => {
    setAuth({
      memberId: null,
      memberName: null,
      memberNo: null,
      accessToken: null,
      refreshToken: null,
      isAuthenticated: false,
    });
    localStorage.removeItem("memberId");
    localStorage.removeItem("memberNo");
    localStorage.removeItem("memberName");
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    window.location.href = "/";
  };

  return (
    <AuthContext.Provider value={{ auth, login, logout}}>
      {children}
    </AuthContext.Provider>
  );
};
