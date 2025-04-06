import { Container, Form, Title, Input, Button } from "../styles/Styles";
import CommentList from "./CommentList";
import { useState, useEffect, useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import axios from "axios";

const CommentForm = ({ boardNo }) => {
  // 사용자가 입력한 댓글내용 + 회원번호 + 게시글 번호
  const { auth } = useContext(AuthContext);
  const [commentContent, setCommentContent] = useState("");

  const handleInsertComment = (e) => {
    e.preventDefault();

    if(commentContent.trim() === '') {
      alert("댓글을 작성해주세요.");
      return;
    }

    if( !auth.isAuthenticated) {
      alert("로그인 후 댓글을 작성해주세요.");
      return;
    }

    axios
      .post(`http://localhost/comments`, {
        refBoardNo: boardNo,
        commentWriter: auth.memberNo,
        commentContent: commentContent, 
      }, {
        headers: {
          Authorization: `Bearer ${auth.accessToken}`,
        },
      })
      .then((response) => {
        if(response.status === 201) {
          alert("댓글이 작성되었습니다.");
          setCommentContent("");
        }
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <Container>
      <Form onSubmit={handleInsertComment}>
        <Title>댓글 작성</Title>
        <Input 
          type="text" 
          value={commentContent}
          placeholder="댓글을 입력하세요."
          onChange={(e) => setCommentContent(e.target.value)}
        />
        <Button>작성하기</Button>
      </Form>
      <CommentList boardNo={boardNo}/>
    </Container>
  );
};

export default CommentForm;
