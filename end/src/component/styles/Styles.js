import styled from "styled-components";

export const Container = styled.div`
  width: 1000px;
  height: auto;
  min-height: ${(props) => (props.height ? props.height : "600px;")};
  margin: 35px auto;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 0 10px skyblue;
  background-color: white;
`;
export const Form = styled.form`
  display: flex;
  flex-direction: column;
`;
export const Title = styled.h2`
  font-size: 33px;
  color: #33333;
  margin-bottom: 70px;
`;
export const Input = styled.input`
  padding: 10px;
  margin: 10px 0;
  border: 1px solid rgba(251, 137, 255, 0.1);
  border-radius: 4px;
  &:focus {
    outline: none;
    border-color: skyblue;
  }
`;
export const Button = styled.button`
  padding: 10px;
  margin-top: 40px;
  font-weight: bold;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  background-color: skyblue;
  color: white;
  &:hover {
    background-color: lightblue;
  }
`;
export const Tabs = styled.div`
  display: flex;
  gap: 20px;
  margin-bottom: 20px;
`;
export const Tab = styled.div`
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
  transition: background-color 0.3s, color 0.3s;
  background-color: ${(props) => props.active ? "skyblue" : "white"};
  color: ${(props) => (props.active ? "white" : "skyblue")};
  &:hover {
    background-color: skyblue;
    color: white;
  }
`;
export const BoardOuter = styled.ul`
  padding: 40px;
  list-style: none;
`;
export const Board = styled.li`
  padding: 15px;
  display: flex;
  justify-content: space-between;
  border-bottom: 1px solid #DDDDDD;
  align-items: center;
  &:last-child {
    border-bottom: none;
  }
  &:hover {
    background-color: white;
    cursor: pointer;
  }
`;
export const BoardTitle = styled.a`
  flex: 2;
  font-size: 1.2em;
  text-decoration: none;
  text-align: center;
  color: rgb(33, 33, 33);
  &:hover {
    text-decoration: underline;
  }
`;
export const BoardWriter = styled.span`
  flex: 1;  
  font-size: 0.9em;
  text-align: center;
  color: #888888;
`;
export const BoardContent = styled.p`
  line-height: 1.6;
  min-height: 200px;
  margin-bottom: 20px;
  font-size: 1.2em;
  color: #555555;
`;
export const CreateDate = styled.span`
  flex: 1;
  font-size: 0.9em;
  text-align: center;
  color: rgb(55, 55, 55);
`;
export const ImagePreview = styled.img`
  max-width: 100%;
  min-width: auto;
  height: auto;
  margin-bottom: 15px;
  border-radius: 8px;
`;
export const ImageContainer = styled.div`
  margin-bottom: 15px;
  display: flex;
  justify-content: center;
  align-items: center;
`;
export const Label = styled.label`
  margin-bottom: 5px;
  font-weight: bold;
`;
export const CommentContainer = styled.div`
  margin-top: 20px;
`;
export const CommentItem = styled.div`
  padding: 10px 0; 
  border-bottom: 1px solid #ddd;
`;
export const CommentAuthor = styled.p`
  margin: 0;
  font-weight: bold;
`;
export const CommentContent = styled.p`
  margin: 5px 0;
`;
export const CommentDate = styled.span`
  font-size: 12px;
  color: #888;
`;
