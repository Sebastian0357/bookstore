// src/services/fileUpload.js
import axios from 'axios';

// 通用的文件上传方法
export function handleFileUpload(event, allowedTypes = ["image/jpeg", "image/png", "application/pdf"], maxSize = 10 * 1024 * 1024) {
  const file = event.target.files[0];

  // 1. 检查文件类型
  if (!allowedTypes.includes(file.type)) {
    alert("不支持的文件类型，仅支持 JPG/PNG/PDF");
    return null;
  }

  // 2. 检查文件大小
  if (file.size > maxSize) {
    alert("文件大小不能超过 10MB");
    return null;
  }

  return file; // 如果符合要求，返回文件对象
}

// 上传文件并返回 URL
export function submitFile(file) {
  return new Promise((resolve, reject) => {
    if (!file) {
      resolve(null); // 如果没有文件，直接返回 null
      return;
    }

    const formData = new FormData();
    formData.append("file", file);

    axios.post("http://localhost:1118/upload/file", formData, {
      headers: {
        Authorization: "Bearer " + localStorage.getItem("jwt_token"),
        "Content-Type": "multipart/form-data",
      }
    }).then(response => {
      resolve(response.data.url); // 上传成功后，返回文件 URL
    }).catch(error => {
      alert("上传失败：" + error.response.data);
      reject(error);
    });
  });
}
