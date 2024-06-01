const KOREAN_TIME_OFFSET = 9 * 60 * 60 * 1000;

const dateUtils = {
  calculateTimeDifference(previousDate: Date, currentDate: Date) {
    const msPerMinute = 60 * 1000;
    const msPerHour = msPerMinute * 60;
    const msPerDay = msPerHour * 24;
    const timeDifference = currentDate.getTime() - previousDate.getTime();

    if (timeDifference < msPerMinute) return "방금 전";
    if (timeDifference < msPerHour) return `${Math.floor(timeDifference / msPerMinute)}분 전`;
    if (timeDifference < msPerDay) return `${Math.floor(timeDifference / msPerHour)}시간 전`;
    return `${Math.round(timeDifference / msPerDay)}일 전`;
  },

  parseTimeDifference(previousTime: string) {
    const currentTime = Date.now() - KOREAN_TIME_OFFSET;
    const previousDate = new Date(previousTime);
    const currentDate = new Date(currentTime);

    return this.calculateTimeDifference(previousDate, currentDate);
  },

  parseTimestampText(timestamp: string) {
    const time = new Date(timestamp);

    return `${time.getFullYear()}. ${time.getMonth() + 1}. ${time.getDate()}`;
  },

  isValidDateText(dateText: string | undefined) {
    const date = new Date(dateText || "");

    return !isNaN(date.getTime());
  },

  parseDateText(dateText: string | undefined) {
    const date = new Date(dateText || "");
    const timezoneOffset = date.getTimezoneOffset() * 60000;
    const convertedDate = new Date(date.getTime() - timezoneOffset);

    return convertedDate.toISOString();
  },
};

export default dateUtils;
