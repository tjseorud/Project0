import { BoardContent, BoardWriter, Container, Title, Button, ImageContainer, ImagePreview, Form, Label, Input } from "../styles/Styles";
import { useState, useEffect, useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";
import CommentForm from "../Comment/CommentForm";

const BoardDetail = () => {
  const { id } = useParams();
  const [board, setBoard] = useState(null);
  const [loading, setLoading] = useState(true);
  const navi = useNavigate();
  const { auth } = useContext(AuthContext);
  const [error, setError] = useState(false);
  const [file , setFile] = useState(null);

  useEffect(() => {

    axios
      .get(`http://localhost/boards/${id}`)
      .then((response) => {
        console.log(response);
        setBoard(response.data);
        setLoading(false);
      })
      .catch((error) => {
        setError(true);
        setLoading(false);
      });
  }, [id]);

  if(loading) {
    return (
      <Container>
        <Title>게시글을 불러오는 중 입니다...</Title>
      </Container>
    );
  };

  if(error) {
    return (
      <Container>
        <Title>게시글을 찾을 수 없습니다. 🥕</Title>
      </Container>
    );
  };

  const handleBack = () => {
    navi(-1);
  };

  const handleDelete = (e) => {
    e.preventDefault(); //기본 이벤트 제거

    if(confirm("정말로 삭제하시겠습니까?")) {
      axios
        .delete(`http://localhost/boards/${id}`,{
          headers: {
            Authorization: `Bearer ${auth.accessToken}`,
          },
        })
        .then(() => {
          setBoard({
            boardTitle: "삭제 중 입니다...",
            boardContent: "삭제 중 입니다...",
            boardWriter: "삭제 중 입니다...",  
          });

          setTimeout(() => {
            alert("삭제 되었습니다.");
            navi("/boards");
          }, 3600);
        });
    }
  };

  const handleUpdate = (e) => {
    e.preventDefault(); //기본 이벤트 제거
    ("use strict");

    if( !boardTitle || !boardContent) {
      alert("제목과 내용을 입력하세요.");
      return;
    }

    const formData = new FormData();
    formData.append("boardTitle", boardTitle);
    formData.append("boardContent", boardContent);

    if(file){
      formData.append("file", file);
    }

    axios
      .put(`http://localhost/boards/${id}`, formData, {
        headers: {
          Authorization: `Bearer ${auth.accessToken}`,
          "Content-Type": "multipart/form-data",
        },
      })
      .then(() => {
        setTimeout(() => {
          alert("수정 되었습니다.");
          navi("/boards");
        }, 3600);
      });
  };

  const handleFileChange = (e) => {
    const selectedFile = e.target.files[0];
    // console.log(selectedFile);
    const allowedTypes = ["image/jpg", "image/jpeg", "image/png", "image/gif", "image/svg"];
    const maxSize = 1024 * 1024 * 10; //10MB?

    if(selectedFile && !allowedTypes.includes(selectedFile.type)) {
      alert("이미지 파일만 업로드 가능");
      return;
    }

    if(selectedFile && selectedFile.size > maxSize) {
      alert("파일이 너무 큼니다.");
      return;
    }

    setFile(selectedFile);
  };

  return (
    <>
      <Container>
        { !(board.boardWriter === auth.memberId) ? (
          <>
            <Title>{board.boardTitle}</Title>
            <BoardWriter>작성자: {board.boardWriter}</BoardWriter>
            <BoardContent>{board.boardContent}</BoardContent>
            {board.boardFileUrl ? (
              <ImageContainer>
                <ImagePreview src={board.boardFileUrl} alt="이미지" />
              </ImageContainer>
            ) : (
              <label></label>
            )}
          </>
        ) : (
          <>
            <Label>제목</Label>
            <Input 
              type="text" 
              value={board.boardTitle}
              placeholder="제목을 입력하세요" 
              onChange={(e) => setBoardTitle(e.target.value)}
            />
            <br/>
            <Label>내용</Label>
            <Input 
              type="text" 
              value={board.boardContent}
              placeholder="내용을 입력하세요" 
              onChange={(e) => setBoardContent(e.target.value)}
            />
            <br/>
            <Label>작성자</Label>
            <Input 
              type="text" 
              value={board.boardWriter}
              readOnly
              style={{ backgroundColor: "lightgray" }}
            />
            <br/>
            <Label htmlFor="file">파일첨부</Label>
            <Input 
              type="file" 
              id="file"
              accept="image/*"
              onChange={handleFileChange}
            />
            <br/>
            <ImageContainer>
              <ImagePreview src={file} alt="미리보기" />
            </ImageContainer>
            <br/>
            <Form onSubmit={handleUpdate}>
              <Button style={{ background: "green" }}>수정하기</Button>
            </Form>
            <Form onSubmit={handleDelete}>
              <Button style={{ background: "crimson" }}>삭제하기</Button>
            </Form> 
          </>
        )}
        <Button onClick={handleBack}>뒤로가기</Button>
      </Container>
      <CommentForm boardNo={id}/>
    </>
  );
};

export default BoardDetail;
