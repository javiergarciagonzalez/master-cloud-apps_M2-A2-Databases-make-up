const express = require('express');
const router = express.Router();
const { User, toResponse, isValidEmail } = require('../models/user.js');
const Topic = require('../models/topic.js').Topic;
const mongoose = require('mongoose');

const INVALID_USER_ID_RESPONSE = { "error": "Invalid user id" };
const USER_NOT_FOUND_RESPONSE = { "error": "User not found" };

router.get('/', async (req, res) => {
    const allUsers = await User.find().exec();
    res.json(toResponse(allUsers));
});

router.get('/:id', async (req, res) => {
    const id = req.params.id;

    if (!mongoose.Types.ObjectId.isValid(id)) {
        return res.status(400).send(INVALID_USER_ID_RESPONSE);
    }

    const user = await User.findById(id);
    if (!user) {
        return res.status(404).send(USER_NOT_FOUND_RESPONSE);
    }

    res.json(toResponse(user));

});

router.post('/', async (req, res) => {

    const result = await User.find({ userName: req.body.userName }).exec();
    if (result.length > 0) {
        return res.status(409).send({ "error": "Already exists a user with that userName" });
    }

    const user = new User({
        userName: req.body.userName,
        email: req.body.email
    });

    try {

        const savedUser = await user.save();
        res.json(toResponse(savedUser));

    } catch (error) {
        console.log(error);
        res.status(400).send(error);
    }
});

router.patch('/:id', async (req, res) => {
    const id = req.params.id;

    if (!mongoose.Types.ObjectId.isValid(id)) {
        return res.status(400).send(INVALID_USER_ID_RESPONSE);
    }

    if (!isValidEmail(req.body.email)) {
        return res.status(400).send({ "error": "Invalid email" });
    }

    const user = await User.findById(id);
    if (!user) {
        return res.status(404).send(USER_NOT_FOUND_RESPONSE);
    }

    user.email = req.body.email
    const updatedUser = await user.save();
    res.json(toResponse(updatedUser));

});

router.delete('/:id', async (req, res) => {
    const id = req.params.id;

    if (!mongoose.Types.ObjectId.isValid(id)) {
        return res.status(400).send(INVALID_USER_ID_RESPONSE);
    }

    const user = await User.findById(id);
    if (!user) {
        return res.status(404).send(USER_NOT_FOUND_RESPONSE);
    }

    const result = await Topic.find({ "posts": { $elemMatch: { "user": id } } }).exec();
    if (result.length > 0) {
        return res.status(409).send({ "error": "Can't delete user because has associated posts" });
    }

    await User.findByIdAndDelete(id);

    res.json(toResponse(user));
});

router.get('/:id/posts', async (req, res) => {
    const id = req.params.id;

    if (!mongoose.Types.ObjectId.isValid(id)) {
        return res.status(400).send(INVALID_USER_ID_RESPONSE);
    }

    const user = await User.findById(id);
    if (!user) {
        return res.status(404).send(USER_NOT_FOUND_RESPONSE);
    }

    const userPosts = await Topic.aggregate([
        { $unwind: "$posts" },
        { $match: { "posts.user": user._id } },
        {
            $project: {
                "_id": 0,
                "id": "$posts._id",
                "topicId": "$_id",
                "post": "$posts.post",
                "title": "$posts.title"
            }
        },
    ]);

    res.json(userPosts);

});

module.exports = router;