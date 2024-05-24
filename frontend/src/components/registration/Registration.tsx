import styled from "styled-components";
import pageLogo from "../../img/icon/pageLogo.svg";
import AuthorizationForm from "../authorization/AuthorizationForm";
import { useNavigate } from "react-router-dom";
<<<<<<< be-dev
import useRegistrationLogic from "../../hooks/useRegistrationLogic";

function Registration() {
  const {
    state: { isValidated, validationMessage, allFilled, errorMessage },
    setIdValue,
    setPasswordValue,
    setPasswordValidationValue,
    setNicknameValue,
=======
import useRegistrationLogic from "../../hooks/logics/useRegistrationLogic";

function Registration() {
  const {
    idValueRef,
    passwordValueRef,
    passwordValidationValueRef,
    nicknameValueRef,
    isValidated,
    isSubmitable,
    validationMessage,
    errorMessage,
    handleOnChange,
>>>>>>> team-05
    handleRegistrationClick,
    handleValidationClick,
  } = useRegistrationLogic();
  const navigate = useNavigate();

  return (
    <RegistrationWrapper>
      <Logo src={pageLogo} alt="page-logo" onClick={() => navigate("/")} />
<<<<<<< be-dev
      <AuthorizationForm type="id" onInputChange={setIdValue} />
=======
      <AuthorizationForm ref={idValueRef} type="id" onChange={handleOnChange} />
>>>>>>> team-05
      <ValidationWrapper>
        <DuplicateValidation onClick={handleValidationClick}>중복 확인</DuplicateValidation>
        <ValidationMessageWrapper>
          <ValidationMessage validated={isValidated}>{validationMessage}</ValidationMessage>
        </ValidationMessageWrapper>
      </ValidationWrapper>
<<<<<<< be-dev
      <AuthorizationForm type="password" onInputChange={setPasswordValue} />
      <AuthorizationForm type="password-validation" onInputChange={setPasswordValidationValue} />
      <AuthorizationForm type="nickname" onInputChange={setNicknameValue} />
      {errorMessage && <ErrorMessage>{errorMessage}</ErrorMessage>}
      <SubmitButton allFilled={allFilled} onClick={handleRegistrationClick}>
=======
      <AuthorizationForm ref={passwordValueRef} type="password" onChange={handleOnChange} />
      <AuthorizationForm ref={passwordValidationValueRef} type="password-validation" onChange={handleOnChange} />
      <AuthorizationForm ref={nicknameValueRef} type="nickname" onChange={handleOnChange} />
      {errorMessage && <ErrorMessage>{errorMessage}</ErrorMessage>}
      <SubmitButton isSubmitable={isSubmitable} onClick={handleRegistrationClick}>
>>>>>>> team-05
        회원가입
      </SubmitButton>
    </RegistrationWrapper>
  );
}

const RegistrationWrapper = styled.div`
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

const ValidationWrapper = styled.div`
  width: 320px;
  display: flex;
  justify-content: space-between;
`;

const DuplicateValidation = styled.button`
  width: 100px;
  height: 2em;
  background-color: #595959;
  border: 1px solid #595959;
  border-radius: 16px;
  color: white;
  cursor: pointer;
`;

const ValidationMessageWrapper = styled.div`
  display: flex;
  align-items: center;
`;

const ValidationMessage = styled.span<{ validated: boolean }>`
  color: ${({ validated }) => (validated ? "green" : "red")};
`;

<<<<<<< be-dev
const SubmitButton = styled.button<{ allFilled: boolean }>`
=======
const SubmitButton = styled.button<{ isSubmitable: boolean }>`
>>>>>>> team-05
  width: 320px;
  height: 56px;
  border: 1px solid #595959;
  border-radius: 16px;
  background-color: #595959;
  font-size: 20px;
  color: white;
<<<<<<< be-dev
  opacity: ${({ allFilled }) => (allFilled ? "1" : "0.32")};
  cursor: ${({ allFilled }) => (allFilled ? "pointer" : "default")};
=======
  opacity: ${({ isSubmitable }) => (isSubmitable ? "1" : "0.32")};
  cursor: ${({ isSubmitable }) => (isSubmitable ? "pointer" : "default")};
>>>>>>> team-05
  transition: all 0.5s ease-in-out;
`;

const ErrorMessage = styled.div`
  color: red;
  margin-top: 10px;
`;

export default Registration;
