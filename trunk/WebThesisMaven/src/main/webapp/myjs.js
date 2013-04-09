function submit_offer() {
    
    var name = document.add_offer.name;
    var price = document.add_offer.price;
    var quantity = document.add_offer.quantity;
    var minBuyQuantity = document.add_offer.minimal_buy;
    var day = document.add_offer.dob_day;
    var month = document.add_offer.dob_month;
    var year = document.add_offer.dob_year;
    
    if (name.value == ""){
        alert('Name field is blank');
        return false;
    } else if (quantity.value == "") {
        alert('Quantity field is blank');
        return false;
    } else  if (price.value == "") {
        alert('Price field is blank');
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
    } else if (Number(minBuyQuantity.value) > Number(quantity.value)) {
        alert('Minimal buy quantity should be less ten quantity');
        return false;
    } else if(isNaN(minBuyQuantity.value)){
        alert('Minimal buy quantity must be a number');
        return false;        
    }else if (isNaN(day.value)) {
        alert('Day must be a number');
        return false; 
    } else if (isNaN(month.value)) {
        alert('Month must be a number');
        return false; 
    } else if (isNaN(year.value)) {
        alert('Year must be a number');
        return false;
    } else {
        return true;
    }

}

function update_company() {
    
    var name = document.updateCompany.name;
    var phone = document.updateCompany.phone;
    
    if (name.value == "") {
        alert("Name field is blank")
        return false;
    } else if (phone.value == "") {
        alert("Phone number field is blank");
        return false;
    } else if (isNaN(phone.value)) {
        alert("Phone number must be a number");
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
        alert('Company name field is blank');
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
    } else if (password.value !== passwordVer.value ) {
        alert('Passwords dont match');
        return false;   
    } else if (username.value == "") {
        alert('Username is blank');
        return false;   
    } else if (password.value == "") {
        alert('Password field is blank');
        return false;   
    } else {
        return true;
    }
}

function change_password() {
    
    var oldPassword = document.change_password.old_password;
    var newPassword = document.change_password.new_password;
    var newPasswordVer = document.change_password.new_password_ver;
    
    if (oldPassword.value == "") {
        alert ("Old password field is blank");
        return false;
    } else if (newPassword.value == "") {
        alert ("New password field is blank");
        return false;
    } else if (newPasswordVer.value == "") {
        alert("New password verification field is blank");
        return false;       
    } else if (newPassword.value !== newPasswordVer.value) {
        alert ("New password fields dont match");
        return false;
    } else {
        return true;       
    }

}


function login() {
    
    var userName = document.login.userName;
    var password = document.login.pwd;
    
    if (userName.value == "") {
        alert ("Username field is blank");
        return false;
    } else if (password.value == "") {
        alert("Password field is blank");
        return false;
    } else {
        return true;       
    } 
}

function findOffer() {
    
    var minQuantity = document.findOffer.minQuantity;
    var maxQuantity = document.findOffer.maxQuantity;
    var minQuantityToBuy = document.findOffer.minQuantityToBuy;
    var maxQuantityToBuy = document.findOffer.maxQuantityToBuy;
    var minPrice = document.findOffer.minPrice;
    var maxPrice = document.findOffer.maxPrice;
     
    if (isNaN(minQuantity.value) || isNaN(maxQuantity.value) || 
        isNaN(minQuantityToBuy.value) || isNaN(maxQuantityToBuy.value) || 
        isNaN(minPrice.value) || isNaN(maxPrice.value)) {
        alert("All fields except search text must be a number");
        return false;
    } else {
        return true         
    }     
}
    




