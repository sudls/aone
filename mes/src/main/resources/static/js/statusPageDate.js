document.addEventListener("DOMContentLoaded", function() {
// 시작 날짜와 종료 날짜의 input 요소를 가져옵니다.
    var startDateInput = document.getElementById("staSDate");
    var endDateInput = document.getElementById("staEDate");

// preWeek 버튼을 가져옵니다.
    var preWeekButton = document.getElementById("preWeek");
    preWeekButton.addEventListener("click", goToPreviousWeek);

// nextWeek 버튼을 가져옵니다.
    var nextWeekButton = document.getElementById("nextWeek");
    nextWeekButton.addEventListener("click", goToNextWeek);

// 현재 날짜를 가져옵니다.
    var currentDate = new Date();

// 페이지 로드 시 초기 기간을 설정합니다.
    setWeekRange(currentDate);

// 이전 주로 이동하는 함수
    function goToPreviousWeek() {
        var previousWeek = new Date(currentDate.getTime());
        previousWeek.setDate(previousWeek.getDate() - 7);

        // Adjust the previous week to start on Monday
        var previousMonday = new Date(previousWeek);
        previousMonday.setDate(previousMonday.getDate() - (previousMonday.getDay() + 6) % 7);

        setWeekRange(previousMonday);
    }

// 다음 주로 이동하는 함수
    function goToNextWeek() {
        var nextWeek = new Date(currentDate.getTime());
        nextWeek.setDate(nextWeek.getDate() + 7);

        // Adjust the next week to start on Monday
        var nextMonday = new Date(nextWeek);
        nextMonday.setDate(nextMonday.getDate() - (nextMonday.getDay() + 6) % 7);

        setWeekRange(nextMonday);
    }


// 시작 날짜와 종료 날짜 input 값을 설정하는 함수
    function setWeekRange(date) {
        currentDate = date;

        // 시작 날짜를 현재 주의 월요일로 설정합니다.
        var startOfWeek = date.getDate() - date.getDay() + (date.getDay() === 0 ? -6 : 1);
        var startOfWeekDate = new Date(date.setDate(startOfWeek));
        var formattedStartOfWeekDate = startOfWeekDate.toISOString().split("T")[0];
        startDateInput.value = formattedStartOfWeekDate;

        // 종료 날짜를 현재 주의 일요일로 설정합니다.
        var endOfWeek = startOfWeek + 6;
        var endOfWeekDate = new Date(date.setDate(endOfWeek));
        var formattedEndOfWeekDate = endOfWeekDate.toISOString().split("T")[0];
        endDateInput.value = formattedEndOfWeekDate;

        // 현재 주와 비교하여 nextWeek 버튼을 비활성화합니다.
        var currentWeek = getWeekNumber(new Date());
        var displayedWeek = getWeekNumber(currentDate);
        // 오늘 주가 표시된 주와 같은 주인 경우에만 nextWeek 버튼을 비활성화합니다.
        nextWeekButton.disabled = displayedWeek === (currentWeek+1);
        console.log("이거확인점 : "+displayedWeek );

        console.log("이거확인점 : "+currentWeek );
    }

// 주 번호를 가져오는 함수
    function getWeekNumber(date) {
        var oneJan = new Date(date.getFullYear(), 0, 1);
        var millisecondsInWeek = 604800000;
        return Math.ceil(((date - oneJan) / millisecondsInWeek) + oneJan.getDay() / 7);
    }
});