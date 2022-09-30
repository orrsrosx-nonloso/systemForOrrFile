var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
define(["require", "exports", "./ajax.js"], function (require, exports, ajax_js_1) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    ajax_js_1 = __importDefault(ajax_js_1);
    class FileService {
        constructor() {
        }
        static getFileList(url) {
            if (url === null) {
                return ajax_js_1.default.ajax({
                    url: '/getListFile'
                });
            }
            else {
                return ajax_js_1.default.ajax({
                    url: '/getListFile?path=' + url
                });
            }
        }
        static getFileEditContent(url) {
            return ajax_js_1.default.ajax({
                url: '/editFile?path=' + url
            });
        }
        static deleteFile(url) {
            return ajax_js_1.default.ajax({
                url: '/deleteFile?path=' + url
            });
        }
        static preserveFile(url, content) {
            return ajax_js_1.default.ajax({
                url: '/preserve?path=' + url,
                method: 'POST',
                data: JSON.stringify({
                    'content': content
                })
            });
        }
        static createFile(create_path, content_push) {
            return ajax_js_1.default.ajax({
                url: '/create?path=' + create_path,
                method: 'POST',
                data: JSON.stringify({
                    'content': content_push
                })
            });
        }
    }
    exports.default = FileService;
});
