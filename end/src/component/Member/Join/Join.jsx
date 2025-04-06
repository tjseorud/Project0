import { Button, Container, Form, Input, Title } from "../../styles/Styles";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const Join = () => {
  const [memberId, setMemberId] = useState("");
  const [memberPw, setMemberPw] = useState("");
  const [memberName, setMemberName] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const navi = useNavigate();

  const handleInputId = (e) => {
    setMemberId(e.target.value);
  };
  const handleInputPw = (e) => {
    setMemberPw(e.target.value);
  };
  const handleInputName = (e) => {
    setMemberName(e.target.value);
  };
  const handleSubmit = (e) => {
    e.preventDefault();

    axios
      .post(`http://localhost/members`, {
        memberId,
        memberPw,
        memberName,
      })
      .then((result) => {
        console.log(result);

        if(result.status === 201) {
          alert("회원가입이 완료되었습니다.");
          setTimeout(() => {
            navi("/");
          }, 1000);
        }
      })
      .catch((error) => {
        console.log(error);
        setErrorMsg(error.response.data["error-message"]);
        // setErrorMsg(error.response.data.memberId);
      });
  };

  return (
    <>
      <Container>
        <Form onSubmit={handleSubmit}>
          <Title>회원가입</Title>
          <Input
            onChange={handleInputId}
            name="memberId"
            type="text"
            placeholder="아이디를 입력해주세요."
            required
          />
          <label>{errorMsg}</label>
          <Input
            onChange={handleInputPw}
            name="memberPw"
            type="password"
            placeholder="비밀번호를 입력해주세요."
            required
          />
          <Input
            onChange={handleInputName}
            name="memberName"
            type="text"
            placeholder="이름을 입력해주세요."
            required
          />
          <Button type="submit">가입하기</Button>
        </Form>
      </Container>
    </>
  );
};

export default Join;
