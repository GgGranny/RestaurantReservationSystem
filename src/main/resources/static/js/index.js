document.querySelector('[aria-labelledby="notificationDropDown"]').addEventListener('click', function () {
    document.querySelector('.dropdown-menu').classList.toggle('animated--grow-in');
});


const toggler = document.querySelector(".toggler-btn");

toggler.addEventListener("click", function () {
    const sidebar = document.querySelector("#sidebar");
    const isToggled = sidebar.classList.toggle("collapsed");
    const width = window.innerWidth;
	console.log('btn clicked');
    if (width <= 768) {
        if (isToggled) {
            console.log("Sidebar is toggled and window width is less than or equal to 768px");

            document.querySelector(".mail-dropdown").style.cssText = `
                left: -8rem !important;
                width: 260px !important;
            `;
            document.querySelector(".notification-dropdown").style.cssText = `
                left: -6rem !important;
                height: 350px;
                width: 260px !important;
            `;
        } else {
            console.log("Sidebar is not toggled and window width is less than or equal to 768px");

            document.querySelector(".mail-dropdown").style.cssText = `
                left: -11rem !important;
                width: 21rem !important;
            `;
            document.querySelector(".notification-dropdown").style.cssText = `
                left: -8rem !important;
                width: 21rem !important;
            `;

            document.querySelector(".nav-search-icon").addEventListener("click", function () {
                sidebar.classList.remove("collapsed");
            });
            // document.querySelector(".search-for-sm-window").style.cssText = `
            // width: 31vw !important;
            // `;
        }
    } else {
        // Apply styles or leave default for screens larger than 768px
        console.log("Screen width is greater than 768px");

        document.querySelector(".mail-dropdown").style.cssText = `
            width: 20rem !important;
            position: absolute !important;
            right: 0 !important;
        `;
        document.querySelector(".notification-dropdown").style.cssText = `
            width: 20rem !important;
            position: absolute !important;
            right: 0 !important;
        `;
    }
});




