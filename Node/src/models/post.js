const mongoose = require('mongoose');
const User = require('./user.js');

const postSchema = new mongoose.Schema({
    post: {
        type: String,
        required: [true, 'Post is mandatory']
    },
    title: {
        type: String,
        min: [0, 'Title must be at least 0'],
        max: [30, 'Title must be less or equals than 30'],
        required: [true, 'Title is mandatory']
    },
    //Other alternative is just put here the userName and mail for the user to avoid lookup or populate when getting the post. But when a change is performed in user, these values have to be updated in every post of the database
    user: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
        required: [true, 'User is mandatory']
    }
});

function toResponse(document) {

    if (document instanceof Array) {
        return document.map(elem => toResponse(elem));
    } else {
        let response = document.toObject({ versionKey: false });
        response.id = response._id.toString();
        delete response._id;
        response.user = User.toResponse(document.user)
        delete response.user.id
        return response;
    }
}

module.exports = { postSchema, toResponse }
