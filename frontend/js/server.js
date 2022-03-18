const express = require('express');
const path = require('path');

let initialPath = path.join(__dirname, "public");

const app = express();
app.use(express.static(initialPath))

app.get('/', (req, res) => {
    res.sendFile(path.join(initialPath, "home.html"));
})

app.listen("3000", () => {
    console.log("listening..............");
})