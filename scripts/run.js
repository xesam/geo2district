let fs = require("fs");
let transform = require("./prepare.js");

let argv = process.argv;
let in_dir = argv[2];
let out_dir = argv[3];
let skeleton = argv[4];

transform.run(skeleton, in_dir, out_dir);
