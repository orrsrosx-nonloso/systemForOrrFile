import FileService from "./request.js"

let filename: string = null;
export let filepath: string = null;
export class FileController {

    static filetype = new Array("ada", "ads", "as", "ascx", "asp", "bat", "c", "cmd", "conf", "cpp", "cpt", "css", "csv",
        "gitignore", "go", "groovy", "h", "htm", "html", "info", "java", "js", "json", "jsp", "less", "log", "markdown", "md", "mk",
        "pg", "php", "properties", "py", "scss", "sql", "text", "textile", "ts", "txt", "vue", "webapp", "xml", "yaml", "yml")
    //功能按钮管理
    static text: HTMLElement = document.getElementById("textBox");
    static buttonResponse: HTMLElement = document.querySelector("#buttonResponse");
    static d2: HTMLElement = document.querySelector("#d2");
    static contentEnd = (document.querySelector("#contentEnd") as HTMLInputElement);
    static deleteResponses: HTMLElement = document.querySelector("#deleteResponses");
    static contentDelete = (document.querySelector("#contentDelete") as HTMLInputElement);
    constructor() { 
        
    }





    public editInput(element: any) {
        filepath = element.dataset.pathtwo;
        filename = element.dataset.filename;
    }
    /**
     * 点击文件时进行的操作
     */
    public fileOperations(element: any) {
        //图片和文本编辑框
        let textBox: HTMLElement = document.getElementById("textBox");
        let editImg: HTMLElement = document.getElementById("editImg");
        let childImg: any = (document.getElementById("childImg") as HTMLInputElement);
        let preserve: HTMLElement = document.getElementById("preserve");
        let download: HTMLElement = document.getElementById("download");
        let deleteSelectFile: HTMLElement = document.getElementById("deleteSelectFile");
        this.editInput(element);
        let url = filepath;
        FileService.getFileEditContent(url).then(data => {
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
                    textBox.setAttribute("contenteditable", "false")
                    preserve.style.display = "inline-block";
                    deleteSelectFile.style.display = "inline-block";
                    download.style.display = "inline-block";
                }
            } else {
                preserve.style.display = "inline-block";
                deleteSelectFile.style.display = "inline-block";
                download.style.display = "inline-block";
                if (filename.indexOf("png") != -1 || filename.indexOf("jpg") != -1 || filename.indexOf("gif") != -1) {
                    childImg.src = data.fileContent;
                    editImg.style.display = "block";
                    textBox.style.display = "none";
                    preserve.style.display = "none";
                } else {
                    editImg.style.display = "none";
                    textBox.dataset.path = filepath;
                    textBox.dataset.fname = filename;
                    textBox.innerText = data.fileContent;
                    textBox.setAttribute("contenteditable", "true")
                    textBox.style.display = "block";
                    preserve.style.display = "inline-block";

                }
            }
        });



    }



    /**
     * 文件删除
     */
    public deleteFile() {

        if (filename === null) {
            FileController.contentDelete.placeholder = "未选中文件";
        } else {
            FileController.contentDelete.placeholder = "确定删除文件？";
        }
        FileController.deleteResponses.style.display = "block";
        FileController.d2.style.display = "block";
    }


    /*
     *下载
     */
    public download() {
        if (filename == null) {
            FileController.contentEnd.placeholder = "未选中文件";
            FileController.buttonResponse.style.display = "block";
            FileController.d2.style.display = "block";
        } else {
            let url = filepath;
            let fName = filename;
            var aEle = document.createElement("a"); // 创建a标签
            aEle.download = fName; // 设置下载文件的文件名
            aEle.href = '/downloadFile?path=' + url; // 后台返回的下载地址
            aEle.click();
            aEle.remove();
        }
    }

    //文件创建的按钮
    public createFileButton() {
        let a: HTMLElement = document.getElementById("d1");
        let b: HTMLElement = document.getElementById("d2");
        let createFile: HTMLElement = document.getElementById("createFile");
        createFile.style.display = "block";
        a.style.display = "block";
        b.style.display = "block";
    }

    //文件创建的按钮
    public createDirButton() {
        let createDir: HTMLElement = document.getElementById("createDir");
        let a: HTMLElement = document.getElementById("d1");
        let b: HTMLElement = document.getElementById("d2");
        createDir.style.display = "block";
        a.style.display = "block";
        b.style.display = "block";
    }

    //操作确认按钮 创建成功 未选中文件 创建成功
    public commit() {
        FileController.buttonResponse.style.display = "none";
        FileController.d2.style.display = "none";
        if (FileController.contentEnd.placeholder === "文件删除成功"
            || FileController.contentEnd.placeholder === "创建成功"
            || FileController.contentEnd.placeholder === "文件不存在！"
            || FileController.contentEnd.placeholder === "文件夹不存在！"
            || FileController.contentEnd.placeholder === "创建成功(不带后缀默认txt)"
        ) {

            // let treeFile: HTMLElement = document.getElementById("fileTree");
            // treeFile.remove();
            // let data: DirCatalog = await FileService.getFileList(null);
            // console.log(data);
            // TreeNode.createFileAndDir(data);
            location.replace(location.href);
        }
    }

    //删除文件确认功能
    public commitDelete() {

        let url = filepath;
        FileService.deleteFile(url).then(data => {
            if (data === true) {
                FileController.contentEnd.placeholder = "文件删除成功";
            } else {
                FileController.contentEnd.placeholder = "文件删除失败";
            }
        });


        FileController.buttonResponse.style.display = "block";
        FileController.deleteResponses.style.display = "none";
    }

    //删除文件确认功能
    public close() {
        FileController.deleteResponses.style.display = "none";
        FileController.d2.style.display = "none";
    }

    //文件创建
    public createFile() {

        let element: HTMLElement = document.getElementById("createFile");
        let createPath = null;
        let content = (element.parentElement.previousElementSibling as HTMLInputElement);
        createPath = filepath;
        if (content.value != "") {

            if (FileController.canCreate(content.value) != false) {
                let contentPush = "file:" + content.value;
                FileService.createFile(createPath, contentPush).then(data => {
                    if (data.fileCanEdit === true) {
                        FileController.contentEnd.placeholder = "创建成功";
                    } else {
                        FileController.contentEnd.placeholder = "文件已存在";
                    }
                });
            }
            else {
                let contentPush = "file:" + content.value + ".txt";
                FileService.createFile(createPath, contentPush).then(data => {
                    if (data.fileCanEdit === true) {
                        FileController.contentEnd.placeholder = "创建成功(不带后缀默认txt)";
                    } else {
                        FileController.contentEnd.placeholder = "文件已存在";
                    }
                });
            }
            let a: HTMLElement = document.getElementById("d1");
            element.style.display = "none";
            a.style.display = "none";
            content.value = null;
            FileController.buttonResponse.style.display = "block";
        }
        else {
            alert("请输入内容！");
        }
    }

    public static canCreate(value: string): boolean {
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

    //文件夹创建
    public createDir() {
        let element: HTMLElement = document.getElementById("createDir");
        let createPath = null;
        let content = (element.parentElement.previousElementSibling as HTMLInputElement);
        createPath = filepath;
        if (content.value != "") {
            let contentPush = "dir:" + content.value;
            FileService.createFile(createPath, contentPush).then(data => {
                if (data.fileCanEdit == true) {
                    FileController.contentEnd.placeholder = "创建成功";
                } else {
                    FileController.contentEnd.placeholder = "文件已存在";
                }
            });


            let a: HTMLElement = document.getElementById("d1");
            element.style.display = "none";
            a.style.display = "none";
            content.value = null;
            FileController.buttonResponse.style.display = "block";
        }
        else {
            alert("请输入内容！");
        }
    }

    //文件保存操作
    public preserve() {
        if (filename == null) {
            FileController.contentEnd.placeholder = "未选中文件";
        } else {
            if (FileController.text.innerText != "无法编辑的文件!!") {
                let content = FileController.text.innerText;
                let url = filepath;
                FileService.preserveFile(url, content).then(data => {
                    if (data.fileCanEdit = true) {
                        FileController.contentEnd.placeholder = "保存成功";
                    } else {
                        FileController.contentEnd.placeholder = "保存失败";
                    }
                });

            }
            else {
                FileController.contentEnd.placeholder = "文件无法编辑！!";
            }
        }
        let d2: HTMLElement = document.getElementById("d2");
        FileController.buttonResponse.style.display = "block";
        d2.style.display = "block";

    }

}