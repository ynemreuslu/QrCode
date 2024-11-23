# 📱 QR Code Generator & Scanner  

An advanced QR code application built with **Jetpack Compose** and following the **MVVM architecture**. Generate, scan, and store QR codes with a clean and modern user experience.  

---

## 🛠 Features  

- **QR Code Generation**  
  - Generate QR codes for any text input using **ZXing**.  
  - Save generated QR codes to the local database.  

- **QR Code Scanning**  
  - Scan QR codes in real-time using **ML Kit**.  
  - Extract data from scanned QR codes and save them for future use.  

- **Local Storage**  
  - View and manage a history of saved QR codes stored in a **Room database**.  

- **Modern Development Practices**  
  - Fully developed with **Jetpack Compose** for a modern UI.  
  - Built using the **MVVM architecture** for scalability and maintainability.  
  - Uses **Hilt** for dependency injection.  

- **Bottom Navigation**  
  - Navigate seamlessly between **Scanner**, **History Content**, **Creator Content**, and **Creator** screens using a bottom navigation bar.  

---

## 🧑‍💻 Technologies  

| **Technology**         | **Purpose**                                      |  
|------------------------|--------------------------------------------------|  
| **Jetpack Compose**     | For building a declarative and modern UI.       |  
| **MVVM Architecture**   | For separating concerns and ensuring testability.|  
| **ZXing Library**       | For generating QR codes.                        |  
| **ML Kit**              | For scanning QR codes.                          |  
| **Room Database**       | For local storage of QR codes.                  |  
| **Hilt**                | For dependency injection and modularity.        |  
| **Navigation Component**| For seamless screen navigation with Bottom Navigation. |  
| **Parcelable**          | For data transfer between screens.              |  

---

## 📂 Project Structure  

This app is structured using the **MVVM architecture** to ensure clean code practices:  

1. **Model**:  
   - Represents the data layer with Room database entities and DAOs.  

2. **ViewModel**:  
   - Acts as the middle layer handling business logic and state management.  

3. **View**:  
   - Composable functions reactively display data from the ViewModel.  

---

## 📱 Screens  

### 1️⃣ **Scanner Screen**  
- Scan QR codes in real-time using your device's camera.  
- Extract data and save scanned QR codes to the database.  

### 2️⃣ **History Content**  
- View a list of saved QR codes stored in the Room database.  
- Delete or view detailed information about any saved code.  

### 3️⃣ **Creator Content**  
- Input text and generate a QR code using **ZXing**.  
- Save the generated QR codes to the local database.  

### 4️⃣ **Creator Screen**  
- Create new QR codes by entering text and generating a code.  
- Save the generated QR codes to your local storage.  

---

## 📸 Screenshots  

<div style="display: flex; justify-content: space-around;">
    <img src="https://github.com/ynemreuslu/QrCode/blob/fb326125b472fe5e9b0422c92c3e7e6690adacc9/app/src/main/assets/images/scanner.png" alt="Scanner Screen" width="200"/>
    <img src="https://github.com/ynemreuslu/QrCode/blob/fb326125b472fe5e9b0422c92c3e7e6690adacc9/app/src/main/assets/images/history_content.png" alt="History Content Screen" width="200"/>
    <img src="https://github.com/ynemreuslu/QrCode/blob/fb326125b472fe5e9b0422c92c3e7e6690adacc9/app/src/main/assets/images/creator_content.png" alt="Creator Content Screen" width="200"/>
    <img src="https://github.com/ynemreuslu/QrCode/blob/fb326125b472fe5e9b0422c92c3e7e6690adacc9/app/src/main/assets/images/creator.png" alt="Creator Screen" width="200"/>
</div>

---

## 🚀 Getting Started  

Follow these steps to set up and run the project on your local machine:  

 **Clone the repository**:  
   git clone https://github.com/ynemreuslu/QrCode.git  
---
## 💬 Contact  
Have questions or feedback? Feel free to open an issue or contact me:  

📧 Email: [ynemreuslu@gmail.com](mailto:ynemreuslu@gmail.com)  
🌐 GitHub: [https://github.com/ynemreuslu](https://github.com/ynemreuslu)  
