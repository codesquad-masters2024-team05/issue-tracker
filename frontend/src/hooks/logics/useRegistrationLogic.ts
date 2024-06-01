import { sendIdValidationRequest, sendRegistrationRequest } from "../../api/LoginAPI";
import { useNavigate } from "react-router-dom";
import { useMutation } from "react-query";
import { RefObject, useRef, useState } from "react";

const INPUT_MIN_LENGTH = 6;
const NICKNAME_MIN_LENGTH = 2;
const INPUT_REGEX = /^[A-Za-z0-9]+$/;
const ERROR_MESSAGES = {
  INPUT_REGEX: "ID, 비밀번호와 닉네임은 알파벳과 숫자만 포함해야 합니다.",
  INPUT_LENGTH: "ID와 비밀번호는 최소 6자이여야 합니다.",
  PASSWORD_VALIDATION: "비밀번호와 비밀번호 확인이 일치하지 않습니다.",
  NICKNAME_LENGTH: "닉네임은 최소 2자이여야 합니다.",
  REGISTRATION_FAILURE: "회원가입에 실패하였습니다.",
  UNKNOWN: "알 수 없는 에러가 발생하였습니다.",
};

const getRefValue = (ref: RefObject<HTMLInputElement>) => ref.current?.value || "";

const useRegistrationLogic = () => {
  const idValueRef = useRef<HTMLInputElement>(null);
  const passwordValueRef = useRef<HTMLInputElement>(null);
  const passwordValidationValueRef = useRef<HTMLInputElement>(null);
  const nicknameValueRef = useRef<HTMLInputElement>(null);
  const [isValidated, setIsValidated] = useState(false);
  const [isSubmitable, setIsSubmitable] = useState(false);
  const [validationMessage, setValidationMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate();

  const { mutate: validateId } = useMutation(sendIdValidationRequest, {
    onSuccess: (message) => {
      setValidationMessage(message);
      setIsValidated(true);
    },
    onError: (error) => {
      const errorMessage = error instanceof Error ? error.message : ERROR_MESSAGES.UNKNOWN;
      setValidationMessage(errorMessage);
      setIsValidated(false);
    },
  });

  const { mutate: register } = useMutation(sendRegistrationRequest, {
    onSuccess: () => navigate("/login"),
    onError: () => setErrorMessage(ERROR_MESSAGES.REGISTRATION_FAILURE),
  });

  const validateInputs = () => {
    const inputRefs = [idValueRef, passwordValueRef, passwordValidationValueRef, nicknameValueRef];
    if (inputRefs.some((ref) => !INPUT_REGEX.test(getRefValue(ref)))) return ERROR_MESSAGES.INPUT_REGEX;
    if (!isPasswordValid()) return ERROR_MESSAGES.PASSWORD_VALIDATION;
    if (!isInputLengthValid()) return ERROR_MESSAGES.INPUT_LENGTH;
    if (!isNicknameLengthValid()) return ERROR_MESSAGES.NICKNAME_LENGTH;
  };

  const isPasswordValid = () => getRefValue(passwordValueRef) === getRefValue(passwordValidationValueRef);
  const isInputLengthValid = () =>
    getRefValue(idValueRef).length >= INPUT_MIN_LENGTH && getRefValue(passwordValueRef).length >= INPUT_MIN_LENGTH;
  const isNicknameLengthValid = () => getRefValue(nicknameValueRef).length >= NICKNAME_MIN_LENGTH;

  const handleValidationClick = () => validateId(getRefValue(idValueRef));
  const handleRegistrationClick = () => {
    if (!isSubmitable) return;

    const validationResult = validateInputs();
    if (validationResult) {
      setErrorMessage(validationResult);
      return;
    }

    setErrorMessage("");
    register({
      userId: getRefValue(idValueRef),
      userPassword: getRefValue(passwordValueRef),
      userNickname: getRefValue(nicknameValueRef),
    });
  };

  const handleOnChange = () => {
    const currentIsSubmitable =
      [idValueRef, passwordValueRef, passwordValidationValueRef, nicknameValueRef].every((ref) => getRefValue(ref)) &&
      isValidated;

    setIsSubmitable(currentIsSubmitable);
  };

  return {
    idValueRef,
    passwordValueRef,
    passwordValidationValueRef,
    nicknameValueRef,
    isValidated,
    isSubmitable,
    validationMessage,
    errorMessage,
    handleOnChange,
    handleRegistrationClick,
    handleValidationClick,
  };
};

export default useRegistrationLogic;
