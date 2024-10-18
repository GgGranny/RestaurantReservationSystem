

let selectedTable; 


document.querySelectorAll('.days button').forEach(button => {
    button.addEventListener('click', function () {
        document.querySelectorAll('.days button').forEach(btn => btn.classList.remove('active'));
        this.classList.add('active');
    });
});


const errorMessage = document.createElement('div');
errorMessage.style.color = 'red';
errorMessage.style.display = 'none'; // Initially hidden
document.body.appendChild(errorMessage);

// You can expand this for changing months, etc.


const shapes = ["table-round", "table-square", "table-rectangle"];
const tables = document.querySelectorAll(".table");

tables.forEach(function(table) {
	const randomIndex = Math.floor(Math.random() * shapes.length);
    
    // Assign the random shape class to the table
    table.classList.add(shapes[randomIndex]);
})

document.querySelectorAll('.table').forEach(table => {
    table.addEventListener('click', function () {
        // Check if this table is already selected
        const isSelected = this.classList.contains('selected');

        // Remove the selected class from all tables
        document.querySelectorAll('.table').forEach(t => t.classList.remove('selected'));

        // If the clicked table was not selected, add the selected class
        if (!isSelected) {
            const tableIdElement = this.querySelector('#tableId');
            if (tableIdElement) {
                console.log(tableIdElement.value);
                selectedTable = tableIdElement.value; // Ensure selectedTable is declared properly
            }
            this.classList.add('selected');
        }else {
			selectedTable = null;
		}
    });
});

