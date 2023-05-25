function updateTime() {
    var currentTime = new Date();
    var hours = currentTime.getHours();
    var minutes = currentTime.getMinutes();
    var seconds = currentTime.getSeconds();

    var timeString = hours.toString().padStart(2, '0') + ':' + minutes.toString().padStart(2, '0') + ':' + seconds.toString().padStart(2, '0');

    document.getElementById('current-time').textContent = timeString;
}

function createCalendar(year, month) {
    var calendar = document.getElementById("calendar");
    calendar.innerHTML = ""; // 기존의 달력 내용을 초기화합니다.

    var currentDate = new Date(year, month);
    var currentMonth = currentDate.getMonth();
    var currentYear = currentDate.getFullYear();

    // 달력 헤더 생성
    var header = document.createElement("div");
    header.classList.add("calendar-header");
    header.textContent = currentYear + "년 " + (currentMonth + 1) + "월";
    calendar.appendChild(header);

    // 요일 레이블 생성
    var daysOfWeek = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
    var dayLabels = document.createElement("div");
    dayLabels.classList.add("calendar-days");
    for (var i = 0; i < daysOfWeek.length; i++) {
        var dayLabel = document.createElement("div");
        dayLabel.classList.add("calendar-day");
        dayLabel.textContent = daysOfWeek[i];
        dayLabels.appendChild(dayLabel);
    }
    calendar.appendChild(dayLabels);

    var firstDayOfMonth = new Date(currentYear, currentMonth, 1).getDay();
    var daysInMonth = new Date(currentYear, currentMonth + 1, 0).getDate();

    var date = 1;
    for (var i = 0; i < 6; i++) {
        var weekRow = document.createElement("div");
        weekRow.classList.add("calendar-week");
        for (var j = 0; j < 7; j++) {
            var dayCell = document.createElement("div");
            dayCell.classList.add("calendar-day");
            if (i === 0 && j < firstDayOfMonth) {
                dayCell.classList.add("prev-month");
                var prevMonthDays = new Date(currentYear, currentMonth, 0).getDate();
                dayCell.textContent = prevMonthDays - (firstDayOfMonth - j - 1);
            } else if (date > daysInMonth) {
                dayCell.classList.add("next-month");
                dayCell.textContent = date - daysInMonth;
                date++;
            } else {
                if (date === new Date().getDate() && currentMonth === new Date().getMonth() && currentYear === new Date().getFullYear()) {
                    dayCell.classList.add("today");
                }
                dayCell.textContent = date;
                date++;
            }
            weekRow.appendChild(dayCell);
        }
        calendar.appendChild(weekRow);
    }
    updateTime();
}

window.onload = function() {
    var currentDate = new Date();
    var currentYear = currentDate.getFullYear();
    var currentMonth = currentDate.getMonth();

    createCalendar(currentYear, currentMonth);
    setInterval(updateTime, 1000);
};
