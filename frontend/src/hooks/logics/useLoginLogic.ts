import { sendLoginRequest } from "../../api/LoginAPI";
import useUserStore from "../stores/useUserStore";
import { useNavigate } from "react-router-dom";
import { useMutation } from "react-query";
import { useRef, useState } from "react";

const MIN_LENGTH = 6;
const INPUT_REGEX = /^[A-Za-z0-9]+$/;
const INPUT_REGEX_ERROR_MESSAGE = "ID와 비밀번호는 알파벳과 숫자만 포함해야 합니다.";
const INPUT_LENGTH_ERROR_MESSAGE = "ID와 비밀번호는 최소 6자이여야 합니다.";
const UNKNOWN_ERROR_MESSAGE = "알 수 없는 에러가 발생하였습니다.";

const useLoginLogic = () => {
  const { setUserId, setIsLoggedIn } = useUserStore();
  const idValueRef = useRef<HTMLInputElement>(null);
  const passwordValueRef = useRef<HTMLInputElement>(null);
  const [errorMessage, setErrorMessage] = useState("");
  const [isSubmitable, setIsSubmitable] = useState(false);
  const navigate = useNavigate();

  const { mutate: login, isLoading } = useMutation(sendLoginRequest, {
    onSuccess: () => {
      setUserId(idValueRef.current?.value || "");
      setIsLoggedIn(true);
      navigate("/");
    },
    onError: (error) => {
      const errorMessage = error instanceof Error ? error.message : UNKNOWN_ERROR_MESSAGE;
      setErrorMessage(errorMessage);
    },
  });

  const handleLoginClick = async () => {
    const idValue = idValueRef.current?.value || "";
    const passwordValue = passwordValueRef.current?.value || "";

    if (!isSubmitable) return;

    if (!INPUT_REGEX.test(idValue) || !INPUT_REGEX.test(passwordValue)) {
      setErrorMessage(INPUT_REGEX_ERROR_MESSAGE);
      return;
    }

    if (idValue.length < MIN_LENGTH || passwordValue.length < MIN_LENGTH) {
      setErrorMessage(INPUT_LENGTH_ERROR_MESSAGE);
      return;
    }

    setErrorMessage("");
    login({ userId: idValue, userPassword: passwordValue });
  };

  const handleOnChange = () => {
    const idValue = idValueRef.current?.value;
    const passwordValue = passwordValueRef.current?.value;
    const currentIsSubmitable = !!(idValue && passwordValue);

    if (currentIsSubmitable === !isSubmitable) setIsSubmitable(currentIsSubmitable);
  };

  return { isSubmitable, isLoading, errorMessage, idValueRef, passwordValueRef, handleLoginClick, handleOnChange };
};

export default useLoginLogic;
