import { useState, useEffect } from "react";
import { CommentAuthor, CommentContainer, CommentContent, CommentDate, CommentItem, } from "../styles/Styles";
import axios from "axios";

const CommentList = ({ boardNo }) => {
  const [comments, setComments] = useState([]);

  useEffect(() => {
    axios
      .get(`http://localhost/comments?boardNo=${boardNo}`)
      .then((response) => {
        setComments([...response.data]);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  return (
    <CommentContainer>
      {comments.length === 0 ? 
        <CommentItem>
          <CommentAuthor>댓글을</CommentAuthor>
          <CommentContent>찾을 수 없습니다. </CommentContent>
          <CommentDate>🥕</CommentDate>
        </CommentItem>
      : comments.map((comments) => {
          return (
            <CommentItem>
              <CommentAuthor>{comments.CommentAuthor}</CommentAuthor>
              <CommentContent>{comments.CommentContent}</CommentContent>
              <CommentDate>{comments.CommentDate}</CommentDate>
            </CommentItem>
          ) 
        })
      }
    </CommentContainer>
  );
};

export default CommentList;
