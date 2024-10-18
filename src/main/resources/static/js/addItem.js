/*const preview = document.querySelector("#previewImage");
const imageData = document.querySelector("#itemImage");

console.log("hello ");
imageData.onchange = e => {
    const [file] = imageData.files;
    if (file) {
        preview.src = URL.createObjectURL(file);
    }
}
*/
const viewBtn = document.querySelectorAll(".menuViewBtn");
viewBtn.forEach(function(btn) {
    btn.addEventListener("click", function() {
		
		const itemName = this.closest('tr').querySelector(".itemName").textContent;
		const itemCatagory = this.closest('tr').querySelector(".itemCatagory").textContent;
		const itemDes = this.closest('tr').querySelector(".itemDescription").textContent;
		const itemImage = this.closest('tr').querySelector(".itemImage").getAttribute("src");
		const itemPrice = this.closest('tr').querySelector(".itemPrice").textContent;
		
		const date = new Date();
		
		const day = date.getDay();
		const month = date.getMonth() + 1;
		const year = date.getFullYear();
		
		let currentDate = `${day}/${month}/${year}`;
        // Find the corresponding hidden input with the item ID in the same table row
        const itemId = this.closest('tr').querySelector('.itemId').value;
        console.log(itemId); // Log the itemId to ensure it's correct
		console.log(itemName);
        // Optionally, update modal content dynamically
        const modalTitle = document.querySelector("#exampleModalLabel");
        modalTitle.textContent = "Item ID: " + itemId; // Update the modal title with the item ID
		
		document.querySelector("#modalHeading").textContent = itemName;
		document.querySelector("#modalImage").setAttribute("src", itemImage);
		document.querySelector("#modalDescription").textContent = itemDes;
		document.querySelector("#modalDate").textContent = currentDate;
		document.querySelector("#modalItemPrice").textContent = itemPrice;
        // You can also update other parts of the modal with additional item details if needed
    });
});
    	