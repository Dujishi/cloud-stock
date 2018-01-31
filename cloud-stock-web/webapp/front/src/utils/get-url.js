const globalMock = false;
const devHosts = ['localhost', '127.0.0.1'];
const mockHost = 'https://www.easy-mock.com/mock/5a056b15e264ca23e8c74b46/cloud-stock';
const isDevHost = devHosts.indexOf(location.hostname) > -1;

function getUrl(key, mock) {
    if (isDevHost) {
        if (location.href.indexOf('mockServer') > -1 || mock || globalMock) {
            return `${mockHost}/${key}`;
        }
    }
    return key;
}

export default getUrl;
