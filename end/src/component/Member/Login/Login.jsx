import { Button, Container, Form, Input, Title } from "../../styles/Styles";
import { useState, useContext } from "react";
import axios from "axios";
import { AuthContext } from "../../context/AuthContext";

const Login = () => {
  const [memberId, setMemberId] = useState("");
  const [memberPw, setMemberPw] = useState("");
  const [msg, setMsg] = useState("");
  const { auth, login } = useContext(AuthContext);

  const handleLogin = (e) => {
    e.preventDefault();

    const regexp = /^[a-zA-Z0-9]+$/;

    if( !regexp.test(memberId)) {
      setMsg("아이디값이 유효하지 않습니다.");
      return;
    }else {
      setMsg("");
    };

    axios
      .post(`http://localhost/auth/login`, {
        memberId: memberId,
        memberPw: memberPw,
      })
      .then((result) => {
        //console.log(result);  
        const { memberId, memberName, memberNo, accessToken, refreshToken } = result.data;
        login(memberId, memberName, memberNo, accessToken, refreshToken);
        // sessionStorage.setItem();
        alert("로그인 되었습니다.");
        window.location.href = "/";
      })
      .catch((error) => {
        console.log(error);
        alert(error.response.data["error-message"]);
      });  
  };

  return (
    <>
      <Container>
        <Form onSubmit={handleLogin}>
          <Title>로그인</Title>
          <Input 
            onChange={(e) => setMemberId(e.target.value)}
            type="text" 
            placeholder="아이디를 입력해주세요." 
            required 
          />
          <Input
            onChange={(e) => setMemberPw(e.target.value)}
            type="password"
            placeholder="비밀번호를 입력해주세요."
            required
          />
          <label>{msg}</label>
          <Button type="submit">로그인</Button>
        </Form>
      </Container>
    </>
  );
};

export default Login;
