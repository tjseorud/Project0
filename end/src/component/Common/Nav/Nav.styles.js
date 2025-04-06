import styled from "styled-components";

export const StyledNav = styled.nav`
  width: 100%;
  height: 50px;
  padding: 1rem 0;
  display: flex;
  justify-content: center;
  gap: 2rem;
`;
export const NavLink = styled.a`
  width: 100px;
  padding: 0.5rem 1rem;
  font-size: 1.2rem;
  font-weight: 600;
  text-decoration: none;
  text-align: center;
  letter-spacing: 1px;
  border-radius: 20px;
  transition: all 0.3s ease;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.17);
  color: white;
  background: linear-gradient(
    150deg,
    skyblue,
    greenyellow
  );
  &:hover {
    opacity: 0.8;
    cursor: pointer;
    transform: scale(1.05);
    color: orangered;
  }
`;
