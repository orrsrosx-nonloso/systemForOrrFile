define(["require", "exports"], function (require, exports) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    class Ajax {
        constructor() { }
    }
    Ajax.ajax = ({ url, method = 'GET', isJson = true, data }) => new Promise((resolve, reject) => {
        let xmlHttpRequest = new XMLHttpRequest();
        xmlHttpRequest.open(method, url);
        xmlHttpRequest.onreadystatechange = () => {
            if (xmlHttpRequest.readyState === 4) {
                let result = isJson ? JSON.parse(xmlHttpRequest.responseText) : xmlHttpRequest.responseText;
                resolve(result);
            }
        };
        xmlHttpRequest.onerror = err => reject(err);
        xmlHttpRequest.send(data);
    });
    exports.default = Ajax;
});
