function updateFileName() {
    const fileInput = document.getElementById('file');
    const fileNameDiv = document.getElementById('file-name');
    const files = fileInput.files;

    if (files.length > 0) {
        const fileNames = Array.from(files).map(file => file.name).join(', ');
        fileNameDiv.textContent = `선택된 파일: ${fileNames}`;
    } else {
        fileNameDiv.textContent = '선택된 파일이 없습니다.';
    }
}
