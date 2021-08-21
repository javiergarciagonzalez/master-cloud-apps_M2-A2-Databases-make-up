const mongoose = require('mongoose');
const Post = require('./post.js');

const topicSchema = new mongoose.Schema({
    title: {
        type: String,
        required: [true, 'Title is mandatory']
    },
    message: String,
    author: {
        type: String,
        required: [true, 'Author is mandatory']
    },
    posts: [Post.postSchema]
});

const Topic = mongoose.model('Topic', topicSchema);

function toResponse(document) {

    if (document instanceof Array) {
        return document.map(elem => JSON.parse('{"id": "' + elem._id.toString() + '","title": "' + elem.title.toString() + '"}'));
    } else {
        let response = document.toObject({ versionKey: false });
        response.id = response._id.toString();
        response.posts = Post.toResponse(document.posts)
        delete response._id;
        return response;
    }
}

module.exports = { Topic, toResponse };
