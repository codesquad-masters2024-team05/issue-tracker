const numberUtils = {
  parsePercentage(current: number, total: number) {
    return (!isNaN((current / total) * 100) && ((current / total) * 100).toFixed(0)) || "0";
  },
};

export default numberUtils;
