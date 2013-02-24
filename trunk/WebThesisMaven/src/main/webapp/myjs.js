function submit_offer() {
    
    var name = document.form1.name;
    var description = document.form1.description;
    var price = document.form1.price;
    var quantity = document.form1.quantity;
    var id_company = document.form1.id_company;
    
    if (name.value == "" || description.value == "" || price.value == "" || quantity.value == ""){
        alert('One of fields is blank');
        return false;
    } else if (isNaN(price.value)) {
        alert('Price must be a number');
        return false;   
    } else if (isNaN(quantity.value)) {
        alert('Quantity must be a number');
        return false;
    } else if (price.value < 0) {
        alert('Price must be positive or 0');
        return false;
    } else if (quantity.value <= 0) {
        alert('Quantity must be positive');
        return false;
    } else {
        return true;
    }
}


function submit_company() {
    
    var name = document.form2.name;
    var email = document.form2.email;
    var phone = document.form2.phone;
    
    if (name.value == "" || email.value == "" || phone.value == ""){
        alert('One of fields is blank');
        return false;
    } else if (isNaN(phone.value)) {
        alert('Phone number must be a number');
        return false;   
    } else {
        return true;
    }
}
    




