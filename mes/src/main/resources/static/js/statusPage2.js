// 그래프 업데이트 함수
function updateGraph(data) {
    let progressCircles = document.querySelectorAll('.progress-circle');

    if (progressCircles.length === 0) {
        return; // No progress circles found, exit the function
    }

    progressCircles.forEach(function(progressCircle, index) {
        let jobId = data[index].jobId;
        let productName = data[index].productName;
        let startTime = data[index].startTime;
        let endTime = data[index].endTime;


        let span = progressCircle.querySelector('span');
        let valueBar = progressCircle.querySelector('.value-bar');

//현재시간
       let currentTime = new Date();
// let currentTime = new Date(2023, 5, 6, 14, 20); //임시


        //시작시간이 없을 때, 시작시간이 현재시간보다 미래일때
        if (startTime === null || startTime>currentTime) {
            span.textContent = '0%';
            progressCircle.classList.add('p0');
            return;
        }
        // 현재시간이 완료시간을 지났다면 (작업이 완료됨)
        if (endTime < currentTime) {

            span.textContent = '0%';
            progressCircle.classList.remove('over50');
            for (let i = 0; i <= 100; i++) {
                progressCircle.classList.remove('p' + i);
            }
            valueBar.style.transform = 'rotate(0deg)';
            span.classList.add('p0');

            //데이터 지우기
            data[index] = {jobId: '', productName: '', startTime: null, endTime: null};


            return;
        }

        // 퍼센테이지 계산
        let totalDuration = endTime - startTime;
        let currentDuration = currentTime - startTime;
        let percent = Math.min((currentDuration / totalDuration) * 100, 100);
        span.textContent = percent.toFixed(0) + '%';

        // 그래프값 변경
        progressCircle.classList.remove('over50');
        for (let i = 0; i <= 100; i++) {
            progressCircle.classList.remove('p' + i);
            span.classList.remove('p0');
        }
        progressCircle.classList.add('p' + Math.floor(percent));

        if (percent > 50) {
            progressCircle.classList.add('over50');
        }

        valueBar.style.transform = `rotate(${percent * 3.6}deg)`;


        //0%일때 레이블 회색
        if (span.textContent === '0%') {
            span.classList.add('p0'); // 'p0' 클래스 추가
        } else {
            span.classList.remove('p0'); // 'p0' 클래스 제거
        }


    });
}


function calGraph() {
    let data = [];

    for (let i = 1; i <= 10; i++) {
        let jobIdValue = document.getElementById('jobId' + i).textContent;
        let productNameValue = document.getElementById('productName'+i).textContent;

        let startTimeValue = null;
        let endTimeValue = null;


        //공정시작시간
        let dateString = document.getElementById('startTime'+i).textContent;
        let parts = dateString.split(' ');
        if(parts.length>1) {
            let dateParts = parts[0].split('/');
            let timeParts = parts[1].split(':');

            let year = parseInt(dateParts[0]) + 2000; // 연도를 4자리로 변환
            let month = parseInt(dateParts[1]) - 1; // 월은 0부터 시작하므로 1을 빼줌
            let day = parseInt(dateParts[2]);
            let hour = parseInt(timeParts[0]);
            let minute = parseInt(timeParts[1]);

            startTimeValue = new Date(year, month, day, hour, minute);

            //공정종료시간
            dateString = document.getElementById('endTime' + i).textContent;
            parts = dateString.split(' ');
            dateParts = parts[0].split('/');
            timeParts = parts[1].split(':');

            year = parseInt(dateParts[0]) + 2000; // 연도를 4자리로 변환
            month = parseInt(dateParts[1]) - 1; // 월은 0부터 시작하므로 1을 빼줌
            day = parseInt(dateParts[2]);
            hour = parseInt(timeParts[0]);
            minute = parseInt(timeParts[1]);

            endTimeValue = new Date(year, month, day, hour, minute);
        }

        data.push({
            jobId: jobIdValue,
            productName: productNameValue,
            startTime: startTimeValue,
            endTime: endTimeValue,
        });


    }
    //console.log(data);

    updateGraph(data);

    // 10초마다 데이터 업데이트
    setInterval(function() {
        // 그래프 업데이트
        updateGraph(data);
    }, 10000);

};
