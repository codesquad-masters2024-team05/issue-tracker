const SERVER = process.env.REACT_APP_SERVER;

export interface LabelContent {
  labelName: string;
  description: string;
  textColor: string;
  bgColor: string;
}

const SERVER_ERROR_MESSAGE = "서버 연결에 실패하였습니다.";

export const sendLabelsRequest = async () => {
  try {
    const response = await fetch(`${SERVER}/labels`, { credentials: "include" });

    if (!response.ok) throw new Error(SERVER_ERROR_MESSAGE);

    return response.json();
  } catch (error) {
    const message = error instanceof Error ? error.message : SERVER_ERROR_MESSAGE;
    throw new Error(message);
  }
};

export const postNewLabel = async (labelContent: LabelContent) => {
  try {
    const request = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      credentials: "include" as RequestCredentials,
      body: JSON.stringify(labelContent),
    };
    const response = await fetch(`${SERVER}/label`, request);

    if (!response.ok) throw new Error(SERVER_ERROR_MESSAGE);

    return response;
  } catch (error) {
    const message = error instanceof Error ? error.message : SERVER_ERROR_MESSAGE;
    throw new Error(message);
  }
};

export const sendPutLabelRequest = async (labelId: number, labelContent: LabelContent) => {
  try {
    const request = {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      credentials: "include" as RequestCredentials,
      body: JSON.stringify(labelContent),
    };
    const response = await fetch(`${SERVER}/label/${labelId}`, request);

    if (!response.ok) throw new Error(SERVER_ERROR_MESSAGE);

    return response;
  } catch (error) {
    const message = error instanceof Error ? error.message : SERVER_ERROR_MESSAGE;
    throw new Error(message);
  }
};

export const sendDeleteLabelRequest = async (labelId: number) => {
  try {
    const request = {
      method: "DELETE",
      credentials: "include" as RequestCredentials,
    };
    const response = await fetch(`${SERVER}/label/${labelId}`, request);

    if (!response.ok) throw new Error(SERVER_ERROR_MESSAGE);

    return response;
  } catch (error) {
    const message = error instanceof Error ? error.message : SERVER_ERROR_MESSAGE;
    throw new Error(message);
  }
};
