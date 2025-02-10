const modal = document.getElementById("supportModal");
const span = document.getElementsByClassName("close")[0];
const stationNameInput = document.getElementById("stationName");
const supportForm = document.getElementById("supportForm");
const photoInput = document.getElementById("photo");
const fileList = document.getElementById("fileList");
const fileLabelText = document.getElementById("fileLabelText");
const fileSelectButton = document.getElementById("fileSelectButton"); // "파일 선택" 버튼

// 선택된 파일 목록 저장
let selectedFiles = [];

// 관서 클릭 시 모달 열기
function stationClick(stationName) {
    if (stationNameInput) {
        stationNameInput.value = stationName;
    }
    modal.style.display = "block";
}

// 닫기 버튼 클릭 시 모달 닫기
span.onclick = function () {
    closeModal();
};

// 모달 닫기 함수
function closeModal() {
    modal.style.display = "none";
    supportForm.reset();
    selectedFiles = [];
    renderFileList();
    fileLabelText.textContent = "선택된 파일 없음"; // 파일 텍스트 초기화
}

// "파일 선택" 버튼 클릭 시 파일 입력 트리거
fileSelectButton.addEventListener("click", () => {
    photoInput.click(); // 숨겨진 파일 입력 요소 클릭
});

// 파일 입력 변경 시 처리
photoInput.addEventListener("change", function () {
    for (const file of photoInput.files) {
        if (!selectedFiles.some(f => f.name === file.name && f.size === file.size && f.lastModified === file.lastModified)) {
            selectedFiles.push(file);
        }
    }

    // 파일 선택 텍스트 업데이트
    if (selectedFiles.length === 0) {
        fileLabelText.textContent = "선택된 파일 없음";
    } else {
        fileLabelText.textContent = `${selectedFiles.length}개의 파일 선택됨`;
    }

    renderFileList();
    photoInput.value = ""; // 파일 입력 필드 초기화
});

// 파일 목록 렌더링
function renderFileList() {
    fileList.innerHTML = "";
    selectedFiles.forEach((file, index) => {
        const li = document.createElement("li");

        // 파일 이름 표시
        const fileNameSpan = document.createElement("span");
        fileNameSpan.className = "file-name";
        fileNameSpan.textContent = file.name;

        // 삭제 버튼 추가
        const removeButton = document.createElement("button");
        removeButton.className = "remove-file";
        removeButton.innerHTML = "&times;";
        removeButton.onclick = function () {
            removeFile(index);
        };

        li.appendChild(fileNameSpan);
        li.appendChild(removeButton);
        fileList.appendChild(li);
    });
}

// 파일 제거 함수
function removeFile(index) {
    selectedFiles.splice(index, 1);

    // 파일 선택 텍스트 업데이트
    if (selectedFiles.length === 0) {
        fileLabelText.textContent = "선택된 파일 없음";
    } else {
        fileLabelText.textContent = `${selectedFiles.length}개의 파일 선택됨`;
    }

    renderFileList();
}

// 폼 제출 처리
supportForm.addEventListener("submit", function (event) {
    event.preventDefault();

    const formData = new FormData();
    formData.append("stationName", stationNameInput.value);
    formData.append("message", document.getElementById("message").value.trim());

    // 선택된 파일 추가
    selectedFiles.forEach(file => {
        formData.append("photo", file);
    });

    // 서버로 전송
    fetch("/submitSupport", {
        method: "POST",
        body: formData,
    })
        .then(response => response.json())
        .then(data => {
            if (data.status === "success") {
                alert("지원 요청이 성공적으로 전송되었습니다!");
                closeModal();
            } else {
                alert("지원 요청 전송에 실패했습니다.");
            }
        })
        .catch(error => {
            console.error("Error:", error);
            alert("지원 요청 전송에 실패했습니다.");
        });
});
