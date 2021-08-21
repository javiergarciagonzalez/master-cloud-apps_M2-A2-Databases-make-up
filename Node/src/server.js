const express = require('express');
const database = require('./db.js');
const topicsRouter = require('./routes/topicRouter.js');
const usersRouter = require('./routes/userRouter.js');
const app = express();

const port = process.env.PORT || 3000;

//Convert json bodies to JavaScript object
app.use(express.json());
app.use('/api/v1/topics', topicsRouter);
app.use('/api/v1/users', usersRouter);

async function main() {

    await database.connect();

    app.listen(port, () => {
        console.log(`Server listening on port ${port}!`);
    });

    process.on('SIGINT', () => {
        database.disconnect();
        console.log('Process terminated');
        process.exit(0);
    });
}

main();