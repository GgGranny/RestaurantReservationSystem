/*$(document).ready(function() {
     fetch('/api/items')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
        .then(items => {
            displayRestaurants(items);
        })
        .catch(error => {
            console.error('Error fetching data:', error);
        });
        
    });
    
     function displayRestaurants(restaurants) {
        const searchResultsDiv = document.getElementById('searchResults').querySelector('ul');
        searchResultsDiv.innerHTML = ''; // Clear previous results

        restaurants.forEach(restaurant => {
            const listItem = document.createElement('li');
            const link = document.createElement('a');
            const div = document.createElement('div');
            const img = document.createElement('img');
            img.src = `data:image/png;base64,${restaurant.itemImage}`; // Set the image URL
            console.log(restaurant.itemImage);
            img.alt = restaurant.name; // Set the alt text
            img.style.width = '50px'; // Set styles
            img.style.height = '50px';

            const span = document.createElement('span');
            span.textContent = restaurant.name; // Use the appropriate property for the name

            div.appendChild(img);
            div.appendChild(span);
            link.appendChild(div);
            listItem.appendChild(link);
            searchResultsDiv.appendChild(listItem);
        });
    }*/
