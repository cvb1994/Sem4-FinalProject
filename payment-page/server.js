var express = require('express');
var app = express();
app.use(express.static('dist/payment-page'));
app.get('/', function (req, res,next) {
res.redirect('/');
});
app.listen(8080)