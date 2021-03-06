export default function copyToClipboard(text) {
    // if (text.indexOf('-') !== -1) {
    //     const arr = text.split('-');
    //     text = arr[0] + arr[1];
    // }
    const textArea = document.createElement('textarea');
    textArea.style.position = 'fixed';
    textArea.style.top = '0';
    textArea.style.left = '0';
    textArea.style.width = '2em';
    textArea.style.height = '2em';
    textArea.style.padding = '0';
    textArea.style.border = 'none';
    textArea.style.outline = 'none';
    textArea.style.boxShadow = 'none';
    textArea.style.background = 'transparent';
    textArea.value = text;
    document.body.appendChild(textArea);
    textArea.select();
    let successful = false;
    try {
        successful = document.execCommand('copy');
    } catch (err) {
        successful = true;
    }
    document.body.removeChild(textArea);
    return successful;
}
