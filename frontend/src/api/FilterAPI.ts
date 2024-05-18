const SERVER = process.env.REACT_APP_SERVER;

const SERVER_ERROR_MESSAGE = "서버 연결에 실패하였습니다.";

export const sendFiltersRequest = async () => {
  try {
    const response = await fetch(`${SERVER}/filters`);

    if (!response.ok) throw new Error(SERVER_ERROR_MESSAGE);

    return response.json();
  } catch (error) {
    throw new Error(SERVER_ERROR_MESSAGE);
  }
}