const express = require("express");
const http = require("http");

const app = express();
const port = 4200;

app.use(express.static(__dirname + "/dist/frontend/"));

app.get("/*", (req, res) => {
  res.sendFile(__dirname + "/dist/frontend/index.html");
});

http.createServer(app).listen(port, () => {
  console.log(`Server listening on the port ${port}`);
});