//Calander Js
const monthYearLabel = document.getElementById("month-year");
const datesContainer = document.querySelector(".dates");
const prevButton = document.getElementById("prev");
const nextButton = document.getElementById("next");
const btn = document.getElementById("btn");
const days = ["sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "Saturday"];

let currentMonth = new Date().getMonth();
let currentYear = new Date().getFullYear();
const today = new Date();

const daysInMonth = (month, year) => new Date(year, month + 1, 0).getDate();

let selectedDay;
let selectedMonth;
let selectedTime;
let dayNumber;

let restaurantId = document.querySelector("#resId").value;
const populateDates = (month, year) => {
    datesContainer.innerHTML = "";
    const totalDays = daysInMonth(month, year);
    const firstDay = new Date(year, month, 1).getDay(); // Get day of the week for the 1st date

    // Create blank spaces for days before the 1st of the month
    for (let i = 0; i < firstDay; i++) {
        const blankDate = document.createElement("div");
        blankDate.classList.add("date", "blank");
        datesContainer.appendChild(blankDate);
    }

    // Add dates and disable previous month dates
    for (let i = 1; i <= totalDays; i++) {
        const date = document.createElement("div");
        date.classList.add("date");
        date.textContent = i;

        const currentDate = new Date(year, month, i);

        // Disable all days of previous months
        if (year < today.getFullYear() || (year === today.getFullYear() && month < today.getMonth())) {
            date.classList.add("disabled");
        } else if (currentDate.getDate() < today.getDate() && (year === today.getFullYear() && month === today.getMonth())) {
            // Disable past days in the current month
            date.classList.add("disabled");
        } else {
            date.addEventListener("click", () => {
				selectedMonth = currentMonth;
				selectedDay = i;
				let now = new Date(`${year}-${month + 1}-${i}`);
                console.log(days[now.getDay()] + now.getDay());
                dayNumber = now.getDay();
                
                document.querySelectorAll(".date").forEach(d => d.classList.remove("selected"));
                date.classList.add("selected");
               $.ajax({
                    url: '/user/saveData1',  // This is the Spring Boot endpoint
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify({
                        year: currentYear,
                        month: selectedMonth + 1,  // Add 1 since JS months are 0-indexed
                        day: selectedDay,
                        number: dayNumber,
                        resId: restaurantId,
	         			tableId: selectedTable
                    }),
                    success: function(response) {
                        console.log('Date successfully sent to the server:', response);
                        renderTables(response.checkedTables);
						populateButtons(response.timeIntervals);
                    },
                    error: function(error) {
                        console.error('Error sending date to server:', error);
                    }
                    });
                    
                    
                    
                    
                    
            });
        }

        datesContainer.appendChild(date);
    }
};

//code for retriving the table data 
window.onload = function() {
	const date = new Date();
	console.log(date.getDate()); 
	console.log(date.getDay());
	console.log(date.getMonth());
	selectedDay = date.getDate();
	selectedMonth = date.getMonth();
	dayNumber = date.getDay();
	
	let d = document.querySelectorAll(".date");
	d.forEach(dt=> {
		if(dt.textContent.includes(selectedDay)) {
			dt.classList.add("selected");
		}
	})
	
	
	console.log(selectedDay);
	const data = {
			year: currentYear,
	         month: selectedMonth + 1,  // Add 1 since JS months are 0-indexed
	         day: selectedDay,
	         number: dayNumber,
	         resId: restaurantId,
	         tableId: selectedTable
		}
	
	
	$.ajax({
		url: '/user/saveData1',
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(response) {
			console.log("data sent successfully" + response);
			
			renderTables(response.checkedTables);
			populateButtons(response.timeIntervals);
			
		},
		error: function(xhr, status, error) {
			console.log("error : " + status + " " + error);
		}
		
		
	});
	
};

function showError(message) {
    errorMessage.textContent = message;
    errorMessage.style.display = 'block'; // Show error message
}

function renderTables(checkedTable) {
	tables.forEach(t => {
        let tId = t.querySelector('input[name="tableId"]').value;

        let tableData = checkedTable.find(ct => ct.tableId == tId);
        console.log(tableData);
        if(tableData) {
			if(tableData.status == "user-Reserved") {
				console.log(tableData.status);
				t.style.backgroundColor = "#4CAF50";
				t.querySelector('input[name="tableId"]').disabled = true;
			} else if (tableData.status === "reserved") {
                t.style.backgroundColor = "#FF7043"; 
                t.querySelector('input[name="tableId"]').disabled = true;
            } else {
                t.style.backgroundColor = "";      
                t.querySelector('input[name="tableId"]').disabled = false; 
            }
		}else {
            // If no reservation data is found, reset table state
            t.style.backgroundColor = "";  // Default background (normal state)
            t.querySelector('input[name="tableId"]').disabled = false;  // Enable input
        }
        
		console.log(tId);
    });
   
}

function populateButtons(intervals) {
    let timeContainer = document.querySelector(".time-options");
    if (!timeContainer) {
        return;
    }
    timeContainer.textContent = "";

    const currentTime = new Date(); // Get the current time

    intervals.forEach(interval => {
        const timeBtn = document.createElement("button");
        timeBtn.value = interval;
        timeBtn.textContent = interval;
        timeBtn.className = "timeBtns";

        // Convert the interval to a Date object for comparison
        const [time, modifier] = interval.split(" ");
        let [hours, minutes] = time.split(":");
        hours = parseInt(hours, 10);
        minutes = parseInt(minutes, 10);

        // Convert to 24-hour format
        if (modifier === "PM" && hours !== 12) {
            hours += 12; // Convert PM to 24-hour format
        } else if (modifier === "AM" && hours === 12) {
            hours = 0; // Midnight case
        }

        const intervalTime = new Date();
        intervalTime.setHours(hours, minutes, 0, 0); // Set the hours and minutes

        // Disable button if the interval time has already passed
        if (intervalTime < currentTime) {
            timeBtn.disabled = true; // Disable the button
            timeBtn.classList.add("disabled"); // Add a disabled class for styling
        }

        timeContainer.appendChild(timeBtn);
    });

    // Event listeners for button clicks
    document.querySelectorAll('.time-options button').forEach(button => {
        button.addEventListener('click', function () {
            document.querySelectorAll('.time-options button').forEach(btn => btn.classList.remove('active'));
            selectedTime = this.value;
            this.classList.add('active');
        });
    });
}

console.log(document.querySelectorAll(".timeBtns"));



const updateCalendar = () => {
    const monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    monthYearLabel.textContent = `${monthNames[currentMonth]} ${currentYear}`;
    populateDates(currentMonth, currentYear);
};

prevButton.addEventListener("click", () => {
    currentMonth--;
    if (currentMonth < 0) {
        currentMonth = 11;
        currentYear--;
    }
    updateCalendar();
});

nextButton.addEventListener("click", () => {
    currentMonth++;
    if (currentMonth > 11) {
        currentMonth = 0;
        currentYear++;
    }
    updateCalendar();
});

// Initial calendar load
updateCalendar();


const nextBtn = document.querySelector("#nextBtn");
document.querySelector("#nextBtn").addEventListener('click', function() {
    history.pushState({page: 2}, "Reservation Step 2", "/reservation2");
});




nextBtn.addEventListener('click',function() {
	 if (selectedTable == null || selectedDay == null || selectedTime == null) {
        showError("Please select a table, date, and time before proceeding.");
        return; // Prevent proceeding if any selection is missing
    }
	let data = {
			year: currentYear,
	         month: selectedMonth + 1,  // Add 1 since JS months are 0-indexed
	         day: selectedDay,
	         time: selectedTime,
	         number: dayNumber,
	         tableId: selectedTable
	}
	$.ajax({
		url: '/user/reservation1',
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function(response) {
			console.log("data sent successfully" + response);
			let queryParams = `?year=${currentYear}&month=${selectedMonth + 1}&day=${selectedDay}&time=${selectedTime}&number=${dayNumber}&tableId=${selectedTable}`;
			window.location.href="/user/reservation2" + queryParams;
		},
		error: function(xhr, status, error) {
			console.log("error : " + status + " " + error);
		}
	});
});
