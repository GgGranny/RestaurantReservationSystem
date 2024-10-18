/**
 * 
 */

 /*const dropdowns = document.querySelectorAll(".select-Time");

function generateTimeIntervals() {
    const intervals = [];
    let startTime = new Date();
    
    // Set the start time to 00:00 (midnight)
    startTime.setHours(0, 0, 0, 0); 
    
    // Loop through 24 hours, generating intervals of 30 minutes
    for (let i = 0; i < 48; i++) { // 48 intervals of 30 minutes in 24 hours
       let hours = startTime.getHours();
        let minutes = startTime.getMinutes().toString().padStart(2, '0');
        
        // Determine AM or PM
        let period = hours >= 12 ? 'PM' : 'AM';
        
        // Convert to 12-hour format
        let twelveHourFormat = hours % 12;
        twelveHourFormat = twelveHourFormat ? twelveHourFormat : 12; // If 0, make it 12

        intervals.push(`${twelveHourFormat}:${minutes} ${period}`);

        // Add 30 minutes to the time
        startTime.setMinutes(startTime.getMinutes() + 30);
    }

    return intervals;
}
*/
/*const timeIntervals = generateTimeIntervals();
const names = ["sunday_open", "sunday_close", "monday_open", "monday_close", "tuesday_open", "tuesday_close", "wednesday_open", "wednesday_close", "thursday_open", "thursday_close", "friday_open", "friday_close", "saturday_open", "saturday_close"];
let i = 0;
// Loop through each dropdown and append options
dropdowns.forEach(dropdown => {
    let ids = dropdown.id;
    
    // Set name attribute for "open" and "close"
    if (ids.includes("open")) {
        dropdown.setAttribute("name", names[i]);
    } else if (ids.includes("close")) {
        dropdown.setAttribute("name", names[i]);
    }
    i++;
});*/

// Generate time intervals
/*timeIntervals.forEach(time => {
    // Loop through each dropdown and append options
    dropdowns.forEach(dropdown => {
        const option = document.createElement("option");
        option.textContent = time;
        option.value = time;
        dropdown.appendChild(option);
    });
});*/
// Set default selected times
/*document.querySelectorAll('.select-Time').forEach(dropdown => {
    const defaultTime = dropdown.getAttribute('data-default');
    if (defaultTime) {
        dropdown.value = defaultTime;
    }
});*/
