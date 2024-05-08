import { useState } from "react";
import styled, { css } from "styled-components";

interface LoginFormProps {
  type: "id" | "password";
  onInputChange: (value: string) => void;
}

const MAX_LENGTH = {
  id: 16,
  password: 12,
};
const LABEL_TEXT = {
  id: "아이디",
  password: "비밀번호",
};

function LoginForm({ type, onInputChange }: LoginFormProps) {
  const [inputValue, setInputValue] = useState("");
  const [isFocused, setIsFocused] = useState<boolean>(false);
  const inputType = type === "password" ? type : "text";

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setInputValue(event.target.value);
    onInputChange(event.target.value);
  };

  return (
    <Form isFocused={isFocused}>
      <LoginParagraph>
        <LoginInput
          type={inputType}
          value={inputValue}
          maxLength={MAX_LENGTH[type]}
          onChange={(event) => handleInputChange(event)}
          required
          onFocus={() => setIsFocused(true)}
          onBlur={() => setIsFocused(false)}
        />
        <LoginInputLabel>
          <span>{LABEL_TEXT[type]}</span>
        </LoginInputLabel>
      </LoginParagraph>
    </Form>
  );
}

const Form = styled.div<{ isFocused: boolean }>`
  width: 288px;
  height: 56px;
  display: flex;
  padding: 0 1em;
  flex-direction: column;
  justify-content: center;
  border: 1px solid #eceef5;
  border-radius: 16px;
  background-color: #eceef5;
  transition: all 0.5s ease;

  ${(props) =>
    props.isFocused &&
    css`
      background-color: white;
      outline: 1.6px solid black;
    `}
`;

const LoginParagraph = styled.p`
  position: relative;
  width: 100%;
  height: 24px;
  margin: 0;
`;

const LoginInput = styled.input`
  position: relative;
  top: 35%;
  width: 100%;
  height: 100%;
  padding: 0;
  border: 0 none;
  background-color: transparent;
  color: #595f63;
  outline: none;

  &:focus + label span,
  &:valid + label span {
    transform: translateY(-60%);
    font-size: 12px;
    color: #595f63;
  }

  &:focus + label::after,
  &:valid + label::after {
    width: 100%;
    transform: translateX(0);
  }
`;

const LoginInputLabel = styled.label`
  position: absolute;
  top: -50%;
  left: 0%;
  width: 100%;
  height: 100%;
  text-align: left;
  pointer-events: none;

  &::after {
    content: "";
    position: absolute;
    left: 0;
    bottom: -1px;
    width: 0;
    height: 100%;
    border: none;
    transition: all 0.3s ease;
  }

  span {
    position: absolute;
    top: 1em;
    left: 0;
    bottom: 5px;
    transition: all 0.3s ease;
  }
`;

export default LoginForm;
