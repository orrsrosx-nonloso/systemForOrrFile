var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
define(["require", "exports", "./request.js"], function (require, exports, request_js_1) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    exports.FileController = exports.filepath = void 0;
    request_js_1 = __importDefault(request_js_1);
    let filename = null;
    exports.filepath = null;
    class FileController {
        constructor() { }
        editInput(element) {
            exports.filepath = element.dataset.pathtwo;
            filename = element.dataset.filename;
        }
        fileOperations(element) {
            let textBox = document.getElementById("textBox");
            let editImg = document.getElementById("editImg");
            let childImg = document.getElementById("childImg");
            let preserve = document.getElementById("preserve");
            let download = document.getElementById("download");
            let deleteSelectFile = document.getElementById("deleteSelectFile");
            this.editInput(element);
            let url = exports.filepath;
            request_js_1.default.getFileEditContent(url).then(data => {
                if (data.fileCanEdit === false) {
                    if (data.fileContent === "文件不存在！") {
                        FileController.contentEnd.placeholder = "文件不存在！";
                        FileController.buttonResponse.style.display = "block";
                        FileController.d2.style.display = "block";
                        preserve.style.display = "none";
                        deleteSelectFile.style.display = "none";
                        download.style.display = "none";
                    }
                    else {
                        editImg.style.display = "none";
                        textBox.innerText = "无法编辑的文件!!";
                        textBox.style.display = "block";
                        textBox.setAttribute("contenteditable", "false");
                        preserve.style.display = "inline-block";
                        deleteSelectFile.style.display = "inline-block";
                        download.style.display = "inline-block";
                    }
                }
                else {
                    preserve.style.display = "inline-block";
                    deleteSelectFile.style.display = "inline-block";
                    download.style.display = "inline-block";
                    if (filename.indexOf("png") != -1 || filename.indexOf("jpg") != -1 || filename.indexOf("gif") != -1) {
                        childImg.src = data.fileContent;
                        editImg.style.display = "block";
                        textBox.style.display = "none";
                        preserve.style.display = "none";
                    }
                    else {
                        editImg.style.display = "none";
                        textBox.dataset.path = exports.filepath;
                        textBox.dataset.fname = filename;
                        textBox.innerText = data.fileContent;
                        textBox.setAttribute("contenteditable", "true");
                        textBox.style.display = "block";
                        preserve.style.display = "inline-block";
                    }
                }
            });
        }
        deleteFile() {
            if (filename === null) {
                FileController.contentDelete.placeholder = "未选中文件";
            }
            else {
                FileController.contentDelete.placeholder = "确定删除文件？";
            }
            FileController.deleteResponses.style.display = "block";
            FileController.d2.style.display = "block";
        }
        download() {
            if (filename == null) {
                FileController.contentEnd.placeholder = "未选中文件";
                FileController.buttonResponse.style.display = "block";
                FileController.d2.style.display = "block";
            }
            else {
                let url = exports.filepath;
                let fName = filename;
                var aEle = document.createElement("a");
                aEle.download = fName;
                aEle.href = '/downloadFile?path=' + url;
                aEle.click();
                aEle.remove();
            }
        }
        createFileButton() {
            let a = document.getElementById("d1");
            let b = document.getElementById("d2");
            let createFile = document.getElementById("createFile");
            createFile.style.display = "block";
            a.style.display = "block";
            b.style.display = "block";
        }
        createDirButton() {
            let createDir = document.getElementById("createDir");
            let a = document.getElementById("d1");
            let b = document.getElementById("d2");
            createDir.style.display = "block";
            a.style.display = "block";
            b.style.display = "block";
        }
        commit() {
            FileController.buttonResponse.style.display = "none";
            FileController.d2.style.display = "none";
            if (FileController.contentEnd.placeholder === "文件删除成功"
                || FileController.contentEnd.placeholder === "创建成功"
                || FileController.contentEnd.placeholder === "文件不存在！"
                || FileController.contentEnd.placeholder === "文件夹不存在！"
                || FileController.contentEnd.placeholder === "创建成功(不带后缀默认txt)") {
                location.replace(location.href);
            }
        }
        commitDelete() {
            let url = exports.filepath;
            request_js_1.default.deleteFile(url).then(data => {
                if (data === true) {
                    FileController.contentEnd.placeholder = "文件删除成功";
                }
                else {
                    FileController.contentEnd.placeholder = "文件删除失败";
                }
            });
            FileController.buttonResponse.style.display = "block";
            FileController.deleteResponses.style.display = "none";
        }
        close() {
            FileController.deleteResponses.style.display = "none";
            FileController.d2.style.display = "none";
        }
        createFile() {
            let element = document.getElementById("createFile");
            let createPath = null;
            let content = element.parentElement.previousElementSibling;
            createPath = exports.filepath;
            if (content.value != "") {
                if (FileController.canCreate(content.value) != false) {
                    let contentPush = "file:" + content.value;
                    request_js_1.default.createFile(createPath, contentPush).then(data => {
                        if (data.fileCanEdit === true) {
                            FileController.contentEnd.placeholder = "创建成功";
                        }
                        else {
                            FileController.contentEnd.placeholder = "文件已存在";
                        }
                    });
                }
                else {
                    let contentPush = "file:" + content.value + ".txt";
                    request_js_1.default.createFile(createPath, contentPush).then(data => {
                        if (data.fileCanEdit === true) {
                            FileController.contentEnd.placeholder = "创建成功(不带后缀默认txt)";
                        }
                        else {
                            FileController.contentEnd.placeholder = "文件已存在";
                        }
                    });
                }
                let a = document.getElementById("d1");
                element.style.display = "none";
                a.style.display = "none";
                content.value = null;
                FileController.buttonResponse.style.display = "block";
            }
            else {
                alert("请输入内容！");
            }
        }
        static canCreate(value) {
            if (value.indexOf(".") != -1) {
                value = value.split(".")[1];
                if (FileController.filetype.indexOf(value) > -1) {
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        createDir() {
            let element = document.getElementById("createDir");
            let createPath = null;
            let content = element.parentElement.previousElementSibling;
            createPath = exports.filepath;
            if (content.value != "") {
                let contentPush = "dir:" + content.value;
                request_js_1.default.createFile(createPath, contentPush).then(data => {
                    if (data.fileCanEdit == true) {
                        FileController.contentEnd.placeholder = "创建成功";
                    }
                    else {
                        FileController.contentEnd.placeholder = "文件已存在";
                    }
                });
                let a = document.getElementById("d1");
                element.style.display = "none";
                a.style.display = "none";
                content.value = null;
                FileController.buttonResponse.style.display = "block";
            }
            else {
                alert("请输入内容！");
            }
        }
        preserve() {
            if (filename == null) {
                FileController.contentEnd.placeholder = "未选中文件";
            }
            else {
                if (FileController.text.innerText != "无法编辑的文件!!") {
                    let content = FileController.text.innerText;
                    let url = exports.filepath;
                    request_js_1.default.preserveFile(url, content).then(data => {
                        if (data.fileCanEdit = true) {
                            FileController.contentEnd.placeholder = "保存成功";
                        }
                        else {
                            FileController.contentEnd.placeholder = "保存失败";
                        }
                    });
                }
                else {
                    FileController.contentEnd.placeholder = "文件无法编辑！!";
                }
            }
            let d2 = document.getElementById("d2");
            FileController.buttonResponse.style.display = "block";
            d2.style.display = "block";
        }
    }
    exports.FileController = FileController;
    FileController.filetype = new Array("ada", "ads", "as", "ascx", "asp", "bat", "c", "cmd", "conf", "cpp", "cpt", "css", "csv", "gitignore", "go", "groovy", "h", "htm", "html", "info", "java", "js", "json", "jsp", "less", "log", "markdown", "md", "mk", "pg", "php", "properties", "py", "scss", "sql", "text", "textile", "ts", "txt", "vue", "webapp", "xml", "yaml", "yml");
    FileController.text = document.getElementById("textBox");
    FileController.buttonResponse = document.querySelector("#buttonResponse");
    FileController.d2 = document.querySelector("#d2");
    FileController.contentEnd = document.querySelector("#contentEnd");
    FileController.deleteResponses = document.querySelector("#deleteResponses");
    FileController.contentDelete = document.querySelector("#contentDelete");
});
