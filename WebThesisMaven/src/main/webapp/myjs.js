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
    
    var name = document.add_company.name;
    var email = document.add_company.email;
    var phone = document.add_company.phone;
    var emailVer = document.add_company.emailVer;
    var password = document.add_company.pwd;
    var passwordVer = document.add_company.pwdVer;
    var username = document.add_company.usrname;
    
    if (name.value == ""){
        alert('Name field is blank');
        return false;
    } else if (email.value == "") {
        alert('Email field is blank');
        return false;   
    } else if (phone.value == "") {
        alert('Phone number field is blank');
        return false;   
    } else if (isNaN(phone.value)) {
        alert('Phone number must be a number');
        return false;   
    } else if (email.value !== emailVer.value) {
        alert('Emails dont match');
        return false;   
    }  else if (password.value !== passwordVer.value ) {
        alert('Passwords dont match');
        return false;   
    }  else if (username.value == "") {
        alert('Username is blank');
        return false;   
    } else if (password.value == "") {
        alert('Password field is blank');
        return false;   
    } else {
        return true;
    }
}
    




