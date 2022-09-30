 class Ajax {
    constructor(){}

    static ajax = ({ url, method = 'GET', isJson = true, data }:
    { url: string, method?: string, isJson?: boolean, data?: any }) =>
    new Promise<any>((resolve, reject) => {
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
}

export default Ajax;