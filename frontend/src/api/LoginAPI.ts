const SERVER = process.env.REACT_APP_SERVER;

interface LoginState {
  userId: string;
  userPassword: string;
}

interface UserState {
  userId: string;
  userNickname: string;
}

interface RegistrationState extends UserState {
  userPassword: string;
}

const LOGIN_ERROR_MEESAGE: { [key: number]: string } = {
  400: "아이디가 존재하지 않습니다.",
  404: "아이디나 비밀번호가 일치하지 않습니다.",
};

const REGISTRATION_ERROR_MESSAGE: { [key: number]: string } = {
  400: "아이디, 비밀번호의 형식이 올바르지 않습니다.",
  409: "이미 존재하는 아이디로 회원가입을 시도하였습니다.",
};

const ID_CHECK_SUCCESS = "사용할 수 있는 아이디입니다.";
const ID_CHECK_FAIL = "이미 존재하는 아이디입니다.";
const LOGIN_ID_NOT_FOUND_ERROR_MESSAGE = "아이디가 존재하지 않습니다.";
const LOGIN_INPUT_VALIDATION_ERROR_MESSAGE = "아이디나 비밀번호가 일치하지 않습니다.";
const REGISTRATION_FORMAT_ERROR_MESSAGE = "아이디, 비밀번호의 형식이 올바르지 않습니다.";
const REGISTRATION_DUPLICATE_VALIDATION_FAILURE_MEESAGE = "이미 존재하는 아이디로 회원가입을 시도하였습니다.";
const SERVER_ERROR_MESSAGE = "서버 연결에 실패하였습니다.";

export const sendLoginRequest: (loginState: LoginState) => Promise<Response> = async (loginState) => {
  try {
    const request = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      credentials: "include" as RequestCredentials,
      body: JSON.stringify(loginState),
    };
    const response = await fetch(`${SERVER}/login`, request);

    if (!response.ok) {
      const errorMessage = LOGIN_ERROR_MEESAGE[response.status] || SERVER_ERROR_MESSAGE;
      throw new Error(errorMessage);
    }
    
    if (response.status === 400) throw new Error(LOGIN_INPUT_VALIDATION_ERROR_MESSAGE);
    if (response.status === 404) throw new Error(LOGIN_ID_NOT_FOUND_ERROR_MESSAGE);
    if (!response.ok) throw new Error(SERVER_ERROR_MESSAGE);

    return response;
  } catch (error) {
    const message = error instanceof Error ? error.message : SERVER_ERROR_MESSAGE;
    throw new Error(message);
  }
};

export const sendRegistrationRequest: (registrationState: RegistrationState) => Promise<Response> = async (
  registrationState
) => {
  try {
    const request = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(registrationState),
    };
    const response = await fetch(`${SERVER}/registration`, request);


    if (!response.ok) {
      const errorMessage = REGISTRATION_ERROR_MESSAGE[response.status] || SERVER_ERROR_MESSAGE;
      throw new Error(errorMessage);
    }
    
    if (response.status === 400) throw new Error(REGISTRATION_FORMAT_ERROR_MESSAGE);
    if (response.status === 409) throw new Error(REGISTRATION_DUPLICATE_VALIDATION_FAILURE_MEESAGE);
    if (!response.ok) throw new Error(SERVER_ERROR_MESSAGE);

    return response;
  } catch (error) {
    const message = error instanceof Error ? error.message : SERVER_ERROR_MESSAGE;
    throw new Error(message);
  }
};

export const sendIdValidationRequest = async (idValue: string): Promise<string> => {
  try {
    const response = await fetch(`${SERVER}/validation/${idValue}`);

    if (response.status === 409) throw new Error(ID_CHECK_FAIL);
    if (!response.ok) throw new Error(SERVER_ERROR_MESSAGE);

    return ID_CHECK_SUCCESS;
  } catch (error) {
    const message = error instanceof Error ? error.message : SERVER_ERROR_MESSAGE;
    throw new Error(message);
  }
};
