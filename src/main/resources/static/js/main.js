var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
define(["require", "exports", "./request.js", "./tree.js", "./controller.js"], function (require, exports, request_js_1, tree_js_1, controller_js_1) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    request_js_1 = __importDefault(request_js_1);
    tree_js_1 = __importDefault(tree_js_1);
    class TreeNode extends tree_js_1.default {
        constructor() {
            super();
            this.highLight = null;
            this.left = document.getElementById('item-catalog');
            this.deleteSelectFile = document.querySelector("#deleteSelectFile");
            this.download = document.querySelector("#download");
            this.fileButton = document.querySelector("#fileButton");
            this.dirButton = document.querySelector("#dirButton");
            this.filePreserve = document.querySelector("#preserve");
            this.createFile = document.querySelector("#createFile");
            this.createDir = document.querySelector("#createDir");
            this.commit = document.querySelector("#commit");
            this.commitDelete = document.querySelector("#commitDelete");
            this.close = document.querySelector("#close");
            let fileController = new controller_js_1.FileController();
            this.deleteSelectFile.addEventListener('click', fileController.deleteFile.bind(this));
            this.download.addEventListener('click', fileController.download.bind(this));
            this.fileButton.addEventListener('click', fileController.createFileButton.bind(this));
            this.dirButton.addEventListener('click', fileController.createDirButton.bind(this));
            this.filePreserve.addEventListener('click', fileController.preserve.bind(this));
            this.createFile.addEventListener('click', fileController.createFile.bind(this));
            this.createDir.addEventListener('click', fileController.createDir.bind(this));
            this.commit.addEventListener('click', fileController.commit.bind(this));
            this.commitDelete.addEventListener('click', fileController.commitDelete.bind(this));
            this.close.addEventListener('click', fileController.close.bind(this));
        }
        createTree() {
            if (controller_js_1.filepath === null) {
                this.download.setAttribute("style", "background-color: #CCCCCC;pointer-events: none;opacity:0.6;");
                this.filePreserve.setAttribute("style", "background-color: #CCCCCC;pointer-events: none;opacity:0.6;");
                this.deleteSelectFile.setAttribute("style", "background-color: #CCCCCC;pointer-events: none;opacity:0.6;");
            }
            request_js_1.default.getFileList(null).then(data => {
                let treeToggle = this.treeToggle.bind(this);
                console.log(treeToggle);
                tree_js_1.default.createFileAndDir(data, this.left, treeToggle, this.operations.bind(this));
            });
        }
        treeToggle(element) {
            element = element.path[0];
            if (this.highLight != null) {
                this.highLight.style.backgroundColor = "";
            }
            this.highLight = element.parentElement;
            this.highLight.style.backgroundColor = "#56b5f5";
            let url = element.dataset.dir;
            request_js_1.default.getFileList(url).then(data => {
                if (data.existence === false) {
                    controller_js_1.FileController.contentEnd.placeholder = "文件夹不存在！";
                    controller_js_1.FileController.buttonResponse.style.display = "block";
                    controller_js_1.FileController.d2.style.display = "block";
                }
                else {
                    this.filePreserve.setAttribute("style", "background-color: #96b5f7;display: inline-block;");
                    this.deleteSelectFile.setAttribute("style", "background-color: #96b5f7;display: inline-block;");
                    let textBox = document.getElementById("textBox");
                    let download = document.getElementById("download");
                    download.style.display = "none";
                    textBox.style.display = "none";
                    tree_js_1.default.spreadDom(data, element, this.treeToggle.bind(this), this.operations.bind(this));
                }
            });
        }
        operations(element) {
            element = element.path[0];
            if (this.highLight != null) {
                this.highLight.style.backgroundColor = "";
            }
            this.highLight = element.parentElement;
            this.highLight.style.backgroundColor = "#56b5f5";
            let download = document.getElementById("download");
            let deleteSelectFile = document.querySelector("#deleteSelectFile");
            let filePreserve = document.querySelector("#preserve");
            filePreserve.setAttribute("style", "background-color: #96b5f7;display: inline-block;");
            deleteSelectFile.setAttribute("style", "background-color: #96b5f7;display: inline-block;");
            download.setAttribute("style", "background-color: #96b5f7;display: inline-block;");
            new controller_js_1.FileController().fileOperations(element);
        }
    }
    new TreeNode().createTree();
});
