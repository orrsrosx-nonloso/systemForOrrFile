import $ from "./ajax.js"
/**
 * 文件操作
 */
 class FileService {
    constructor() {
    }
    /**
     * 获取初始文件树列表以及展开功能
     */
    public static getFileList(url: string) {

        if (url === null) {
            return $.ajax({
                url: '/getListFile'
            });
        }
        else {
            return $.ajax({
                url: '/getListFile?path=' + url
            });
        }
    }

    //编辑文件
    public static getFileEditContent(url: string) {
        return $.ajax({
            url: '/editFile?path=' + url
        });
    }

    //删除文件
    public static deleteFile(url: string) {
        return $.ajax({
            url: '/deleteFile?path=' + url
        });
    }

    //保存
    public static preserveFile(url: string, content: string) {
        return $.ajax({
            url: '/preserve?path=' + url,
            method: 'POST',
            data: JSON.stringify({
                'content': content
            })
        });
    }

    //创建
    public static createFile(create_path: string, content_push: string) {
        return $.ajax({
            url: '/create?path=' + create_path,
            method: 'POST',
            data: JSON.stringify({
                'content': content_push
            })
        });
    }


}
export default FileService;