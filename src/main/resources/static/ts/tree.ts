/**
 * 文件夹目录信息
 */
interface DirCatalog {
    dirName: number;
    dirPath: string;
    directories: Array<DirCatalog>;
    fileList: Array<FileCatalog>;
    lastModifiedTime: string;
    existence: boolean;

}


/**
 * 文件目录信息
 */
interface FileCatalog {
    lastModifiedTime: string;
    fileSize: number;
    fileContent: string;
    resMessage: string;
    data: string;
    canEdit: boolean;
}


class Tree {

    public static createFileAndDir(data: any, left: HTMLElement, treeToggle: any, operations: any) {
        let createHtml: HTMLElement = document.createElement('ul');
        createHtml.setAttribute("id", "fileTree");
        //文件夹目录创建
        for (let dirVo of data.directories) {
            Tree.createDirCatalog(data, dirVo, createHtml, treeToggle);
        }

        //文件目录创建
        for (let fileInfo of data.fileList) {
            Tree.createFileCatalog(data, fileInfo, createHtml, operations);
        }
        left.appendChild(createHtml);
    }
    //文件夹树创建
    public static createDirCatalog(data: DirCatalog, dirVo: any, createHtml: HTMLElement, treeToggle: any) {
        let dirLi: HTMLElement = document.createElement('li');
        let dirSpanOne: HTMLElement = document.createElement('div');
        let dirSpanI: HTMLElement = document.createElement('i');
        let dirSpanTwo: HTMLElement = document.createElement('span');
        dirSpanI.setAttribute('class', 'fa fa-folder');
        dirSpanI.setAttribute('style', 'margin:  0 2.5px 0 2.5px;');
        dirSpanOne.setAttribute('style', 'margin: 2.5px 0 2.5px 0;display:-moz-inline-box;display:inline-block;height:21px;white-space:nowrap;overflow:hidden;width:300px;');
        dirSpanTwo.setAttribute('class', 'icon');
        dirSpanTwo.addEventListener("click", treeToggle);
        dirSpanTwo.setAttribute('data-dir', data.dirPath + '/' + dirVo.dirName);
        dirSpanTwo.setAttribute('data-dirname', dirVo.dirName);
        dirSpanTwo.innerText = dirVo.dirName;
        dirLi.appendChild(dirSpanOne);
        dirSpanOne.appendChild(dirSpanI);
        dirSpanOne.appendChild(dirSpanTwo);
        let brotherUl: HTMLElement = document.createElement('ul');
        brotherUl.setAttribute('style', 'display:none')
        dirLi.appendChild(brotherUl);
        createHtml.appendChild(dirLi);
    }

    //文件树创建
    public static createFileCatalog(data: DirCatalog, fileInfo: any, createHtml: HTMLElement, operations: any) {
        let fileLi: HTMLElement = document.createElement('li');
        let fileSpanOne: HTMLElement = document.createElement('div');
        let fileSpanTwo: HTMLElement = document.createElement('span');
        let dirSpanI: HTMLElement = document.createElement('i');
        fileSpanOne.setAttribute('style', 'margin: 2.5px 0 2.5px 0;display:-moz-inline-box;display:inline-block;height:21px;white-space:nowrap;overflow:hidden;width:300px;');
        dirSpanI.setAttribute('class', 'fa fa-file-o');
        dirSpanI.setAttribute('style', 'margin:  0 2.5px 0 2.5px;');
        fileSpanTwo.setAttribute('data-filename', fileInfo.fileName);
        fileSpanTwo.addEventListener("click", operations);
        fileSpanTwo.setAttribute('data-pathTwo', data.dirPath + '/' + fileInfo.fileName);
        fileSpanTwo.setAttribute('class', 'icon');
        fileSpanTwo.innerText = fileInfo.fileName;
        fileLi.appendChild(fileSpanOne);
        fileSpanOne.appendChild(dirSpanI);
        fileSpanOne.appendChild(fileSpanTwo);
        createHtml.appendChild(fileLi);
    }





    //展开功能
    public static spreadDom(data: any, element: any, treeToggle: any, operations: any): void {
        let child: HTMLElement = element.parentElement.nextElementSibling; /*只返回元素节点之后的兄弟元素节点*/

        if (child.style.display === 'none') {
            if (child.children.length === 0) {
                if (data.directories.length === 0 && data.fileList.length === 0) {
                    element.dataset.full = "1";
                }
                let createHtml: HTMLElement = document.createElement('ul');
                createHtml.setAttribute("id", "fileTree");
                for (let dirVo of data.directories) {
                    Tree.createDirCatalog(data, dirVo, createHtml, treeToggle);

                }
                for (let fileVo of data.fileList) {
                    Tree.createFileCatalog(data, fileVo, createHtml, operations);
                }
                child.appendChild(createHtml);


            }
            if (data.directories.length === 0 && data.fileList.length === 0) {
                if (element.previousElementSibling.getAttribute("class").indexOf("fa-folder-open") != -1) {
                    element.previousElementSibling.setAttribute("class", "fa fa-folder");
                } else {
                    element.previousElementSibling.setAttribute("class", "fa fa-folder-open");

                }
            } else {
                child.style.display = 'block';
                element.previousElementSibling.setAttribute("class", "fa fa-folder-open");
            }
        } else {
            element.previousElementSibling.setAttribute("class", "fa fa-folder");
            child.style.display = 'none';
        }
    }




}

export default Tree;