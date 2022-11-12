/* client side JavaScript for when a user creates a new account */
const form = document.getElementById('create_account');
form.addEventListener('submit', createAccount);

/* creates a new member account */
function createAccount() {
    /* collect values given by user */
    const fullName = document.getElementById('full_name').value;
    const dob = document.getElementsById('birth_date').value;
    const email = document.getElementsById('email').value;
    const phone = document.getElementsById('phone').value;
    const password = document.getElementsById('password').value;
    const confirmPassword = document.getElementsById('confirm_password').value;
    const avatar = document.getElementsByClassName('avatar').value;

    acceptablePassword(password, confirmPassword); /* verify password */




}

/* confirms that the password given is the same as confirm password value and meets criteria */
function acceptablePassword(password, confirmPassword) {
    let goodCapital = false;
    let goodSpecial = false;
    let goodNumber = false;

    /* password and confirm password is the same value */
    if (password != confirmPassword) {
        return false;
    }

    /* loop through the password given */
    for (let i = 0; i < password.length; i++) {
        let asciiValue = password.charCodeAt(i);

        if (asciiValue >= 65 && asciiValue <= 90) { /* if character is a capital letter */
            goodCapital = true;
        } else if (asciiValue == 33 || asciiValue == 35 || asciiValue == 36 || asciiValue == 64 
                    || asciiValue == 37 || asciiValue == 126 || asciiValue == 38) { /* else if character is a special character */
            goodSpecial = true;
        } else if (asciiValue >= 48 && asciiValue <= 57) { /* else if character is a number */
            goodNumber = true;
        }
    }

    /* given password meets criteria */
    if (password.length < 8) { /* if less than 8 characters long */
        alert("Must have 8 or more characters");
        return false;
    } else if (!goodCapital) { /* else if no captial letters used */
        alert("Must have at least one capital letter");
        return false;
    } else if (!goodSpecial) { /* else if no special charaters used */
        alert("Must have at least one special character (!, #, $, @, %, ~, &)");
        return false;
    } else if (!goodNumber) { /* else if no numbers used */
        alert("Must have at least one number");
        return false;
    }

    return true;
}

/* password show/hide toggle */
function showPassword() {
    var showPass = document.getElementById('password');    
    var showConfirm = document.getElementById('confirm_password');

    if (showPass.type === "password") { /* if currently hidden */
        showPass.type = "text";
        showConfirm.type = "text";
    } else { /* else currently shown */
        showPass.type = "password";
        showConfirm.type ="password";
    }
}