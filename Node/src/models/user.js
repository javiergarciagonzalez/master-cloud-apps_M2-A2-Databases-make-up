const mongoose = require('mongoose');

const isValidEmail = function(email) {
    const mailformat = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

    return typeof email == 'string' && email != '' && mailformat.test(email);
}

const userSchema = new mongoose.Schema({
    userName: {
        type: String,
        required: [true, 'userName is mandatory'],
        unique: true
    },
    email: {
        type: String,
        validate: {
            validator: isValidEmail,
            message: props => `${props.value} is not a valid email`
        },
        required: [true, 'Email is mandatory']
    }
});

const User = mongoose.model('User', userSchema);

function toResponse(document) {

    if (document instanceof Array) {
        return document.map(elem => toResponse(elem));
    }

    let response = document.toObject({ versionKey: false });
    response.id = response._id.toString();
    delete response._id;
    
    return response;
}

module.exports = { User, userSchema, isValidEmail, toResponse }
