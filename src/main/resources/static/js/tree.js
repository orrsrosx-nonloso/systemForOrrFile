define(["require", "exports"], function (require, exports) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    class Tree {
        static createFileAndDir(data, left, treeToggle, operations) {
            let createHtml = document.createElement('ul');
            createHtml.setAttribute("id", "fileTree");
            for (let dirVo of data.directories) {
                Tree.createDirCatalog(data, dirVo, createHtml, treeToggle);
            }
            for (let fileInfo of data.fileList) {
                Tree.createFileCatalog(data, fileInfo, createHtml, operations);
            }
            left.appendChild(createHtml);
        }
        static createDirCatalog(data, dirVo, createHtml, treeToggle) {
            let dirLi = document.createElement('li');
            let dirSpanOne = document.createElement('div');
            let dirSpanI = document.createElement('i');
            let dirSpanTwo = document.createElement('span');
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
            let brotherUl = document.createElement('ul');
            brotherUl.setAttribute('style', 'display:none');
            dirLi.appendChild(brotherUl);
            createHtml.appendChild(dirLi);
        }
        static createFileCatalog(data, fileInfo, createHtml, operations) {
            let fileLi = document.createElement('li');
            let fileSpanOne = document.createElement('div');
            let fileSpanTwo = document.createElement('span');
            let dirSpanI = document.createElement('i');
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
        static spreadDom(data, element, treeToggle, operations) {
            let child = element.parentElement.nextElementSibling;
            if (child.style.display === 'none') {
                if (child.children.length === 0) {
                    if (data.directories.length === 0 && data.fileList.length === 0) {
                        element.dataset.full = "1";
                    }
                    let createHtml = document.createElement('ul');
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
                    }
                    else {
                        element.previousElementSibling.setAttribute("class", "fa fa-folder-open");
                    }
                }
                else {
                    child.style.display = 'block';
                    element.previousElementSibling.setAttribute("class", "fa fa-folder-open");
                }
            }
            else {
                element.previousElementSibling.setAttribute("class", "fa fa-folder");
                child.style.display = 'none';
            }
        }
    }
    exports.default = Tree;
});
