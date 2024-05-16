window.onload = () => {
    MozAtesPoliceApp.setValidateMsg(document);
}

// element.hasClass('className');
HTMLElement.prototype.hasClass = function(className) {
    return (' ' + this.className + ' ').indexOf(' ' + className + ' ') > -1;
};

/**
 	node.empty();
 */
Node.prototype.empty = function(){
    this.innerHTML = "";
}

// 숫자 3자리수 마다 comma
function numberComma(number) {
	if(number == null) return 0;
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

// 현재 날짜와 시간을 가져오는 함수
function getCurrentDateTime(date , formatType) {
    // YYYY-MM-DDTHH:MM 형식으로 포맷팅
    const formattedDate = date.getFullYear() + '-' + ('0' + (date.getMonth() + 1)).slice(-2) + '-' + ('0' + date.getDate()).slice(-2);
    const formattedTime = ('0' + date.getHours()).slice(-2) + ':' + ('0' + date.getMinutes()).slice(-2);
    
    switch(formatType){
		case 'datetime-local':
			return formattedDate + 'T' + formattedTime;
		default:
			return formattedDate
	}
}

//스크롤 푸터픽스메뉴 이벤트
window.onload = function(){
	let fixMenu = document.getElementById('fixed-menu');
	let lastTouchY = 0;
	let isScrolling = false;

	window.addEventListener('touchstart', function(event) {
		lastTouchY = event.touches[0].clientY;
		isScrolling = false;
	});

	window.addEventListener('touchmove', function(event) {
		let currentY = event.touches[0].clientY;
		let deltaY = currentY - lastTouchY;
		lastTouchY = currentY;
		
		if(fixMenu){
			if (deltaY > 0) {
				fixMenu.style.bottom="0px";
				fixMenu.style.transition="0.5s";
			} else {
				fixMenu.style.bottom="-70px";
				fixMenu.style.transition="0.5s";
			}
		}
		
		isScrolling = true;
	});
}

// 생년월일 유효성 keyup 이벤트
function keyupDateCheck(event, pattern, separator) {
	// 패턴 및 구분자 유효성 검사
	if (!["yyyyMMdd", "ddMMyyyy", "MMddyyyy"].includes(pattern)) {
		console.log("keyupDateCheck() --> Invalid Date Pattern");
		event.target.value = '';
		return false;
	}
	if (separator.length > 1) {
		console.log("keyupDateCheck() --> Separator Length Too Long");
		event.target.value = '';
		return false;
	}

	const inputKey = event.key;
	if (inputKey == "Backspace" || inputKey == "Delete") {
		event.target.value = '';
		return false;
	}

	// 숫자 외 문자 제거
	let dateVal = event.target.value.replace(/\D/g, "");

	// MM과 dd값에 따라 0삽입
	switch (pattern) {
		case "yyyyMMdd":
			// day
			if (dateVal.length == 7 && dateVal.charAt(6) > 3) {
				dateVal = dateVal.slice(0, 6) + 0 + dateVal.slice(6);
			}
			// Month
			if (dateVal.length == 5 && dateVal.charAt(4) > 1) {
				dateVal = dateVal.slice(0, 4) + 0 + dateVal.slice(4);
			}
			break;
		case "ddMMyyyy":
			// day
			if (dateVal.length == 1 && dateVal > 3) {
				dateVal = 0 + dateVal;
			}
			// Month
			if (dateVal.length == 3 && dateVal.charAt(2) > 1) {
				dateVal = dateVal.slice(0, 2) + 0 + dateVal.slice(2);
			}
			break;
		case "MMddyyyy":
			// day
			if (dateVal.length == 3 && dateVal.charAt(2) > 3) {
				dateVal = dateVal.slice(0, 2) + 0 + dateVal.slice(2);
			}
			// Month
			if (dateVal.length == 1 && dateVal > 1) {
				dateVal = 0 + dateVal;
			}
			break;
	}

	// 패턴에 따른 날짜 유효성 검사
	const dayRegex = /^0[1-9]|[12]\d|3[01]$/;
	const monthRegex = /^0[1-9]|1[0-2]$/;
	const today = new Date();
	const yearToday = today.getFullYear();
	let year = "", month = "", day = "";

	let dayValidNum = 0, monthValidNum = 0, yearValidNum = 0;
	if (pattern == "ddMMyyyy") {
		dayValidNum = 6;
		monthValidNum = 2;
		yearValidNum = 4;
	} else if (pattern == "MMddyyyy") {
		dayValidNum = 4;
		monthValidNum = 4;
		yearValidNum = 4;
	}

	// day
	if (dateVal.length >= 8 - dayValidNum) {
		day = dateVal.slice(6 - dayValidNum, 8 - dayValidNum);
		if (!dayRegex.test(day)) {
			event.target.value = '';
			return false;
		}
	}

	// Month
	if (dateVal.length >= 6 - monthValidNum) {
		month = dateVal.slice(4 - monthValidNum, 6 - monthValidNum);
		if (!monthRegex.test(month)) {
			event.target.value = '';
			return false;
		}
		month -= 1; // month starts from 0
	}

	// year
	if (dateVal.length >= 4 + yearValidNum) {
		year = dateVal.slice(0 + yearValidNum, 4 + yearValidNum);
		if (year > yearToday) {
			event.target.value = '';
			return false;
		}
	}

	// 패턴에 따른 날짜 형식화
	let valLength = 0;
	let sliceIdx = 0;
	if (pattern == "yyyyMMdd") {
		valLength = 3;
		sliceIdx = 2;
	}

	if (dateVal.length > 1 + valLength) {
		dateVal = dateVal.slice(0, 2 + sliceIdx) + separator + dateVal.slice(2 + sliceIdx);
	}

	if (dateVal.length > 4 + valLength) {
		dateVal = dateVal.slice(0, 5 + sliceIdx) + separator + dateVal.slice(5 + sliceIdx);
	}

	// 날짜 유효성 검사

	if (dateVal.length > 9) {
		let finalRegex = "";
		switch (pattern) {
			case "yyyyMMdd": finalRegex = /^\d{4}(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])$/; break;
			case "ddMMyyyy": finalRegex = /^(0[1-9]|[12]\d|3[01])(0[1-9]|1[0-2])\d{4}$/; break;
			case "MMddyyyy": finalRegex = /^(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])\d{4}$/; break;
		}
		const dateCheck = new Date(year, month, day);

		if (dateCheck.getMonth() != month
			|| dateCheck.getDate() != day
			|| dateCheck.getFullYear() != year
			|| !(dateCheck < today)
			|| !finalRegex.test(dateVal.replace(/\D/g, ""))
			|| dateCheck === "Invalid Date") {
			event.target.value = '';
			return false;
		}
	}

	event.target.value = dateVal;
}