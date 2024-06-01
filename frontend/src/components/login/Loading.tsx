import styled from "styled-components";
import loading from "../../img/indicator/loading.gif";

function Loading() {
  return (
    <BlurBackground>
      <LoadingImage src={loading} />
    </BlurBackground>
  );
}

const BlurBackground = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: white;
  opacity: 0.9;
  z-index: 10;
`;

const LoadingImage = styled.img`
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 11;
`;

export default Loading;
