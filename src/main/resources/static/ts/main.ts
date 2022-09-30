import FileService from "./request.js"
import Tree from "./tree.js"
import { FileController, filepath } from "./controller.js"

/**
 * 文件名
 * 文件地址
 * 文件高亮效果
 */


/**
 * 创建树结构函数
 */
class TreeNode extends Tree {

    highLight: HTMLElement = null;

    // 左树基本结构
    left: HTMLElement = document.getElementById('item-catalog');

    //删除功能
    deleteSelectFile: HTMLElement = document.querySelector("#deleteSelectFile");

    //文件下载
    download: HTMLElement = document.querySelector("#download");

    //创建文件按钮
    fileButton: HTMLElement = document.querySelector("#fileButton");
    dirButton: HTMLElement = document.querySelector("#dirButton");

    //文件保存按钮
    filePreserve: HTMLElement = document.querySelector("#preserve");

    //文件创建操作
    createFile: HTMLElement = document.querySelector("#createFile");

    //文件夹创建操作
    createDir: HTMLElement = document.querySelector("#createDir");

    //操作完成按钮
    commit: HTMLElement = document.querySelector("#commit");

    //操作完成按钮
    commitDelete: HTMLElement = document.querySelector("#commitDelete");

    //操作完成按钮
    close: HTMLElement = document.querySelector("#close");

    constructor() {
        super();
        let fileController = new FileController();
        //添加各个按钮功能
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

    //初始化文件树
    public createTree() {
        if (filepath === null) {
            this.download.setAttribute("style", "background-color: #CCCCCC;pointer-events: none;opacity:0.6;");
            this.filePreserve.setAttribute("style", "background-color: #CCCCCC;pointer-events: none;opacity:0.6;");
            this.deleteSelectFile.setAttribute("style", "background-color: #CCCCCC;pointer-events: none;opacity:0.6;");
        }
        FileService.getFileList(null).then(data => {
            let treeToggle: any = this.treeToggle.bind(this);
            console.log(treeToggle);
            Tree.createFileAndDir(data, this.left, treeToggle, this.operations.bind(this));
        });
    }


    /**
    * 展开收起
    */
    public treeToggle(element: any) {
        element = element.path[0];

        if (this.highLight != null) {
            this.highLight.style.backgroundColor = "";
        }
        this.highLight = element.parentElement;

        this.highLight.style.backgroundColor = "#56b5f5";
        let url = element.dataset.dir;
        FileService.getFileList(url).then(data => {
            if (data.existence === false) {
                FileController.contentEnd.placeholder = "文件夹不存在！";
                FileController.buttonResponse.style.display = "block";
                FileController.d2.style.display = "block";
            }
            else {
                this.filePreserve.setAttribute("style", "background-color: #96b5f7;display: inline-block;");
                this.deleteSelectFile.setAttribute("style", "background-color: #96b5f7;display: inline-block;");
                let textBox: HTMLElement = document.getElementById("textBox");
                let download: HTMLElement = document.getElementById("download");
                download.style.display = "none";
                textBox.style.display = "none";
                Tree.spreadDom(data, element, this.treeToggle.bind(this), this.operations.bind(this));
            }
        });


    }

    //操作文件
    public operations(element: any) {
        element = element.path[0];
        if (this.highLight != null) {
            this.highLight.style.backgroundColor = "";
        }
        this.highLight = element.parentElement;

        this.highLight.style.backgroundColor = "#56b5f5";
        let download: HTMLElement = document.getElementById("download");
        let deleteSelectFile: HTMLElement = document.querySelector("#deleteSelectFile");
        let filePreserve: HTMLElement = document.querySelector("#preserve");
        filePreserve.setAttribute("style", "background-color: #96b5f7;display: inline-block;");
        deleteSelectFile.setAttribute("style", "background-color: #96b5f7;display: inline-block;");
        download.setAttribute("style", "background-color: #96b5f7;display: inline-block;");

        new FileController().fileOperations(element);
    }

}


new TreeNode().createTree();
