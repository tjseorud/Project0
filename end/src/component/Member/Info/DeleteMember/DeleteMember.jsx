import { Input, Button } from "../../../styles/Styles";
import { useState, useContext } from "react";
import axios from "axios";
import { AuthContext } from "../../../context/AuthContext";

const DeleteMember = () => {
  const { auth } = useContext(AuthContext);
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleDeleteMember = () => {
    axios
      .delete("http://localhost/members", {
        password,
      }, {
        headers: {
          Authorization: `Bearer ${auth.accessToken}`,
        }
      })
      .then(() => {
        alert("탈퇴 되었습니다.");
        window.location.href = "/";
      })
      .catch((error) => {
        setError(
          error.response.data["error-message"] || "탈퇴 중 오류가 발생했습니다."
        );
      });
  };

  return (
    <>
      <Input
        type="password"
        placeholder="탈퇴를 원하시면 비밀번호를 입력해주세요."
        onChange={(e) => setPassword(e.target.value)}
        required
      />
      <label>{error}</label>
      <Button 
        type="button"
        onClick={handleDeleteMember}
        style={{ backgroundColor: "red" }} 
      >
        탈퇴하기🥕
      </Button>
    </>
  );
};

export default DeleteMember;
