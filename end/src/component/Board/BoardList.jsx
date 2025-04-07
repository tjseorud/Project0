import { Container, Title, Button, BoardOuter, BoardTitle, BoardWriter, Board, CreateDate, } from "../styles/Styles";
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

const BoardList = () => {
  const navi = useNavigate();
  const [boards, setBoards] = useState([]);
  const [page, setPage] = useState(0);
  const [hasmore, setHasmore] = useState(true);

  useEffect(() => {
    axios
      .get("http://localhost/boards", {
        params: {
          page: page,
        },
      })
      .then((response) => {
        console.log(response);
        setBoards([...boards, ...response.data]); // ... 연산자를 사용하여 boards 배열에 게시글 추가

        if(response.data.length < 3) {
          setHasmore(false);  // 더 이상 게시글이 없으면 false
        }
      })
      .catch((error) => {
        console.log(error);
      });
  }, [page]);

  const handelMove = () => {
    setPage(page + 1); 
  };

  return (
    <Container height="600px">
      <Title>게시판 목록</Title>
      <BoardOuter>
        <Button onClick={() => navi("/boardForm")}>새 글 작성</Button>
        {boards.map((board) => (
          <Board 
            key={board.boardNo} 
            onClick={() => navi(`/boards/${board.boardNo}`)}
          >
            <BoardTitle>{board.boardTitle}</BoardTitle>
            <BoardWriter>{board.boardWriter}</BoardWriter>
            <CreateDate>{board.createDate}</CreateDate>
          </Board>
        ))}
        {hasmore && <Button onClick={handelMove}>더보기 🥕</Button>}
      </BoardOuter>
    </Container>    
  );
};

export default BoardList;
