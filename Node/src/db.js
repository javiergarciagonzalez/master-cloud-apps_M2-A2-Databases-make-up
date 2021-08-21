const mongoose = require('mongoose');
const url = "mongodb://localhost:27017/topicsDB";
const User = require('./models/user.js').User;
const Topic = require('./models/topic.js').Topic;


async function connect() {

    await mongoose.connect(url, {
        useUnifiedTopology: true,
        useNewUrlParser: true,
        useFindAndModify: false
    });

    console.log("Connected to Mongo");

    await init();
}

async function disconnect() {
    await mongoose.connection.close();
    console.log("Disconnected from MongoDB")
}

async function init() {

    console.log('Initializing database');

    console.log('Populating database with users');

    await User.deleteMany({});

    await new User({
        _id: new mongoose.Types.ObjectId("5fda3234e9e3fd53e3907bed"),
        userName: "user1",
        email: "user1@email.es"
    }).save();

    await new User({
        _id: new mongoose.Types.ObjectId("5fda3234e9e3fd53e3907bef"),
        userName: "user2",
        email: "user2@email.es"
    }).save();

    console.log('Populating database with topics');

    await Topic.deleteMany({});

    await new Topic({
        _id: new mongoose.Types.ObjectId("5fda3234e9e3fd53e3907bf0"),
        title: "Topic 1 title",
        summary: "Topic 1 summary",
        author: "Topic 1 author",
        publisher: "Topic 1 publisher",
        publicationYear: 1992
    }).save();

    const topic2 = await new Topic({
        _id: new mongoose.Types.ObjectId("5fda350d3749aa4832165b84"),
        title: "Topic 2 title",
        summary: "Topic 2 summary",
        author: "Topic 2 author",
        publisher: "Topic 2 publisher",
        publicationYear: 2006
    }).save();

    console.log('Populating database with posts');

    topic2.posts.push({
        _id: new mongoose.Types.ObjectId("5fdb4812df5c2555a401b6da"),
        post: "Topic 2 post 1 from user 1",
        title: 'Title 1',
        user: new mongoose.Types.ObjectId("5fda3234e9e3fd53e3907bed")
    });
    topic2.posts.push({
        _id: new mongoose.Types.ObjectId("5fdb4812df5c2555a401b6db"),
        post: "Topic 2 post 2 from user 1",
        title: 'Title 2',
        user: new mongoose.Types.ObjectId("5fda3234e9e3fd53e3907bed")
    });

    await topic2.save();

    console.log('Database initialized');
}

module.exports = { connect, disconnect }