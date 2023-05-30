
document.addEventListener("DOMContentLoaded", function() {
  // AJAX 호출 코드 작성
  $.ajax({
    url: "/status/facility-info",
    method: "GET",
    success: function(data) {
    console.log(JSON.stringify(data, null, 2)); // JSON 형태로 출력
    /*{
      "startTime": "2023-05-31T13:00:00.780303",
      "endTime": "2023-06-03T14:00:00.780303",
      "facilityId": "extraction_1",
      "productName": "양배추즙",
      "workOrderId": 12
    },*/

    for(let d of data){
    //날짜형식변경
    let startTime = new Date(d.startTime);
    let formattedStartTime = `${startTime.getFullYear().toString().slice(2)}/${(startTime.getMonth() + 1).toString().padStart(2, "0")}/${startTime.getDate().toString().padStart(2, "0")} ${startTime.getHours().toString().padStart(2, "0")}:${startTime.getMinutes().toString().padStart(2, "0")}`;
    let endTime = new Date( d.endTime);
    let formattedEndTime = `${endTime.getFullYear().toString().slice(2)}/${(endTime.getMonth() + 1).toString().padStart(2, "0")}/${endTime.getDate().toString().padStart(2, "0")} ${endTime.getHours().toString().padStart(2, "0")}:${endTime.getMinutes().toString().padStart(2, "0")}`;

    let currentTime = new Date();
    //     let currentTime = new Date(2023, 5, 6, 14, 20); //임시
    // console.log("fnfne :  "+startTime+","+currentTime + d.facilityId);
    if(startTime<=currentTime) {
        switch (d.facilityId) {
            case "extraction_1" :
                document.getElementById("jobId1").textContent = d.workOrderId;
                document.getElementById("productName1").textContent = d.productName;
                document.getElementById("startTime1").textContent = formattedStartTime;
                document.getElementById("endTime1").textContent = formattedEndTime;
                break;
            case "extraction_2" :
                document.getElementById("jobId2").textContent = d.workOrderId;
                document.getElementById("productName2").textContent = d.productName;
                document.getElementById("startTime2").textContent = formattedStartTime;
                document.getElementById("endTime2").textContent = formattedEndTime;
                break;
            case "mix_1" :
            case "mix_3" :
                document.getElementById("jobId3").textContent = d.workOrderId;
                document.getElementById("productName3").textContent = d.productName;
                document.getElementById("startTime3").textContent = formattedStartTime;
                document.getElementById("endTime3").textContent = formattedEndTime;
                break;
            case "mix_2" :
            case "mix_4" :
                document.getElementById("jobId4").textContent = d.workOrderId;
                document.getElementById("productName4").textContent = d.productName;
                document.getElementById("startTime4").textContent = formattedStartTime;
                document.getElementById("endTime4").textContent = formattedEndTime;
                break;
            case "pouch_1" :
                document.getElementById("jobId5").textContent = d.workOrderId;
                document.getElementById("productName5").textContent = d.productName;
                document.getElementById("startTime5").textContent = formattedStartTime;
                document.getElementById("endTime5").textContent = formattedEndTime;
                break;
            case "pouch_2" :
                document.getElementById("jobId6").textContent = d.workOrderId;
                document.getElementById("productName6").textContent = d.productName;
                document.getElementById("startTime6").textContent = formattedStartTime;
                document.getElementById("endTime6").textContent = formattedEndTime;
                break;
            case "liquid_stick_1" :
                document.getElementById("jobId7").textContent = d.workOrderId;
                document.getElementById("productName7").textContent = d.productName;
                document.getElementById("startTime7").textContent = formattedStartTime;
                document.getElementById("endTime7").textContent = formattedEndTime;
                break;
            case "liquid_stick_2" :
                document.getElementById("jobId8").textContent = d.workOrderId;
                document.getElementById("productName8").textContent = d.productName;
                document.getElementById("startTime8").textContent = formattedStartTime;
                document.getElementById("endTime8").textContent = formattedEndTime;
                break;
            case "inspection" :
                document.getElementById("jobId9").textContent = d.workOrderId;
                document.getElementById("productName9").textContent = d.productName;
                document.getElementById("startTime9").textContent = formattedStartTime;
                document.getElementById("endTime9").textContent = formattedEndTime;
                break;
            case "cooling" :
                document.getElementById("jobId10").textContent = d.workOrderId;
                document.getElementById("productName10").textContent = d.productName;
                document.getElementById("startTime10").textContent = formattedStartTime;
                document.getElementById("endTime10").textContent = formattedEndTime;
                break;

        }
        console.log(d.facilityId);
    }
        calGraph();
    }


    },
    error: function(xhr, status, error) {
      // AJAX 호출 실패 시 실행되는 코드
      console.error(error);
    }
  });
});
