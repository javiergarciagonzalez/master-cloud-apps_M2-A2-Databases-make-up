const express = require('express');
const router = express.Router();
const { Topic, toResponse: toResponseTopic } = require('../models/topic.js');
const User = require('../models/user.js').User;
const { toResponse: toResponsePost } = require('../models/post.js');
const mongoose = require('mongoose');

const INVALID_TOPIC_ID_RESPONSE = { "error": "Invalid topic id" };
const TOPIC_NOT_FOUND_RESPONSE = { "error": "Topic not found" }
const INVALID_POST_ID_RESPONSE = { "error": "Invalid post id" };
const POST_NOT_FOUND_RESPONSE = { "error": "Post not found" }

router.get('/', async (req, res) => {
    const allTopics = await Topic.find().exec();
    res.json(toResponseTopic(allTopics));
});

router.get('/:id', async (req, res) => {
    const { id } = req.params;

    if (!mongoose.Types.ObjectId.isValid(id)) {
        return res.status(400).send(INVALID_TOPIC_ID_RESPONSE);
    }

    // If userName and name is placed in the post, there is no need to populate.
    const topic = await Topic.findById(id).populate('posts.user');
    
    if (!topic) {
        return res.status(404).send(TOPIC_NOT_FOUND_RESPONSE);
    }

    res.json(toResponseTopic(topic));
});

router.post('/', async (req, res) => {

    const topic = new Topic({
        title: req.body.title,
        message: req.body.message,
        author: req.body.author,
    });

    await topic.save()
        .then(savedTopic => res.json(toResponseTopic(savedTopic)))
        .catch(error => {
            console.log(error);
            res.status(400).send(error);
        });

});


router.post('/:id/posts', async (req, res) => {
    const { id } = req.params;

    if (!mongoose.Types.ObjectId.isValid(id)) {
        return res.status(400).send(INVALID_TOPIC_ID_RESPONSE);
    }
    if (!req.body.userName) {
        return res.status(400).send({ "error": "User name is mandatory" });
    }

    const topic = await Topic.findById(id);
    if (!topic) {
        return res.status(404).send(TOPIC_NOT_FOUND_RESPONSE);
    }

    const user = await User.findOne({ userName: req.body.userName })
    if (!user) {
        return res.status(404).json({ "error": "User not found" });
    };

    topic.posts.push({
        title: req.body.title,
        post: req.body.post,
        user: user._id
    });

    await topic.save()
        .then(async savedTopic => {
            await savedTopic.populate(['posts', savedTopic.posts.length - 1, 'user'].join('.')).execPopulate();
            const post = savedTopic.posts[savedTopic.posts.length - 1];
            res.json(toResponsePost(post));
        })
        .catch(error => {
            console.log(error);
            res.status(400).send(error);
        });

});


router.delete('/:id/posts/:postId', async (req, res) => {
    const { id, postId  } = req.params;

    if (!mongoose.Types.ObjectId.isValid(id)) {
        return res.status(400).send(INVALID_TOPIC_ID_RESPONSE);
    }
    if (!mongoose.Types.ObjectId.isValid(postId)) {
        return res.status(400).send(INVALID_POST_ID_RESPONSE);
    }

    const topic = await Topic.findById(id).populate('posts.user');
    if (!topic) {
        return res.status(404).send(TOPIC_NOT_FOUND_RESPONSE);
    }

    const post = await topic.posts.id(postId);
    if (!post) {
        return res.status(404).send(POST_NOT_FOUND_RESPONSE);
    }

    post.remove();
    await topic.save();
    res.json(toResponsePost(post));

});

module.exports = router;