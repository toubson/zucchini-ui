import "./styles/main.less";

import "@babel/polyfill";

import ReactDOM from "react-dom";

import AppRouter from "./AppRouter";

ReactDOM.render(AppRouter(), document.getElementById("content"));
