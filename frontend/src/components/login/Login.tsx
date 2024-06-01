import styled from "styled-components";
import pageLogo from "../../img/icon/pageLogo.svg";
import AuthorizationForm from "../authorization/AuthorizationForm";
import { useNavigate } from "react-router-dom";
import useLoginLogic from "../../hooks/logics/useLoginLogic";
import Loading from "./Loading";

function Login() {
  const { isSubmitable, errorMessage, isLoading, idValueRef, passwordValueRef, handleLoginClick, handleOnChange } =
    useLoginLogic();
  const navigate = useNavigate();

  return (
    <LoginWrapper>
      <Logo src={pageLogo} alt="page-logo" onClick={() => navigate("/")} />
      <GithubLoginButton>Github 계정으로 로그인</GithubLoginButton>
      <div>or</div>
      <AuthorizationForm ref={idValueRef} type="id" onChange={handleOnChange} />
      <AuthorizationForm ref={passwordValueRef} type="password" onChange={handleOnChange} />
      <LoginButton onClick={handleLoginClick} isSubmitable={isSubmitable}>
        아이디로 로그인
      </LoginButton>
      {errorMessage && <ErrorMessage>{errorMessage}</ErrorMessage>}
      <RegistrationButton onClick={() => navigate("/registration")}>회원가입</RegistrationButton>
      {isLoading && <Loading />}
    </LoginWrapper>
  );
}

const LoginWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1em;
  justify-content: center;
  align-items: center;
  color: #6e7191;
`;

const Logo = styled.img`
  margin-bottom: 48px;
  cursor: pointer;
`;

const GithubLoginButton = styled.button`
  width: 320px;
  height: 56px;
  border: 1px solid #7e7e7e;
  border-radius: 16px;
  padding: 0 24px;
  color: #7e7e7e;
  background-color: transparent;
  font-size: 20px;
  cursor: pointer;
`;

const LoginButton = styled.button<{ isSubmitable: boolean }>`
  width: 320px;
  height: 56px;
  border: 1px solid #595959;
  border-radius: 16px;
  background-color: #595959;
  font-size: 20px;
  color: white;
  opacity: ${({ isSubmitable }) => (isSubmitable ? "1" : "0.32")};
  cursor: ${({ isSubmitable }) => (isSubmitable ? "pointer" : "default")};
  transition: all 0.5s ease-in-out;
`;

const RegistrationButton = styled.button`
  border: none;
  background-color: transparent;
  font-size: 16px;
  color: #4e4b66;
  cursor: pointer;
`;

const ErrorMessage = styled.div`
  color: red;
  margin-top: 10px;
`;

export default Login;