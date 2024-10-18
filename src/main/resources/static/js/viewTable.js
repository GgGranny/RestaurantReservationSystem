const edit_modal = document.querySelector("#exampleModal");
// if (edit_modal.classList.toggle("show")) {
//     edit_modal.style.cssText = `
//         padding-right: 0px !important;
//     `;
// }else {
//     edit_modal.classList.remove()
// }




//edit item
const editBtns = document.querySelectorAll(".editBtn");

editBtns.forEach(function(btn){
	btn.addEventListener("click", function() {
		const itemName = this.closest('tr').querySelector(".itemName").textContent;
		const itemCatagory = this.closest('tr').querySelector(".itemCatagory").textContent;
		const itemDes = this.closest('tr').querySelector(".itemDescription").textContent;
		const itemImage = this.closest('tr').querySelector(".itemImage").getAttribute("src");
		const itemPrice = this.closest('tr').querySelector(".itemPrice").textContent;
		
		console.log(itemPrice);
		
		console.log(itemCatagory);
		const itemId = this.closest('tr').querySelector(".itemId").value;
		console.log(itemId);
		
		document.querySelector("#nameToEdit").value= itemName;
		
		 let mappedCatagory = mapCategory(itemCatagory);
		document.querySelector("#itemCatagoryEdit").value= mappedCatagory;
		
		document.querySelector("#itemDescriptionEdit").value = itemDes;
		
		document.querySelector(".itemId").value = itemId;
		document.querySelector("#itemPriceEdit").value = itemPrice;
		
		const preview = document.querySelector("#previewImage");
		const imageData = document.querySelector("#itemImageEdit");
		
		if(imageData!= null) {
			console.log("hello ");
			imageData.onchange = e => {
			    const [file] = imageData.files;
			    if (file) {
			        preview.src = URL.createObjectURL(file);
			    }
			}
		}else {
			preview.setAttribute("src",itemImage);
		}
		
	})
});

function mapCategory(displayedCategory) {
    switch(displayedCategory.toLowerCase()) {
        case "Drinks":
            return "Drinks";
        case "Desert":
            return "Desert";
        case "Fast Food":
            return "Fast Food";
        case "Veg":
            return "Veg";
        case "non-veg":
            return "non-veg";
        default:
            return ""; // Return empty or default option if no match
    }
}



/////------------------view Table and edit --------------------------

const tableEditBtns = document.querySelectorAll("#tableEditBtn");
console.log("hello there");
tableEditBtns.forEach(function(btn) {
	btn.addEventListener("click", function() {
		console.log('hello');
		const tableNo = this.closest('tr').querySelector("#tableNo").textContent;
		const tableLocation = this.closest('tr').querySelector("#tableLocation").textContent;
		const noOfPeople = this.closest('tr').querySelector("#onOfPeople").textContent;
		console.log(tableNo);
		
		let id = this.closest('tr').querySelector("#tableId").value;
		
		console.log(id)
		document.querySelector("#tableIdUpdate").value = id;
		
		document.querySelector("#tableNoEdit").value = tableNo;
		document.querySelector("#tableLocationEdit").value = tableLocation;
		document.querySelector("#noOfPeopleEdit").value = noOfPeople;
	})
})


